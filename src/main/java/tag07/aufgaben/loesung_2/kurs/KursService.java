package tag07.aufgaben.loesung_2.kurs;


import java.sql.*;
import tag07.template.MySQL;

public class KursService {

  /**
   * Fragt alle Kurse aus der Datenbank ab und erstellt die Model-Objekte
   */
  public static void selectKurs() {
    // Statement-Objekt erstellen.
    // Werden keine Variablen im Statement gebraucht, reicht ein einfaches Statement-Objekt.
    try (Connection connection = MySQL.getConnection();
        Statement statement = connection.createStatement()) {
      // executeQuery führt die Abfrage aus und gibt ein ResultSet mit den Ergebnissen zurück.
      ResultSet rs = statement.executeQuery("select * from kurs");

      // Solange Ergebnisse im ResultSet sind...
      while (rs.next()) {
        // fragen wir aus dem ResultSet die Spalten ab.
        // Dies können wir über Index (ACHTUNG: beginnen bei 1!) oder Spaltenname machen
        String kursKürzel = rs.getString(1);
        String kursName = rs.getString(2);

        // Daraus erstellen wir Model-Objekte und fügen sie der Liste hinzu.
        new Kurs(kursKürzel, kursName);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Erstellt einen neuen Kurs und fügt diesen in der Datenbank ein.
   *
   * @return Der erstellte Kurs
   */
  public static Kurs createKurs(String kursKürzel, String kursName) {
    Kurs k = null;

    // Hier verwenden wir die PreparedStatement-Klasse, denn die erlaubt es uns, Parameter dem Statement hinzuzufügen.
    // Diese Vorgehensweise ist zu bevorzugen, da die Gefahr von Injections verringert wird.
    try (Connection connection = MySQL.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "INSERT INTO kurs (kursKürzel, kursName) VALUES (?, ?)")) {
      // Die Parameter im Statement können wir durch Setter-Methoden befüllen. ACHTUNG: Index beginnt hier bei 1!
      statement.setString(1, kursKürzel);
      statement.setString(2, kursName);

      // Statement ausführen
        if (statement.executeUpdate() > 0) {
            k = new Kurs(kursKürzel, kursName);
        }

      return k;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Aktualisiert einen Kurs in der Datenbank.
   *
   * @return True, wenn erfolgreich, sonst False.
   */
  public static boolean updateKurs(Kurs kurs, String attribut, Object wert) {
    // Auch hier verwenden wir PreparedStatement
    try (Connection connection = MySQL.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "UPDATE kurs SET " + attribut + " = ? WHERE kursKürzel = ?")) {
      statement.setObject(1, wert);
      statement.setString(2, kurs.getKursKürzel());

      // Statement ausführen
      return statement.executeUpdate() == 1;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }
}
