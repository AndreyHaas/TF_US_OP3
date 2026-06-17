package tag03.template.models;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Artikel {
    public static final Map<Integer, Artikel> artikels = new HashMap<>();

    private final int nummer;
    private String bezeichnung;
    private BigDecimal preis;
    private final Hersteller hersteller;

    public Artikel(int nummer, String bezeichnung, BigDecimal preis, Hersteller hersteller) {
        this.nummer = nummer;
        this.bezeichnung = bezeichnung;
        this.preis = preis;
        this.hersteller = hersteller;

        artikels.put(nummer, this);
    }

    public int getNummer() {
        return nummer;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public BigDecimal getPreis() {
        return preis;
    }

    public void setPreis(BigDecimal preis) {
        this.preis = preis;
    }

    public Hersteller getHersteller() {
        return hersteller;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Artikel.class.getSimpleName() + "[", "]")
                .add("nummer=" + nummer)
                .add("bezeichnung='" + bezeichnung + "'")
                .add("preis=" + preis)
                .add("hersteller=" + hersteller)
                .toString();
    }
}
