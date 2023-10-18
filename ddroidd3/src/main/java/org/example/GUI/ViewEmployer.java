package org.example.GUI;

import org.example.Model.Create;

import javax.swing.*;
import java.awt.*;

public class ViewEmployer extends JFrame {
    private final JFrame frame;
    private JLabel lTitle, lFirstName, lLastName, lEmail, lCompanyName, lError, lUsername, lPassword;
    private JButton bJoin;
    private JTextField tFirstName, tLastName, tEmail, tCompanyName, tUsername, tPassword;
    private Create create = new Create();

    public String gettFirstName() {
        return tFirstName.getText();
    }

    public String gettLastName() {
        return tLastName.getText();
    }

    public String gettEmail() {
        return tEmail.getText();
    }

    public String gettCompanyName() {
        return tCompanyName.getText();
    }

    public String gettUsername() {
        return tUsername.getText();
    }

    public String gettPassword() {
        return tPassword.getText();
    }


    public boolean gettFirstNameEmpty() {
        return tFirstName.getText().isEmpty();
    }

    public boolean gettLastNameEmpty() {
        return tLastName.getText().isEmpty();
    }

    public boolean gettEmailEmpty() {
        return tEmail.getText().isEmpty();
    }

    public boolean gettCompanyNameEmpty() {
        return tCompanyName.getText().isEmpty();
    }

    public boolean gettUsernameEmpty() {
        return tUsername.getText().isEmpty();
    }

    public boolean gettPasswordEmpty() {
        return tPassword.getText().isEmpty();
    }


    public ViewEmployer(String name) {
        frame = new JFrame(name);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setSize(1600, 900);
        frame.setLocationRelativeTo(null);
    }


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

        lTitle = create.makeLabel("Employer Form   ");
        lFirstName = create.makeLabel("First name*  ");
        lLastName = create.makeLabel("Last Name*  ");
        lEmail = create.makeLabel("Email*  ");
        lCompanyName = create.makeLabel("Company Name*  ");
        lUsername = create.makeLabel("Username*  ");
        lPassword = create.makeLabel("Password*  ");


        tFirstName = create.makeTextField();
        tLastName = create.makeTextField();
        tEmail = create.makeTextField();
        tCompanyName = create.makeTextField();
        tUsername = create.makeTextField();
        tPassword = create.makeTextField();


        bJoin = create.makeButon("createEmployer", " Join Us ");
        bJoin.addActionListener(controller);

        frame.setLayout(new FlowLayout());
        frame.add(lTitle);

        frame.add(lTitle);
        frame.add(lFirstName);
        frame.add(tFirstName);
        frame.add(lLastName);
        frame.add(tLastName);
        frame.add(lEmail);
        frame.add(tEmail);
        frame.add(lCompanyName);
        frame.add(tCompanyName);
        frame.add(lUsername);
        frame.add(tUsername);
        frame.add(lPassword);
        frame.add(tPassword);


        frame.add(bJoin);

        frame.setVisible(true);

    }
}
