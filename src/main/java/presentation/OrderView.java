package presentation;

import businessLogic.ComandaBLL;
import businessLogic.exceptii.FacturaNechitateException;
import businessLogic.exceptii.ProduseInsuficienteException;
import dao.ClientDAO;
import businessLogic.exceptii.NoSuchIdException;
import businessLogic.exceptii.NoSuchNameException;
import businessLogic.exceptii.NoSuchProductException;
import dataModels.Client;
import presentation.tables.ProductTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderView {
    private final JFrame frame = new JFrame("Warehouse Orders");
    private final JPanel content = new JPanel();

    private final JLabel lClient = new JLabel("Nume Client");
    private final JLabel lProdus = new JLabel("Produs");
    private final JTextField tClient = new JTextField();
    private final JTextField tProdus = new JTextField();
    private final JLabel lCantitate = new JLabel("Cantitate");
    private final JTextField tCantitate = new JTextField();
    private final JButton creare = new JButton("CREARE COMANDA");
    private final JButton facturare = new JButton("FACTURARE");
    private final JButton afisare = new JButton("Afisare comenzi");

    ComandaBLL comandaBLL = new ComandaBLL();


    public OrderView() {
        content.setBackground(new Color(255,200,200));
        frame.setBounds(0, 0, 600, 600);
        content.setBounds(0, 0, 600, 600);
        content.setLayout(null);

        lClient.setBounds(50,0,500,50);
        lClient.setForeground(new Color(102,0,51));
        tClient.setBounds(50,50,500,50);
        lProdus.setBounds(50,100,500,50);
        lProdus.setForeground(new Color(102,0,51));
        tProdus.setBounds(50,150,500,50);
        lCantitate.setBounds(50,200,500,50);
        lCantitate.setForeground(new Color(102,0,51));
        tCantitate.setBounds(50,250,500,50);

        creare.setBounds(50,350,225,50);
        creare.setBackground(new Color(102,0,51));
        creare.setForeground(new Color(255,200,200));
        facturare.setBounds(325,350,225,50);
        facturare.setBackground(new Color(102,0,51));
        facturare.setForeground(new Color(255,200,200));
        afisare.setBounds(50,450,225,50);
        afisare.setBackground(new Color(102,0,51));
        afisare.setForeground(new Color(255,200,200));

        CreareActionListener acC = new CreareActionListener();
        creare.addActionListener(acC);
        FacturaActionListener acF = new FacturaActionListener();
        facturare.addActionListener(acF);
        AfisareActionListener acA = new AfisareActionListener();
        afisare.addActionListener(acA);

        content.add(lCantitate);
        content.add(lClient);
        content.add(lProdus);
        content.add(tCantitate);
        content.add(tClient);
        content.add(tProdus);
        content.add(creare);
        content.add(facturare);
        content.add(afisare);

        frame.setContentPane(content);
        frame.setVisible(true);
    }

    void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    /**
     * action listener pentru butonul de adaugare a unei comenzi
     */
    class CreareActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                try {
                    int cantitate = Integer.parseInt(tCantitate.getText());
                    comandaBLL.inserare(tClient.getText(),tProdus.getText(),cantitate);
                }
                catch(FacturaNechitateException err){
                    showMessage(err.getMessage());
                }
            }catch(NumberFormatException numberFormatException){
                showMessage("Date introduse gresit, cantitate trebuie sa fie numar");
            } catch (ProduseInsuficienteException | NoSuchProductException | NoSuchNameException | NoSuchIdException err) {
                showMessage(err.getMessage());
            }
        }
    }

    /**
     * action listener pentru facturare
     */
    class FacturaActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            comandaBLL.facturare();
        }
    }

    /**
     * action listener pentru butonul de afisare a cosului de cumparaturi
     */
    class AfisareActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            ComandaBLL c = new ComandaBLL();
            ProductTable table = new ProductTable(comandaBLL.getProduseActuale());
        }
    }
}
