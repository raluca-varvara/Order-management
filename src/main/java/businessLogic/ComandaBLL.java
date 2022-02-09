package businessLogic;

import businessLogic.exceptii.FacturaNechitateException;
import businessLogic.exceptii.ProduseInsuficienteException;
import businessLogic.validators.ProductQuantityValidator;
import dao.ComandaDAO;
import businessLogic.exceptii.NoSuchIdException;
import businessLogic.exceptii.NoSuchNameException;
import businessLogic.exceptii.NoSuchProductException;
import dataModels.Client;
import dataModels.Comanda;
import dataModels.Factura;
import dataModels.Produs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ComandaBLL {
    private Factura factura = new Factura();
    File file = new File("factura.txt");
    FileWriter writer;

    /**
     * metoda care creaza o comanda, prima oara se cauta numele clientului, apoi numele produsului si daca se gasesc, se valideaza cantitatea produsului
     * se calculeaza apoi pretul si se verifica daca este inceputul comenzii sau este acelasi client care comanda se plaseaza comanda
     * @param numeClient numele clientului care face comanda
     * @param numeProdus numele produsului comandat
     * @param cantitate cantitatea in care se vrea sa se comande
     * @return comanda inserata
     * @throws NoSuchIdException
     * @throws NoSuchNameException daca nu exista numele clientului
     * @throws NoSuchProductException daca nu exista numele produsului
     * @throws ProduseInsuficienteException daca nu exista destule produse
     * @throws FacturaNechitateException daca un alt client vrea sa faca comanda dar cel de dinainte nu si-a achitat-o
     */
    public Comanda inserare(String numeClient, String numeProdus, int cantitate) throws NoSuchIdException, NoSuchNameException, NoSuchProductException, ProduseInsuficienteException, FacturaNechitateException {
        ClientBLL cl = new ClientBLL();
        Client client = cl.findClientByName(numeClient);//se cauta clientul

        ProductBLL produsBLL = new ProductBLL(); //se cauta id-ul produsului
        Produs produs = produsBLL.findByName(numeProdus);

        ProductQuantityValidator pqv = new ProductQuantityValidator();
        pqv.validate(produs, cantitate); //se valideaza

        int pret = cantitate * produs.getPret(); //se calculeaza pretul

        if (factura.getInProcesare() == 0 || client.getId() == factura.getIdClient()) {
            ComandaDAO c = new ComandaDAO();
            Comanda comanda = c.insertComanda(client.getId(),produs.getId(),cantitate, pret); //se insereaza comanda
            produs.setCantitate(produs.getCantitate()-cantitate);
            produsBLL.updateById(produs); //se updateaza produsul
            factura.setInProcesare(1); //se seteaza ca e in procesare
            factura.setIdClient(client.getId());
            Produs pr = produsBLL.findByName(numeProdus);
            pr.setCantitate(cantitate);
            factura.getProduse().add(pr);
            factura.setPretTotal(factura.getPretTotal()+comanda.getPret()); //se adauga produsul la factura
            return comanda;
        }
        else{
            throw new FacturaNechitateException();
        }

    }

    /**
     * se scriu intr-un fisier txt toate produsele din comanda de pana acum si se reinitializeaza factura
     */
    public void facturare(){
        try{
            file.createNewFile();
            writer = new FileWriter("factura.txt");
            for(Produs p: factura.getProduse()){
                writer.write(p.toString());
            }
            writer.write("Pret total = "+factura.getPretTotal());
            writer.close();
            factura = new Factura();
        }
        catch (IOException er) {
            er.printStackTrace();
        }
    }

    /**
     *
     * @return lista cu produsele din "cosul de cumparaturi"
     */
    public List<Produs> getProduseActuale(){
        return factura.getProduse();
    }
}
