package com.lovelyshades.dao.base;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractSqlServerDaoAdapter {

    private final ConnectionProvider connectionProvider;

    protected AbstractSqlServerDaoAdapter(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    protected Connection getConnection() throws SQLException {
        return connectionProvider.getConnection();
    }
}
