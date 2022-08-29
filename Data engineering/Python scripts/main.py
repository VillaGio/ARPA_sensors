from pickle_functions import *
from integrity_functions import *
from update_functions import *

"""
Extended Main to perform utility operations such as: 
 - generating pickle files of DB tables
 - performing integrity checks between data in DB tables and data in source CSV
 - checking for number of updates in data retrived from API for further optimization pursposes (for sensors data)
"""


def main():
    i = int(input("Enter 1 to generate pickle files, 2 to run the integrity checks, 3 to run the update checks: \n"))
    if i == 1: 
        # (1) Generating pickle files 
        arg1 = input("Enter 'sens' to work with sensors data; enter 'weather' to work with weather data: \n")
        arg2 = int(input("Enter a year to generate the correspondig database table pickle; enter 0 to generate pickles for all available tables: \n"))
        generatePickles(arg1,arg2)
    elif i == 2:
        # (2) Checking for data integrity
        arg2 = input("Enter 'sens' to work with sensors data; enter 'weather' to work with weather data: \n")
        arg1 = int(input("Enter a year to run its corresponding integrity check ; enter 0 to run integrity checks for all available tables: \n"))
        checkIntegrity(arg1, arg2)
    elif i == 3:
        # (3) Checking for updates for table 2022 of sensors data in the database
        arg = input("Enter a valid datetime (format: yyyy-MM-ddThh:mm:ss) or 0 to move to the weekly comparison: \n")
        if arg == "0":
            file1 = input("Enter the name of the file from last week (ex. 2022-06-01_data):\n ")
            file2 = input("Enter the name of the file from this week (ex. 2022-06-07_data):\n ")
            weeklyUpdateCheck(file1,file2)
        else:
            updateCheck(arg)


if __name__ == "__main__":
    main()

