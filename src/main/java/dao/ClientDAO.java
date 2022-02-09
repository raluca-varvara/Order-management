package dao;

import connection.ConnectionFactory;
import dataModels.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public class ClientDAO extends AbstractDAO<Client>{
    /**
     * se cauta un client cu un anumit nume
     * @param name numele clientului
     * @return clientul gasit sau null daca nu exista
     */
    public Client findByName(String name){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("nume");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            List<Client> rez = createObjects(resultSet);
            if(rez.size()==0)
                return null;
            return rez.get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"ClientDAO:findByName " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
}
