# Sollten Probleme auftreten, mal folgendes probieren:
# REPAIR TABLE mysql.proc;

USE onlineshop;

DROP VIEW IF EXISTS selectAlleBestellungen;
DROP VIEW IF EXISTS selectAnzahlGekaufterProdukte;
DROP PROCEDURE IF EXISTS insertArtikel;
DROP PROCEDURE IF EXISTS updateArtikelPreis;

# Mit Views können erstellte SQL Queries abgespeichert werden.
# Ein wichtiger Einsatzzweck ist, damit Abfragen für die Entwickler einer
# Anwendung zu erstellen und die interne Struktur der Datenbank zu verbergen.
# Das erleichtert die Zusammenarbeit, wenn die Datenbank und die GUI Anwendung von
# unterschiedlichen Entwicklern erstellt werden.

CREATE VIEW IF NOT EXISTS selectAlleBestellungen AS
	SELECT kunde.nummer 'kunde', kunde.name 'kunde_name',
    	bestellung.nummer 'bestellung', bestellung.datum,
        a1.straßeNr 'rechnung', a2.straßeNr 'lieferung',
        artikel.nummer 'artikel', artikel.bezeichnung,
        bp.anzahl,
        hersteller.nummer 'hersteller', hersteller.name 'hersteller_name'
    FROM kunde
    JOIN bestellung ON kunde.nummer = bestellung.kunde
    JOIN adresse a1 ON bestellung.rechnungsadresse = a1.id
    JOIN adresse a2 ON bestellung.lieferadresse = a2.id
    JOIN bestellposition bp ON bestellung.nummer = bp.bestellung
    JOIN artikel ON bp.artikel = artikel.nummer
    JOIN hersteller ON artikel.hersteller = hersteller.nummer;

# Die erstellte View kann nun wie eine Tabelle abgefragt werden.
#SELECT * FROM selectAlleBestellungen;

# Spaltenbezeichnungen können angepasst werden. So wurde aus 'nummer' in der Tabelle kunde einfach nur 'kunde'
#SELECT * FROM selectAlleBestellungen WHERE kunde = 123123124;


# Pro Kunde die Gesamtzahl der gekauften Produkte. Ausgabe mit Kundennummer und Anzahl.
CREATE VIEW selectAnzahlGekaufterProdukte AS
    SELECT k.nummer, IFNULL(SUM(bp.anzahl), 0) anzahl
    FROM kunde k
    LEFT JOIN bestellung b ON k.nummer = b.kunde
    LEFT JOIN bestellposition bp ON b.nummer = bp.bestellung
    GROUP BY k.nummer;

#SELECT * FROM selectAnzahlGekaufterProdukte;


# Stored Procedures haben einen ähnlichen Zweck wie Views.
# Sie erlauben es uns, die interne Struktur der Datenbank zu verbergen und
# stattdessen Funktionen für die Bearbeitung der Daten bereit zu stellen.
# So muss der Entwickler der Anwendung nicht wissen, wie Spalten und Tabellen
# heißen, sondern muss nur den Namen der Parameter der Stored Procedure kennen.
# Dies erleichtert die Zusammenarbeit zwischen mehreren Entwicklern.
# Sollte sich zum Beispiel mal eine Tabelle ändern, müssen nur die Views und
# Procedures angepasst werden, aber nicht der Programmcode.
# Außerdem können wir durch Stored Procedures Schleifen und Verzweigungen, die für
# Inserts, Updates oder Deletes eventuell notwendig wären, auf die Datenbank
# auslagern.

DELIMITER $	# Der Delimiter markiert das Ende einer Anweisung.
			# Für Create Procedure müssen wir den Delimiter ändern, damit es keine Syntaxfehler gibt.

# Mit OUT können wir Rückgabe-Parameter angeben.
# Diese Variable füllen wir hier mit der erzeugten AUTO_INCREMENT ID (nummer), welche wir nach dem Ausführen der Prozedur abfragen können.

CREATE PROCEDURE insertArtikel (bez VARCHAR(100), p DECIMAL(8,2), h INT, OUT n INT) # bez = bezeichnung, p = preis, h = hersteller, n = nummer
BEGIN
	INSERT INTO artikel (bezeichnung, preis, hersteller)
    VALUES (bez, p, h); # Parameter als Values

    SELECT LAST_INSERT_ID() INTO n; # Der erstellte Primärschlüssel in den OUT-Parameter schreiben
END$

DELIMITER ;

# Aufruf der Prozedur mit CALL
CALL insertArtikel('BluRay Player Ultra-Deluxe', 149.90, 1, @nummer);
SELECT @nummer; # Ausgabe der Variable @nummer, die nun den Primary Key des neuen Artikels beinhaltet.
SELECT * FROM artikel;


DELIMITER $
CREATE PROCEDURE updateArtikelPreis (nummer INT, preis DECIMAL(8,2))
BEGIN
	UPDATE artikel SET artikel.preis = preis WHERE artikel.nummer = nummer;
END$
DELIMITER ;

# Wir können in SQL Variablen erstellen und diesen Werte zuweisen.
SET @nummer = (
    SELECT nummer
    FROM artikel
    WHERE bezeichnung = 'BluRay Player Ultra-Deluxe' LIMIT 1);

SELECT @nummer;

# Und diese Variablen dann auch zum Aufruf von Views und Stored Procedures verwenden
CALL updateArtikelPreis(@nummer, 99.99);

SELECT * FROM artikel;

# In Stored Procedures sind auch Verzweigungen und Schleifen möglich. Alles zu Stored Procedures findet ihr hier:
# https://www.mysqltutorial.org/introduction-to-sql-stored-procedures.aspx