DROP DATABASE IF EXISTS firmenverwaltung;

CREATE DATABASE firmenverwaltung;

USE firmenverwaltung;

CREATE TABLE IF NOT EXISTS mitarbeiter (
    mitarbeiternummer VARCHAR(50) PRIMARY KEY,
    email VARCHAR(150) UNIQUE,
    pin_hash VARBINARY(255),
    vorname VARCHAR(100),
    nachname VARCHAR(100),
    einstellungsjahr INT,
    einstellungsmonat INT
);