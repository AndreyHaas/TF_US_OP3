package tag03.template.models.services;

import tag03.template.MySQL;
import tag03.template.models.Artikel;
import tag03.template.models.Hersteller;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ArtikelService {
    public static void selectArtikel() {
        try (Connection con = MySQL.getConnection()) {
            Statement statement = con.createStatement();

            ResultSet rs = statement.executeQuery("SELECT * FROM Artikel");

            while (rs.next()) {
                int nummer = rs.getInt("nummer");
                String bezeichnung = rs.getString("bezeichnung");
                BigDecimal preis = rs.getBigDecimal("preis");

                Hersteller hersteller = Hersteller.herstellers.get(rs.getInt("hersteller"));

                new Artikel(nummer, bezeichnung, preis, hersteller);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}