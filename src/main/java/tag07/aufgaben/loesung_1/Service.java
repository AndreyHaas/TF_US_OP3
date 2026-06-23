package tag07.aufgaben.loesung_1;

import java.sql.*;
import tag07.template.MySQL;

public class Service {

  /**
   * Führt die Prozedur "insertArtikel" aus und gibt die durch AUTO_INCREMENT erstellte
   * Artikelnummer zurück.
   *
   * @return Die erzeugte Artikelnummer.
   */
  public static int insertKunde(String name) {
    try (Connection connection = MySQL.getConnection();
        CallableStatement statement = connection.prepareCall("{CALL insertKunde(?, ?)}")) {
      // Parameter setzen
      statement.setString(1, name);

      // Den Out-Parameter müssen wir registrieren und den SQL-Datentypen angeben (machen wir hier über JDBCType)
      statement.registerOutParameter(2, JDBCType.INTEGER);

      // Wenn das Insert in der Prozedur klappt (das heißt, executeUpdate gibt einen positiven Wert zurück)
        if (statement.executeUpdate() > 0)
        // können wir den Out-Parameter abfragen.
        {
            return statement.getInt(2);
        }

      return -1;
    } catch (SQLException e) {
      e.printStackTrace();
      return -1;
    }
  }

  /**
   * Führt die Prozedur "updateArtikelPreis" aus und gibt die Anzahl der betroffenen Datensätze
   * zurück.
   *
   * @return Die Anzahl der betroffenen Datensätze.
   */
  public static int updateKundeName(int nummer, String name) {
    try (Connection connection = MySQL.getConnection();
        CallableStatement statement = connection.prepareCall("{CALL updateKundeName(?,?)}")) {
      statement.setInt(1, nummer);
      statement.setString(2, name);

      return statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      return -1;
    }
  }
}
