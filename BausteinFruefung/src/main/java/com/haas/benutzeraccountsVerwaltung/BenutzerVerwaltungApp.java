package com.haas.benutzeraccountsVerwaltung;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BenutzerVerwaltungApp {
    private static final Logger log = LoggerFactory.getLogger(BenutzerVerwaltungApp.class);

    static void main() {
        log.info("=== BENUTZERVERWALTUNG GESTARTET ===");

        System.out.println("=== BENUTZERVERWALTUNG ===" + System.lineSeparator());

        System.out.println("1. Registrierung:");
        String user = "AndyHaas";
        String geheimPass = "geheim123";
        String vorname = "Andy";
        String nachname = "Haas";

        boolean registriert = Benutzer.register(user, geheimPass, vorname, nachname);

        if (registriert) {
            System.out.println("Benutzer " + user + " erfolgreich registriert!");
        } else {
            System.err.println("Benutzer existiert bereits oder Fehler!");
        }

        System.out.println(System.lineSeparator() + "2. Registrierung mit bereits existierendem Benutzernamen:");
        String neuPass = "neu123";
        boolean registriert2 = Benutzer.register(user, neuPass, vorname, nachname);

        if (registriert2) {
            System.out.println("Benutzer registriert (unerwartet!)");
        } else {
            System.out.println("Registrierung fehlgeschlagen (wie erwartet, da Benutzer bereits existiert).");
        }

        System.out.println(System.lineSeparator() + "3. Login mit richtigem Passwort:");
        Benutzer eingeloggterBenutzer = Benutzer.login(user, geheimPass);

        if (eingeloggterBenutzer != null) {
            System.out.println("Login erfolgreich!");
            System.out.println("   Benutzername: " + eingeloggterBenutzer.benutzername());
            System.out.println("   Vorname: " + eingeloggterBenutzer.vorname());
            System.out.println("   Nachname: " + eingeloggterBenutzer.nachname());
        } else {
            System.err.println("Login fehlgeschlagen.");
        }

        System.out.println(System.lineSeparator() + "4. Login mit falschem Passwort:");
        String falschesPasswort = "falsches_passwort";

        Benutzer loginFalsch = Benutzer.login(user, falschesPasswort);

        if (loginFalsch != null) {
            System.out.println("Login erfolgreich (unerwartet!)");
        } else {
            System.err.println("Login fehlgeschlagen (wie erwartet, da Passwort falsch).");
        }

        System.out.println(System.lineSeparator() + "5. Login mit nicht existierendem Benutzernamen:");
        String unbekannteUser = "unbekannt";
        String passwortVonUnbekannteUser = "passwort";
        Benutzer loginUnbekannt = Benutzer.login(unbekannteUser, passwortVonUnbekannteUser);

        if (loginUnbekannt != null) {
            System.out.println("Login erfolgreich (unerwartet!)");
        } else {
            System.out.println("Login fehlgeschlagen (wie erwartet, da Benutzer nicht existiert).");
        }

        log.info("=== BENUTZERVERWALTUNG BEENDET ===");
    }
}