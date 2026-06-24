package tag08.aufgaben.lösung_1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Eine Klasse, die für die Verwaltung der Connection zuständig ist
 */
public class MySQL
{
    // Um Abfragen auf die Datenbank tätigen zu können, brauchen wir eine Connection. Diese Connection brauchen wir häufiger,
    // also lagern wir die Arbeit mit dieser in eine eigene Klasse aus.

    private static String connectionString = "jdbc:mysql://127.0.0.1:3306/onlineshop";

    // Setter für ConnectionString, damit wir die Klasse in anderen Programmen nutzen können und nur den ConnectionString austauschen müssen.
    public static void setConnectionString(String connectionString)
    {
        MySQL.connectionString = connectionString;
    }

    /**
     * Erstellt und öffnet die Connection.
     * @return Das Connection-Objekt
     */
    public static Connection getConnection() throws SQLException
    {
        // Connection instanziieren, damit wird sie auch geöffnet.
        return DriverManager.getConnection(connectionString, "root", "");
    }
}
