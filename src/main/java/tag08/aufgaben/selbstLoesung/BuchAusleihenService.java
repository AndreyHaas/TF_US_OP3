package tag08.aufgaben.selbstLoesung;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import tag09.MySQL;

public class BuchAusleihenService {

  public void buchAusleihen(int buchId, int kundeNr, int tage) {
    String query = "{CALL buch_ausleihen(?, ?, ?)}";

    try (Connection connection = MySQL.getConnection();
        CallableStatement stmt = connection.prepareCall(query)) {

      stmt.setInt(1, buchId);
      stmt.setInt(2, kundeNr);
      stmt.setInt(3, tage);

      stmt.execute();
      System.out.println("Buch erfolgreich ausgeliehen!");

    } catch (SQLException e) {
      System.err.println("Fehler: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void buchZurueckgeben(int buchId) {
    String sql = "{CALL buch_zurueckgeben(?)}";

    try (Connection conn = MySQL.getConnection();
        CallableStatement stmt = conn.prepareCall(sql)) {

      stmt.setInt(1, buchId);
      stmt.execute();
      System.out.println("Buch erfolgreich zurückgegeben!");

    } catch (SQLException e) {
      System.err.println("Fehler: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void zeigeAusleihen() {
    String sql = "SELECT * FROM view_ausleihen";

    try (Connection conn = MySQL.getConnection();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)) {

      System.out.println("Aktuelle Ausleihen:");
      while (resultSet.next()) {
        System.out.printf("  %s | %s | %d Tage%n",
            resultSet.getString("Buch"),
            resultSet.getString("Kunde"),
            resultSet.getInt("Tage")
        );
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}