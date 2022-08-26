from postgres_functions import *
from pickle_functions import *
from integrity_functions import *
import json
import pandas as pd   
import numpy as np 
import urllib.request
from datetime import date
import glob


def fromJsonFileToDf(filename:str):
    """Convert the json file with data of current year retrieved from API into a dataframe with correct headers and data types.

    Parameters
    ----------
    filename : str
        The name of the json file storing API data (without extension)
     
    Returns
    -------
    pandas.Dataframe
        The dataframe version of the json file with correct naming and datatype
    """

    # Read json file with API data
    with open("../Data/Sensors/update22_spanCheck/" + filename + ".json") as fp:
        record_list = json.load(fp)
   
    # Convert jsondf into a flat table if nacessary
    df = pd.json_normalize(record_list)

    # Rename column data to dataora for consistency with standards
    df.rename(columns={"data":"dataora"}, inplace = True)

    # Change column order for consistency with standards
    df = df.reindex(columns=["idsensore","dataora", "valore", "stato", "idoperatore"])

    # Set correct datatypes for all columns but dataora
    df = df.astype({"idsensore": int, "valore": float, "idoperatore": int, "stato": str})

    # Convert dataora column to datetime with correct format
    df["dataora"] = pd.to_datetime(df["dataora"], format="%Y/%m/%d %H:%M:%S")
    
    # Return converted json df
    return df


def fromJsonApiToDf(datetime:str="2022-05-29T12:00:00"):
    """Convert and save data of current year in json format retrieved from API into json file.

    Parameters
    ----------
    datetime : str
        The string with the ending date of the comparison timeframe

    Returns
    -------
    pandas.Dataframe
        The dataframe version of the json data with correct naming and datatype
        Also saves data into a json file
    """

    # API url with constraint on starting day
    url="https://www.dati.lombardia.it/resource/nicp-bhqi.json?$limit=3000000&$where=data<='%s'" %datetime #lte = < , gte = >

    # Read data from API in json format
    with urllib.request.urlopen(url) as response:
        record_list = json.loads(response.read().decode())
    
     # Convert jsondf into a flat table if nacessary
    df = pd.json_normalize(record_list)

    # Rename column data to dataora for consistency with standards
    df.rename(columns={"data":"dataora"}, inplace = True)

    # Change column order for consistency with standards
    df = df.reindex(columns=["idsensore","dataora", "valore", "stato", "idoperatore"])

    # Set correct datatypes for all columns but dataora
    df = df.astype({"idsensore": int, "valore": float, "idoperatore": int, "stato": str})

    # Convert dataora column to datetime with correct format
    df["dataora"] = pd.to_datetime(df["dataora"], format="%Y/%m/%d %H:%M:%S")
    
    # Save data into json file with name set a timestamp_data for history
    with open('../Data/Sensors/update22_spanCheck/%s_data.json' %str(date.today()), 'w') as outfile: 
        json.dump(record_list, outfile)

    # Return df with API data
    return df


def getSortedJsonDf(jsonDf):
    """Returns the sorted version of the dataframe in input.
       Sorting is applied according to fields 'dataora' and 'idsensore'.

    Parameters
    ----------
    jsonDf : pandas.DataFrame
        The dataframe to be sorted
     
    Returns
    -------
    pandas.Dataframe
        The sorted dataframe
    """
    # Sort df according to datetime and idsensore
    df = jsonDf.sort_values(["dataora","idsensore"])

    # Keep original index
    df = df.reset_index(drop=True)
    
    # Return sorded df
    return df


def countDifferences(df_update):
    """Returns a grouped version of the input dataframe with a column storing the name of the month a another column storing the count of updated records in that month.

    Parameters
    ----------
    df_update : pandas.DataFrame
        The dataframe with 'update' column to count number of updated records
     
    Returns
    -------
    pandas.Dataframe
        The grouped dataframe with month and associated number of updated records.
    """
    # Add new column 'mese' to df with the name of the month of each record dataora
    df_update["mese"] =  df_update["dataora"].dt.month_name()

    # Add new column 'count' with value 1 for each record
    df_update["count"] = [1]*len(df_update)
    
    # Group df by month and flag field update, using function 'count' as aggregation method on column count
    return df_update.groupby(["mese", "update"], as_index= False)["count"].agg("count")


def updateCheck(datetime:str="2022-05-29T12:00:00"):
    """Saves a csv with the result of the comparinson on updated records from beginning 2022 till arg datetime. 

    Parameters
    ----------
    datetime : str
        The string storing the datetime object representing the ending date of the comparison timeframe

    Returns
    -------
    None
    """
    print("\nStarting update check.")
    print("\n> Retrieving data from api...")

    # Get data prior to datetime from API 
    apiDf = fromJsonApiToDf(datetime)

    # Sort dataframe according to dataora and idsensore
    apiDf = getSortedJsonDf(apiDf)
    print("> Retrieving data from database table...")

    # Get sorted DB table with data from 2022
    dbDf = getSortedDbTable(2022)
    
    print("> Comparision...")

    # Left join using data from API as master table
    df = pd.merge(apiDf,dbDf, on=['idsensore','dataora', 'valore', 'stato'], how='left', indicator='Exist')

    # Add column 'update' which is true if record is only present on API (i.e. was updated)
    df['update'] = np.where(df.Exist == 'both', False, True)

    # Keep only updated records
    df = df[df["update"]==True]
    
    # Get and save df with month and number of updated records in that month
    countDifferences(df).to_csv("../Data/Sensors/update22_spanCheck/test_{!s}.csv".format(str(date.today())))
    print("\nResults successfully saved into test_{!s}.csv".format(str(date.today())))


def weeklyUpdateCheck(lastWeekFile: str, thisWeekFile: str):
    """Performs a comparison between data retrieved the current week and data retrieved last week.
       Analyses how many records were updated for each month. 

    Parameters
    ----------
    lastWeekFile : str
        The string storing the name of the json file storing data retrieved last week
    thisWeekFile: str
        The string storing the name of the json file storing data retrieved this week
     
    Returns
    -------
    None
    """
    print("\nStarting update check.")
    print("\n> Retrieving data loaded on %s..." %lastWeekFile[:-5])

    # Get data from previous week 
    lastWeek = fromJsonFileToDf(lastWeekFile)

    # Sort data from previous week
    lastWeek = getSortedJsonDf(lastWeek)
    print("\n> Retrieving data loaded on %s..." %thisWeekFile[:-5])

    # Get data from current week 
    thisWeek = fromJsonFileToDf(thisWeekFile)

    # Sort data from current week
    thisWeek = getSortedJsonDf(thisWeek)
    
    print("> Comparision...")
    # Left join using data from this week as master table
    df = pd.merge(thisWeek, lastWeek, on=['idsensore','dataora', 'valore', 'stato'], how='left', indicator='Exist')
    
    # Add column 'update' which is true if record is only present this week (i.e. was updated)
    df['update'] = np.where(df.Exist == 'both', False, True)
    
    # Keep only updated records
    df = df[df["update"]==True]
    
    # Get and display df with month and number of updated records in that month
    print(countDifferences(df))
    print("\nDone.")


def UpdateCheckFinal():
    """Performs a comparison between data retrieved at week t and data retrieved at week t-1 for all the week files present in the directory.
       Analyses how many records were updated for each month during each week. 
       Saves results into a txt for easier analysis.

    Parameters
    ----------
    None
     
    Returns
    -------
    None
    """
    files = glob.glob("../Data/Sensors/update22_spanCheck/*.json")
    
    print("\nStarting update check.")
    
    # Loop over all json weekly files in the directory
    for i in range(len(files)-2,len(files)-1):

        # Get last week file name without prefix and extension
        lastWeekFile = files[i][-20:-5]

        # Get this week file name without prefix and extension
        thisWeekFile = files[i+1][-20:-5]
        print("\n> Retrieving data loaded on %s..." %lastWeekFile[:-5])

        # Get data from previous week 
        lastWeek = fromJsonFileToDf(lastWeekFile)

        # Sort data from previous week
        lastWeek = getSortedJsonDf(lastWeek)
        print("\n> Retrieving data loaded on %s..." %thisWeekFile[:-5])

        # Get data from current week 
        thisWeek = fromJsonFileToDf(thisWeekFile)
        
        # Sort data from current week
        thisWeek = getSortedJsonDf(thisWeek)
        print("\n> Comparision...")

        # Left join using data from this week as master table
        df = pd.merge(thisWeek, lastWeek, on=['idsensore','dataora', 'valore', 'stato'], how='left', indicator='Exist')
        
        # Add column 'update' which is true if record is only present this week (i.e. was updated)
        df['update'] = np.where(df.Exist == 'both', False, True)
        
        # Keep only updated records
        df = df[df["update"]==True]
        
        # Get df with month and number of updated records in that month
        dfDiff = countDifferences(df)

        # Display df with month and number of updated records in that month
        print(dfDiff)

        # Write results of the comparisons in a txt file to easen analysis 
        with open("../Data/Sensors/update22_spanCheck/comparisons.txt", 'a') as f:
            separator = "\nComparison between data loaded on %s and %s\n" %(lastWeekFile[:-5],thisWeekFile[:-5])
            dfAsString = dfDiff.to_string(header=True, index=False, columns=["mese","count"])
            f.write(separator)
            f.write(dfAsString+"\n")
            f.close()
        i+=1
    print("\nComparison results saved in comparisons.txt.")    
    print("\nDone.")

