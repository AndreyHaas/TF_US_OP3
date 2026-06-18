package tag03.aufgaben.loesung_1.models.services;


import tag03.aufgaben.loesung_1.MySQL;
import tag03.aufgaben.loesung_1.models.Adresse;
import tag03.aufgaben.loesung_1.models.Kunde;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdresseService {
    public static void selectAdresse() {
        try (Connection connection = MySQL.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery("select * from adresse");
            while (rs.next()) {
                int id = rs.getInt("id");
                String strasseNr = rs.getString("strasseNr");
                String plz = rs.getString("plz");
                String ort = rs.getString("ort");

                Kunde kunde = Kunde.kunden.get(rs.getInt("kunde"));

                new Adresse(id, strasseNr, plz, ort, kunde);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
