package org.example.view;

import org.example.model.security.Report;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ReportView extends JFrame {
    private JTextField tfName;
    private JTextField tfDate;
    private JButton btnGenerate;
    private JLabel lDate, lName, lTitle, lTotalProducts, lTotalMoney, lTotalCustomers, lPeriod;

    private final Color navy = new Color(0, 0, 90);
    private final Color purple = new Color(100, 19, 80);
    private static final Font bigFont = new Font("PT Sans", Font.PLAIN, 26);

    public ReportView() {
        setTitle("Report Generator");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initComponents();
        addComponents();
    }

    private void initComponents() {
        lName = createLabel("Name:", bigFont, navy);
        lDate = createLabel("Period:", bigFont, navy);
        lTitle = createLabel("Report", bigFont, purple);

        lPeriod = createLabel(null, bigFont, purple);
        lTotalCustomers = createLabel(null, bigFont, purple);
        lTotalMoney = createLabel(null, bigFont, purple);
        lTotalProducts = createLabel(null, bigFont, purple);

        tfName = new JTextField(15);
        tfDate = new JTextField(15);

        btnGenerate = createButton(null, "Generate", purple);
    }

    private void addComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 2, 10, 10));

        mainPanel.add(lName);
        mainPanel.add(tfName);
        mainPanel.add(lDate);
        mainPanel.add(tfDate);
        mainPanel.add(new JLabel());
        mainPanel.add(btnGenerate);

        JPanel reportPanel = new JPanel();
        reportPanel.setLayout(new GridLayout(4, 1));
        reportPanel.add(lTitle);
        reportPanel.add(lPeriod);
        reportPanel.add(lTotalCustomers);
        reportPanel.add(lTotalMoney);
        reportPanel.add(lTotalProducts);

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.NORTH);
        add(reportPanel, BorderLayout.CENTER);
    }

    public void setReport(Report report) {
        lPeriod.setText("Cashier: " + report.getName() + "     Period: " + report.getDateStart() + " -> " + report.getDateEnd());
        lTotalCustomers.setText("Nr. Customers: " + report.getTotalCustomers());
        lTotalMoney.setText("Money: " + report.getTotalMoney());
        lTotalProducts.setText("Nr. Products: " + report.getTotalProducts());
    }

    public String getTfName() {
        return tfName.getText();
    }

    public String getTfDate() {
        return tfDate.getText();
    }

    public void setGenerateButtonListener(ActionListener generateButtonListener) {
        btnGenerate.addActionListener(generateButtonListener);
    }

    private JLabel createLabel(String newText, Font newFont, Color newColor) {
        JLabel newLabel = new JLabel(newText);
        newLabel.setFont(newFont);
        newLabel.setForeground(newColor);
        return newLabel;
    }

    private JButton createButton(ImageIcon newImage, String newText, Color newColor) {
        JButton aux = new JButton(newText);
        aux.setIcon(newImage);
        aux.setBackground(newColor);
        aux.setForeground(Color.white);
        aux.setFont(bigFont);
        aux.setBorder(null);
        return aux;
    }

}
