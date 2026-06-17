package tag03.aufgaben.loesung_1.models.services;


import tag03.aufgaben.loesung_1.MySQL;
import tag03.aufgaben.loesung_1.models.Adresse;
import tag03.aufgaben.loesung_1.models.Bestellung;
import tag03.aufgaben.loesung_1.models.Kunde;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class BestellungService {
    public static void selectBestellung() {
        try (Connection connection = MySQL.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("select * from bestellung");
            while (rs.next()) {
                int nummer = rs.getInt("nummer");
                LocalDateTime datum = rs.getObject("datum", LocalDateTime.class);

                Kunde kunde = Kunde.kunden.get(rs.getInt("kunde"));
                Adresse rechnungsadresse = Adresse.adressen.get(rs.getInt("rechnungsadresse"));
                Adresse lieferadresse = Adresse.adressen.get(rs.getInt("lieferadresse"));

                new Bestellung(nummer, datum, kunde, rechnungsadresse, lieferadresse);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
