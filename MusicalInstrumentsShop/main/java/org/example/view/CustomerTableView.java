package org.example.view;

import org.example.model.role.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomerTableView extends JFrame {
    private JTable table;

    public CustomerTableView(List<Customer> customers) {
        initializeUI();
        populateTable(customers);
    }

    private void initializeUI() {
        setTitle("Customer Information");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Font tableFont = new Font("Arial", Font.PLAIN, 14);

        String[] columns = {"Id","Name", "CNP", "Card", "Address", "Fidelity Points"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        table = new JTable(model);
        table.setFont(tableFont);

        JScrollPane scrollPane = new JScrollPane(table);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private void populateTable(List<Customer> customers) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        for (Customer customer : customers) {
            Object[] rowData = {
                    customer.getId(),
                    customer.getName(),
                    customer.getCNP(),
                    customer.getCard(),
                    customer.getAddress(),
                    customer.getPoints_fidelity()
            };
            model.addRow(rowData);
        }
    }
}
