package tag05;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLInjection {
    public static final String connection = "jdbc:mysql://localhost:3306/injection?allowMultiQueries=true";
    public static final String username = "root";
    public static final String password = "";

    static void main() {
        String name = "Andreas";
       /* System.out.println("Normales Insert");
        insert(name);*/
        name = "'); DROP TABLE person -- ";
        System.out.println("Injection");
        insert(name);
    }

    private static void insert(String name) {
        String query = "INSERT INTO person (name) VALUES ('" + name + "')";
        try (Statement statement = DriverManager.getConnection(connection, username, password).createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Fehler beim INSERT " + e.getMessage());
        }
    }

    private static void insertParams(String name) {
        String query = "INSERT INTO person (name) VALUES (?)";

        try (PreparedStatement statement = DriverManager.getConnection(connection, username, password).prepareStatement(query)) {
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Fehler beim insertParams " + e.getMessage());
        }
    }
}
