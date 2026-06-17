package tag03.aufgaben.lösung_2;


import tag03.aufgaben.loesung_1.MySQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class Baustelle
{
    public static final HashMap<String, Baustelle> baustellen = new HashMap<>();
    private final String baustellennummer;
    private String baustellenbezeichnung;

    public Baustelle(String baustellennummer, String baustellenbezeichnung)
    {
        this.baustellennummer = baustellennummer;
        this.baustellenbezeichnung = baustellenbezeichnung;

        baustellen.put(baustellennummer, this);
    }

    @Override
    public String toString()
    {
        return "Baustelle{" +
                "baustellennummer='" + baustellennummer + '\'' +
                ", baustellenbezeichnung='" + baustellenbezeichnung + '\'' +
                '}';
    }

    /**
     * Fragt von der Datenbank die Baustellen ab und erzeugt die Model-Objekte.
     */
    public static void selectBaustelle()
    {
        try (Connection connection = MySQL.getConnection();
             Statement statement = connection.createStatement())
        {

            ResultSet rs = statement.executeQuery("select * from baustelle");

            while(rs.next())
            {
                String baustellennummer = rs.getString("baustellennummer");
                String baustellenbezeichnung = rs.getString("baustellenbezeichnung");

                new Baustelle(baustellennummer, baustellenbezeichnung);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
}
