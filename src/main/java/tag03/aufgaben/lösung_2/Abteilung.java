package tag03.aufgaben.lösung_2;

import tag03.aufgaben.loesung_1.MySQL;// Ich importiere hier unsere MySQL Klasse aus einem anderen Package, anstatt sie zu kopieren.

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class Abteilung {
    public static final HashMap<Integer, Abteilung> abteilungen = new HashMap<>();
    private int abteilungsnummer;
    private String abteilungsname;

    public Abteilung(int abteilungsnummer, String abteilungsname) {
        this.abteilungsnummer = abteilungsnummer;
        this.abteilungsname = abteilungsname;

        abteilungen.put(abteilungsnummer, this);
    }

    @Override
    public String toString() {
        return "Abteilung{" +
                "abteilungsnummer=" + abteilungsnummer +
                ", abteilungsname='" + abteilungsname + '\'' +
                '}';
    }

    /**
     * Fragt von der Datenbank die Abteilungen ab und erzeugt die Model-Objekte.
     */
    public static void selectAbteilung() {
        try (Connection connection = MySQL.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("select * from abteilung");

            while (rs.next()) {
                int abteilungsnummer = rs.getInt("abteilungsnummer");
                String abteilungsname = rs.getString("abteilungsname");

                new Abteilung(abteilungsnummer, abteilungsname);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
