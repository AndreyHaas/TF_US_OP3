package tag03.aufgaben.loesung_1.models;

import java.util.HashMap;
import java.util.Map;

public class Adresse {
    public static final Map<Integer, Adresse> adressen = new HashMap<>();

    private final int id;
    private String straßeNr;
    private String plz;
    private String ort;

    private final Kunde kunde;


    public Adresse(int id, String straßeNr, String plz, String ort, Kunde kunde) {
        this.id = id;
        this.straßeNr = straßeNr;
        this.plz = plz;
        this.ort = ort;
        this.kunde = kunde;

        adressen.put(id, this);
    }

    public int getId() {
        return id;
    }

    public String getStraßeNr() {
        return straßeNr;
    }

    public void setStraßeNr(String straßeNr) {
        this.straßeNr = straßeNr;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    // Kein Setter zu Kunde, da sich die Objektreferenz nicht ändern darf.
    public Kunde getKunde() {
        return kunde;
    }

    @Override
    public String toString() {
        return "Adresse{" +
                "id=" + id +
                ", straßeNr='" + straßeNr + '\'' +
                ", plz='" + plz + '\'' +
                ", ort='" + ort + '\'' +
                ", kunde=" + kunde +
                '}';
    }
}
