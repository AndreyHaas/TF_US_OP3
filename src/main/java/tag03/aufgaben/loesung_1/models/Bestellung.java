package tag03.aufgaben.loesung_1.models;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Bestellung {
    public static final Map<Integer, Bestellung> bestellungen = new HashMap<>();

    private final int nummer;
    private final LocalDateTime datum;

    private final Kunde kunde;
    private Adresse rechnungsadresse;
    private Adresse lieferadresse;


    public Bestellung(int nummer, LocalDateTime datum, Kunde kunde, Adresse rechnungsadresse, Adresse lieferadresse) {
        this.nummer = nummer;
        this.datum = datum;
        this.kunde = kunde;
        this.rechnungsadresse = rechnungsadresse;
        this.lieferadresse = lieferadresse;

        bestellungen.put(nummer, this);
    }

    // Kein Setter, da Nummer Primärschlüssel ist und sich nicht ändern darf.
    public int getNummer() {
        return nummer;
    }

    // Das Datum sollte sich auch nicht ändern lassen, darum kein Setter,
    public LocalDateTime getDatum() {
        return datum;
    }

    // Die Referenz auf den Kunden soll sich auch nicht ändern können.
    public Kunde getKunde() {
        return kunde;
    }

    public Adresse getRechnungsadresse() {
        return rechnungsadresse;
    }

    public void setRechnungsadresse(Adresse rechnungsadresse) {
        this.rechnungsadresse = rechnungsadresse;
    }

    public Adresse getLieferadresse() {
        return lieferadresse;
    }

    public void setLieferadresse(Adresse lieferadresse) {
        this.lieferadresse = lieferadresse;
    }

    @Override
    public String toString() {
        return "Bestellung{" +
                "nummer=" + nummer +
                ", datum=" + datum +
                ", kunde=" + kunde +
                ", rechnungsadresse=" + rechnungsadresse +
                ", lieferadresse=" + lieferadresse +
                '}';
    }
}
