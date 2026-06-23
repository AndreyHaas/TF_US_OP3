package tag07.aufgaben.loesung_2.zimmer;

import java.util.HashMap;

public class Zimmer {

  public static final HashMap<Integer, Zimmer> zimmer = new HashMap<>();

  public Integer getZimmerNummer() {
    return zimmerNummer;
  }

  private final Integer zimmerNummer;

  public void setTelefonNummer(Integer telefonNummer) {
    this.telefonNummer = telefonNummer;
  }

  private Integer telefonNummer;

  public Zimmer(Integer zimmerNummer, Integer telefonNummer) {
    this.zimmerNummer = zimmerNummer;
    this.telefonNummer = telefonNummer;

    zimmer.put(zimmerNummer, this);
  }

  @Override
  public String toString() {
    return "Zimmer{" +
        "zimmerNummer=" + zimmerNummer +
        ", telefonNummer=" + telefonNummer +
        '}';
  }
}
