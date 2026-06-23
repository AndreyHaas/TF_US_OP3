package tag07.template;

/* Testen der offenen Verbindungen in phpMyAdmin mit
show status where `variable_name` = 'Threads_connected';
show processlist;

Damit können wir beweisen, dass try-with-resources tatsächlich die Verbindung schließt.
*/

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

// Um Abfragen auf die Datenbank tätigen zu können, brauchen wir eine Connection. Diese Connection brauchen wir häufiger, also lagern wir die Arbeit mit dieser in eine eigene Klasse aus.

/**
 * Eine Klasse, die für die Verwaltung der Connection zuständig ist.
 */
public class MySQL
{
    private static MySQL instance = null; // Wir implementieren die Klasse als Singleton-Pattern

    private final MysqlDataSource dataSource = new MysqlDataSource(); // Zur Verbindung zur Datenbank nutzen wir hier ein DataSource-Objekt.

	private String connectionString = "jdbc:mysql://127.0.0.1:3306/onlineshop";

    /**
     * Setter für ConnectionString, damit wir die Klasse in anderen Programmen nutzen können und nur den ConnectionString austauschen müssen.
     */
    public void setConnectionString(String connectionString)
    {
        this.connectionString = connectionString;

        dataSource.setURL(connectionString);
    }

    // Singleton-Pattern: Privater Konstruktor!
    private MySQL()
    {
        // Damit das DataSource-Objekt verwendet werden kann, müssen noch Einstellungen vorgenommen werden.
        dataSource.setUser("root");
        dataSource.setPassword("");

		// Die DataSource verbindet zur Datenbank über den Connection String.
		dataSource.setURL(connectionString);
	}

    /**
     * Gibt die Instanz dieser Klasse zurück. Erzeugt eine neue Instanz, falls noch keine existiert.
     */
    public static MySQL getInstance()
    {
        // Singleton-Pattern: Wenn noch kein Objekt der Klasse existiert, wird eins erzeugt.
        if (instance == null)
            instance = new MySQL();

        return instance;
    }

    /**
     * Stellt eine Verbindung zur Datenbank her und gibt ein Connection-Objekt zurück.
     */
    public static Connection getConnection() throws SQLException
    {
        return getInstance().dataSource.getConnection();
    }
}