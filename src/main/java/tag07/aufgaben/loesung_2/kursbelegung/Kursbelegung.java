package tag07.aufgaben.loesung_2.kursbelegung;


import java.util.ArrayList;
import tag07.aufgaben.loesung_2.kurs.Kurs;
import tag07.aufgaben.loesung_2.student.Student;

public class Kursbelegung {

  public static final ArrayList<Kursbelegung> kursbelegungen = new ArrayList<>();

  private final Kurs kurs;
  private final Student student;

  private final String semester;

  private Double note;

  public Kurs getKurs() {
    return kurs;
  }

  public Student getStudent() {
    return student;
  }

  public String getSemester() {
    return semester;
  }

  public void setNote(Double note) {
      if (KursbelegungService.updateKursbelegung(this, note)) {
          this.note = note;
      }
  }

  public Kursbelegung(Kurs kurs, Student student, String semester, Double note) {
    this.kurs = kurs;
    this.student = student;
    this.semester = semester;
    this.note = note;

    kursbelegungen.add(this);
  }

  @Override
  public String toString() {
    return "Kursbelegung{" +
        "kurs=" + kurs +
        ", student=" + student +
        ", semester='" + semester + '\'' +
        ", note=" + note +
        '}';
  }
}


