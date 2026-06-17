package tag03.aufgaben.loesung_1.models;

// Model-Klassen sind Klassen, welche rein dazu da sind, die Daten zu halten.


import java.util.HashMap;
import java.util.Map;

/**
 * Model-Klasse für Hersteller.
 */
public class Hersteller {
    // Wir nutzen hier eine HashMap, um einfacher an die Hersteller zu kommen, wenn wir diese für Artikel speichern möchten.
    public static final Map<Integer, Hersteller> hersteller = new HashMap<>();

    private final int nummer;
    private String name;

    public Hersteller(int nummer, String name) {
        this.nummer = nummer;
        this.name = name;

        hersteller.put(nummer, this);
    }

    // Kein Setter, da Nummer Primärschlüssel ist und sich nicht ändern darf.
    public int getNummer() {
        return nummer;
    }

    public String getName() {
        return name;
    }

//    public void setName(String name)
//    {
//        // Wird der Name im Model geändert, aktualisieren wir sofort die Datenbank
//        // und wenn das erfolgreich war, ändern wir auch das Attribut des Objektes.
//        if (HerstellerService.updateHersteller(this, "name", name))
//            this.name = name;
//    }

    // toString() überschreiben, für eine einfache Ausgabe aller Attribute.
    @Override
    public String toString() {
        return "Hersteller{" +
                "nummer=" + nummer +
                ", name='" + name + '\'' +
                '}';
    }
}
