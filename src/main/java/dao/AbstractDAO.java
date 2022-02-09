package dao;

import businessLogic.exceptii.NoSuchIdException;
import connection.ConnectionFactory;
import dao.exceptions.DuplicateEntryException;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> tClass;
    @SuppressWarnings("unchecked")
    public AbstractDAO(){
        tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    /**
     * metoda ce se ocupa cu crearea unui string ce va reprezenta o interogare in baza de date
     * @param field elementul dupa care se face interogarea
     * @return stringul ce va reprezenta interogarea
     */
    protected String createSelectQuery(String field){
        StringBuilder sel = new StringBuilder();
        sel.append("select * from "+ tClass.getSimpleName());
        sel.append(" where "+field+" =?");

        return sel.toString();
    }
    /**
     * metoda ce se ocupa cu crearea unui string pentru executarea unei inserari in baza de date
     * @return stringul/query-ul ce va fi executat
     */
    private String createInsertQuery(){
        StringBuilder ins = new StringBuilder();
        ins.append("insert into "+tClass.getSimpleName());
        int idx = 0;
        for (Field field : tClass.getDeclaredFields()) {
            String fieldName = field.getName();
            if(idx==0)
                ins.append(" (" + fieldName);
            else
                ins.append(", "+ fieldName);
            idx++;
        }
        ins.append(") values ");
        for(int i = 0; i<tClass.getDeclaredFields().length; i++){
            if(i == 0)
                ins.append("(?");
            else
                ins.append(", ?");
        }
        ins.append(") ");
        return ins.toString();
    }
    /**
     * metoda ce se ocupa cu crearea unui query pentru stergerea unui anumit obiect din baza de date
     * @param field -parametrul dupa care se face stergerea
     * @return stringul ce se ocupa cu query-ul
     */
    private String createDeleteQuery(String field){
        StringBuilder del = new StringBuilder();
        del.append("delete from "+ tClass.getSimpleName());
        del.append(" where "+field+" =?");

        return del.toString();
    }
    /**
     * metoda ce se ocupa cu crearea unui query pentru stergerea unui anumit obiect din baza de date
     * @param str -fieldul dupa care se face updatarea
     * @return stringul ce se ocupa cu query-ul
     */
    private String createUpdateQuery(String str){
        StringBuilder upd = new StringBuilder();
        upd.append("update "+tClass.getSimpleName()+" set ");
        int idx = 0;
        for (Field field : tClass.getDeclaredFields()) {
            String fieldName = field.getName();
            if(idx!=0)
                if(idx == 1)
                    upd.append(fieldName+" = ? ");
                else
                    upd.append(", "+fieldName+" = ? ");
            idx++;
        }
        upd.append(" where "+str+" = ?");
        return upd.toString();
    }
    /**
     * metoda ce executa interogarea si cauta un id in baza de date
     * @param id - id-ul care se cauta
     * @return obiectul gasit sau null
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if(createObjects(resultSet).size()==0)
                return null;
            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"StudentDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
    /**
     * creeaza o lista de obiecte returnate de interogare
     * @param resultSet - rezultatul interogarii
     * @return lista de obiecte care se gaseste in result
     */
    protected List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = tClass.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : tClass.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, tClass);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     *metoda ce insereaza un obiect in baza de dare
     * @param t obiectul ce trebuie inserat
     * @return obectul inserat
     * @throws DuplicateEntryException daca mai exista deja id-ul
     */
    public T insert(T t) throws DuplicateEntryException {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createInsertQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            int i = 1;
            for (Field field : tClass.getDeclaredFields()) {
                field.setAccessible(true);
                if(field.get(t) instanceof String)
                    statement.setString(i,field.get(t).toString());
                else if(field.get(t) instanceof Integer)
                    statement.setInt(i,((Integer) field.get(t)).intValue());
                i++;
            }
            int j = statement.executeUpdate();
            return t;
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, "StudentDAO:insert " + e.getMessage());
            throw new DuplicateEntryException();
        }
        finally{
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }
    /**
     * metoda ce executa stergerea create mai sus
     * @param id id-ul in functie de care se sterge
     * @throws NoSuchIdException
     */
    public void deleteById(int id) throws NoSuchIdException {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            int j = statement.executeUpdate();
            if(j==0)
                throw new NoSuchIdException();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"StudentDAO:findById " + e.getMessage());
            throw new NoSuchIdException();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }
    /**
     * metoda ce executa updatarea de mai sus dupa id
     * @param t - obiectul cu ale carui campuri se updateaza tupla
     * @return - noul obiect creat
     * @throws NoSuchIdException
     */
    public T updateById(T t) throws NoSuchIdException {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createUpdateQuery("id");
        int firstPar = 0;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            int i = 1;
            for (Field field : tClass.getDeclaredFields()) {
                field.setAccessible(true);
                if(field.getName().equals("id")){
                    if(field.get(t) instanceof Integer)
                        firstPar = ((Integer) field.get(t)).intValue();
                }
                else{
                    if(field.get(t) instanceof String)
                        statement.setString(i,field.get(t).toString());
                    else if (field.get(t) instanceof Integer)
                        statement.setInt(i, ((Integer) field.get(t)).intValue());
                    i++;
                }
            }
            statement.setInt(i, firstPar);
            int j = statement.executeUpdate();
            if(j==0)
                throw new NoSuchIdException();
            return t;
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING,"StudentDAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
    public List<T> selectAll(){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "select * from "+tClass.getSimpleName();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
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
