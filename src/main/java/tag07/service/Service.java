package tag07.service;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.lang3.StringUtils;
import tag03.template.MySQL;

public class Service {

  public static String selectAlleBestellungen() {
    try (Connection connection = MySQL.getConnection(); Statement statement = connection.createStatement()) {

      String query = "SELECT * FROM selectAlleBestellungen";
      ResultSet resultSet = statement.executeQuery(query);

      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(" | ");

      for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
        stringBuilder.append(resultSet.getMetaData().getColumnName(i)).append(" | ");
      }

      stringBuilder.append(System.lineSeparator());

      while (resultSet.next()) {
        stringBuilder.append(" | ");

        for (int i = 1; i < resultSet.getMetaData().getColumnCount(); i++) {
          stringBuilder.append(resultSet.getString(i)).append(" | ");
        }

        stringBuilder.append(System.lineSeparator());
      }

      return stringBuilder.toString();

    } catch (SQLException exception) {
      exception.printStackTrace();
      return StringUtils.EMPTY;
    }
  }

  public static String selectAnzahlGekaufteProdukte(int kundenNummer) {
    String query = "SELECT * FROM selectAnzahlGekaufteProdukte WHERE nummer = ?";
    try (Connection connection = MySQL.getConnection(); PreparedStatement statement = connection.prepareStatement(
        query)) {
      statement.setInt(1, kundenNummer);
      ResultSet resultSet = statement.getResultSet();
      StringBuilder sb = new StringBuilder();

      if (resultSet == null) {
        return "Dieser Kund hat nix gekauft";
      }

      if (resultSet.next()) {
        sb.append("Kunde: ").append(kundenNummer).append(" - Anzahl: ")
            .append(resultSet.getInt("anzahl"));
      }

      return sb.toString();

    } catch (SQLException ex) {
      ex.printStackTrace();
      return StringUtils.EMPTY;
    }
  }

  public static int insertArtikel(String bezeichnung, BigDecimal preis, int hersteller) {

    String query = "{CALL insertArtikel(?,?,?,?)}";

    int falscheResult = -1;

    try (Connection connection = MySQL.getConnection(); CallableStatement statement = connection.prepareCall(
        query)) {
      statement.setString(1, bezeichnung);
      statement.setBigDecimal(2, preis);
      statement.setInt("h", hersteller);

      statement.registerOutParameter("n", JDBCType.INTEGER);

      return statement.executeUpdate() > 0 ? statement.getInt(4) : falscheResult;
    } catch (SQLException ex) {
      ex.printStackTrace();
      return falscheResult;
    }
  }

  public static int updateArtikelPreis(int nummer, BigDecimal preis) {
    try (
        Connection connection = MySQL.getConnection();
        CallableStatement statement = connection.prepareCall("{CALL updateArtikelPreis(?, ?)}"))
    {
      statement.setInt("nummer", nummer);
      statement.setBigDecimal("preis", preis);

      return statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      return -1;
    }
  }
}
