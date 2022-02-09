package presentation;

import businessLogic.ClientBLL;
import dao.exceptions.DuplicateEntryException;
import businessLogic.exceptii.NoSuchIdException;
import dataModels.Client;
import presentation.tables.ClientTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ClientOperationsView {

    private final JFrame frame = new JFrame("Warehouse Client");
    private final JPanel content = new JPanel();

    private final JLabel lId = new JLabel("ID");
    private final JLabel lNume = new JLabel("Nume");
    private final JTextField tId = new JTextField();
    private final JTextField tNume = new JTextField();
    private final JButton add = new JButton("ADD");
    private final JButton edit = new JButton("EDIT");
    private final JButton delete = new JButton("DELETE");
    private final JButton afisare = new JButton("AFISARE");
    private final JTextArea editInfo = new JTextArea("introduceti id-ul clientului pe care vreti sa il editati\n si noile informatii");
    private final JTextArea deleteInfo = new JTextArea("introduceti numai id-ul clientului pe care vreti\n sa il stergeti");

    public ClientOperationsView() {
        content.setBackground(new Color(255,200,200));
        frame.setBounds(0, 0, 600, 700);
        content.setBounds(0, 0, 600, 700);
        content.setLayout(null);

        lId.setBounds(50,0,500,50);
        lId.setForeground(new Color(102,0,51));
        tId.setBounds(50,50,500,50);
        lNume.setBounds(50,100,500,50);
        lNume.setForeground(new Color(102,0,51));
        tNume.setBounds(50,150,500,50);

        add.setBounds(50,250,150,50);
        add.setBackground(new Color(102,0,51));
        add.setForeground(new Color(255,200,200));
        edit.setBounds(50,350,150,50);
        edit.setBackground(new Color(102,0,51));
        edit.setForeground(new Color(255,200,200));
        delete.setBounds(50,450,150,50);
        delete.setBackground(new Color(102,0,51));
        delete.setForeground(new Color(255,200,200));
        afisare.setBounds(50,550,150,50);
        afisare.setBackground(new Color(102,0,51));
        afisare.setForeground(new Color(255,200,200));

        editInfo.setBounds(250,350,250,50);
        editInfo.setForeground(new Color(102,0,51));
        editInfo.setBackground(new Color(255,200,200));
        editInfo.setEditable(false);
        deleteInfo.setBounds(250,450,250,50);
        deleteInfo.setForeground(new Color(102,0,51));
        deleteInfo.setBackground(new Color(255,200,200));
        deleteInfo.setEditable(false);

        AddActionListener acA = new AddActionListener();
        add.addActionListener(acA);
        DeleteActionListener acD = new DeleteActionListener();
        delete.addActionListener(acD);
        UpdateActionListener acU = new UpdateActionListener();
        edit.addActionListener(acU);
        AfisareActionListener acAf = new AfisareActionListener();
        afisare.addActionListener(acAf);

        content.add(lId);
        content.add(tId);
        content.add(lNume);
        content.add(tNume);
        content.add(add);
        content.add(edit);
        content.add(delete);
        content.add(afisare);
        content.add(editInfo);
        content.add(deleteInfo);

        frame.setContentPane(content);
        frame.setVisible(true);

    }
    void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }
    /**
     * action listener pentru butonul de daugare a unui client
     */
    class AddActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Client client = new Client(Integer.parseInt(tId.getText()), tNume.getText());
                try{
                    ClientBLL clientBLL = new ClientBLL();
                    clientBLL.insert(client);
                }catch( DuplicateEntryException err){
                    showMessage(err.getMessage());
                }
            }catch(NumberFormatException err){
                showMessage("Nu sunt bune datele");
            }

        }
    }
    /**
     * action listener pentru butonul de delete
     */
    class DeleteActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(tId.getText());
                try {
                    ClientBLL clientBLL = new ClientBLL();
                    clientBLL.deleteById(id);
                } catch (NoSuchIdException noSuchIdException) {
                    showMessage(noSuchIdException.getMessage());
                }
            }catch(NumberFormatException err){
                showMessage("Id-ul trebuie sa fie un numar");
            }

        }
    }
    /**
     * action listener pentru butonul de update
     */
    class UpdateActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Client client = new Client(Integer.parseInt(tId.getText()), tNume.getText());
                try {
                    ClientBLL clientBLL = new ClientBLL();
                    clientBLL.updateById(client);
                } catch (NoSuchIdException noSuchIdException) {
                    showMessage(noSuchIdException.getMessage());
                }
            }catch(NumberFormatException err){
                showMessage("Id-ul trebuie sa fie un numar");
            }

        }
    }

    /**
     * action listener pentru butonul de afisare
     */
    class AfisareActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            ClientBLL cl = new ClientBLL();
            List<Client> l = cl.selectAll();
            ClientTable table = new ClientTable(l);
        }
    }
}
