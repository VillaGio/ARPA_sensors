--Populate stations_check_datsastop (do if from Talend)
insert into stations_check_datastop
select idsensore, storico 
from stations 
where storico='S' and datastop='9999-12-31 00:00:00.000'::timestamp;



---Populate stations_check_datsastart (do if from Talend)
insert into stations_check_datastart
select idsensore, storico 
from stations 
where datastart='9999-12-31 00:00:00.000'::timestamp;



---Check for duplicates
with tabella_duplicati as(
select  idsensore,dataora, count(*) from sens_data_2005
group by 1,2
having count(*)  > 1)

select  * from sens_data_2005
where (idsensore,dataora) in(
select idsensore,dataora from tabella_duplicati)



---Check for daylight changing time (set as 01:59:00)
select * from sens_data_2005 
where extract (minute from dataora) != 00;



---CATALOG queries to rettify table station  
SELECT tablename 
FROM pg_catalog.pg_tables 
where schemaname = 'woneli' and tablename like 'sens_data_%';


select idsensore, max(dataora) 
from sens_data_2021
where exists (
select * 
from stations_check_datastop 
where sens_data_2021.idsensore = stations_check_datastop.idsensore) 
group by idsensore;


select idsensore, max(ultima_acquisizione) from prova 
group by idsensore;

