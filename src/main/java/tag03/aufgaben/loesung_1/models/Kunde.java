package tag03.aufgaben.loesung_1.models;

import java.util.HashMap;
import java.util.Map;

public class Kunde {
    public static final Map<Integer, Kunde> kunden = new HashMap<>();

    private final int nummer;
    private String name;

    public Kunde(int nummer, String name) {
        this.nummer = nummer;
        this.name = name;

        kunden.put(nummer, this);
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
//        if (KundeService.updateKunde(this, "name", name))
//            this.name = name;
//    }

    @Override
    public String toString() {
        return "Kunde{" +
                "nummer=" + nummer +
                ", name='" + name + '\'' +
                '}';
    }
}
