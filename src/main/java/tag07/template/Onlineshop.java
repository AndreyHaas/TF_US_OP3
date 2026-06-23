package tag07.template;

import java.math.BigDecimal;

public class Onlineshop
{
    public static void main(String[] args)
    {
        String alles = Service.selectAlleBestellungen();
        System.out.println(alles);

        String anzahl = Service.selectAnzahlGekaufterProdukte(123123123);
        System.out.println(anzahl);
        System.out.println();

        int nummer = Service.insertArtikel("Hundefutter", BigDecimal.valueOf(1.99), 2);
        System.out.println(nummer);

        int betroffen = Service.updateArtikelPreis(nummer, BigDecimal.valueOf(1.49));
        System.out.println(betroffen);

    }
}
