package tag07.aufgaben.loesung_2.zimmer;

import java.sql.*;
import tag07.template.MySQL;


public class ZimmerService {

  /**
   * Fragt alle Zimmer aus der Datenbank ab und erstellt die Model-Objekte
   */
  public static void selectZimmer() {
    try (Connection connection = MySQL.getConnection();
        Statement statement = connection.createStatement()) {
      ResultSet rs = statement.executeQuery("select * from zimmer");

      while (rs.next()) {
        int zimmerNummer = rs.getInt("zimmerNummer");
        int telefonNummer = rs.getInt("telefonNummer");

        new Zimmer(zimmerNummer, telefonNummer);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Erstellt ein neues Zimmer und fügt dieses in der Datenbank ein.
   *
   * @return Das erstellte Zimmer
   */
  public static Zimmer createZimmer(Integer zimmerNummer, Integer telefonNummer) {
    Zimmer z = null;

    try (Connection connection = MySQL.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "INSERT INTO zimmer (zimmerNummer, telefonNummer) VALUES (?, ?)")) {
      statement.setInt(1, zimmerNummer);
      statement.setInt(2, telefonNummer);

        if (statement.executeUpdate() > 0) {
            z = new Zimmer(zimmerNummer, telefonNummer);
        }

      return z;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Aktualisiert ein Zimmer in der Datenbank.
   *
   * @return True, wenn erfolgreich, sonst False.
   */
  public static boolean updateZimmer(Zimmer zimmer, String attribut, Object wert) {
    try (Connection connection = MySQL.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "UPDATE zimmer SET " + attribut + " = ? WHERE zimmerNummer = ?")) {
      statement.setObject(1, wert);
      statement.setInt(2, zimmer.getZimmerNummer());

      return statement.executeUpdate() == 1;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

}
