package dao;

import connection.ConnectionFactory;
import dataModels.Client;
import dataModels.Produs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * clasa ce se ocupa cu accesul la baza de date pentru tabelul produse
 */

public class ProdusDAO extends AbstractDAO<Produs> {
    /**
     * se cauta un produs cu un anumit nume
     * @param name numele produsului care se cauta
     * @return un produs daca numele produsului este gasit in baza de date sau null in caz contrar
     */
    public Produs findByName(String name){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("nume");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            List<Produs> rez = createObjects(resultSet);
            if(rez.size()==0)
                return null;
            return rez.get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"StudentDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

}
