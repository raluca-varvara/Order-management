package dao;

import businessLogic.exceptii.ProduseInsuficienteException;
import connection.ConnectionFactory;
import businessLogic.exceptii.NoSuchIdException;
import businessLogic.exceptii.NoSuchNameException;
import businessLogic.exceptii.NoSuchProductException;
import dataModels.Comanda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ComandaDAO extends AbstractDAO<Comanda>{

    private final static String INSERT_QUERY = "insert into comanda (idClient, idProdus, cantitate, pret) values (?, ?, ?, ?)";

    /**
     * se creeaza o comanda
     * @param idClient id ul clientului care face comanda
     * @param idProdus id-ul produsului comandat
     * @param cantitate cantitatea in care se comanda produsul
     * @param pret pretul total al comenzii
     * @return comanda adaugata
     */
    public Comanda insertComanda( int idClient, int idProdus, int cantitate, int pret)  {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(INSERT_QUERY);
            statement.setInt(1,idClient);
            statement.setInt(2,idProdus);
            statement.setInt(3,cantitate);
            statement.setInt(4,pret);
            Comanda comanda = new Comanda(0, idClient, idProdus, cantitate, pret); //se creaza comanda
            statement.executeUpdate(); //se executa


            return comanda;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        finally{

        }
        return null;
    }
}
