package org.example;

import org.example.GUI.View;

import javax.swing.*;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws ParseException {

        JFrame home =new View("Calculator de Polinoame");
        home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        home.pack();
    }
}