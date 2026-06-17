package tag03.template;

import tag03.template.models.Artikel;
import tag03.template.models.Hersteller;
import tag03.template.models.services.ArtikelService;
import tag03.template.models.services.HerstellerService;

public class Onlineshop {
    static void main() {
        HerstellerService.selectHersteller();
        ArtikelService.selectArtikel();

        for (Hersteller hersteller : Hersteller.herstellers.values()) {
            System.out.println(hersteller);
        }

        for (Artikel artikel : Artikel.artikels.values()) {
            System.out.println(artikel);
        }
    }
}
