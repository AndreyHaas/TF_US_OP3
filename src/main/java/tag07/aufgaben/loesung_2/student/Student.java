package tag07.aufgaben.loesung_2.student;


import java.time.LocalDate;
import java.util.HashMap;
import tag07.aufgaben.loesung_2.zimmer.Zimmer;

public class Student {

  public static final HashMap<Integer, Student> studenten = new HashMap<>();

  public Integer getStudentNummer() {
    return studentNummer;
  }

  private final Integer studentNummer;

  public void setStudentVorname(String studentVorname) {
    this.studentVorname = studentVorname;
  }

  public void setStudentNachname(String studentNachname) {
    this.studentNachname = studentNachname;
  }

  public void setGeburtsdatum(LocalDate geburtsdatum) {
    this.geburtsdatum = geburtsdatum;
  }

  private String studentVorname;
  private String studentNachname;
  private LocalDate geburtsdatum;

  // zimmerNummer ist in student ein Fremdschlüssel. Wir speichern uns hier also die Referenz auf das passende Model-Objekt.
  private final Zimmer zimmer;

  public Student(Integer studentNummer, String studentVorname, String studentNachname,
      LocalDate geburtsdatum, Zimmer zimmer) {
    this.studentNummer = studentNummer;
    this.studentVorname = studentVorname;
    this.studentNachname = studentNachname;
    this.geburtsdatum = geburtsdatum;
    this.zimmer = zimmer;

    studenten.put(studentNummer, this);
  }

  @Override
  public String toString() {
    return "Student{" +
        "studentNummer=" + studentNummer +
        ", studentVorname='" + studentVorname + '\'' +
        ", studentNachname='" + studentNachname + '\'' +
        ", geburtsdatum=" + geburtsdatum +
        ", zimmer=" + zimmer +
        '}';
  }
}
