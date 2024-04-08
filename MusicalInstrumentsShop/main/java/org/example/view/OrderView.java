package org.example.view;

import org.example.model.cart.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class OrderView extends JFrame {
    private JTable table;
    private JTextField tfIdOrder;
    private JButton btnProcessOrder;
    private JLabel lIdOrder, lFidelity;

    private final Color navy = new Color(0, 0, 90);
    private final Color purple = new Color(100, 19, 80);
    private static final Font bigFont = new Font("PT Sans", Font.PLAIN, 26);

    public OrderView() {
        setTitle("Order Processing");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        addComponents();
    }

    private void initComponents() {
        lIdOrder = createLabel("Enter Order ID:", bigFont, navy);
        tfIdOrder = new JTextField(15);
        btnProcessOrder = createButton(null, "Process Order", purple);
        lFidelity = createLabel("", bigFont, navy);
    }

    private void addComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        idPanel.add(lIdOrder);
        idPanel.add(tfIdOrder);

        JPanel fidelityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fidelityPanel.add(lFidelity);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnProcessOrder);

        mainPanel.add(idPanel);
        mainPanel.add(fidelityPanel);
        mainPanel.add(buttonPanel);

        add(mainPanel, BorderLayout.NORTH);
    }

    public Long getIdOrder() {
        try {
            return Long.parseLong(tfIdOrder.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid order ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void setListOrders(List<Order> orders) {
        if (table != null) {
            remove(table);
        }

        String[] columns = {"ID", "Number of Products", "Total Price"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        if (orders != null) {
            for (Order order : orders) {
                Object[] row = {order.getId(), order.getNrProducts(), order.getTotalPrice()};
                model.addRow(row);
            }
        }
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setFidelity(String fidelity) {
        lFidelity.setText("Fidelity Points: " + fidelity);
    }

    public void setProcessButtonListener(ActionListener processButtonListener) {
        btnProcessOrder.addActionListener(processButtonListener);
    }

    private JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    private JButton createButton(ImageIcon icon, String text, Color color) {
        JButton button = new JButton(text);
        button.setIcon(icon);
        button.setBackground(color);
        button.setForeground(Color.white);
        button.setFont(bigFont);
        button.setBorder(null);
        return button;
    }
}
