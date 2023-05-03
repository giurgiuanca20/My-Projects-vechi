package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class SimulationFrame {


    private JFrame frame;
    private int noQueue;
    private final Color culFrame = new Color(80, 5, 60);
    private static final Font bigFont = new Font("PT Sans", Font.PLAIN, 26);
    private static final Font hugeFont = new Font("PT Sans", Font.PLAIN, 36);
    private static final Font smallFont = new Font("PT Sans", Font.PLAIN, 18);
    private JLabel[] label;
    private JLabel labelTime, labelWaiting, labelAverageWaiting, labelAverageService, labelPeakHour;

    public SimulationFrame(String name, int noQueue) {
        this.noQueue = noQueue;
        frame = new JFrame(name);
        frame.getContentPane().setBackground(culFrame);
        frame.getContentPane().setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 900);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        creaza();
    }

    public JLabel creazaLabel(Font newFont, String s) {
        JLabel newLabel = new JLabel(s);
        newLabel.setFont(newFont);
        newLabel.setForeground(Color.white);
        return newLabel;
    }

    public void creaza() {
        label = new JLabel[noQueue];
        labelTime = creazaLabel(hugeFont, " ");
        labelWaiting = creazaLabel(bigFont, " ");
        frame.add(labelTime);
        frame.add(labelWaiting);
        for (int i = 0; i < noQueue; i++) {
            label[i] = creazaLabel(bigFont, " ");
            frame.add(label[i]);
        }
        labelAverageWaiting = creazaLabel(smallFont, " ");
        frame.add(labelAverageWaiting);
        labelAverageService = creazaLabel(smallFont, " ");
        frame.add(labelAverageService);
        labelPeakHour = creazaLabel(smallFont, " ");
        frame.add(labelPeakHour);
    }

    public void setText(String s, int i) {
        label[i - 1].setText(s);
    }

    public void setTime(String s) {
        labelTime.setText(s);
    }

    public void setWaiting(String s) {
        labelWaiting.setText(s);
    }

    public void setAverageWaiting(String s) {
        labelAverageWaiting.setText(s);
    }

    public void setAverageService(String s) {
        labelAverageService.setText(s);
    }

    public void setPeakHour(String s) {
        labelPeakHour.setText(s);
    }
}
