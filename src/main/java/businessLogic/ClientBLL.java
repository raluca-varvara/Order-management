package businessLogic;

import dao.ClientDAO;
import dao.exceptions.DuplicateEntryException;
import businessLogic.exceptii.NoSuchIdException;
import businessLogic.exceptii.NoSuchNameException;
import dataModels.Client;

import java.util.List;

public class ClientBLL {

    /**
     * metoda care cauta in baza de date un client cu un anumit nume
     * @param s - numele de cautat
     * @return clientul gasit
     * @throws NoSuchNameException -nu exista numele
     */
    public Client findClientByName(String s) throws  NoSuchNameException {
        ClientDAO dao = new ClientDAO();
        Client cl = null;
        cl = dao.findByName(s);
        if(cl == null)
            throw new NoSuchNameException();
        return cl;
    }

    /**
     * metoda care creeaza o lista cu toate obiectele din tabelul client
     * @return lista de clienti
     */
    public List<Client> selectAll(){
        ClientDAO dao = new ClientDAO();
        List<Client> cl = dao.selectAll();
        return cl;
    }

    /**
     * se doreste inserarea unui client
     * @param c clientul de inserat
     * @return clientul inserat
     * @throws DuplicateEntryException daca exista deja cheia primara care vrea sa se insereze
     */
    public Client insert(Client c) throws DuplicateEntryException {
        ClientDAO dao = new ClientDAO();
        Client cl = dao.insert(c);

        return cl;
    }

    /**
     * updatarea unui client in functie de client
     * @param c noul obiect cu care se updateaza tupa
     * @return  clientul nou
     * @throws NoSuchIdException daca nu exista id-ul clientului care trebuie editat
     */
    public Client updateById(Client c) throws NoSuchIdException {
        ClientDAO dao = new ClientDAO();
        Client cl = dao.updateById(c);

        return cl;
    }

    /**
     * metoda care sterge un client din baza de date
     * @param id id-ul clientului de sters
     * @throws NoSuchIdException nu exista id-ul care se vrea sa se stearga
     */
    public void deleteById(int id) throws NoSuchIdException {
        ClientDAO dao = new ClientDAO();
        dao.deleteById(id);
    }
}
