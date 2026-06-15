CREATE DATABASE Universitaet;
USE Universitaet;

CREATE TABLE Zimmer (
	ZimmerNummer INT PRIMARY KEY,
	Telefonnummer INT UNIQUE
);

CREATE TABLE Kurs (
	KursKürzel CHAR(6) PRIMARY KEY,
	Name VARCHAR(100) NOT NULL
);

CREATE TABLE Student (
	StudentNummer INT PRIMARY KEY CHECK (StudentNummer <= 99999 AND StudentNummer > 0),
	Nachname VARCHAR(100) NOT NULL,
	Vorname VARCHAR(100) NOT NULL,
	Geburtsdatum DATE NOT NULL,
	ZimmerNummer INT,
    FOREIGN KEY (ZimmerNummer) REFERENCES Zimmer (ZimmerNummer)
);

CREATE TABLE Kursbelegung (
	StudentNummer INT,
	KursKürzel CHAR(6),
	Semester CHAR(3) NOT NULL,
	Note DECIMAL(2,1),
	PRIMARY KEY (StudentNummer, KursKürzel), -- Primary Key Spalten sind automatisch NOT NULL und bei zusammengesetzten Schlüsseln in der Kombination UNIQUE
    FOREIGN KEY (StudentNummer) REFERENCES Student (StudentNummer),
    FOREIGN KEY (KursKürzel) REFERENCES Kurs (KursKürzel)
);

INSERT INTO Zimmer (ZimmerNummer, Telefonnummer) VALUES
(120, 2136),
(237, 3127);
SELECT * FROM Zimmer;

INSERT INTO Kurs (KursKürzel, Name) VALUES
('Mat122', 'Zählen bis 10'),
('Phy120', 'Grundlagen der Schwerkraft'),
('Wiw330', 'Geldausgeben ganz leicht'),
('Mat130', 'Rechnen mit Fingern'),
('Phy230', 'Schweben für Anfänger'),
('Mat120', 'Zählen bis 3');
SELECT * FROM Kurs;

INSERT INTO Student (StudentNummer, Nachname, Vorname, Geburtsdatum, ZimmerNummer) VALUES
(3215, 'Jonas', 'Mike', '1991-02-23', 120),
(3456, 'Klaus', 'Schmidt', '1990-03-05', 237),
(4576, 'Paul', 'Neider', '1989-07-17', 120),
(1111, 'Lisa', 'Müller', '1990-04-15', 237);


SELECT * FROM Student;

INSERT INTO Kursbelegung (StudentNummer, KursKürzel, Semester, Note) VALUES
(3215,'Mat122','W88',1.4),
(3215,'Phy120','S88',2.5),
(3215,'Wiw330','W89',3.1),
(3456,'Mat122','W87',3.2),
(3456,'Mat130','S87',2.9),
(4576,'Phy230','W88',2.8),
(4576,'Mat120','S88',2.1);
SELECT * FROM Kursbelegung;

# Durch die Check-Einschränkung bei Student können keine Studenten mit Nummer > 99.999 und <= 0 angelegt werden
#INSERT INTO Student (StudentNummer, Vorname, Nachname, Geburtsdatum) VALUES
#(100000, 'Vorname', 'Nachname', '1990-01-01');