package org.example.gl.ces.dao.taux;

import org.example.gl.ces.dao.connection.ConnectionHolder;

import java.sql.SQLException;

public class TauxDao {

    public TauxEntity findAll() {
        var taux = new TauxEntity();
        var connexion = ConnectionHolder.INSTANCE.getConnection();
        try (var statement = connexion.createStatement()) {
            try (var result = statement.executeQuery("select * from taux")) {
                while (result.next()) {
                    var nom = result.getString(1);
                    var value = result.getDouble(2);
                    switch (nom) {
                        case "CES":
                            taux.setTauxCes(value);
                            break;
                        case "CONGES":
                            taux.setTauxConges(value);
                            break;
                        default:
                            throw new IllegalStateException("Taux non supporté : nom=" + nom + ", valeur=" + value);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return taux;
    }
}
