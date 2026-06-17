package tag03.aufgaben.loesung_1.models.services;


import tag03.aufgaben.loesung_1.MySQL;
import tag03.aufgaben.loesung_1.models.Artikel;
import tag03.aufgaben.loesung_1.models.Bestellposition;
import tag03.aufgaben.loesung_1.models.Bestellung;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BestellpositionService {
    public static void selectBestellposition() {
        try (Connection connection = MySQL.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("select * from bestellposition");
            while (rs.next()) {
                Bestellung bestellung = Bestellung.bestellungen.get(rs.getInt("bestellung"));
                Artikel artikel = Artikel.artikel.get(rs.getInt("artikel"));

                int anzahl = rs.getInt("anzahl");

                new Bestellposition(bestellung, artikel, anzahl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
