package tag07.template;

import java.math.BigDecimal;
import java.sql.*;

public class Service
{
    /**
     * Führt die View "selectAlles" aus und gibt das Ergebnis als String zurück.
     * @return Das Ergebnis der View-Abfrage als String.
     */
    public static String selectAlleBestellungen()
    {
        // getConnection() gibt uns die Connection zurück, die wir hier im Try-With-Resources als Variable hinterlegen.
        // Dadurch wird die Connection bei Verlassen des Try-Blocks automatisch geschlossen! Wir sparen uns hier den Finally-Block!
        try (Connection connection = MySQL.getConnection(); // Im Try-With-Resources können mehrere Befehle durch Semikolon getrennt aufgelistet werden.
             Statement statement = connection.createStatement())
        {
            // Views können einfach wie Tabellen abgefragt werden.
            ResultSet rs = statement.executeQuery("select * from selectAlleBestellungen");

            // Wir bauen uns eine rudimentäre Ausgabe zusammen. Nicht hübsch, aber erledigt den Job.
            StringBuilder sb = new StringBuilder();

            // Wir können über getMetaData() die Anzahl der Spalten und die Namen der Spalten abfragen.
            sb.append(" | ");
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
                sb.append(rs.getMetaData().getColumnName(i)).append(" | ");

            sb.append("\n");

            // Für jeden Datensatz im ResultSet per For-Schleife die Spalten-Werte dem StringBuilder hinzufügen:
            while (rs.next())
            {
                sb.append(" | ");
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
                    sb.append(rs.getString(i)).append(" | ");

                sb.append("\n");
            }

            return sb.toString();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return "";
        }
    }

    public static String selectAnzahlGekaufterProdukte(int kundennummer)
    {
        try (Connection connection = MySQL.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                 "select * from selectAnzahlGekaufterProdukte where nummer = ?"))
        {
            statement.setInt(1, kundennummer);

            ResultSet rs = statement.executeQuery();

            StringBuilder sb = new StringBuilder();

            if (rs.next())
            {
                sb.append("Kunde: ").append(kundennummer).append(" - Anzahl: ").append(rs.getInt("anzahl"));
            }

            return sb.toString();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Führt die Prozedur "insertArtikel" aus und gibt die durch AUTO_INCREMENT erstellte Artikelnummer zurück.
     * @return Die erzeugte Artikelnummer.
     */
    public static int insertArtikel(String bezeichnung, BigDecimal preis, int hersteller)
    {
        // Um StoredProcedures auszuführen brauchen wir CallableStatement.
        try (Connection connection = MySQL.getConnection();
                CallableStatement statement = connection.prepareCall("{CALL insertArtikel(?,?,?,?)}"))
        {
            // Parameter setzen
            statement.setString(1, bezeichnung);
            statement.setBigDecimal(2, preis);
            statement.setInt("h", hersteller); // Anstatt Index kann auch der Name des Parameters verwenden werden.

            // Den Out-Parameter müssen wir registrieren und den SQL-Datentypen angeben (machen wir hier über JDBCType)
            statement.registerOutParameter("n", JDBCType.INTEGER);
            // Wenn JDBCType nicht gefunden werden kann: java.sql.Types verwenden.

            // Wenn das Insert in der Prozedur klappt (das heißt, executeUpdate gibt einen positiven Wert zurück)
            if (statement.executeUpdate() > 0)
                // können wir den Out-Parameter abfragen.
                return statement.getInt(4);

            return -1;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Führt die Prozedur "updateArtikelPreis" aus und gibt die Anzahl der betroffenen Datensätze zurück.
     * @return Die Anzahl der betroffenen Datensätze.
     */
    public static int updateArtikelPreis(int nummer, BigDecimal preis)
    {
        // Um Stored Procedures auszuführen brauchen wir CallableStatement.
        try (Connection connection = MySQL.getConnection();
                CallableStatement statement = connection.prepareCall("{CALL updateArtikelPreis(?,?)}"))
        {
            statement.setInt("nummer", nummer);
            statement.setBigDecimal("preis", preis);

            return statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return -1;
        }
    }
}
