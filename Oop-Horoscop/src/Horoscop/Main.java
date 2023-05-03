package Horoscop;

import Horoscop.gui.*;

import javax.swing.*;
import java.sql.SQLException;
import java.text.ParseException;

public class Main
{
    public static void main( String[] args ) throws ParseException, SQLException {
        JFrame frame = new View("HOROSCOP_ANCA");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
    }
}
