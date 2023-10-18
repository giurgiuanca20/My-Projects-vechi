
package org.example.GUI;

import org.example.Model.Create;

import javax.swing.*;
import java.awt.*;

public class ViewJob extends JFrame {
    private final JFrame frame;
    private JLabel lTitle, lJobTitle, lJobDescription, lError;
    private JButton bAddJob;

    private JTextField tJobTitle, tJobDescription;
    private Create create=new Create();


    public String gettJobTitle() {
        return tJobTitle.getText();
    }

    public String gettJobDescription() {
        return tJobDescription.getText();
    }

    public boolean gettJobTitleEmpty() {
        return tJobTitle.getText().isEmpty();
    }

    public boolean gettJobDescriptionEmpty() {
        return tJobDescription.getText().isEmpty();
    }


    public ViewJob(String name) {
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

        lTitle = create.makeLabel("Application Form");
        lJobTitle = create.makeLabel("Title:   ");
        lJobDescription = create.makeLabel("Description:   ");

        tJobTitle = create.makeTextField();
        tJobDescription = create.makeTextField();

        bAddJob = create.makeButon( "addJob", " Add job ");
        bAddJob.addActionListener(controller);

        frame.setLayout(new FlowLayout());

        frame.add(lTitle);
        frame.add(lJobTitle);
        frame.add(tJobTitle);
        frame.add(lJobDescription);
        frame.add(tJobDescription);

        frame.add(bAddJob);

        frame.setVisible(true);
    }
}
