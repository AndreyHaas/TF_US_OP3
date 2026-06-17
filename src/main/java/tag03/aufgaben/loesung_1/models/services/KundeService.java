package tag03.aufgaben.loesung_1.models.services;


import tag03.aufgaben.loesung_1.MySQL;
import tag03.aufgaben.loesung_1.models.Kunde;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class KundeService {
    public static void selectKunde() {
        try (Connection connection = MySQL.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery("select * from kunde");
            while (rs.next()) {
                int nummer = rs.getInt("nummer");
                String name = rs.getString("name");

                new Kunde(nummer, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
