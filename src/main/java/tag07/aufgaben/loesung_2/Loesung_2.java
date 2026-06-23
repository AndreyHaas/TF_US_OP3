package tag07.aufgaben.loesung_2;

import tag07.aufgaben.loesung_2.kurs.Kurs;
import tag07.aufgaben.loesung_2.kurs.KursService;
import tag07.aufgaben.loesung_2.kursbelegung.Kursbelegung;
import tag07.aufgaben.loesung_2.kursbelegung.KursbelegungService;
import tag07.aufgaben.loesung_2.student.Student;
import tag07.aufgaben.loesung_2.student.StudentService;
import tag07.aufgaben.loesung_2.zimmer.ZimmerService;
import tag07.template.MySQL;

public class Loesung_2 {

  public static void main() {
    MySQL.getInstance().setConnectionString("jdbc:mysql://127.0.0.1:3306/universität");

    ZimmerService.selectZimmer();
    StudentService.selectStudenten();
    KursService.selectKurs();
    KursbelegungService.selectKursbelegung();

        /*for (Zimmer z : Zimmer.zimmer.values())
            System.out.println(z);*/

    Kursbelegung kursbelegung = KursbelegungService.createKursbelegung(Kurs.kurse.get("Mat130"),
        Student.studenten.get(4576), "S22", null);
    if (kursbelegung != null) {
      kursbelegung.setNote(1.1);
    }

    for (Kursbelegung k : Kursbelegung.kursbelegungen) {
      System.out.println(k);
    }
  }
}
