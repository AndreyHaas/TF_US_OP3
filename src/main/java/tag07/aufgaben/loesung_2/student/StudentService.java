package tag07.aufgaben.loesung_2.student;


import java.sql.*;
import java.time.LocalDate;
import tag07.aufgaben.loesung_2.zimmer.Zimmer;
import tag07.template.MySQL;


public class StudentService {

  /**
   * Fragt alle Studenten aus der Datenbank ab und erstellt die Model-Objekte
   */
  public static void selectStudenten() {
    try (Connection connection = MySQL.getConnection();
        Statement statement = connection.createStatement()) {
      ResultSet rs = statement.executeQuery("select * from student");

      while (rs.next()) {
        int studentNummer = rs.getInt("studentNummer");
        String studentVorname = rs.getString("studentVorname");
        String studentNachname = rs.getString("studentNachname");
        LocalDate geburtsdatum = rs.getObject("geburtsdatum", LocalDate.class);
        int zimmerNummer = rs.getInt("zimmerNummer");

        new Student(studentNummer, studentVorname, studentNachname, geburtsdatum,
            Zimmer.zimmer.get(zimmerNummer));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Erstellt einen neuen Studenten und fügt diesen in der Datenbank ein.
   *
   * @return Der erstellte Student
   */
  public static Student createStudent(Integer studentNummer, String studentVorname,
      String studentNachname, LocalDate geburtsdatum, Zimmer zimmer) {
    Student s = null;

    try (Connection connection = MySQL.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "INSERT INTO student (studentNummer, studentVorname, studentNachname, geburtsdatum, zimmerNummer) VALUES (?, ?, ?, ?, ?)")) {

      statement.setInt(1, studentNummer);
      statement.setString(2, studentVorname);
      statement.setString(3, studentNachname);
      statement.setObject(4, geburtsdatum);
      statement.setInt(5, zimmer.getZimmerNummer());

        if (statement.executeUpdate() > 0) {
            new Student(studentNummer, studentVorname, studentNachname, geburtsdatum, zimmer);
        }

      return s;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Aktualisiert einen Studenten in der Datenbank.
   *
   * @return True, wenn erfolgreich, sonst False.
   */
  public static boolean updateStudent(Student student, String attribut, Object wert) {
    try (Connection connection = MySQL.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "UPDATE student SET " + attribut + " = ? WHERE studentNummer = ?")) {

      statement.setObject(1, wert);
      statement.setInt(2, student.getStudentNummer());

      return statement.executeUpdate() == 1;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }
}
