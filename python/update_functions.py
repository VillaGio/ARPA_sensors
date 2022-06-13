from postgres_functions import *
from pickle_functions import *
from integrity_functions import *
import requests
import json
import pandas as pd   
import numpy as np 
import urllib.request
from datetime import date



def getLastCheckedTable(lastCheckedDate):
    connection,cursor = postgresConnection()
    
    print("Retrieving records from table sens_data_2022 prior to %s..." % lastCheckedDate)
    cursor.execute("SELECT * FROM sens_data_2022 WHERE dataora <= '%s'::timestamp;" % lastCheckedDate)
    postgresTable = cursor.fetchall()
    
    df = generateDf(postgresTable)
    print("Records successfully retrieved and saved.")
    
    closeConnection(connection, cursor)

    return df


def fromJsonFileToDf(filename):
    with open("../dati_sensori_1968_2020/test_update/" + filename + ".json") as fp:
        record_list = json.load(fp)
    df = pd.json_normalize(record_list)
    df.rename(columns={"data":"dataora"}, inplace = True)
    df = df.reindex(columns=["idsensore","dataora", "valore", "stato", "idoperatore"])
    df = df.astype({"idsensore": int, "valore": float, "idoperatore": int, "stato": str})
    df["dataora"] = pd.to_datetime(df["dataora"], format="%Y/%m/%d %H:%M:%S")
    return df

def fromJsonApiToDf(datetime):
    url="https://www.dati.lombardia.it/resource/nicp-bhqi.json?$limit=1300000&$where=data<='%s'" %datetime #lte = < , gte = >

    with urllib.request.urlopen(url) as response:
        record_list = json.loads(response.read().decode())

    df = pd.json_normalize(record_list)
    df.rename(columns={"data":"dataora"}, inplace = True)
    df = df.reindex(columns=["idsensore","dataora", "valore", "stato", "idoperatore"])
    df = df.astype({"idsensore": int, "valore": float, "idoperatore": int, "stato": str})
    df["dataora"] = pd.to_datetime(df["dataora"], format="%Y/%m/%d %H:%M:%S")
    with open('../dati_sensori_1968_2020/test_update/%s_data.json' %str(date.today()), 'w') as outfile: json.dump(record_list, outfile)
    return df


def getSortedJsonDf(jsonDf):
    df = jsonDf.sort_values(["dataora","idsensore"])
    df = df.reset_index(drop=True)
    return df


def compareDfs(dbDf, apiDf):
    df_update = dbDf.copy() 
    df_update["update"] = np.where(
        np.logical_and((df_update['idsensore'] == apiDf["idsensore"]), 
        (df_update["dataora"] == apiDf["dataora"]), 
        np.logical_or((df_update['valore']!= apiDf["valore"]),
        (df_update['stato']!= apiDf["stato"]))),
         True, False)
    return df_update

def compareDfsAllCols(dbDf, apiDf):
    df_update = dbDf.copy()
    df_update["idsensoreAPI"] = apiDf["idsensore"]
    df_update["dataoraAPI"] = apiDf["dataora"]
    df_update["valoreAPI"] = apiDf["valore"]
    df_update["statoAPI"] = apiDf["stato"]

    df_update["update"] = np.where(
        np.logical_and(df_update['idsensore'] == apiDf["idsensore"], 
        df_update["dataora"] == apiDf["dataora"],
        df_update['valore']!= apiDf["valore"]), True, False)
    
    return df_update

def countDifferences(df_update):
    df_update["mese"] =  df_update["dataora"].dt.month_name()
    df_update["count"] = [1]*len(df_update)
    return df_update.groupby(["mese", "update"], as_index= False)["count"].agg("count")


def updateCheck(datetime):
    print("\nStarting update check.")
    print("\n> Retrieving data from api...")
    apiDf = fromJsonApiToDf(datetime)
    apiDf = getSortedJsonDf(apiDf)
    print("> Retrieving data from database table...")
    dbDf = getSortedDbTable(2022)
    
    print("> Comparision...")
    df = pd.merge(apiDf,dbDf, on=['idsensore','dataora', 'valore', 'stato'], how='left', indicator='Exist')
    df['update'] = np.where(df.Exist == 'both', False, True)
    df = df[df["update"]==True]
    
    countDifferences(df).to_csv("../dati_sensori_1968_2020/test_update/test_{!s}.csv".format(str(date.today())))
    print("\nResults successfully saved into test_{!s}.csv".format(str(date.today())))

def weeklyUpdateCheck(lastWeekFile, thisWeekFile):
    print("\nStarting update check.")
    print("\n> Retrieving data loaded on %s..." %lastWeekFile[:-5])
    lastWeek = fromJsonFileToDf(lastWeekFile)
    lastWeek = getSortedJsonDf(lastWeek)
    print("\n> Retrieving data loaded on %s..." %thisWeekFile[:-5])
    thisWeek = fromJsonFileToDf(thisWeekFile)
    thisWeek = getSortedJsonDf(thisWeek)
    
    print("> Comparision...")
    df = pd.merge(lastWeek,thisWeek, on=['idsensore','dataora', 'valore', 'stato'], how='left', indicator='Exist')
    df['update'] = np.where(df.Exist == 'both', False, True)
    df = df[df["update"]==True]
    
    print(countDifferences(df))
    print("\nDone.")

