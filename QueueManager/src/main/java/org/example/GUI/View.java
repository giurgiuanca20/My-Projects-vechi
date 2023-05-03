package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private JFrame frame;
    private final Color culFrame = new Color(155, 5, 60);
    private final Color culButoane = new Color(80, 5, 60);
    private final Color culTextField = new Color(205, 70, 60);
    private static final Font bigFont = new Font("PT Sans", Font.PLAIN, 26);
    private JLabel lNrClienti, lNrQueue, lTSimulation, lTArrivalMin, lTArrivalMax, lTServiceMin, lTServiceMax;
    private JTextField tNrClienti, tNrQueue, tTSimulation, tTArrivalMin, tTArrivalMax, tTServiceMin, tTServiceMax;
    private JButton bGata;
    Controller controller = new Controller(this);

    public View(String name) {
        frame = new JFrame(name);
        frame.getContentPane().setBackground(culFrame);
        frame.getContentPane().setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 900);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        this.prepareGui();
    }

    public int gettNrClienti() {
        return Integer.parseInt(tNrClienti.getText());
    }

    public int gettNrQueue() {
        return Integer.parseInt(tNrQueue.getText());
    }

    public int gettTSimulation() {
        return Integer.parseInt(tTSimulation.getText());
    }

    public int gettTArrivalMin() {
        return Integer.parseInt(tTArrivalMin.getText());
    }

    public int gettTArrivalMax() {
        return Integer.parseInt(tTArrivalMax.getText());
    }

    public int gettTServiceMin() {
        return Integer.parseInt(tTServiceMin.getText());
    }

    public int gettTServiceMax() {
        return Integer.parseInt(tTServiceMax.getText());
    }

    public JButton creazaButon(ImageIcon newImage, String newAction, String newText, Color newColor) {
        JButton aux = new JButton(newText);
        aux.setIcon(newImage);
        aux.setBackground(newColor);
        aux.setForeground(Color.white);
        aux.setFont(bigFont);
        aux.setBorder(null);
        aux.setActionCommand(newAction);
        aux.addActionListener(this.controller);
        aux.setBorder(null);
        return aux;
    }

    public JLabel creazaLabel(String newText, Font newFont) {
        JLabel newLabel = new JLabel(newText);
        newLabel.setFont(newFont);
        newLabel.setForeground(Color.white);
        return newLabel;
    }

    public JTextField creazaTextField(int coloane) {
        JTextField newTextField = new JTextField(coloane);
        newTextField.setBackground(culTextField);
        newTextField.setForeground(Color.white);
        newTextField.setBorder(null);
        return newTextField;
    }

    public void prepareGui() {

        lNrClienti = creazaLabel("Clienti:", bigFont);
        tNrClienti = creazaTextField(10);
        lNrQueue = creazaLabel("Cozi:", bigFont);
        tNrQueue = creazaTextField(10);
        lTSimulation = creazaLabel("Time:", bigFont);
        tTSimulation = creazaTextField(10);
        lTArrivalMin = creazaLabel("Arrival min:", bigFont);
        tTArrivalMin = creazaTextField(10);
        lTArrivalMax = creazaLabel("Arrival max:", bigFont);
        tTArrivalMax = creazaTextField(10);
        lTServiceMin = creazaLabel("Service min:", bigFont);
        tTServiceMin = creazaTextField(10);
        lTServiceMax = creazaLabel("Service max:", bigFont);
        tTServiceMax = creazaTextField(10);
        bGata = creazaButon(null, "gata", "  Gata  ", culButoane);


        frame.add(lNrClienti);
        frame.add(tNrClienti);
        frame.add(lNrQueue);
        frame.add(tNrQueue);
        frame.add(lTArrivalMin);
        frame.add(tTArrivalMin);
        frame.add(lTArrivalMax);
        frame.add(tTArrivalMax);
        frame.add(lTServiceMin);
        frame.add(tTServiceMin);
        frame.add(lTServiceMax);
        frame.add(tTServiceMax);
        frame.add(lTSimulation);
        frame.add(tTSimulation);
        frame.add(bGata);

    }
}
