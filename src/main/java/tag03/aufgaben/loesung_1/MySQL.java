package tag03.aufgaben.loesung_1;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQL {
    private static MySQL instance = null; // Wir implementieren die Klasse als Singleton-Pattern

    private final MysqlDataSource dataSource = new MysqlDataSource(); // Zur Verbindung zur Datenbank nutzen wir hier ein DataSource-Objekt.

    private String connectionString = "jdbc:mysql://127.0.0.1:3306/onlineshop";

    /**
     * Setter für ConnectionString, damit wir die Klasse in anderen Programmen nutzen können und nur den ConnectionString austauschen müssen.
     */
    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;

        dataSource.setURL(connectionString);
    }

    // Singleton-Pattern: Privater Konstruktor!
    private MySQL() {
        // Damit das DataSource-Objekt verwendet werden kann, müssen noch Einstellungen vorgenommen werden.
        dataSource.setUser("root");
        dataSource.setPassword("");

        // Die DataSource verbindet zur Datenbank über den Connection String.
        dataSource.setURL(connectionString);
    }

    /**
     * Gibt die Instanz dieser Klasse zurück. Erzeugt eine neue Instanz, falls noch keine existiert.
     */
    public static MySQL getInstance() {
        // Singleton-Pattern: Wenn noch kein Objekt der Klasse existiert, wird eins erzeugt.
        if (instance == null)
            instance = new MySQL();

        return instance;
    }

    /**
     * Stellt eine Verbindung zur Datenbank her und gibt ein Connection-Objekt zurück.
     */
    public static Connection getConnection() throws SQLException {
        return getInstance().dataSource.getConnection();
    }
}
