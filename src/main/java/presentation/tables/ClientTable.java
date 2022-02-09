package presentation.tables;

import dataModels.Client;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ClientTable extends AbstractTable<Client>{
    /**
     * clasa care defineste un frame care contine un tabel cu o lista de clienti
     */
    private final JFrame frame = new JFrame("Warehouse");
    private final JPanel content = new JPanel();
    private final JTable table;

    public ClientTable(List<Client> l) {
        Object[] columns = this.columnsNames(l);
        Object[][] tableData = this.tableInput(l);
        table = new JTable(tableData, columns);

        table.setBackground(new Color(255,200,200));
        frame.setBounds(0, 0, 600, 600);
        content.setBounds(0, 0, 600, 600);
        content.setLayout(new BorderLayout());
        content.add(table.getTableHeader(), BorderLayout.PAGE_START);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        content.add(scrollPane, BorderLayout.CENTER);

        frame.setContentPane(content);
        frame.setVisible(true);
    }
}
