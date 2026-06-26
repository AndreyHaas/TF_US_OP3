package com.haas.benutzeraccountsVerwaltung;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    private MySQLConnection() {
        /* This utility class should not be instantiated */
    }

    private static final String URL = "jdbc:mysql://localhost:3306/benutzerverwaltung";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}