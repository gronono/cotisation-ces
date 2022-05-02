package org.example.gl.ces.dao.connection;

import java.sql.Connection;
import java.sql.SQLException;

public class SchemaInitializer {

    private final Connection connection;

    SchemaInitializer(Connection connection) {
        this.connection = connection;
    }

    public void initialize() throws SQLException {
        createSchema();
        insertTaux("CES",   0.077725);
        insertTaux("CONGES",  0.1);
    }

    private void createSchema() throws SQLException {
        try (var statement = connection.createStatement()) {
            statement.execute("CREATE TABLE taux (" +
                " ID VARCHAR PRIMARY KEY, " +
                " VALEUR NUMERIC(9,4) NOT NULL " +
                ")");
        }
    }

    private void insertTaux(String nom, double valeur) throws SQLException {
        try (var statement = connection.prepareStatement("INSERT INTO taux (ID, VALEUR) VALUES (?, ?)")) {
            statement.setString(1, nom);
            statement.setDouble(2, valeur);
            statement.execute();
        }
    }
}
