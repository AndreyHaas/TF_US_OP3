
DROP DATABASE IF EXISTS Onlineshop;

CREATE DATABASE Onlineshop;

USE Onlineshop;

CREATE TABLE kunde (
	nummer INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL    
);

CREATE TABLE adresse (
	id INT PRIMARY KEY AUTO_INCREMENT,
    strasseNr VARCHAR(100) NOT NULL,
    plz CHAR(5) NOT NULL,
    ort VARCHAR(100) NOT NULL,
    kunde INT NOT NULL,
    FOREIGN KEY (kunde) REFERENCES kunde(nummer)
);

CREATE TABLE bestellung(
	nummer INT PRIMARY KEY AUTO_INCREMENT,
    datum DATETIME NOT NULL DEFAULT NOW(),
    kunde INT NOT NULL,
    rechnungsadresse INT NOT NULL,
    lieferadresse INT NOT NULL,
    FOREIGN KEY (kunde) REFERENCES kunde(nummer),
    FOREIGN KEY (rechnungsadresse) REFERENCES adresse(id),
    FOREIGN KEY (lieferadresse) REFERENCES adresse(id)
);

CREATE TABLE hersteller(
	nummer INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE artikel(
	nummer INT PRIMARY KEY AUTO_INCREMENT,
    bezeichnung VARCHAR(100) NOT NULL,
    preis DECIMAL(8,2) NOT NULL CHECK(preis >= 0)
);

ALTER TABLE artikel ADD hersteller INT NOT NULL;
ALTER TABLE artikel ADD FOREIGN KEY (hersteller) REFERENCES hersteller(nummer);

CREATE TABLE bestellposition(
	bestellung INT,
    artikel INT,
    anzahl INT NOT NULL CHECK (anzahl > 0),
    FOREIGN KEY (bestellung) REFERENCES bestellung(nummer),
    FOREIGN KEY (artikel) REFERENCES artikel(nummer),
    PRIMARY KEY (bestellung, artikel)
);
