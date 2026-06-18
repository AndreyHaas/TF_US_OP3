package tag03.template.models.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import tag03.template.MySQL;
import tag03.template.models.Hersteller;

public class HerstellerService {
    public static void selectHersteller() {
        try (Connection connection = MySQL.getConnection()) {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM hersteller";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int nummer = rs.getInt("nummer");
                String name = String.valueOf(rs.getString("name"));
                new Hersteller(nummer, name);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static Hersteller createHersteller(String name) {
        try (
            Connection conn = MySQL.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO hersteller (name) VALUES (?)",
                Statement.RETURN_GENERATED_KEYS)
        ) {

            stmt.setString(1, name);
            stmt.executeUpdate();

            int id = 0;
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }

            return new Hersteller(id, name);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean updateHersteller(Hersteller hersteller, String attribut, Object wert) {
        try (
            Connection connection = MySQL.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                "UPDATE hersteller SET " + attribut + " = ? WHERE nummer = ?")
        ) {

            statement.setObject(1, wert);
            statement.setInt(2, hersteller.getNummer());

            int anzahl = statement.executeUpdate();
            return anzahl == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
