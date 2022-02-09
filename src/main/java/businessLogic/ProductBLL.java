package businessLogic;

import dao.ProdusDAO;
import dao.exceptions.DuplicateEntryException;
import businessLogic.exceptii.NoSuchIdException;
import businessLogic.exceptii.NoSuchProductException;
import dataModels.Produs;

import java.util.List;

public class ProductBLL {

    public Produs findProdusById(int id) throws NoSuchIdException {
        ProdusDAO produsDAO = new ProdusDAO();
        Produs p = produsDAO.findById(id);
        if(p == null)
            throw new NoSuchIdException();
        return p;
    }

    /**
     * se cauta un produs cu un anumit nume
     * @param name numele produsului cautat
     * @return produsul gasit
     * @throws NoSuchProductException nu exista produsul
     */
    public Produs findByName(String name) throws NoSuchProductException {
        ProdusDAO produsDAO = new ProdusDAO();
        Produs p = null;
        p = produsDAO.findByName(name);
        if(p == null)
            throw new NoSuchProductException();
        return p;
    }

    /**
     * se insereaza un produs in baza de date
     * @param p produsul de inserat
     * @return produsul inserat
     * @throws DuplicateEntryException daca exista deja id-ul produsului
     */
    public Produs insert(Produs p) throws DuplicateEntryException {
        ProdusDAO produsDAO = new ProdusDAO();
        Produs pr = produsDAO.insert(p);
        return pr;
    }

    /**
     * se updateaza produsul cu un anumit id
     * @param p produsul nou
     * @return produsul updatat
     * @throws NoSuchIdException daca nu exista produsul care se vrea updatat
     */
    public Produs updateById(Produs p) throws NoSuchIdException {
        ProdusDAO produsDAO = new ProdusDAO();
        Produs pr = produsDAO.updateById(p);
        return pr;
    }

    /**
     * s esterge un produs cu un anumit id
     * @param id id -ul care se vrea sters
     * @throws NoSuchIdException nu exista id ul respectiv in baza de date
     */
    public void deleteById(int id) throws NoSuchIdException {
        ProdusDAO produsDAO = new ProdusDAO();
        produsDAO.deleteById(id);
    }

    public List<Produs> selectAll(){
        ProdusDAO dao = new ProdusDAO();
        List<Produs> cl = dao.selectAll();
        return cl;
    }
}
