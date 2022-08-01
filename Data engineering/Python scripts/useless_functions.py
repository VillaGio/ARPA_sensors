from postgres_functions import *
from pickle_functions import *
from integrity_functions import *
import requests
import json
import pandas as pd   
import numpy as np 
import urllib.request
from datetime import date

#erano dentro update_functions

def getLastCheckedTable(lastCheckedDate):
    connection,cursor = postgresConnection()
    
    print("Retrieving records from table sens_data_2022 prior to %s..." % lastCheckedDate)
    cursor.execute("SELECT * FROM sens_data_2022 WHERE dataora <= '%s'::timestamp;" % lastCheckedDate)
    postgresTable = cursor.fetchall()
    
    df = generateDf(postgresTable)
    print("Records successfully retrieved and saved.")
    
    closeConnection(connection, cursor)
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

    