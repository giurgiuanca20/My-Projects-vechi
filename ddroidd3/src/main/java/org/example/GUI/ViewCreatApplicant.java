package org.example.GUI;

import org.example.Model.Create;

import javax.swing.*;
import java.awt.*;

public class ViewCreatApplicant extends JFrame {
    private final JFrame frame;
    private JLabel lExcellent, lTitle, lSummary, lFirstName, lLastName, lPhone, lEmail, lAddress, lCountry, lState, lCity;
    private JButton bSearchJob;
    private Create create = new Create();

    public ViewCreatApplicant(String name) {
        frame = new JFrame(name);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 900);
        frame.setLocationRelativeTo(null);

    }


    public void prepareGui(Controller controller, String firstName, String lastName, String phone, String email, String address1, String address2, String country, String state, String city) {

        lExcellent = create.makeLabel("Excellent!");
        lTitle = create.makeLabel("See you in November 2023!");

        lSummary = create.makeLabel("Submission Summary:");
        lFirstName = create.makeLabel("First name:    " + firstName);
        lLastName = create.makeLabel("Last Name:    " + lastName);
        lPhone = create.makeLabel("Phone number:    " + phone);
        lEmail = create.makeLabel("Email:    " + email);
        // Display address as a combination of address1 and address2 (if available)
        if (address2.equals(""))
            lAddress = create.makeLabel("Address:    " + address1);
        else
            lAddress = create.makeLabel("Address:    " + address1 + ", " + address2);
        lCountry = create.makeLabel("Country:    " + country);
        lState = create.makeLabel("State:    " + state);
        lCity = create.makeLabel("City:    " + city);

        bSearchJob = create.makeButon("searchJob", "Search Job");
        bSearchJob.addActionListener(controller);


        frame.setLayout(new FlowLayout());
        frame.add(lTitle);
        frame.add(lExcellent);
        frame.add(lSummary);
        frame.add(lFirstName);
        frame.add(lLastName);
        frame.add(lPhone);
        frame.add(lEmail);
        frame.add(lAddress);
        frame.add(lCountry);
        frame.add(lState);
        frame.add(lCity);

        frame.add(bSearchJob);

        frame.setVisible(true);

    }
}

