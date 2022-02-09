package main;

import dao.ClientDAO;
import dao.ComandaDAO;
import dataModels.Client;
import dataModels.Comanda;
import presentation.ChooseTableView;

public class ClassDemo {

    public static void main(String[] args) {

        try {
            ClassDemo c = new ClassDemo();
            Class cls = c.getClass();

            // field long l

            ComandaDAO com = new ComandaDAO();
            Comanda comanda = new Comanda(2, 3 ,3, 1, 20);
            //com.insert(comanda);

            Client client =  new Client(6,"George Branzovenescu");
            ClientDAO cl = new ClientDAO();
            //Client newCl = cl.findByName("Max Blecher");
            //System.out.println(newCl.toString());
            ChooseTableView chooseTableView = new ChooseTableView();
        } catch(Exception e) {
            System.out.println(e.toString());
        }
    }

    public ClassDemo() {
        // no argument constructor
    }

    public ClassDemo(String l) {
        this.l = l;
    }



    private String l = "Ana";
}
