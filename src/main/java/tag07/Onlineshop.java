package tag07;

import java.math.BigDecimal;
import tag07.service.Service;

public class Onlineshop {

  public static void main(String[] args) {

    /*int hundefutter = Service.insertArtikel("Hundefutter", BigDecimal.valueOf(1.99), 2);
    System.out.println(hundefutter);*/

    System.out.println(Service.updateArtikelPreis(3, BigDecimal.valueOf(3.99)));
  }
}