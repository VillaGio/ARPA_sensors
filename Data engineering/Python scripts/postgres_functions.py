from psycopg2 import Error, connect
import pandas as pd


def postgresConnection():
    """Opens the connection to Postgres Database instance woneli.

    Parameters
    ----------
    None

    Returns
    -------
    psycopg2.connection
        connection to Postgres instance woneli
    psycopg2.cursor
        cursor to execute Postgres commands in a session
    """

    try:
        # Connect to an existing database
        connection = connect(user="woneli",
                            password="woneli",
                            host="188.165.216.169",
                            port="5432",
                            database="postgres01")

        # Create a cursor to perform database operations
        cursor = connection.cursor()
        
        # Print PostgreSQL details
        #print("PostgreSQL server information: ")
        #print(connection.get_dsn_parameters(), "\n")
        print("Connection established \n")
        
        return connection, cursor
    
    except (Exception, Error) as error:
        print("Error while connecting to PostgreSQL: ", error)


def closeConnection(connection, cursor):
    """Closes the provided cursor and connection.

    Parameters
    ----------
    connection : psycopg2.connection
        The connection to be closed
    cursor : psycopg2.cursor
        The cursor to be closed
        
    Returns
    -------
    None
    """

    # Close the cursor
    cursor.close()

    # Close the connection
    connection.close()

    # Display info message
    print("\nPostgreSQL connection closed.")


def getTable(year: int, kind: str, cursor):
    """Execute a SELECT * FROM ... query on data of the specified year.

    Parameters
    ----------
    year : int
        The year of the data stored in the table to retrieve
    kind : str
        Takes values either "sens" or "weather", to fetch tables either of sensors or weather data.
    cursor : psycopg2.cursor
        The cursor to be used to execute the Postgres command to retrieve the table data
        
    Returns
    -------
    list[tuples]
        a list of tuples representing the selected rows in the query
    """
    
    # Execute select query
    q = "SELECT * FROM %s_data_%d;" % (kind, year)
    cursor.execute(q)

    # Get query result
    tab = cursor.fetchall()
    
    # Returns query results
    return tab


 