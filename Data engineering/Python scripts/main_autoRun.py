from pickle_functions import *
from integrity_functions import *
from postgres_functions import *
from update_functions import *
import time 

"""
Simplified Main to be run automatically in the Windows task scheduler
to check for number of updates in data retrived from API for further optimization pursposes
"""

def main():
    updateCheck("2022-05-29T12:00:00")
    time.sleep(5)
    UpdateCheckFinal()

if __name__ == "__main__":
    main()
