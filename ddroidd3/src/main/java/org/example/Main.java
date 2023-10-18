package org.example;

import org.example.GUI.ViewHome;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new ViewHome("Bootcamp application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
    }
}