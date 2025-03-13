package com.example.Library.Management.System.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DatabaseConnectionTest {
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void testDatabaseConnection() {
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("=== Database Connection Test ===");
            System.out.println("Database Connection Successful!");
            System.out.println("Database: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("Version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("==============================");
        } catch (SQLException e) {
            System.err.println("Database Connection Failed");
            e.printStackTrace();
        }
    }
}
