package tag02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringJoiner;

import org.apache.commons.lang3.StringUtils;

public class JavaSqlBeispiel {

    static void main() {
        Connection connection = null;
        try {
            String schema = "jdbc:mysql://localhost:3306/mydatabase";
            String user = "root";
            String password = StringUtils.EMPTY;
            connection = DriverManager.getConnection(schema, user, password);

            System.out.println("Verbindung zur Datenbank hergestellt!");

            Statement statement = connection.createStatement();
            String query = "SELECT * FROM MyTable";
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                StringJoiner joiner = new StringJoiner(" ");
                joiner.add(String.valueOf(rs.getInt("id")));
                joiner.add(rs.getString("name"));
                joiner.add(String.valueOf(rs.getBigDecimal("wert")));

                System.out.println(joiner);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}