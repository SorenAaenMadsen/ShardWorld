package com.saaenmadsen.shardworld.integrationtest.dbaware;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseTest {


    @Test
    void testConnectivity() throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/shardworld",
                "shardworld", "123456"
        );
    }

    @Test
    void theMain() throws SQLException {

        String jdbcUrl = "jdbc:mariadb://localhost:3306/shardworld";
        String username = "shardworld";
        String password = "123456";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password)) {
            System.out.println("Connected to the MariaDB database!");
        }

    }
}
