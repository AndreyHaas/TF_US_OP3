
/*
	Mehrzeiliger
	Kommentar
*/

# Einzeiliger Kommentar

/* DDL = Data Definition Language */
# Löscht eine Datenbank, wenn sie bereits existiert.
DROP DATABASE IF EXISTS Onlineshop;

CREATE DATABASE Onlineshop;

USE Onlineshop;

CREATE TABLE kunde (
	nummer INT PRIMARY KEY AUTO_INCREMENT, # PRIMARY KEY = Eindeutige Kennzeichnung eines Datensatzes
	name VARCHAR(100) NOT NULL # Variable Größe: Anzahl Zeichen von 1 bis 65,535 (Der Speicherbedarf pro Zeichen richtet sich an den gewählten Zeichensatz)
    # Unterschied zu Microsoft SQL: Dort ist die Angabe in Byte und nicht Anzahl Zeichen.   
);

CREATE TABLE adresse (
	id INT PRIMARY KEY AUTO_INCREMENT, # AUTO_INCREMENT erhöht ID automatisch um 1
    strasseNr VARCHAR(100) NOT NULL, # NOT NULL = Muss ausgefüllt werden, darf nicht leer sein
    plz CHAR(5) NOT NULL, # CHAR = Feste Größe
    ort VARCHAR(100) NOT NULL,
    kunde INT NOT NULL,
    FOREIGN KEY (kunde) REFERENCES kunde(nummer)
);

CREATE TABLE bestellung(
	nummer INT PRIMARY KEY AUTO_INCREMENT,
    datum DATETIME NOT NULL DEFAULT NOW(), # DATETIME = Datum und Uhrzeit
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
    preis DECIMAL(8,2) NOT NULL CHECK(preis >= 0) # 8 Zahlen, wovon 2 Nachkommastellen sind. Reicht bis 999_999.99
	# CHECK prüft auf eine angegebene Bedingung - hier müssen alle Preise größer oder gleich 0 sein.
);

ALTER TABLE artikel ADD hersteller INT NOT NULL;
ALTER TABLE artikel ADD FOREIGN KEY (hersteller) REFERENCES hersteller(nummer);

CREATE TABLE bestellposition(
	bestellung INT,
    artikel INT,
    anzahl INT NOT NULL CHECK (anzahl > 0),
    FOREIGN KEY (bestellung) REFERENCES bestellung(nummer),
    FOREIGN KEY (artikel) REFERENCES artikel(nummer),
    PRIMARY KEY (bestellung, artikel) # Zusammengesetzer Primärschlüssel
);

/*
	Weitere DDL Befehle sind:
    DROP TABLE kunde
    ALTER TABLE ... ADD | MODIFY COLUMN | DROP COLUMN
*/


/* DML = Data Manipulation Language */
INSERT INTO kunde(nummer,name)VALUES
(123123123,'Wilson');

INSERT INTO kunde(name)VALUES
('Sanchez'), # nummer = 123123124
('Bashir'); # nummer = 123123125

INSERT INTO adresse(strasseNr, plz, ort, kunde) VALUES
('Hauptstraße 1','12345','Haupstadt',123123123),
('Nebenstraße 2','12345','Haupstadt',123123124),
('Nebenstraße 8','12345','Haupstadt',123123125);

INSERT INTO bestellung(kunde,rechnungsadresse,lieferadresse)VALUES
(123123123, 1, 1), # bestellt von Wilson , Rechnung an Wilson, Lieferung an Wilson
(123123123, 1, 2), # bestellt von Wilson , Rechnung an Wilson , Lieferung an Sanchez
(123123124, 2, 2); # bestellt von Sanchez , Rechnung an Sanchez , Lieferung an Sanchez

INSERT INTO hersteller (name) VALUES
('Technik Supreme'),
('Dein Garten'),
('Kino und mehr');

INSERT INTO artikel (bezeichnung, preis, hersteller) VALUES
('Flatscreen 50 Zoll', 599.99,1),
('Gießkanne Blau 5L',6.90,2),
('Into the Spiderverse BluRay Special Edition',9.99,3),
('Soundblaster 5.1',99.95,1);

INSERT INTO bestellposition(bestellung,artikel,anzahl) VALUES
(1,1,1), #Bestellung 1 Artikel 1 Anzahl 1
(1,2,1), #Bestellung 1 Artikel 2 Anzahl 1
(2,2,2), #Bestellung 2 Artikel 2 Anzahl 2
(3,3,1); #Bestellung 3 Artikel 3 Anzahl 1

/*
	Weitere DML-Befehle sind:
    UPDATE, DELETE FROM
*/

/* DQL = Data Query Language */
SELECT * FROM kunde;
SELECT * FROM artikel;

SELECT kunde.nummer 'kunde', kunde.name,
	bestellung.nummer 'bestellung', bestellung.datum,
    a1.strasseNr 'rechnung', a2.strasseNr 'lieferung',
    artikel.nummer 'artikel', artikel.bezeichnung,
    bp.anzahl, hersteller.nummer 'hersteller',
    hersteller.name
FROM kunde
LEFT JOIN bestellung ON kunde.nummer = bestellung.kunde
LEFT JOIN adresse a1 ON a1.id = bestellung.rechnungsadresse
LEFT JOIN adresse a2 ON a2.id = bestellung.lieferadresse
LEFT JOIN bestellposition bp ON bestellung.nummer = bp.bestellung
LEFT JOIN artikel ON artikel.nummer = bp.artikel
LEFT JOIN hersteller ON hersteller.nummer = artikel.hersteller;

# oder mit RIGHT JOIN:
SELECT kunde.nummer 'kunde', kunde.name,
	bestellung.nummer 'bestellung', bestellung.datum,
    a1.straßeNr 'rechnung', a2.straßeNr 'lieferung',
    artikel.nummer 'artikel', artikel.bezeichnung,
    bp.anzahl,
    hersteller.nummer 'hersteller', hersteller.name
FROM bestellung
JOIN adresse a1 ON a1.id = bestellung.rechnungsadresse
JOIN adresse a2 ON a2.id = bestellung.lieferadresse
JOIN bestellposition bp ON bestellung.nummer = bp.bestellung
JOIN artikel ON artikel.nummer = bp.artikel
JOIN hersteller ON hersteller.nummer = artikel.hersteller
RIGHT JOIN kunde ON kunde.nummer = bestellung.kunde;

/* DCL = Data Control Language (Rechteverwaltung) */

/* TCL = Transaction Control Language */
