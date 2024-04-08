package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CashierView extends JFrame {
    private JTextField tfUsername;
    private JTextField tfPassword;
    private JTextField tfName;
    private JTextField tfAddress;
    private JTextField tfCard;
    private JTextField tfCNP;
    private JTextField tfUpdate;
    private JTextField tfDelete;
    private JButton btnLogout;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnView;
    private JButton btnProcess;
    private JLabel lTitle, lName, lAddress, lCard, lCNP, lUpdate,lDelete;

    private final Color navy = new Color(0, 0, 90);
    private final Color purple = new Color(100, 19, 80);
    private static final Font bigFont = new Font("PT Sans", Font.PLAIN, 26);

    public CashierView() {
        initializeUI();
        initializeComponents();
        setupLayout();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeUI() {
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setTitle("Cashier View");
    }

    private void initializeComponents() {
        lTitle = createLabel("CASHIER:", bigFont, purple);
        lName = createLabel("Name:", bigFont, navy);
        lAddress = createLabel("Address:", bigFont, navy);
        lCard = createLabel("Card:", bigFont, navy);
        lCNP = createLabel("CNP:", bigFont, navy);
        lUpdate = createLabel("Update:", bigFont, navy);
        lDelete = createLabel("Delete:", bigFont, navy);

        tfUsername = createTextField("stefi@gmail.com");
        tfPassword = createTextField("stefi31@");
        tfName = createTextField("stefi");
        tfAddress = createTextField("stefi");
        tfCNP = createTextField("1234567890123");
        tfCard = createTextField("1234567890123456");
        tfUpdate = createTextField("");
        tfDelete = createTextField("");

        btnLogout = createButton("Logout", purple);
        btnAdd = createButton("Add Client", purple);
        btnView = createButton("View", purple);
        btnUpdate = createButton("Update", purple);
        btnProcess = createButton("Process", purple);
        btnDelete = createButton("Delete", purple);
    }

    private void setupLayout() {
        setLayout(new GridLayout(0, 2, 20, 20));
        add(lTitle);
        add(new JLabel());

        add(lName);
        add(tfName);
        add(lAddress);
        add(tfAddress);
        add(lCNP);
        add(tfCNP);
        add(lCard);
        add(tfCard);
        add(lUpdate);
        add(tfUpdate);
        add(btnUpdate);
        add(lDelete);
        add(tfDelete);
        add(btnDelete);
        add(btnLogout);
        add(tfUsername);
        add(new JLabel());
        add(tfPassword);
        add(btnAdd);
        add(btnView);
        add(btnProcess);
    }

    private JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    private JTextField createTextField(String placeholder) {
        JTextField textField = new JTextField();
        textField.setFont(bigFont);
        textField.setText(placeholder);
        return textField;
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(bigFont);
        button.setBackground(color);
        button.setForeground(Color.white);
        button.setBorderPainted(false);
        return button;
    }

    public String getUsername() {
        return tfUsername.getText();
    }

    public String getPassword() {
        return tfPassword.getText();
    }

    public String getTfName() {
        return tfName.getText();
    }

    public String getTfAddress() {
        return tfAddress.getText();
    }

    public String getTfUpdate() {
        return tfUpdate.getText();
    }

    public String getTfDelete() {
        return tfDelete.getText();
    }

    public String getTfCNP() {
        return tfCNP.getText();
    }

    public String getTfCard() {
        return tfCard.getText();
    }

    public void setLogoutButtonListener(ActionListener logoutButtonListener) {
        btnLogout.addActionListener(logoutButtonListener);
    }

    public void setAddButtonListener(ActionListener addButtonListener) {
        btnAdd.addActionListener(addButtonListener);
    }

    public void setUpdateButtonListener(ActionListener updateButtonListener) {
        btnUpdate.addActionListener(updateButtonListener);
    }
    public void setDeleteButtonListener(ActionListener deleteButtonListener) {
        btnDelete.addActionListener(deleteButtonListener);
    }

    public void setViewButtonListener(ActionListener viewButtonListener) {
        btnView.addActionListener(viewButtonListener);
    }

    public void setProcessButtonListener(ActionListener processButtonListener) {
        btnProcess.addActionListener(processButtonListener);
    }
}
