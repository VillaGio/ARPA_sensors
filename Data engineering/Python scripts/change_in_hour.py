#loop tra gli anni che hanno il giorno di cambio ora riconoscibile e non ad minkiam
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

for year in range(1981,2022):
    yearLoop = [29,28,27,26,25,31,30]
    diff = year - 1981
    if year%4 == 0:
        num_leap_years = diff//4+1
    else:
        num_leap_years = diff//4
    
    tot_diff = diff + num_leap_years
    index_loop = (tot_diff - tot_diff//7 * 7)

    res = yearLoop[index_loop]
    print("Year:", year, " Day: ", res)
