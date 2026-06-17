package tag03.aufgaben.loesung_1.models.services;


import tag03.aufgaben.loesung_1.MySQL;
import tag03.aufgaben.loesung_1.models.Hersteller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HerstellerService {
    public static void selectHersteller() {
        // Über das Connection-Objekt können wir mit der Datenbank kommunizieren.
        // Wir erstellen ein einfaches Statement.
        // Try-With-Resources = Nach dem Try-Block werden reservierte Ressourcen freigegeben. Offene Verbindungen werden geschlossen.
        try (Connection connection = MySQL.getConnection(); // Verwendung der eigenen MySQL-Klasse zur Erzeugung der Connection.
             Statement statement = connection.createStatement()) {
            // Mit executeQuery können wir eine Query ausführen und bekommen dabei ein ResultSet zurück.
            ResultSet rs = statement.executeQuery("SELECT * FROM hersteller");

            // Im ResultSet befinden sich die Daten, die wir von der Datenbank erhalten haben.
            while (rs.next()) // mit next() schieben wir den Lesezeiger auf den nächsten Datensatz.
            {
                // Die Daten fragen wir über den Bezeichner der Spalte oder den Index der Spalte ab.
                int nummer = rs.getInt("nummer");
                String name = rs.getString("name");

                new Hersteller(nummer, name); // Und können dann mit diesen Daten das Model-Objekt erzeugen.
            }
        }
        // Fehler abfangen und ausgeben.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
