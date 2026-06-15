DROP DATABASE IF EXISTS hochbau;
CREATE DATABASE hochbau;

USE hochbau;

CREATE TABLE abteilung (
    abteilungsnummer INT PRIMARY KEY,
	abteilungsname VARCHAR(50)
);

CREATE TABLE baustelle (
    baustellennummer CHAR(4) PRIMARY KEY,
	baustellenbezeichnung VARCHAR(150)
);

CREATE TABLE mitarbeiter (
    mitarbeiternummer CHAR(4) PRIMARY KEY,
	maschinenberechtigung BIT NOT NULL,
	mitarbeitername VARCHAR(50) NOT NULL,
	mitarbeiterPLZ CHAR(5) NOT NULL,
	abteilungsnummer INT,
	FOREIGN KEY (abteilungsnummer) REFERENCES abteilung(abteilungsnummer)
);

CREATE TABLE einsatz (
    mitarbeiternummer CHAR(4),
	baustellennummer CHAR(4),
	stunden INT,
	PRIMARY KEY (mitarbeiternummer, baustellennummer), -- zusammengesetzter PK
	FOREIGN KEY (mitarbeiternummer) REFERENCES mitarbeiter (mitarbeiternummer),
	FOREIGN KEY (baustellennummer)  REFERENCES baustelle(baustellennummer)
);

INSERT INTO abteilung(abteilungsnummer,abteilungsname) VALUES
	(12,'Ausbau'),
	(9,'Hochbau'),
	(10,'Haustechnik');

INSERT INTO baustelle(baustellennummer,baustellenbezeichnung) VALUES
	('B021','MIDL'),
	('B112','Kaufstadt'),
	('B253','GaleriaX'),
	('B056','Brutto');

INSERT INTO mitarbeiter(mitarbeiternummer, mitarbeitername, maschinenberechtigung, abteilungsnummer, mitarbeiterPLZ) VALUES
	('M010','Stein',true,12,'04838'),
	('M009','Örtel',0,9,'04105'),
	('M021','Hahn',1,10,'04509'),
	('M024','Holzer',false,9,'04119');

INSERT INTO einsatz(mitarbeiternummer,baustellennummer,stunden) VALUES
	('M010','B021',12),('M010','B112',23),
	('M009','B253',37),
	('M021','B056',21),('M021','B112',24),('M021','B253',34),
	('M024','B056',8),('M024','B253',24);
