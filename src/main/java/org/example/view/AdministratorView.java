package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class AdministratorView extends JFrame {
    private JTextField tfUsername;
    private JTextField tfPassword;
    private JTextField tfName;
    private JTextField tfPhone;
    private JTextField tfUpdate;
    private JTextField tfDelete;
    private JTextField tfFidelity;
    private JButton btnLogout;
    private JButton btnCreate;
    private JButton btnUpdate;
    private JButton btnView;
    private JButton btnDelete;
    private JButton btnGenerate;
    private JButton btnFidelity;
    private JLabel lNewUser, lNewPassword, lName, lTitle, lPhone,lFidelity;

    private final Color navy = new Color(0, 0, 90);
    private final Color purple = new Color(100, 19, 80);
    private static final Font bigFont = new Font("PT Sans", Font.PLAIN, 26);

    public AdministratorView() {
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(lTitle);
        add(lName);
        add(tfName);
        add(lPhone);
        add(tfPhone);
        add(lNewUser);
        add(tfUsername);
        add(lNewPassword);
        add(tfPassword);
        add(btnCreate);
        add(tfUpdate);
        add(btnUpdate);
        add(tfDelete);
        add(btnDelete);
        add(btnView);
        add(btnGenerate);
        add(lFidelity);
        add(tfFidelity);
        add(btnFidelity);
        add(btnLogout);
    }

    private void initializeFields() {
        lNewUser = createLabel("Username:", bigFont, navy);
        lNewPassword = createLabel("Password:", bigFont, navy);
        lName = createLabel("Name:", bigFont, navy);
        lPhone = createLabel("Phone:", bigFont, navy);
        lTitle = createLabel("ADMINISTRATOR", bigFont, purple);
        lFidelity= createLabel("Set fidelity percentage ", bigFont, purple);

        tfUsername = createTextField("andre@gmail.com");
        tfPassword = createTextField("andre31@");
        tfName = createTextField("andre");
        tfPhone = createTextField("0755638295");
        tfUpdate = createTextField("");
        tfDelete = createTextField("");
        tfFidelity = createTextField("");


        btnLogout = createButton(null, " Logout ", purple);
        btnCreate = createButton(null, " Create ", purple);
        btnUpdate = createButton(null, " Update ", purple);
        btnDelete = createButton(null, " Delete ", purple);
        btnView = createButton(null, " View ", purple);
        btnGenerate = createButton(null, " Generate ", purple);
        btnFidelity = createButton(null, " Set ", purple);
    }

    private JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    private JTextField createTextField(String placeholder) {
        JTextField textField = new JTextField(15);
        textField.setBounds(450, 600, 240, 150);
        textField.setText(placeholder);
        return textField;
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

    public String getUsername() {
        return tfUsername.getText();
    }

    public String getPassword() {
        return tfPassword.getText();
    }

    public String getTfName() {
        return tfName.getText();
    }

    public String getTfPhone() {
        return tfPhone.getText();
    }

    public String getTfUpdate() {
        return tfUpdate.getText();
    }

    public String getTfDelete() {
        return tfDelete.getText();
    }

    public String getTfFidelity() {
        return tfFidelity.getText();
    }

    public void setLogoutButtonListener(ActionListener logoutButtonListener) {
        btnLogout.addActionListener(logoutButtonListener);
    }

    public void setCreateButtonListener(ActionListener createButtonListener) {
        btnCreate.addActionListener(createButtonListener);
    }

    public void setUpdateButtonListener(ActionListener updateButtonListener) {
        btnUpdate.addActionListener(updateButtonListener);
    }

    public void setViewButtonListener(ActionListener viewButtonListener) {
        btnView.addActionListener(viewButtonListener);
    }

    public void setDeleteButtonListener(ActionListener deleteButtonListener) {
        btnDelete.addActionListener(deleteButtonListener);
    }

    public void setGenerateButtonListener(ActionListener generateButtonListener) {
        btnGenerate.addActionListener(generateButtonListener);
    }
    public void setFidelityButtonListener(ActionListener fidelityButtonListener) {
        btnFidelity.addActionListener(fidelityButtonListener);
    }
    public void setVisible() {
        this.setVisible(true);
    }
}
