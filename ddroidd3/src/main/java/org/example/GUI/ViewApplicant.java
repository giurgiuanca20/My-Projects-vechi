package org.example.GUI;

import org.example.DAO.CountryDAO;
import org.example.Model.Create;

import javax.swing.*;
import java.awt.*;

public class ViewApplicant extends JFrame {
    private final JFrame frame;
    private JLabel lTitle, lContactInfo, lAddress, lFirstName, lLastName, lPhone, lEmail, lAddress1, lAddress2, lCountry, lState, lCity, lError, lUsername, lPassword;
    private JButton bJoin;
    private JTextField tFirstName, tLastName, tPhone, tEmail, tAddress1, tAddress2, tUsername, tPassword;
    private JComboBox<String> comboBoxCountry, comboBoxCity;
    private Create create = new Create();
    // Getter methods to retrieve text field values
    public String gettFirstName() {
        return tFirstName.getText();
    }

    public String gettLastName() {
        return tLastName.getText();
    }

    public String gettPhone() {
        return tPhone.getText();
    }

    public String gettEmail() {
        return tEmail.getText();
    }

    public String gettAddress1() {
        return tAddress1.getText();
    }

    public String gettAddress2() {
        return tAddress2.getText();
    }

    public String gettUsername() {
        return tUsername.getText();
    }

    public String gettPassword() {
        return tPassword.getText();
    }

    // Getter methods to check if text fields are empty
    public boolean gettFirstNameEmpty() {
        return tFirstName.getText().isEmpty();
    }

    public boolean gettLastNameEmpty() {
        return tLastName.getText().isEmpty();
    }

    public boolean gettPhoneEmpty() {
        return tPhone.getText().isEmpty();
    }

    public boolean gettEmailEmpty() {
        return tEmail.getText().isEmpty();
    }

    public boolean gettAddress1Empty() {
        return tAddress1.getText().isEmpty();
    }

    public boolean gettUsernameEmpty() {
        return tUsername.getText().isEmpty();
    }

    public boolean gettPasswordEmpty() {
        return tPassword.getText().isEmpty();
    }


    // Getter methods to retrieve selected values from ComboBoxes
    public String getComboBoxCountry() {
        return (String) comboBoxCountry.getSelectedItem();
    }

    public String getComboBoxCity() {
        return (String) comboBoxCity.getSelectedItem();
    }
    // Method to populate the city ComboBox based on the selected country
    public void createCity() {
        String selectedOption = (String) comboBoxCountry.getSelectedItem();

        String[] options2 = CountryDAO.modifyCityComboBox(selectedOption);
        comboBoxCity.setModel(new DefaultComboBoxModel<>(options2));
        comboBoxCity.setEnabled(true);
    }

    public ViewApplicant(String name) {
        frame = new JFrame(name);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setSize(1600, 900);
        frame.setLocationRelativeTo(null);
    }
    // Method to display error message
    public void validare(String s) {
        lError = create.makeLabel(s);
        lError.setForeground(Color.RED);
        frame.add(lError);
    }

    public void prepareGui(Controller controller) {

        frame.getContentPane().removeAll();
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setSize(1600, 900);
        frame.setLocationRelativeTo(null);

        lTitle = create.makeLabel("Aplication Form");
        lContactInfo = create.makeLabel("Contact info");
        lFirstName = create.makeLabel("First name*  ");
        lLastName = create.makeLabel("Last Name*  ");
        lPhone = create.makeLabel("Phone number*  ");
        lEmail = create.makeLabel("Email*  ");
        lAddress = create.makeLabel("Adress");
        lAddress1 = create.makeLabel("Address1*  ");
        lAddress2 = create.makeLabel("Address2  ");
        lCountry = create.makeLabel("Country* ");
        lState = create.makeLabel("State ");
        lCity = create.makeLabel("City* ");
        lUsername = create.makeLabel("Username*  ");
        lPassword = create.makeLabel("Password*  ");

        comboBoxCountry = CountryDAO.createCountryComboBox();
        comboBoxCountry.setActionCommand("comboBoxCountry");
        comboBoxCountry.addActionListener(controller);

        comboBoxCity = CountryDAO.createCityComboBox(getComboBoxCountry());
        comboBoxCity.setActionCommand("comboBoxCity");
        comboBoxCity.addActionListener(controller);

        tFirstName = create.makeTextField();
        tLastName = create.makeTextField();
        tPhone = create.makeTextField();
        tEmail = create.makeTextField();
        tAddress1 = create.makeTextField();
        tAddress2 = create.makeTextField();
        tUsername = create.makeTextField();
        tPassword = create.makeTextField();


        bJoin = create.makeButon("createApplicant", " Join Us ");
        bJoin.addActionListener(controller);

        frame.setLayout(new FlowLayout());
        frame.add(lTitle);

        frame.add(lContactInfo);
        frame.add(lFirstName);
        frame.add(tFirstName);
        frame.add(lLastName);
        frame.add(tLastName);
        frame.add(lPhone);
        frame.add(tPhone);
        frame.add(lEmail);
        frame.add(tEmail);

        frame.add(lAddress);
        frame.add(lAddress1);
        frame.add(tAddress1);
        frame.add(lAddress2);
        frame.add(tAddress2);
        frame.add(lCountry);
        frame.add(comboBoxCountry);
        frame.add(lState);
        frame.add(lCity);
        frame.add(comboBoxCity);
        frame.add(lUsername);
        frame.add(tUsername);
        frame.add(lPassword);
        frame.add(tPassword);

        frame.add(bJoin);

        frame.setVisible(true);

    }
}
