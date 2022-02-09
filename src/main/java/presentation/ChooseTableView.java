package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChooseTableView {
    /**
     * frame-ul principal in care se alege sectiunea
     */
    private final JFrame frame = new JFrame("Warehouse");
    private final JPanel content = new JPanel();

    private final JLabel title = new JLabel("Warehouse",SwingConstants.CENTER);;
    private final JButton clienti = new JButton("CLIENTI");
    private final JButton produse = new JButton("PRODUSE");
    private final JButton comenzi = new JButton("COMENZI");

    public ChooseTableView() {
        content.setBackground(new Color(255,200,200));
        frame.setBounds(0, 0, 600, 600);
        content.setBounds(0, 0, 600, 600);
        content.setLayout(null);

        title.setBounds(100, 50, 400, 50);
        title.setFont(new Font("Serif",Font.BOLD, 35));
        title.setForeground(new Color(102,0,51));

        clienti.setBounds(100, 150, 400, 50);
        clienti.setBackground(new Color(102,0,51));
        clienti.setForeground(new Color(255,200,200));
        produse.setBounds(100, 250, 400, 50);
        produse.setBackground(new Color(102,0,51));
        produse.setForeground(new Color(255,200,200));
        comenzi.setBounds(100, 350, 400, 50);
        comenzi.setBackground(new Color(102,0,51));
        comenzi.setForeground(new Color(255,200,200));

        ClientActionListener acC = new ClientActionListener();
        this.addClientiListener(acC);
        ProduseActionListener acP = new ProduseActionListener();
        this.addProduseListener(acP);
        ComenziActionListener acO = new ComenziActionListener();
        this.addComenziListener(acO);

        content.add(title);
        content.add(clienti);
        content.add(produse);
        content.add(comenzi);

        frame.setContentPane(content);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void addClientiListener(ClientActionListener ac) {
        clienti.addActionListener(ac);
    }
    void addProduseListener(ProduseActionListener ac) {
        produse.addActionListener(ac);
    }
    void addComenziListener(ActionListener ac) {
        comenzi.addActionListener(ac);
    }

    class ClientActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            ClientOperationsView c = new ClientOperationsView();
        }
    }
    class ProduseActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            ProductOperationsView c = new ProductOperationsView();
        }
    }

    class ComenziActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            OrderView c = new OrderView();
        }
    }

}
