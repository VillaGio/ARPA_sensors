from pickle_functions import *
from integrity_functions import *
from postgres_functions import *
from update_functions import *



def main():
    i = int(input("Enter 1 to generate pickle files, 2 to run the integrity checks, 3 to run the update checks: \n"))
    if i == 1: 
        # (1) Generating pickle files for each table in the database
        arg = int(input("Enter a year to generate the correspondig database table pickle; enter 0 to generate pickles for all available tables: \n"))
        generatePickles(arg)
    elif i == 2:
        # (2) Checking for data integrity for each table in the database
        arg = int(input("Enter a year to run its corresponding integrity check ; enter 0 to run integrity checks for all available tables: \n"))
        checkIntegrity(arg)
    elif i == 3:
        # (3) Checking for updates for each table in the database
        arg = input("Enter a valid datetime (format: yyyy-MM-ddThh:mm:ss): \n")
        updateCheck(arg)
    


if __name__ == "__main__":
    main()

