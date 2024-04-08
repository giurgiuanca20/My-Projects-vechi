package org.example.view;

import org.example.model.role.Cashier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CashierTableView extends JFrame {
    private final JTable table;

    public CashierTableView(List<Cashier> cashiers) {
        setTitle("Cashiers");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columns = {"Id","Name", "Phone"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Cashier cashier : cashiers) {
            Object[] row = {cashier.getId(),cashier.getName(), cashier.getPhone()};
            model.addRow(row);
        }

        table = new JTable(model);
        table.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
}
