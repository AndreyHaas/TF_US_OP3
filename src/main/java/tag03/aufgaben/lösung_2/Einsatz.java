package tag03.aufgaben.lösung_2;



import tag03.aufgaben.loesung_1.MySQL;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Einsatz
{
    public static final ArrayList<Einsatz> einsätze = new ArrayList<>();

    private Mitarbeiter mitarbeiter;
    private Baustelle baustelle;
    private int stunden;

    public Einsatz(Mitarbeiter mitarbeiter, Baustelle baustelle, int stunden)
    {
        this.mitarbeiter = mitarbeiter;
        this.baustelle = baustelle;
        this.stunden = stunden;

        einsätze.add(this);
    }

    @Override
    public String toString()
    {
        return "Einsatz{" +
                "mitarbeiter='" + mitarbeiter + '\'' +
                ", baustelle='" + baustelle + '\'' +
                ", stunden=" + stunden +
                '}';
    }

    /**
     * Fragt von der Datenbank die Einsätze ab und erzeugt die Model-Objekte.
     */
    public static void selectEinsatz()
    {
        try (Connection connection = MySQL.getConnection();
             Statement statement = connection.createStatement())
        {

            ResultSet rs = statement.executeQuery("select * from einsatz");

            while(rs.next())
            {
                Mitarbeiter mitarbeiter = Mitarbeiter.mitarbeiter.get(rs.getString("mitarbeiternummer"));
                Baustelle baustelle = Baustelle.baustellen.get(rs.getString("baustellennummer"));

                int stunden = rs.getInt("stunden");

                new Einsatz(mitarbeiter, baustelle, stunden);
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
