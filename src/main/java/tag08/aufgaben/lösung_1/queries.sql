

DROP DATABASE IF EXISTS buchverleih;
CREATE DATABASE buchverleih;
USE buchverleih;
CREATE TABLE kunde (
    nummer INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    guthaben INT NOT NULL
);

CREATE TABLE buch (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titel VARCHAR(100) NOT NULL
);

CREATE TABLE ausleihe (
    buch INT PRIMARY KEY,
    kunde INT NOT NULL,
    datum DATE NOT NULL,
    dauer INT NOT NULL,
    FOREIGN KEY (buch) REFERENCES buch (id),
    FOREIGN KEY (kunde) REFERENCES kunde (nummer)
);

INSERT INTO kunde (name, guthaben) VALUES
("Ein Kunde", 10);

INSERT INTO buch (titel) VALUES
("Discworld");
