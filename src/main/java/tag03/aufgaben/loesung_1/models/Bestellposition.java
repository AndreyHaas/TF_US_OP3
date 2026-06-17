package tag03.aufgaben.loesung_1.models;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Bestellposition
{
    public static final List<Bestellposition> bestellpositionen = new ArrayList<Bestellposition>();

    private final Bestellung bestellung;
    private final Artikel artikel;
    private int anzahl;

    public Bestellposition(Bestellung bestellung, Artikel artikel, int anzahl)
    {
        this.bestellung = bestellung;
        this.artikel = artikel;
        this.anzahl = anzahl;

        bestellpositionen.add(this);
    }

    // Kein Setter für Bestellung, da sich die Objektreferenz nicht ändern darf.
    public Bestellung getBestellung()
    {
        return bestellung;
    }

    // Ebenfalls kein Setter.
    public Artikel getArtikel()
    {
        return artikel;
    }

    public int getAnzahl()
    {
        return anzahl;
    }

//    public void setAnzahl(int anzahl)
//    {
//        if (BestellpositionService.updateBestellposition(this, "anzahl", anzahl))
//            this.anzahl = anzahl;
//    }

    @Override
    public String toString()
    {
        return "Bestellposition{" +
                "bestellung=" + bestellung +
                ", artikel=" + artikel +
                ", anzahl=" + anzahl +
                ", gesamtpreis=" + artikel.getPreis().multiply(BigDecimal.valueOf(anzahl)) +
                '}';
    }
}
