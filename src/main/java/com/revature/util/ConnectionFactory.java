package com.revature.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static ConnectionFactory connectionFactory = new ConnectionFactory();
    private Properties properties = new Properties();

    private ConnectionFactory() {
        try {
            properties.load(new FileReader("src/main/java/resources/database.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Lazy instantiation the object
    public static ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    // Static block to CHECK intended driver is available
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(properties.getProperty("url"),
                    properties.getProperty("username"),
                    properties.getProperty("password")
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
