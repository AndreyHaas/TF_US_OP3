DROP DATABASE IF EXISTS bank;
CREATE DATABASE bank;
USE bank;
CREATE TABLE kunde (
id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(100) NOT NULL
);
CREATE TABLE konto (
nummer CHAR(10) PRIMARY KEY,
kontostand DOUBLE NOT NULL,
kunde INT NOT NULL,
FOREIGN KEY (kunde) REFERENCES kunde (id)
);
INSERT INTO kunde (name) VALUES ('WBS Berlin'), ('WBS München');
INSERT INTO konto (nummer, kontostand, kunde) VALUES ('0000123456',1000,1), ('0000987654',2000,2);
SELECT * FROM kunde JOIN konto ON kunde.id = konto.kunde;

