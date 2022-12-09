Changer de hote: client.java

la grammaire:
-insensible a la casse sauf pour les donnees

voir la liste des basse de donnees:
>>show databases

voir la liste des tables:
>>show tables

supprimer une base de donnees:
>>drop database nom_bdd

selectionner une base de donnees:
>>use database nom_bdd

creer une base de donnees:
>>create database nom_bdd

creation de table:
>>create table nomtable with col1,col2,col3
exemple: create table etudiant with id,nom,prenom

decrire une table
>>show details from nom_table

supprimer table:
>>drop table nom_table

insertion de donnees:
>>insert into nom_table values_col1,values_col2,...
exemple: insert into etudiant 1,Rakotonirina,Tiavina

projection:
>>select * from nom_table 
exemple: select * from etudiant
>>select col1,col2,col3 from nom_table
exemple: select id,nom from etudiant

selection:
>>select * from nom_table where col1 = ... [or col1 = ...] [and col2 = ...]
exemple: select * from etudiant where nom = Rakotonirina 

>>select * from nom_table where col1 like ... [or col1 = ...] [and col2 like ...]
exemple: select * from etudiant where nom like Rak and etu = 1

natural join:
>>select * from nom_table join nom_table2
exemple: select * from etudiant join cours
>>select * from nom_table join nom_table2 join nom_table3

suprression 
>>delete from nomtable [where ...]

current time
>>now()

update
>>update set nom = haha from nom_table [where ...]

quitter le programme
>>bye