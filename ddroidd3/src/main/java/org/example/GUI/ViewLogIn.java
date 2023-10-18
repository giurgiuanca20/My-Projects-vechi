package org.example.GUI;

import org.example.Model.Create;

import javax.swing.*;
import java.awt.*;

public class ViewLogIn extends JFrame {

    private final JFrame frame;
    private JLabel lUsername, lPassword;
    private JButton bFinish;

    private JTextField tUsername, tPassword;
    private Create create=new Create();

    public String gettUsername() {
        return tUsername.getText();
    }

    public String gettPassword() {
        return tPassword.getText();
    }


    public ViewLogIn(String name) {
        frame = new JFrame(name);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setSize(1600, 900);
        frame.setLocationRelativeTo(null);
    }
    public void prepareGui(Controller controller) {

        frame.getContentPane().removeAll();
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setSize(1600, 900);
        frame.setLocationRelativeTo(null);

        lUsername = create.makeLabel("Username*");
        lPassword = create.makeLabel("Password*");


        tUsername = create.makeTextField();
        tPassword = create.makeTextField();


        bFinish = create.makeButon("FinishLogIn", " Log in ");
        bFinish.addActionListener(controller);

        frame.setLayout(new FlowLayout());

        frame.add(lUsername);
        frame.add(tUsername);
        frame.add(lPassword);
        frame.add(tPassword);

        frame.add(bFinish);

        frame.setVisible(true);

    }
}
