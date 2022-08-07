--Populate stations_check_datsastop
insert into stations_check_datastop
select idsensore
from stations_sens 
where storico='S' and datastop='9999-12-31 00:00:00.000'::timestamp;



---Populate stations_check_datsastart
insert into stations_check_datastart
select idsensore
from stations_sens 
where datastart='0001-01-01 00:00:00.000'::timestamp;



---Check for duplicates in sens_data tables
with tabella_duplicati as(
select  idsensore,dataora, count(*) from sens_data_2012
group by 1,2
having count(*)  > 1)

select * from sens_data_2012
where (idsensore,dataora) in(
select idsensore,dataora from tabella_duplicati)



---Check for duplicates in weather_data tables
with tabella_duplicati as(
select  idsensore,dataora,idoperatore,  count(*) from weather_data_2012
group by 1,2,3
having count(*)  > 1)

select * from weather_data_2012
where (idsensore,dataora, idoperatore) in(
select idsensore,dataora,idoperatore from tabella_duplicati)



--Rettify duplicates with NA vs VA values in 2017
with tabella_duplicati as(
select  idsensore,dataora, count(*) from sens_data_2017
group by 1,2
having count(*)  > 1)

delete from sens_data_2017
where (idsensore,dataora) in(
select idsensore,dataora from tabella_duplicati) and stato = 'NA'



--Rettify duplicate with non-updated idOperatore in 2013
delete from sens_data_2013 
where dataora = '2013-11-20 07:00:00.000'::timestamp and idoperatore =15504;



---Check for daylight changing time (set as 01:59:00)
select * from sens_data_2021 
where extract (minute from dataora) != 00;



---CATALOG queries to rettify table station  

---Rettify datastop
SELECT tablename 
FROM pg_catalog.pg_tables 
where schemaname = 'public' and tablename like 'sens_data_%';


select idsensore, max(dataora) 
from sens_data_2021
where exists (
select * 
from stations_check_datastop 
where sens_data_2021.idsensore = stations_check_datastop.idsensore) 
group by idsensore;


select idsensore, max(ultima_acquisizione) from stations_check_datastop 
group by idsensore;



---Rettify datastart
SELECT tablename FROM pg_catalog.pg_tables
where schemaname = 'public' and tablename like 'sens_data_%'
order by tablename desc;


select idsensore, min(dataora) 
from sens_data_2021
where exists (
select * 
from stations_check_datastop 
where sens_data_2021.idsensore = stations_check_datastop.idsensore) 
group by idsensore;


select idsensore, min(prima_acquisizione) from stations_check_datastart
group by idsensore;