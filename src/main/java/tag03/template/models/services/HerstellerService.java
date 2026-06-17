package tag03.template.models.services;

import tag03.template.MySQL;
import tag03.template.models.Hersteller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HerstellerService {
    public static void selectHersteller() {
        try (Connection connection = MySQL.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM hersteller");
            while (rs.next()) {
                int nummer = rs.getInt("nummer");
                String name = String.valueOf(rs.getString("name"));
                new Hersteller(nummer, name);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
