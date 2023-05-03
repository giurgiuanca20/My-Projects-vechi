package org.example;

import org.example.GUI.View;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame home =new View("Queue");
        home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        home.pack();
    }
}