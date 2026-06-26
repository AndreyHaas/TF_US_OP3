package com.haas.benutzeraccountsVerwaltung;

import ch.qos.logback.classic.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public record Benutzer(String benutzername, String vorname, String nachname) {

    private static final Logger log = (Logger) LoggerFactory.getLogger(Benutzer.class);

    private static byte[] encode(@NotNull String passwort) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        return digest.digest(passwort.getBytes(StandardCharsets.UTF_8));
    }

    public static boolean register(String benutzername, String passwort, String vorname, String nachname) {

        if (benutzerExistiert(benutzername)) {
            return false;
        }

        byte[] gehashtesPasswort;

        try {
            gehashtesPasswort = encode(passwort);
        } catch (Exception e) {
            log.error("Error encoding passwort in Methode 'register' {}", passwort, e);

            return false;
        }

        String sql = "INSERT INTO benutzer (name, passwort, vorname, nachname) VALUES (?, ?, ?, ?)";

        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, benutzername);
            preparedStatement.setBytes(2, gehashtesPasswort);
            preparedStatement.setString(3, vorname);
            preparedStatement.setString(4, nachname);

            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            log.error("Error registering benutzer {}", benutzername, e);

            return false;
        }
    }

    public static @Nullable Benutzer login(String benutzername, String passwort) {

        if (!benutzerExistiert(benutzername)) {
            return null;
        }

        byte[] gehashtesPasswort;

        try {
            gehashtesPasswort = encode(passwort);
        } catch (Exception e) {
            log.error("Error encoding passwort {} in Methode 'login'.", passwort, e);

            return null;
        }

        String query = "SELECT name, vorname, nachname, passwort FROM benutzer WHERE name = ?";

        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, benutzername);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                byte[] gespeichertesPasswort = resultSet.getBytes("passwort");

                if (MessageDigest.isEqual(gehashtesPasswort, gespeichertesPasswort)) {

                    return new Benutzer(resultSet.getString("name"), resultSet.getString("vorname"), resultSet.getString("nachname"));
                }
            }

        } catch (SQLException e) {
            log.error("Error logging benutzer {} in Methode 'login'.", benutzername, e);
        }

        return null;
    }

    private static boolean benutzerExistiert(String benutzername) {
        String query = "SELECT name FROM benutzer WHERE name = ?";

        try (Connection conn = MySQLConnection.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, benutzername);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            log.error("Error checking benutzer name in Methode 'benutzerExistiert' {}", benutzername, e);

            return false;
        }
    }
}