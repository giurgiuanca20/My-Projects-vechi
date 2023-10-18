package org.example.Model;

import javax.swing.*;
import java.awt.*;

public class Create {
    public Create() {
    }

    Font bigFont = new Font("PT Sans", Font.PLAIN, 26);
    Color culButoane = new Color(150, 6, 60);
    Color culLabel = new Color(100, 5, 60);
    Color culTextField = new Color(205, 70, 60);

    public JButton makeButon(String newAction, String newText) {
        JButton aux = new JButton(newText);
        aux.setBackground(culButoane);
        aux.setForeground(Color.white);
        aux.setFont(bigFont);
        aux.setBorder(null);
        aux.setActionCommand(newAction);
        aux.setBorder(null);
        return aux;
    }

    public JLabel makeLabel(String newText) {
        JLabel newLabel = new JLabel(newText);
        newLabel.setFont(bigFont);
        newLabel.setForeground(culLabel);
        return newLabel;
    }

    public JTextField makeTextField() {
        JTextField newTextField = new JTextField(30);
        newTextField.setBackground(culTextField);
        newTextField.setForeground(Color.white);
        newTextField.setBorder(null);
        return newTextField;
    }
}


