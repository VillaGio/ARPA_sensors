from postgres_functions import *
from pickle_functions import *
from integrity_functions import *
import requests
import json
import pandas as pd   
import numpy as np 
import urllib.request
from datetime import date
import glob

def fromJsonFileToDf(filename):
    with open("../Data/Sensors/update22_spanCheck/" + filename + ".json") as fp:
        record_list = json.load(fp)
   
    df = pd.json_normalize(record_list)
    df.rename(columns={"data":"dataora"}, inplace = True)
    df = df.reindex(columns=["idsensore","dataora", "valore", "stato", "idoperatore"])
    df = df.astype({"idsensore": int, "valore": float, "idoperatore": int, "stato": str})
    df["dataora"] = pd.to_datetime(df["dataora"], format="%Y/%m/%d %H:%M:%S")
    
    return df

def fromJsonApiToDf(datetime):
    url="https://www.dati.lombardia.it/resource/nicp-bhqi.json?$limit=3000000&$where=data<='%s'" %datetime #lte = < , gte = >

    with urllib.request.urlopen(url) as response:
        record_list = json.loads(response.read().decode())
    
    df = pd.json_normalize(record_list)
    df.rename(columns={"data":"dataora"}, inplace = True)
    df = df.reindex(columns=["idsensore","dataora", "valore", "stato", "idoperatore"])
    df = df.astype({"idsensore": int, "valore": float, "idoperatore": int, "stato": str})
    df["dataora"] = pd.to_datetime(df["dataora"], format="%Y/%m/%d %H:%M:%S")
    with open('../Data/Sensors/update22_spanCheck/%s_data.json' %str(date.today()), 'w') as outfile: 
        json.dump(record_list, outfile)

    return df


def getSortedJsonDf(jsonDf):
    df = jsonDf.sort_values(["dataora","idsensore"])
    df = df.reset_index(drop=True)
    
    return df


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
    
    countDifferences(df).to_csv("../Data/Sensors/update22_spanCheck/test_{!s}.csv".format(str(date.today())))
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

def UpdateCheckFinal():
    files = glob.glob("../Data/Sensors/update22_spanCheck/*.json")
    
    print("\nStarting update check.")
    for i in range(len(files)-2,len(files)-1):
        lastWeekFile = files[i][-20:-5]
        thisWeekFile = files[i+1][-20:-5]
        print("\n> Retrieving data loaded on %s..." %lastWeekFile[:-5])
        lastWeek = fromJsonFileToDf(lastWeekFile)
        lastWeek = getSortedJsonDf(lastWeek)
        print("\n> Retrieving data loaded on %s..." %thisWeekFile[:-5])
        thisWeek = fromJsonFileToDf(thisWeekFile)
        thisWeek = getSortedJsonDf(thisWeek)
        print("\n> Comparision...")
        df = pd.merge(lastWeek,thisWeek, on=['idsensore','dataora', 'valore', 'stato'], how='left', indicator='Exist')
        df['update'] = np.where(df.Exist == 'both', False, True)
        df = df[df["update"]==True]
        dfDiff = countDifferences(df)
        print(dfDiff)

        with open("../Data/Sensors/update22_spanCheck/comparisons.txt", 'a') as f:
            separator = "\nComparison between data loaded on %s and %s\n" %(lastWeekFile[:-5],thisWeekFile[:-5])
            dfAsString = dfDiff.to_string(header=True, index=False, columns=["mese","count"])
            f.write(separator)
            f.write(dfAsString+"\n")
            f.close()
        i+=1
    print("\nComparison results saved in comparisons.txt.")    
    print("\nDone.")

