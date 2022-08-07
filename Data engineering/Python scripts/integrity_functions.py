from datetime import date
from random import randint
import pandas as pd


def getSortedCsv(year: int):
    """Returns the sorted version of the csv corresponding to the specified year.
       Sorting is applied according to fields 'dataora' and 'idsensore'.

    Parameters
    ----------
    year : int
        The year corresponding to the csv name to be sorted
     
    Returns
    -------
    pandas.Dataframe
        The sorted dataframe of the provided csv
    """

    # Path of the csv to be sorted
    path = "../Data/Sensors/modifiedForBulk/mod_%d.csv" % year

    # Read csv
    csv = pd.read_csv(path, na_filter = False)

    # Convert field dataora to datetime in order to sort for date
    csv["dataora"] = pd.to_datetime(csv["dataora"], format="%Y/%m/%d %H:%M:%S")

    # Sort csv according to datetime and idsensore
    csv = csv.sort_values(["dataora","idsensore"])

    # Keep original index
    csv = csv.reset_index(drop=True)

    # Return sorded csv
    return csv

def getSortedDbTable(year: int):
    """Returns the sorted version of the DB table (through its pickle file) corresponding to the specified year.
       Sorting is applied according to fields 'dataora' and 'idsensore'.

    Parameters
    ----------
    year : int
        The year corresponding to the csv name to be sorted
     
    Returns
    -------
    pandas.Dataframe
        The sorted dataframe of the provided DB table
    """
    # Path of the DB table pickle to be sorted
    path = "../Pickle files/db_%d.h5" % year

    # Read pickle file to df
    df = pd.read_pickle(path)

    # Sort csv according to datetime and idsensore
    df = df.sort_values(["dataora","idsensore"])

    # Keep original index
    df = df.reset_index(drop=True)

    # Return sorted table
    return df

def checkSize(sourceCsvFile, dbTable):
    """Checks whether the source cvs file and the mirrored DB table have equal number of records.

    Parameters
    ----------
    sourceCsvFile : pandas.DataFrame
        The csv file used as source for importing data in the DB table
    dbTable: pandas.DataFrame
        The DB table storing the data of the sourceCsvFile after ingestion
     
    Returns
    -------
    Boolean
        The result of the comparison between the length of the two input pd.tables
    """

    print(">> Performing check on size consistency...")

    # Comparison between source file length and and file after ingestion
    equalSize = len(sourceCsvFile) == len(dbTable)

    # Display results info messages 
    if equalSize:
        print("   CHECK PASSED: source csv file and target db table size are equal")
    else:
         print("   CHECK FAILED: source csv file and target db table size are different:")
         print("   Source csv file  size: %d" % len(sourceCsvFile))
         print("   Target table file  size: %d" % len(dbTable))

    # Return comparison results
    return equalSize
    

def checkRecordConsistency(sourceCsvFile, dbTable):
    """Takes 100000 random records from the source csv file and DB table and compare their 
    fields values to assert their consistency.
    
    Parameters
    ----------
    sourceCsvFile : pandas.DataFrame
        The csv file used as source for importing data in the DB table
    dbTable: pandas.DataFrame
        The DB table storing the data of the sourceCsvFile after ingestion
     
    Returns
    -------
    None
    """

    print(">> Performing check on records consistency...")

    # Set initial number of not matching records to zero
    wrong_count = 0

    # Run consistency check on n records
    for i in range(100000):
        # Get random index
        r = randint(0,len(dbTable)-1) 
        # Check for matchings 
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
    """Performs an integrity check between the source csv file and DB table by
        checking their total number of records and comparing the values in fields of 100000 random records.
    
    Parameters
    ----------
    year: int
        The year corresponding to the name of the sourceCsvFile and to the data stored in the dbTable to comapare
     
    Returns
    -------
    None
    """

    # Check whether to use user-specified year of default list
    if year == 0:
        # Generate a list with values 1995, 2000, 2004, 2007 and from 2010 to current year
        year_list = [1995, 2000, 2004, 2007]
        current_year = int(date.today().year)
        new_years = range(2010, current_year+1)
        year_list.extend(new_years)
    else:
        year_list = [year]

    print("\nIntegrity check started.")

    # Perform integrity check for the specified year(s)
    for year in year_list:
        print("\nIntegrity check for %d data: " % year)
        print("> Getting source csv file for comparision...")

        # Sort csv so that indexes are comparable with the ones of DBtable
        csv = getSortedCsv(year)
        print("> Getting database table for comparision...")

        # Sort DBtable so that indexes are comparable with the ones of csv
        df = getSortedDbTable(year)

        # Check for consistency in umber of records
        equalSize = checkSize(csv, df)

        if equalSize:
            # Check for value consistency by comparing values of n random records in both tables
            checkRecordConsistency(csv, df)
        else:
            print("   \nNot checking for record integrity as size is inconsistent")
    print("\nIntegrity check completed")

