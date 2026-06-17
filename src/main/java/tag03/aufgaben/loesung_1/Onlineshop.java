package tag03.aufgaben.loesung_1;


import tag03.aufgaben.loesung_1.models.Artikel;
import tag03.aufgaben.loesung_1.models.Bestellposition;
import tag03.aufgaben.loesung_1.models.Hersteller;
import tag03.aufgaben.loesung_1.models.services.*;

public class Onlineshop {
    public static void main(String[] args) {
        // Reihenfolge beachten: Wir müssen zuerst Hersteller laden,
        // damit wir für die Artikel auch die Hersteller-Referenzen speichern können
        HerstellerService.selectHersteller();
        ArtikelService.selectArtikel();

        for (Hersteller h : Hersteller.hersteller.values())
            System.out.println(h); // toString() wird automatisch durch println aufgerufen
        for (Artikel a : Artikel.artikel.values())
            System.out.println(a);

        KundeService.selectKunde();
        AdresseService.selectAdresse();
        BestellungService.selectBestellung();
        BestellpositionService.selectBestellposition();

        for (Bestellposition b : Bestellposition.bestellpositionen)
            System.out.println(b);
    }
}
