package tag08.aufgaben.lösung_1;

import java.util.HashMap;

public class Buch
{
    public static final HashMap<Integer, Buch> bücher = new HashMap<Integer, Buch>();

    private final int id;
    private String titel;

    public Buch(int id, String titel)
    {
        this.id = id;
        this.titel = titel;
        bücher.put(id, this);
    }

    @Override
    public String toString()
    {
        return "Buch{" +
                "id=" + id +
                ", titel='" + titel + '\'' +
                '}';
    }

    public int getId()
    {
        return id;
    }
}
