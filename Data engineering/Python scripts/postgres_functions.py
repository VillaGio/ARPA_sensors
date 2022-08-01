from psycopg2 import Error, connect
import pandas as pd
from varname import nameof

def open_close_connection():
    try:
        # Connect to an existing database
        connection = connect(user="woneli",
                                            password="woneli",
                                            host="188.165.216.169",
                                            port="5432",
                                            database="postgres")

        # Create a cursor to perform database operations
        cursor = connection.cursor()
        # Print PostgreSQL details
        print("PostgreSQL server information")
        print(connection.get_dsn_parameters(), "\n")
        # Executing a SQL query
        cursor.execute("SELECT version();")
        # Fetch result
        record = cursor.fetchone()
        print("You are connected to - ", record, "\n")
    
    except (Exception, Error) as error:
        print("Error while connecting to PostgreSQL: ", error)
    
    finally:
        if (connection):
            cursor.close()
            connection.close()
            print("PostgreSQL connection is closed")

def postgresConnection():
    try:
        # Connect to an existing database
        connection = connect(user="woneli",
                                            password="woneli",
                                            host="188.165.216.169",
                                            port="5432",
                                            database="postgres")

        # Create a cursor to perform database operations
        cursor = connection.cursor()
        
        # Print PostgreSQL details
        print("PostgreSQL server information: ")
        print(connection.get_dsn_parameters(), "\n")
        print("Connection established \n")

        return connection, cursor
    
    except (Exception, Error) as error:
        print("Error while connecting to PostgreSQL: ", error)

def closeConnection(connection, cursor):
    cursor.close()
    connection.close()
    print("\nPostgreSQL connection closed.")

def getTable(year, cursor):
    cursor.execute("SELECT * FROM sens_data_%d;" % year)
    return cursor.fetchall()


