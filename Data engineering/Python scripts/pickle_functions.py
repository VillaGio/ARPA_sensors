from tqdm import tqdm
from postgres_functions import *
from datetime import date
from typing import *
import pandas as pd

def generateDf(table):
    """Generates a dataframe of the provided DB table.

    Parameters
    ----------
    table : list[tuples]
        The table to be converted to dataframe
     
    Returns
    -------
    pandas.DataFrame
        The dataframe of the provided DB table
    """

    # Convert DB table to df
    df = pd.DataFrame(table)
    
    # Set correct column names
    df.columns = ['idsensore','dataora','valore','stato','idoperatore']

    # Return df
    return df


def generatePickle(dataframe, year: int, kind: str):
    """Generates and saves a pickle file of the provided dataframe.
    The pickle file of the provided dataframe is saved as db_year.h5

    Parameters
    ----------
    dataframe : pandas.dataframe
        The dataframe to convert to pickle
    year : int
        The year of the data contained in the dataframe
    kind : str
        Takes values either "sens" or "weather", to generate pickles either of sensors or weather data tables.

        
    Returns
    -------
    None
        
    """

    # Path of the save location of the pickle file
    if kind == "sens":
        path = "../Pickle files/PickleS/"
    else:
        path = "../Pickle files/PickleW/"    

    # Conversion from dataframe to pickle format .h5
    dataframe.to_pickle("%sdb_%d.h5" % (path, year))


def generatePickles(kind:str, year:int=0):
    """Generates and saves pickle files for data in Postgres DB tables from year 1995 to present.
        If year argument is specified, the pickle file for data of that year only will be generated.

    Parameters
    ----------
    year(optional) : int
        The year of the data whose pickle will be generated. 
    kind : str
        Takes values either "sens" or "weather", to generate pickles either of sensors or weather data tables.

    Returns
    -------
    pickle file
        The pickle file of the desired data, saved as db_year.h5
    """

    if kind not in["sens", "weather"]:
        raise Exception("Invalid argument: "+ kind + ". Argument kind can only be assigned values 'sens' or 'weather'")
    
    if kind == "sens":
        # Generate a list with values 1995, 2000, 2004, 2007 and from 2010 to current year
        year_list = [1995, 2000, 2004, 2007]
        current_year = int(date.today().year)
        new_years = range(2010, current_year+1)
        year_list.extend(new_years)
    elif kind == "weather":
        # Generate a list with values 2000, 2005, 2008, 2010, and from 2012 to current year
        year_list = [2000, 2005, 2008, 2010]
        current_year = int(date.today().year)
        new_years = range(2012, current_year+1)
        year_list.extend(new_years)

    # Check whether to use user-specified year of default list
    if year != 0:
        if year not in year_list:
            raise Exception("Data not present for this year. Available years are: " + str(year_list).strip("[]") + ".")
        else:
            year_list = [year]

    # Establish connetion with the database
    connection, cursor=postgresConnection()

    # Generate pickle for the specified year(s)
    for year in tqdm(year_list, desc = "Status"):

        print(" Start working on table %s_data_%d..." % (kind,year))

        # Retrieve Postgres DB table corresponding to specified year
        postgresTable = getTable(year, kind, cursor)

        # Generate a pandas df of the DB table
        df = generateDf(postgresTable)

        # Generate and save pickle file of the pandas df
        generatePickle(df, year, kind)
        print("Generated pickle for table %s_data_%d" % (kind, year))

    # Close connection to Postgres and cursor 
    closeConnection(connection, cursor)
