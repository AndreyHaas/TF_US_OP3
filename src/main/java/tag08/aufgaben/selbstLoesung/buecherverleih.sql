DROP DATABASE IF EXISTS buecherverleih;

CREATE DATABASE buecherverleih;

USE buecherverleih;

CREATE TABLE buch (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titel VARCHAR(255) NOT NULL,
    ausgeliehen BOOLEAN DEFAULT FALSE
);

CREATE TABLE kunde (
    nummer INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    guthaben DECIMAL(10,2) NOT NULL DEFAULT 0.00
);

CREATE TABLE ausleihe (
    buch_id INT NOT NULL,
    kunde_nr INT NOT NULL,
    datum DATE NOT NULL,
    dauer INT NOT NULL,  -- in Tagen
    PRIMARY KEY (buch_id),
    FOREIGN KEY (buch_id) REFERENCES buch(id),
    FOREIGN KEY (kunde_nr) REFERENCES kunde(nummer)
);

INSERT INTO buch (titel) VALUES
    ('Der Herr der Ringe'),
    ('Harry Potter und der Stein der Weisen'),
    ('1984'),
    ('Die unendliche Geschichte');

INSERT INTO kunde (name, guthaben) VALUES
    ('Anna Schmidt', 50.00),
    ('Max Mustermann', 30.00),
    ('Elena Dobrynina', 10.00);

DELIMITER $$

CREATE PROCEDURE buch_ausleihen(
    IN buchId INT,
    IN kundeNr INT,
    IN tage INT
)
BEGIN
    DECLARE buchVerfuegbar BOOLEAN;
    DECLARE kundenGuthaben DECIMAL(10,2);
    DECLARE kosten DECIMAL(10,2);

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    START TRANSACTION;

    -- 1. Prüfen: Ist das Buch verfügbar?
    SELECT ausgeliehen INTO buchVerfuegbar FROM buch WHERE id = buchId;
    IF buchVerfuegbar THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Buch ist bereits ausgeliehen!';
    END IF;

    -- 2. Prüfen: Hat der Kunde genug Guthaben?
    SELECT guthaben INTO kundenGuthaben FROM kunde WHERE nummer = kundeNr;
    SET kosten = tage * 1.00;  -- 1€ pro Tag

    IF kundenGuthaben < kosten THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Nicht genug Guthaben!';
    END IF;

    -- 3. Guthaben abbuchen
    UPDATE kunde
    SET guthaben = guthaben - kosten
    WHERE nummer = kundeNr;

    -- 4. Buch als ausgeliehen markieren
    UPDATE buch
    SET ausgeliehen = TRUE
    WHERE id = buchId;

    -- 5. Ausleihe eintragen
    INSERT INTO ausleihe (buch_id, kunde_nr, datum, dauer)
    VALUES (buchId, kundeNr, CURDATE(), tage);

    COMMIT;
    SELECT 'Buch erfolgreich ausgeliehen!' AS Meldung;
END$$

DELIMITER ;

-- 7. Stored Procedure: Buch zurückgeben
DELIMITER $$

CREATE PROCEDURE buch_zurueckgeben(
    IN buchId INT
)
BEGIN
    DECLARE buchAusgeliehen BOOLEAN;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    START TRANSACTION;

    -- Prüfen: Ist das Buch überhaupt ausgeliehen?
    SELECT ausgeliehen INTO buchAusgeliehen FROM buch WHERE id = buchId;
    IF NOT buchAusgeliehen THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Buch war nicht ausgeliehen!';
    END IF;

    -- Ausleihe löschen
    DELETE FROM ausleihe WHERE buch_id = buchId;

    -- Buch als verfügbar markieren
    UPDATE buch
    SET ausgeliehen = FALSE
    WHERE id = buchId;

    COMMIT;
    SELECT 'Buch erfolgreich zurückgegeben!' AS Meldung;
END$$

DELIMITER ;

-- 8. View: Alle ausgeliehenen Bücher mit Kunden
CREATE VIEW view_ausleihen AS
SELECT
    buch.titel AS Buch,
    kunde.name AS Kunde,
    ausleihe.datum AS Ausgeliehen_am,
    ausleihe.dauer AS Tage,
    DATEDIFF(CURDATE(), ausleihe.datum) AS Tage_seit_Ausleihe
FROM ausleihe
JOIN buch ON ausleihe.buch_id = buch.id
JOIN kunde ON ausleihe.kunde_nr = kunde.nummer;