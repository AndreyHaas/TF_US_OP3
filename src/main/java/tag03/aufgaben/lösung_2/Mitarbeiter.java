package tag03.aufgaben.lösung_2;


import tag03.aufgaben.loesung_1.MySQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class Mitarbeiter
{
    public static final HashMap<String, Mitarbeiter> mitarbeiter = new HashMap<>();

    private final String mitarbeiternummer;
    private boolean maschinenberechtigung;
    private String mitarbeitername;
    private String mitarbeiterPLZ;
    private Abteilung abteilung;

    public Mitarbeiter(String mitarbeiternummer, boolean maschinenberechtigung, String mitarbeitername, String mitarbeiterPLZ, Abteilung abteilung)
    {
        this.mitarbeiternummer = mitarbeiternummer;
        this.maschinenberechtigung = maschinenberechtigung;
        this.mitarbeitername = mitarbeitername;
        this.mitarbeiterPLZ = mitarbeiterPLZ;
        this.abteilung = abteilung;

        mitarbeiter.put(mitarbeiternummer, this);
    }

    @Override
    public String toString()
    {
        return "Mitarbeiter{" +
                "mitarbeiternummer='" + mitarbeiternummer + '\'' +
                ", maschinenberechtigung=" + maschinenberechtigung +
                ", mitarbeitername='" + mitarbeitername + '\'' +
                ", mitarbeiterPLZ='" + mitarbeiterPLZ + '\'' +
                ", abteilung=" + abteilung +
                '}';
    }

    /**
     * Fragt von der Datenbank die Mitarbeiter ab und erzeugt die Model-Objekte.
     */
    public static void selectMitarbeiter()
    {
        try (Connection connection = MySQL.getConnection();
             Statement statement = connection.createStatement())
        {
            ResultSet rs = statement.executeQuery("select * from mitarbeiter");

            while(rs.next())
            {
                String mitarbeiternummer = rs.getString("mitarbeiternummer");
                boolean maschinenberechtigung = rs.getBoolean("maschinenberechtigung");
                String mitarbeitername = rs.getString("mitarbeitername");
                String mitarbeiterPLZ = rs.getString("mitarbeiterPLZ");

                // Weil wir Abteilungen in einer HashMap speichern, können wir die passende Abteilung ganz einfach über die Keys herausfinden
                Abteilung abteilung = Abteilung.abteilungen.get(rs.getInt("abteilungsnummer"));

                new Mitarbeiter(mitarbeiternummer, maschinenberechtigung, mitarbeitername, mitarbeiterPLZ, abteilung);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
