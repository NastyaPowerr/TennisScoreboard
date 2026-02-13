package org.roadmap.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManagerUtil {
    private static final HikariDataSource dataSource;
    private final static String DB_URL = "jdbc:h2:mem:tennis;INIT=RUNSCRIPT FROM 'classpath:init.sql'";

    private ConnectionManagerUtil() {
    }

    static {
        try {
            Class.forName("org.h2.Driver");

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(DB_URL);

            dataSource = new HikariDataSource(config);
            System.out.println("connected successfully");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Couldn't load driver for database.");
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
