-- Bezüglich den Datentypen von MySQL und Java:
-- https://dev.mysql.com/doc/ndbapi/en/mccj-using-clusterj-mappings.html

DROP DATABASE IF EXISTS universität;
CREATE DATABASE universität;
USE universität;

CREATE TABLE kurs (
	kursKürzel CHAR(6) PRIMARY KEY,
	kursName VARCHAR(100) NOT NULL
);

CREATE TABLE zimmer (
	zimmerNummer INT PRIMARY KEY,
	telefonNummer INT NOT NULL UNIQUE
);

CREATE TABLE student (
	studentNummer INT PRIMARY KEY CHECK (StudentNummer <= 99999),
	studentNachname TEXT NOT NULL,
	studentVorname TEXT NOT NULL,
	geburtsdatum DATE NOT NULL,
	zimmerNummer INT,
	FOREIGN KEY (zimmerNummer) REFERENCES zimmer (zimmerNummer)
);

CREATE TABLE kursbelegung (
	kursKürzel CHAR(6),
	studentNummer INT,
	semester CHAR(3) NOT NULL,
	note DOUBLE(2,1),
	PRIMARY KEY (kursKürzel, studentNummer),
	FOREIGN KEY (kursKürzel) REFERENCES kurs (kursKürzel),
	FOREIGN KEY (studentNummer) REFERENCES student (studentNummer)
);

INSERT INTO kurs (kursKürzel, kursName) VALUES
('Mat122', 'Zählen bis 10'),
('Mat130', 'Rechnen mit Fingern'),
('Mat120', 'Zählen bis 3');

INSERT INTO zimmer (zimmerNummer, telefonNummer) VALUES
(120, 2136),
(237, 3127);

INSERT INTO student (studentNummer, studentVorname, studentNachname, geburtsdatum, zimmerNummer) VALUES
(3215, 'Mike', 'Jonas', '1991-02-23', 120),
(3456, 'Klaus', 'Schmidt', '1990-03-05', 237),
(4576, 'Paul', 'Neider', '1989-07-17', 120);

INSERT INTO kursbelegung (kursKürzel, studentNummer, semester, note) VALUES
('Mat122', 3215, 'W88', 1.4),
('Mat122', 3456, 'W87', 3.4),
('Mat130', 3456, 'S87', 2.9),
('Mat120', 4576, 'S88', 2.1);


CREATE VIEW kursbelegungView AS
	SELECT kurs.kursKürzel, kursName, student.studentNummer, studentVorname, studentNachname, semester, note
	FROM kursbelegung
	JOIN kurs ON kursbelegung.kursKürzel = kurs.kursKürzel
	JOIN student ON kursbelegung.studentNummer = student.studentNummer;

DELIMITER $$
CREATE PROCEDURE kursbelegungNeu
(kursKürzel CHAR(6), studentNummer INT, semester CHAR(3), note DOUBLE(2,1))
BEGIN
	INSERT INTO kursbelegung (kursKürzel, studentNummer, semester, note)
	VALUES (kursKürzel, studentNummer, semester, note);
END$$


DELIMITER $$
CREATE PROCEDURE KursbelegungNote
(kursKürzel CHAR(6), studentNummer INT, note DOUBLE(2,1))
BEGIN
	UPDATE kursbelegung k SET k.note = note WHERE k.kursKürzel = kursKürzel AND k.studentNummer = studentNummer;
END$$

SELECT * FROM kursbelegungView;

CALL kursbelegungNeu('Mat120', 3215, 'S21', null);

CALL kursbelegungNote('Mat120', 4576, 2.1);
