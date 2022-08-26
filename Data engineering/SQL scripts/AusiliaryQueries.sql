
---Check for duplicates in sens_data tables
with tabella_duplicati as(
select  idsensore,dataora, count(*) 
from sens_data_2017
group by 1,2
having count(*)  > 1)

select * from sens_data_2017
where (idsensore,dataora) in(
select idsensore,dataora from tabella_duplicati)



---Check for duplicates in weather_data tables
with tabella_duplicati as(
select  idsensore,dataora,idoperatore,  count(*) 
from weather_data_2000
group by 1,2,3
having count(*)  > 1)

select * 
from weather_data_2000
where (idsensore,dataora, idoperatore) in(
select idsensore,dataora,idoperatore from tabella_duplicati)



--Rettify duplicates with NA vs VA values in 2017
with tabella_duplicati as(
select  idsensore,dataora, count(*) 
from sens_data_2017
group by 1,2
having count(*)  > 1)

delete from sens_data_2017
where (idsensore,dataora) in(
select idsensore,dataora from tabella_duplicati) and stato = 'NA'



--Rettify duplicates with different values in 2017 (two records only)
DELETE FROM sens_data_2017 T1
    USING   sens_data_2017 T2
WHERE  T1.ctid < T2.ctid  -- delete the older versions
	and T1.dataora = T2.dataora
    AND T1.idsensore  = T2.idsensore;  -- add more columns if needed



--Rettify duplicate with non-updated idOperatore in 2013
delete from sens_data_2013 
where dataora = '2013-11-20 07:00:00.000'::timestamp and idoperatore =15504;



---Check for daylight changing time (set as 01:59:00)
select * from sens_data_2021 
where extract (minute from dataora) != 00;



--Populate stations_check_datsastop
insert into stations_check_datastop
select idsensore, 'stations_sens' --'stations_weather'
from stations_sens --stations_weather
where storico='S' and datastop='9999-12-31 00:00:00.000'::timestamp;



---Populate stations_check_datsastart
insert into stations_check_datastart
select idsensore, 'stations_sens' --'stations_weather'
from stations_sens --stations_weather
where datastart='0001-01-01 00:00:00.000'::timestamp;



---CATALOG queries to rettify table stations_%  

---Rettify datastop
SELECT tablename 
FROM pg_catalog.pg_tables 
where schemaname = 'public' and tablename like 'sens_data_%'; --'weather_data_%'


select idsensore, max(dataora) 
from sens_data_2021
where exists (
select * 
from stations_check_datastop 
where sens_data_2021.idsensore = stations_check_datastop.idsensore
and stations_check_datastop.table_name='stations_sens') --'stations_weather'
group by idsensore;


select idsensore, max(ultima_acquisizione) from stations_check_datastop 
where table_name= 'stations_sens' --'stations_weather'
group by idsensore;



---Rettify datastart
SELECT tablename FROM pg_catalog.pg_tables
where schemaname = 'public' and tablename like 'sens_data_%'  --'weather_data_%'
order by tablename desc;


select idsensore, min(dataora) 
from sens_data_2021
where exists (
select * 
from stations_check_datastop 
where sens_data_2021.idsensore = stations_check_datastop.idsensore
and stations_check_datastart.table_name='stations_sens') --'stations_weather'
group by idsensore;


select idsensore, min(prima_acquisizione) from stations_check_datastart
where table_name= 'stations_sens' --'stations_weather'
group by idsensore;