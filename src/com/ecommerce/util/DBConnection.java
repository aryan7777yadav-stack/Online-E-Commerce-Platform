package com.ecommerce.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton Database Connection Manager - JDBC
 * Handles MySQL connectivity for the E-Commerce Platform
 */
public class DBConnection {
    private static DBConnection instance;
    // Change these as per your MySQL setup
    private static final String URL = "jdbc:mysql://localhost:3306/ecommerce_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private DBConnection() {
        try {
            Class.forName(DRIVER);
            System.out.println("MySQL Driver Loaded Successfully");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL Driver not found. Add mysql-connector-j.jar to lib/");
            System.err.println("Download: https://dev.mysql.com/downloads/connector/j/");
        }
    }

    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Test connectivity - call this in Review 1 demo
     */
    public boolean testConnection() {
        try (Connection conn = getConnection()) {
            System.out.println("✓ Database Connected: " + conn.getMetaData().getDatabaseProductName());
            return true;
        } catch (SQLException e) {
            System.err.println("✗ Database Connection Failed: " + e.getMessage());
            System.err.println("  -> Make sure MySQL is running and ecommerce_db exists (run database/schema.sql)");
            return false;
        }
    }
}
