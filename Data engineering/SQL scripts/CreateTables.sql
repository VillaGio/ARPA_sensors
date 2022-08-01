
---Create table logs for logs info
create table logs(
	"current_date" timestamp NULL,
	table_name varchar NULL,
	operation varchar NULL,
	number_of_rows int8 NULL,
	duration_in_seconds int8 NULL
);



---Create table stations for stations history 
CREATE TABLE stations(
	idsensore int8 NULL,
	nometiposensore varchar NULL,
	unitamisura varchar NULL,
	idstazione int8 NULL,
	nomestazione varchar null,
	quota int8 NULL,
	provincia varchar NULL,
	comune varchar NULL,
	storico varchar(2) NULL,
	datastart timestamp,
	datastop timestamp NULL,
	utm_nord int8 NULL,
	utm_est int8 NULL,
	lat float8 NULL,
	lng float8 null,
	coordinate varchar null,
	primary key (idsensore, datastart)
);



---Create table stations_check_datastop for stations with S and no enddate
create table stations_check_datastop(
idsensore int8,
ultima_acquisizione timestamp null
);



---Create table stations_check_datastart for stations with no startdate
create table stations_check_datastart(
idsensore int8,
prima_acquisizione timestamp null
);



---Create table for weather data
create table sens_weather_2021 (
	idsensore int8 NULL,
	dataora timestamp NULL,
	valore float8 NULL,
	stato varchar(2) NULL,
	idoperatore int4 NULL
);

