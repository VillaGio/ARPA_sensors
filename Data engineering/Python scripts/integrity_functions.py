from random import randint
from datetime import datetime
import pandas as pd
from numpy import random
from sqlalchemy import true


def getSortedCsv(year):
    path = "../Data/Sensors/modifiedForBulk/mod_%d.csv" % year
    csv = pd.read_csv(path, na_filter = False)
    csv["dataora"] = pd.to_datetime(csv["dataora"], format="%Y/%m/%d %H:%M:%S")
    csv = csv.sort_values(["dataora","idsensore"])
    csv = csv.reset_index(drop=True)
    return csv

def getSortedDbTable(year):
    path = "../Pickle files/db_%d.h5" % year
    df = pd.read_pickle(path)
    df = df.sort_values(["dataora","idsensore"])
    df = df.reset_index(drop=True)
    return df

def checkSize(sourceCsvFile, dbTable):
    print(">> Performing check on size consistency...")
    equalSize = len(sourceCsvFile) == len(dbTable)
    if equalSize:
        print("   CHECK PASSED: source csv file and target db table size are equal")
    else:
         print("   CHECK FAILED: source csv file and target db table size are different:")
         print("   Source csv file  size: %d" % len(sourceCsvFile))
         print("   Target table file  size: %d" % len(dbTable))
    return equalSize
    

def checkRecordIntegrity(sourceCsvFile, dbTable):
    print(">> Performing check on records consistency...")
    wrong_count = 0
    for i in range(100000):
        r = randint(0,len(dbTable)-1) 
        if (sourceCsvFile["idsensore"][r] != dbTable["idsensore"][r] or 
            sourceCsvFile["dataora"][r] != dbTable["dataora"][r] or
            sourceCsvFile["valore"][r] != dbTable["valore"][r]):
            wrong_count += 1
            print('   CHECK FAILED at row: '+ str(r))
    
    if wrong_count == 0: 
        print("   CHEK PASSED")
    else:
        print("   \nTotal number of inconsistent rows: %d." % wrong_count)



def checkIntegrity(year):
    if year == 0:
        year_list = [1995, 2000, 2004, 2007, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021]
    else:
        year_list = [year]
    print("\nIntegrity check started.")
    for year in year_list:
        print("\nIntegrity check for %d data: " % year)
        print("> Getting source csv file for comparision...")
        csv = getSortedCsv(year)
        print("> Getting database table for comparision...")
        df = getSortedDbTable(year)
        equalSize = checkSize(csv, df)
        if equalSize:
            checkRecordIntegrity(csv, df)
        else:
            print("   \nNot checking for record integrity as size is inconsistent")
    print("\nIntegrity check completed")

