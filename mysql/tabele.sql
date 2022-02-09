create database if not exists depozit;
use depozit;

create table if not exists Client
(id int primary key,
nume varchar(255));

create table if not exists Produs
(id int primary key,
nume varchar(255),
pret int,
cantitate int);

create table if not exists Comanda
(id int auto_increment primary key ,
idClient int,
idProdus int,
cantitate int,
pret int,
foreign key(idClient) references client(id) on delete cascade,
foreign key (idProdus) references produs(id) on delete cascade);

create table if not exists Produse_Comenzi
(idProdus int,
idComanda int);
