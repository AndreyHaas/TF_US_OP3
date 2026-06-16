package tag02.JavaMysql.src;

import java.sql.*;


// Importieren von Bibliotheken, z.B. den MySQL Connector:
// https://www.jetbrains.com/help/idea/library.html

public class JavaSQLBeispiel {
    static void main() {

        Connection connection = null;

        try{
            String connectionString ="jdbc:mysql://localhost:3306/mydatabase";
            connection = DriverManager.getConnection(connectionString,"root","");

            System.out.println("Verbindung zur Datenbank hergestellt!");

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM mytable");

            while (rs.next()){
                String s = rs.getInt("id") +
                        " " + rs.getString("name") +
                        " " + rs.getString("beschreibung") +
                        " " + rs.getBigDecimal("wert");
                System.out.println(s);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(connection != null)
                    connection.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }

    }
}
