from click import progressbar
from tqdm import tqdm
from postgres_functions import *

def generateDf(table):
    df = pd.DataFrame(table)
    df.columns = ['idsensore','dataora','valore','stato','idoperatore']
    return df

def generatePickle(dataframe,year):
    path = "../pickle_files/"
    dataframe.to_pickle("%sdb_%s.h5" % (path, year))

def generatePickles(year):
    connection,cursor = postgresConnection()
    if year == 0:
        year_list = [1995, 2000, 2004, 2007, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022]
    else:
        year_list =[year]
    for year in tqdm(year_list, desc = "Status"):
        print(" Start working on table sens_data_%d..." % year)
        postgresTable = getTable(year,cursor)
        df = generateDf(postgresTable)
        generatePickle(df, year)
        print("Generated pickle for table sens_data_%d" % year)

    closeConnection(connection, cursor)