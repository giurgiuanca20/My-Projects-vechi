package org.example.GUI;

import org.example.Model.Create;

import javax.swing.*;
import java.awt.*;

public class ViewHome extends JFrame {

    Controller controller = new Controller(this);
    private final JFrame frame;
    private JLabel lDdroidd, lTitle;
    private JButton bJoinApplicant, bJoinEmployer, bLogIn;
    private Create create = new Create();


    public ViewHome(String name) {
        frame = new JFrame(name);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 900);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        this.prepareGui();
    }

    public void prepareGui() {

        lDdroidd = create.makeLabel("Ddroidd");
        lTitle = create.makeLabel("Autumn-Winter Bootcamp");

        bJoinApplicant = create.makeButon("joinApplicant", " Join Us As Applicant ");
        bJoinApplicant.addActionListener(controller);
        bJoinEmployer = create.makeButon("joinEmployer", " Join Us As Employer ");
        bJoinEmployer.addActionListener(controller);
        bLogIn = create.makeButon("LogIn", " Log In ");
        bLogIn.addActionListener(controller);


        frame.setLayout(new FlowLayout());
        frame.add(lTitle);
        frame.add(bJoinApplicant);
        frame.add(bJoinEmployer);
        frame.add(bLogIn);

    }
}

