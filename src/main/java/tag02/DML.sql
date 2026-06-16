--# DML - DATA MANIPULATION LANGUAGE

# DML
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

--#DQL = DATA QUERY LANGUAGE

SELECT * FROM kunde;
SELECT * FROM artikel;

SELECT kunde.nummer 'kunde', kunde.name,
	bestellung.nummer 'bestellung', bestellung.datum,
    a1.strasseNr 'rechnung', a2.strasseNr 'lieferung',
    artikel.nummer 'artikel', artikel.bezeichnung,
    bp.anzahl, hersteller 'hersteller',
    hersteller.name
FROM kunde
LEFT JOIN bestellung ON kunde.nummer = bestellung.kunde
LEFT JOIN adresse a1 ON a1.id = bestellung.rechnungsadresse
LEFT JOIN adresse a2 ON a2.id = bestellung.rechnungsadresse
LEFT JOIN bestellposition bp ON bestellung.nummer = bp.bestellung
LEFT JOIN artikel on artikel.nummer = bp.artikel
LEFT JOIN hersteller on hersteller.nummer = artikel.hersteller;