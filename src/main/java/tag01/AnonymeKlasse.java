package tag01;

interface Begruese {
  void sageHallo();
}

public class AnonymeKlasse {
    public static void main(String[] args) {

      Begruese begruese = new Begruese() {
        @Override
        public void sageHallo() {
          System.out.println("Hallo Welt!");
        }
      };

      begruese.sageHallo();
      // Begruese begruese = () -> System.out.println("Hallo Welt!");
    }

  /*
  Referenzen können auf Objekte anonymer Klassen zeigen.
  Eine anonyme Klasse ist eine lokale Klasse ohne Namen,
  die bei der Instanziierung definiert wird.
  Die Referenz wird dabei über den Typ der Schnittstelle oder der Elternklasse gehalten.
  */
}