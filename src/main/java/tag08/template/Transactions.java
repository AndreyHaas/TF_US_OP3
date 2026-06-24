package tag08.template;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Transactions {
    private static final String connectionString = "jdbc:mysql://localhost:3306/bank";

    public static void main(String[] args) {
        try {
            // Erster Buch-Vorgang: Beide Konten sind richtig. Betrag wird von Konto1 abgezogen und auf Konto2 gebucht.
            if (buchen("0000123456", "0000987654", 500.0)) System.out.println("Erfolgreich!");
            else System.out.println("Fehler!");

            // Zweiter Buch-Vorgang: Die zweite Kontonummer ist falsch. Es kann der Betrag zwar vom ersten Konto abgezogen, aber nirgends gebucht werden.
            if (buchen("0000987654", "0000", 200.0)) System.out.println("Erfolgreich!");
            else System.out.println("Fehler!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Bucht Geld von einem Konto auf ein anderes.
     *
     * @param kontoVon
     * @param kontoNach
     * @param betrag
     * @return True, wenn erfolgreich. Sonst False.
     * @throws SQLException Auftretende Exceptions werden an den Aufrufer weitergeleitet.
     */
    private static boolean buchen(String kontoVon, String kontoNach, double betrag) throws SQLException {
        // Try-With-Resources = Die Connection wird bei Verlassen des Try-Blocks automatisch geschlossen.
        try (Connection connection = DriverManager.getConnection(connectionString, "root", "")) {
            // Um Transaktionen steuern zu können, müssen wir AutoCommit auf False setzen.
            connection.setAutoCommit(false);

            int anzahl1 = 0;
            int anzahl2 = 0;

            // Wir buchen den Betrag von einem Konto ab.
            try (PreparedStatement statement = connection.prepareStatement("UPDATE konto SET kontostand = kontostand - ? WHERE nummer = ?")) {
                statement.setDouble(1, betrag);
                statement.setString(2, kontoVon);

                // executeUpdate gibt uns die Anzahl der betroffenen Zeilen zurück.
                anzahl1 = statement.executeUpdate();

                // Wenn nicht genau eine Zeile betroffen wurde,
                if (anzahl1 != 1) {
                    // machen wir Rollback und geben False zurück.
                    connection.rollback();
                    return false;
                }
            } catch (SQLException e) {
                // Auch wenn während diesem Vorgang eine Exception auftritt, machen wir rollback().
                connection.rollback();

                throw e; // Mit throw werfen wir die Exception zum Aufrufer der Methode.
            }

            // Danach buchen wir den Betrag auf ein anderes Konto.
            try (PreparedStatement statement = connection.prepareStatement("UPDATE konto SET kontostand = kontostand + ? WHERE nummer = ?")) {
                statement.setDouble(1, betrag);
                statement.setString(2, kontoNach);

                anzahl2 = statement.executeUpdate();

                // Wenn das Update nicht genau eine Zeile betroffen hat,
                if (anzahl2 != 1) {
                    // Rollback!
                    connection.rollback();
                    return false;
                }
            } catch (SQLException e) {
                connection.rollback();

                throw e;
            }

            // Nur wenn der Code bis hier hin erfolgreich läuft und wir an keiner Stelle vorher abbrechen,
            // committen wir die Transaktion und geben true zurück.
            connection.commit();
            return true;
        }
    }
}
