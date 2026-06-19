-- Datenbank erstellen (falls nicht vorhanden)
CREATE DATABASE IF NOT EXISTS Universitätsverwaltung;

-- Zur neu erstellten Datenbank wechseln
USE Universitätsverwaltung;

-- Tabelle: Zimmer
CREATE TABLE IF NOT EXISTS Zimmer
(
    ZNr     INTEGER PRIMARY KEY,
    Telefon VARCHAR(20) NOT NULL
);

-- Tabelle: Student
CREATE TABLE IF NOT EXISTS Student
(
    SNr     INTEGER PRIMARY KEY CHECK (SNr <= 99999),
    SName   VARCHAR(50) NOT NULL,
    Vorname VARCHAR(50) NOT NULL,
    GDatum  DATE        NOT NULL,
    ZNr     INTEGER     NOT NULL,
    FOREIGN KEY (ZNr) REFERENCES Zimmer (ZNr)
);

-- Tabelle: Kurs
CREATE TABLE IF NOT EXISTS Kurs
(
    KNr   VARCHAR(10) PRIMARY KEY,
    KName VARCHAR(100) NOT NULL
);

-- Tabelle: Belegung
CREATE TABLE IF NOT EXISTS Belegung
(
    SNr      INTEGER,
    KNr      VARCHAR(10),
    Note     DECIMAL(2, 1),
    Semester VARCHAR(4) NOT NULL,
    PRIMARY KEY (SNr, KNr),
    FOREIGN KEY (SNr) REFERENCES Student (SNr),
    FOREIGN KEY (KNr) REFERENCES Kurs (KNr)
);

-- Zimmer
INSERT INTO Zimmer (ZNr, Telefon)
VALUES (120, '0721-1234567'),
       (237, '0721-2345678'),
       (301, '0721-3456789');

-- Studenten
INSERT INTO Student (SNr, SName, Vorname, GDatum, ZNr)
VALUES (3215, 'Jonas', 'Mike', '1991-02-23', 120),
       (3456, 'Schmidt', 'Klaus', '1990-03-05', 237),
       (4576, 'Neider', 'Paul', '1989-07-17', 120);

-- Kurse
INSERT INTO Kurs (KNr, KName)
VALUES ('Mat122', 'Zählen bis 10'),
       ('Phy120', 'Grundlagen der Schwerkraft'),
       ('Wiw330', 'Geldausgeben ganz leicht'),
       ('Mat130', 'Rechnen mit Fingern'),
       ('Phy230', 'Schweben für Anfänger'),
       ('Mat120', 'Zählen bis 3');

-- Belegungen
INSERT INTO Belegung (SNr, KNr, Note, Semester)
VALUES (3215, 'Mat122', 1.4, 'W88'),
       (3215, 'Phy120', 2.5, 'S88'),
       (3215, 'Wiw330', 3.1, 'W89'),
       (3456, 'Mat122', 3.2, 'W87'),
       (3456, 'Mat130', 2.9, 'S87'),
       (4576, 'Phy230', 2.8, 'W88'),
       (4576, 'Mat120', 2.1, 'S88');

-- 1. Alle Studenten mit ihren Zimmernummern und Telefonnummern
SELECT s.SNr     AS StudentNummer,
       s.SName   AS StudentName,
       s.Vorname AS StudentVorname,
       s.ZNr     AS ZimmerNummer,
       z.Telefon AS ZimmerTelefon
FROM Student s
         JOIN Zimmer z ON s.ZNr = z.ZNr
ORDER BY s.SNr;

-- 2. Alle Kurse mit Noten für Student Mike Jonas (SNr = 3215)
SELECT k.KNr      AS KurseNummer,
       k.KName    AS KurseName,
       b.Note     AS BelegungNote,
       b.Semester AS BelegungSemester
FROM Belegung b
         JOIN Kurs k ON b.KNr = k.KNr
WHERE b.SNr = 3215
ORDER BY b.Semester;

-- 3. Durchschnittsnote aller Studenten
SELECT s.SNr                 AS StudentNummer,
       s.SName               AS StudentName,
       s.Vorname             AS StudentVorname,
       ROUND(AVG(b.Note), 2) AS Durchschnittsnote
FROM Student s
         JOIN Belegung b ON s.SNr = b.SNr
GROUP BY s.SNr, s.SName, s.Vorname
ORDER BY Durchschnittsnote;

-- 4. Studenten, die den Kurs "Zählen bis 10" belegt haben
SELECT s.SNr      AS StudentNummer,
       s.SName    AS StudentName,
       s.Vorname  AS StudentVorname,
       k.KName    AS KursName,
       b.Note     AS BelegungNote,
       b.Semester AS BelegungSemester
FROM Student s
         JOIN Belegung b ON s.SNr = b.SNr
         JOIN Kurs k ON b.KNr = k.KNr
WHERE k.KName = 'Zählen bis 10'
ORDER BY b.Note;

-- 5. Alle Studenten aus Zimmer 120
SELECT s.SNr     AS StudentNummer,
       s.SName   AS StudentName,
       s.Vorname AS StudentVorname,
       s.GDatum  AS GeburtsDatum
FROM Student s
WHERE s.ZNr = 120
ORDER BY s.SName;

-- 6. Wie viele Studenten wohnen in welchem Zimmer?
SELECT z.ZNr        AS ZimmerNummer,
       z.Telefon    AS ZimmerTelefon,
       COUNT(s.SNr) AS AnzahlStudenten
FROM Zimmer z
         LEFT JOIN Student s ON z.ZNr = s.ZNr
GROUP BY z.ZNr, z.Telefon
ORDER BY z.ZNr;

-- 1. Telefonnummer von Zimmer 120 ändern
UPDATE Zimmer
SET Telefon = '0721-9876543'
WHERE ZNr = 120;

-- 2. Note von Student 3215 im Kurs Mat122 ändern
UPDATE Belegung
SET Note = 1.7
WHERE SNr = 3215
  AND KNr = 'Mat122';

-- 1. Student 4576 aus der Belegungstabelle löschen
DELETE
FROM Belegung
WHERE SNr = 4576;

-- 2. Student 4576 aus der Studententabelle löschen
DELETE
FROM Student
WHERE SNr = 4576;

-- Alle Tabellen anzeigen
SHOW TABLES;

-- Struktur der Tabellen anzeigen
DESCRIBE Zimmer;
DESCRIBE Student;
DESCRIBE Kurs;
DESCRIBE Belegung;