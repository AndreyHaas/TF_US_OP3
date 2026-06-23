package tag07.aufgaben.loesung_2.kurs;

import java.util.HashMap;

public class Kurs {

  // In JavaFX verwenden wir ObservableList als Collection für die Model-Objekte.
  // Sollten sich Werte in den Objekten ändern, wird dadurch die Oberfläche automatisch benachrichtigt und Änderungen werden direkt angezeigt.
  public static final HashMap<String, Kurs> kurse = new HashMap<>();

  private final String kursKürzel;
  private String kursName;

  public String getKursKürzel() {
    return kursKürzel;
  }

  public void setKursName(String kursName) {
      if (KursService.updateKurs(this, "kursName", kursName)) {
          this.kursName = kursName;
      }
  }

  public Kurs(String kursKürzel, String kursName) {
    this.kursKürzel = kursKürzel;
    this.kursName = kursName;

    kurse.put(kursKürzel, this);
  }

  @Override
  public String toString() {
    return "Kurs{" +
        "kursKürzel='" + kursKürzel + '\'' +
        ", kursName='" + kursName + '\'' +
        '}';
  }
}
