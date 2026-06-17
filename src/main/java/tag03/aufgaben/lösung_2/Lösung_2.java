/*
Erstellen Sie ein Java Programm zur Hochbau-Datenbank. Es reicht, wenn vorhandene Daten abgefragt werden können.
Inserts und Updates sind an dieser Stelle noch nicht erforderlich.
Testen Sie das Programm durch Ausgabe aller Daten.

Sie können Controller und Models in eine Klasse packen, um etwas Schreibarbeit zu sparen.
 */

package tag03.aufgaben.lösung_2;


// Hinweis: Ich importiere hier unsere MySQL Klasse aus einem anderen Package, anstatt sie zu kopieren.

import tag03.aufgaben.loesung_1.MySQL;

public class Lösung_2
{
    public static void main(String[] args)
    {
        // Hinweis: Ich habe der MySQL Klasse diesen Setter nachträglich hinzugefügt, damit die Klasse auch für andere Projekte importiert werden kann
        MySQL.getInstance().setConnectionString("jdbc:mysql://127.0.0.1:3306/hochbau");
        // Alle Daten aus der Datenbank abfragen, dabei wegen der Schlüsselbeziehungen auf die Reihenfolge achten.
        Abteilung.selectAbteilung();
        Baustelle.selectBaustelle();
        Mitarbeiter.selectMitarbeiter();

        Einsatz.selectEinsatz();

        /*for (Abteilung a : Abteilung.abteilungen.values())
            System.out.println(a);*/

        /*for (Baustelle b : Baustelle.baustellen.values())
            System.out.println(b);

        for (Mitarbeiter m : Mitarbeiter.mitarbeiter.values())
            System.out.println(m);*/

        // Dank der Überschreibung von toString() können alle Daten bequem ausgegeben werden.
        for (Einsatz e : Einsatz.einsätze)
            System.out.println(e);
    }
}
