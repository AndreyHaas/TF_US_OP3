package tag09;

import java.sql.*;
import java.util.Arrays;
import java.util.Random;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record Mitarbeiter(String mitarbeiternummer, String email, String vorname, String nachname,
                          int einstellungsjahr, int einstellungsmonat) {

  @Override
  public @NotNull String toString() {
    return "Mitarbeiter{" +
        "mitarbeiternummer='" + mitarbeiternummer + '\'' +
        ", email='" + email + '\'' +
        ", vorname='" + vorname + '\'' +
        ", nachname='" + nachname + '\'' +
        ", einstellungsjahr=" + einstellungsjahr +
        ", einstellungsmonat=" + einstellungsmonat +
        '}';
  }

  private static int calculateEmailPruefzahl(String email) {

    if (StringUtils.isEmpty(email)) {
      return 0;
    }

    int summe = 0;

    for (char buchstabe : email.toCharArray()) {
      summe += buchstabe;
    }

    return summe % 10;
  }

  private static byte[] hashPin(String pin) {
    if (pin == null) {
      return new byte[0];
    }
    byte[] hash = new byte[pin.length()];

    for (int i = 0; i < pin.length(); i++) {
      int ziffer = Character.getNumericValue(pin.charAt(i));
      hash[i] = (byte) ((ziffer * 13 + 27) % 128);
    }

    return hash;
  }

  private static String generate6DigitPin(String email) {
    Random rand = new Random();
    int randomFuenfsteller = 10000 + rand.nextInt(90000);
    int pruefzahl = calculateEmailPruefzahl(email);

    return randomFuenfsteller + "" + pruefzahl;
  }

  private static @NotNull String getKuerzel(String name) {
    if (name == null || name.length() < 2) {
      return name == null ? "XX" : name.toUpperCase();
    }

    return name.substring(0, 2).toUpperCase();
  }

  public static @Nullable String registerEmployee(String vorname, String nachname, String email,
      int jahr, int monat) {
    String mitarbeiternummer = String.format("MA-%s-%s-%d-%d",
        getKuerzel(vorname), getKuerzel(nachname), jahr, monat);

    String pin = generate6DigitPin(email);
    byte[] pinHash = hashPin(pin);

    String query = "INSERT INTO mitarbeiter (mitarbeiternummer, email, pin_hash, vorname, nachname, "
        + "einstellungsjahr, einstellungsmonat) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection connection = MySQL.getConnection();
        PreparedStatement statement = connection.prepareStatement(query)) {

      statement.setString(1, mitarbeiternummer);
      statement.setString(2, email);
      statement.setBytes(3, pinHash);
      statement.setString(4, vorname);
      statement.setString(5, nachname);
      statement.setInt(6, jahr);
      statement.setInt(7, monat);

      if (statement.executeUpdate() > 0) {
        System.out.println("Mitarbeiter registriert: " + mitarbeiternummer);
        return pin;
      }

    } catch (SQLException e) {
      if (e.getMessage().contains("Duplicate")) {
        System.err.println("Fehler: E-Mail oder Mitarbeiternummer existiert bereits!");
      } else {
        e.printStackTrace();
      }
    }

    return null;
  }

  public static @Nullable Mitarbeiter verifyLogin(String email, String eingegebenerPin) {
    if (eingegebenerPin == null || eingegebenerPin.length() != 6) {
      System.err.println("Fehler: PIN muss 6-stellig sein!");

      return null;
    }

    int erwartetePruefzahl = calculateEmailPruefzahl(email);
    int letzteZiffer = Character.getNumericValue(eingegebenerPin.charAt(5));

    if (erwartetePruefzahl != letzteZiffer) {
      System.err.println("Fehler: Vor-Check fehlgeschlagen! Prüfzahl stimmt nicht.");

      return null;
    }

    System.out.println("Vor-Check erfolgreich!");

    String sql = "SELECT * FROM mitarbeiter WHERE email = ?";

    try (Connection connection = MySQL.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

      statement.setString(1, email);
      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        byte[] gespeicherterHash = resultSet.getBytes("pin_hash");
        byte[] eingegebenerHash = hashPin(eingegebenerPin);

        if (Arrays.equals(gespeicherterHash, eingegebenerHash)) {
          System.out.println("Login erfolgreich!");

          return new Mitarbeiter(
              resultSet.getString("mitarbeiternummer"),
              resultSet.getString("email"),
              resultSet.getString("vorname"),
              resultSet.getString("nachname"),
              resultSet.getInt("einstellungsjahr"),
              resultSet.getInt("einstellungsmonat")
          );
        } else {
          System.err.println("Falscher PIN!");
        }
      } else {
        System.err.println("Mitarbeiter mit E-Mail " + email + " nicht gefunden!");
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null;
  }
}