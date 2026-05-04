package com.lovelyshades.dao.base;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class SqlServerConnectionProvider implements ConnectionProvider {

    @Value("${sqlserver.datasource.url}")
    private String url;

    @Value("${sqlserver.datasource.username}")
    private String username;

    @Value("${sqlserver.datasource.password}")
    private String password;

    @Value("${sqlserver.datasource.driver-class-name}")
    private String driverClassName;

    @PostConstruct
    public void loadDriver() {
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException exception) {
            throw new IllegalStateException("No se pudo cargar el driver de SQL Server", exception);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
