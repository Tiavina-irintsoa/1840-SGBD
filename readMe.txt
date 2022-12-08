la grammaire:
-insensible a la casse sauf pour les donnees


selectionner une base de donnees:
use database nom_bdd

creer une base de donnees:
create database nom_bdd

supprimer une base de donnees:
drop database nom_bdd

supprimer table:
drop table nom_table

projection:
select * from nom_table 
select col1,col2,col3 from nom_table

selection:
select * from nom_table where col1 = ... [or col1 = ...] [and col2 = ...]

join:
select * from nom_table join nom_table2

creation de table:
create table nomtable with col1,col2,col3

insertion de donnees:
insert into nom_table values_col1,values_col2,...

division:
select * from nom_table1 division nomtable2 col1 by col2

suprresion 
delete from nomtable [where ...]

current time
now()

update set nom = haha from nom_table [where ...]




