use depozit;

insert into Client (id, nume) values (1, "Stefan Gheorghidiu"),
(2, "Max Blecher"),
(3, "Hazel Eddie"),
(4, "Stevie Nicks"),
(6, "Patti Smith"),
(7, "Marlo Brando");

insert into Produs (id, nume, pret, cantitate) values
(1, "paste", 15, 30),
(2, "mozarella", 20, 30),
(3, "pesto", 25, 30),
(4, "rosii", 10, 30),
(5, "cheddar", 15, 30);

insert into Comanda (id, idClient, idProdus, cantitate, pret) values
(1, 1, 1, 1, 15);