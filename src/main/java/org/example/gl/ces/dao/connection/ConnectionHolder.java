package org.example.gl.ces.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton permettant de garder la connection JDBC ouverte.
 */
public enum ConnectionHolder {

    INSTANCE;

    private static final String URL = "jdbc:h2:mem:ces";

    private final Connection connection;

    ConnectionHolder() {
        try {
            this.connection = DriverManager.getConnection(URL, "sa", "");
            new SchemaInitializer(connection).initialize();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
