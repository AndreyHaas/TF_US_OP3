package tag07.aufgaben.loesung_1;

public class Loesung_1 {

  public static void main(String[] args) {
    int nummer = Service.insertKunde("Makdasi");
    System.out.println(nummer);

    int betroffen = Service.updateKundeName(nummer, "Kerstin");
    System.out.println(betroffen);
  }
}
