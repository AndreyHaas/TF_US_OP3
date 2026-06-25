package tag09;

public class FirmenverwaltungApp {

  public static final String EMAIL = "frank.muster@firma.de";

  public static void main() {
    System.out.println("========== REGISTRIERUNG ==========");

    String pin = Mitarbeiter.registerEmployee("Frank", "Muster", EMAIL, 2020, 9);

    if (pin != null) {
      System.out.println("Generierter PIN: " + pin);

      System.out.println(System.lineSeparator() + "========== FEHLGESCHLAGENER LOGIN ==========");
      String falscherPin = "123450";  // Letzte Ziffer 0 statt 9
      Mitarbeiter result = Mitarbeiter.verifyLogin(EMAIL, falscherPin);
      System.err.println("Ergebnis: " + (result == null ? "Login fehlgeschlagen (erwartet)"
          : "Sollte null sein!"));

      System.out.println(System.lineSeparator() + "========== ERFOLGREICHER LOGIN ==========");
      Mitarbeiter eingeloggter = Mitarbeiter.verifyLogin(EMAIL, pin);

      if (eingeloggter != null) {
        System.out.println(
            System.lineSeparator() + "========== EINGELOGGTER MITARBEITER ==========");
        System.out.println(eingeloggter);
      } else {
        System.err.println("Login fehlgeschlagen!");
      }

    } else {
      System.err.println("Registrierung fehlgeschlagen!");
    }
  }
}
