package tag08.aufgaben.lösung_1;

import java.util.HashMap;

public class Kunde
{
    public static final HashMap<Integer, Kunde> kunden = new HashMap<>();

    private final int nummer;
    private String name;
    private int guthaben;

    public Kunde(int nummer, String name, int guthaben)
    {
        this.nummer = nummer;
        this.name = name;
        this.guthaben = guthaben;

        kunden.put(nummer, this);
    }

    @Override
    public String toString()
    {
        return "Kunde{" +
                "nummer=" + nummer +
                ", name='" + name + '\'' +
                ", guthaben=" + guthaben +
                '}';
    }

    public int getNummer()
    {
        return nummer;
    }

    public int getGuthaben()
    {
        return guthaben;
    }

    public void addGuthaben(int betrag)
    {
        guthaben += betrag;
    }
}
