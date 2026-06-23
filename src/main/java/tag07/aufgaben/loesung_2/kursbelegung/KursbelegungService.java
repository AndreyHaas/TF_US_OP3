package tag07.aufgaben.loesung_2.kursbelegung;


import java.sql.*;
import tag07.aufgaben.loesung_2.kurs.Kurs;
import tag07.aufgaben.loesung_2.student.Student;
import tag07.template.MySQL;

public class KursbelegungService {

  /**
   * Fragt alle Kursbelegungen aus der Datenbank ab und erstellt die Model-Objekte
   */
  public static void selectKursbelegung() {
    try (Connection connection = MySQL.getConnection();
        Statement statement = connection.createStatement()) {
      ResultSet rs = statement.executeQuery("select * from kursbelegung");

      while (rs.next()) {
        String kursKürzel = rs.getString("kursKürzel");
        int studentNummer = rs.getInt("studentNummer");
        String semester = rs.getString("semester");
        Double note = rs.getDouble("note");

        new Kursbelegung(Kurs.kurse.get(kursKürzel), Student.studenten.get(studentNummer), semester,
            note);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  public static Kursbelegung createKursbelegung(Kurs kurs, Student student, String semester,
      Double note) {
    // Um StoredProcedures auszuführen, brauchen wir CallableStatement
    try (Connection connection = MySQL.getConnection();
        CallableStatement statement = connection.prepareCall("{CALL kursbelegungNeu(?,?,?,?)}")) {
      statement.setString(1, kurs.getKursKürzel());
      statement.setInt(2, student.getStudentNummer());
      statement.setString(3, semester);
      // Da note Null sein kann, nutzen wir setObject
      statement.setObject(4, note);

      statement.executeUpdate();

      return new Kursbelegung(kurs, student, semester, note);
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static boolean updateKursbelegung(Kursbelegung kursbelegung, Double note) {
    try (Connection connection = MySQL.getConnection();
        CallableStatement statement = connection.prepareCall("{CALL kursbelegungNote(?,?,?)}")) {
      statement.setString(1, kursbelegung.getKurs().getKursKürzel());
      statement.setInt(2, kursbelegung.getStudent().getStudentNummer());
      statement.setObject(3, note);

      statement.executeUpdate();

      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }
}
