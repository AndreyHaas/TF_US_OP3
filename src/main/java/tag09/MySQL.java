package tag09;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

  private MySQL() {
    /* This utility class should not be instantiated */
  }

  private static final String URL = "jdbc:mysql://localhost:3306/firmenverwaltung?useSSL=false&serverTimezone=UTC";
  private static final String USER = "root";
  private static final String PASSWORD = "";

  public static Connection getConnection() throws SQLException {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    }

    return DriverManager.getConnection(URL, USER, PASSWORD);
  }
}