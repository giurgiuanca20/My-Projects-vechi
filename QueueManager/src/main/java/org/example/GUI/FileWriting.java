package org.example.GUI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriting {
    public FileWriting(StringBuilder s) {
        try {
            File file = new File("output.txt");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(s.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}