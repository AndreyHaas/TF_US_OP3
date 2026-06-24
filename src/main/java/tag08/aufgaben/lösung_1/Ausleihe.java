package tag08.aufgaben.lösung_1;

import java.time.LocalDate;
import java.util.HashMap;

public class Ausleihe
{
    public static final HashMap<Buch, Ausleihe> ausgeliehenMap = new HashMap<>();

    private final Buch buch;
    private final Kunde kunde;
    private final LocalDate datum;
    private final int dauer;

    public Ausleihe(Buch buch, Kunde kunde, LocalDate datum, int dauer)
    {
        this.buch = buch;
        this.kunde = kunde;
        this.datum = datum;
        this.dauer = dauer;

        ausgeliehenMap.put(buch, this);
    }

    @Override
    public String toString()
    {
        return "Ausleihe{" +
                "buch=" + buch +
                ", kunde=" + kunde +
                ", datum=" + datum +
                ", dauer=" + dauer +
                '}';
    }
}
