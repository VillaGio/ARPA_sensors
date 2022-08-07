from tqdm import tqdm
from postgres_functions import *
from datetime import date
from typing import *
import pandas as pd

def generateDf(table: List[tuple]):
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


def generatePickle(dataframe, year: int):
    """Generates and saves a pickle file of the provided dataframe.

    Parameters
    ----------
    dataframe : pandas.dataframe
        The dataframe to convert to pickle
    year : int
        The year of the data contained in the dataframe
        
    Returns
    -------
    pickle file
        The pickle file of the provided dataframe, saved as db_year.h5
    """

    # Path of the save location of the pickle file
    path = "../Pickle files/"

    # Conversion from dataframe to pickle format .h5
    return(dataframe.to_pickle("%sdb_%s.h5" % (path, year)))


def generatePickles(year:int=0):
    """Generates and saves a pickle files for data in Postgres DB tables from year 1995 to present.
        If year argument is specified, the pickle file for data of that year only will be generated.

    Parameters
    ----------
    year(optional) : int
        The year of the data whose pickle will be generated. 
        
    Returns
    -------
    pickle file
        The pickle file of the desired data, saved as db_year.h5
    """

    # Open the Postgres connection to woneli instance and generates a cursor
    connection,cursor = postgresConnection()

    # Check whether to use user-specified year of default list
    if year == 0:
        # Generate a list with values 1995, 2000, 2004, 2007 and from 2010 to current year
        year_list = [1995, 2000, 2004, 2007]
        current_year = int(date.today().year)
        new_years = range(2010, current_year+1)
        year_list.extend(new_years)
    else:
        year_list =[year]

    # Generate pickle for the specified year(s)
    for year in tqdm(year_list, desc = "Status"):
        print(" Start working on table sens_data_%d..." % year)

        # Retrieve Postgres DB table corresponding to specified year
        postgresTable = getTable(year,cursor)

        # Generate a pandas df of the DB table
        df = generateDf(postgresTable)

        # Generate and save pickle file of the pandas df
        generatePickle(df, year)
        print("Generated pickle for table sens_data_%d" % year)

    # Close connection to Postgres and cursor 
    closeConnection(connection, cursor)