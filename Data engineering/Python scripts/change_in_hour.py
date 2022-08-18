"""
Function to calcualte the exact day of daylight saving time change in Italy from 1981 to today.
Why 1981? It was the first year Italy adopted a consistent method to establish the day in which the change would take place.
"""

for year in range(1981,2022):

    # List of plausible values starting from 29 as the dstd of the initial year 1981 was the 29th
    yearLoop = [29,28,27,26,25,31,30]

    # Number of years between the initial year and the year of interest
    diff = year - 1981

    # Calculate number of leap years in the time span diff
    if year%4 != 0:
        num_leap_years = diff//4
    else:
        # If the year of interest is itself a leap year a +1 should be added to the count
        num_leap_years = diff//4+1

    # Theoretical number of indexes to move
    tot_diff = diff + num_leap_years

    # Actual number of indexes to move in the yearLoop list
    index_loop = (tot_diff - tot_diff//7 * 7)

    # Extract dtsd
    res = yearLoop[index_loop]

    print("Year:", year, " Day: ", res)


#loop tra gli anni che hanno il giorno di cambio ora che segue un pattern (i.e. dopo il 1981)
#Concetto:
#l'ora cambia l'ultima domenica di marzo. Siccome una settimana ha 7 giorni, l'ultima domenica
#sarà in un giorno compreso negli ultimi 7 gg di marzo, ergo dal 25 al 31.
#Questa info è messa in year_loop. La lista parte dal 29 perchè il 1981 (primo anno che segue una logica)
#ha il cambio ora il 29;
#Concettualmente ci dobbiamo spostare nella lista n volte dove n è la "distanza nel tempo" tra il 1981 
#e l'anno di interesse. Questo perchè ogni anno il giorno x va indietro di 1 finchè non raggiunge il 25,
# poi ricomincia il giro. Questa distanza nel tempo è la variabile diff.
# Siccome gli anni bisestili contano doppio, num_leap years conta quanti anni bisestili ci sono nella 
#"distanza nel tempo".
#La differenza totale di "salti" nella lista sarà quindi data da diff + num_leap_years
#Per "resettare" l'indice e tornare all'inzio della lista una volta raggiunta la fine si usa la formula in
#index_loop che in pratica calcola l'indice finale data la lunghezza della lista e la differenza di salti.
#Poi si estrae il giorno corrispondente nella lista