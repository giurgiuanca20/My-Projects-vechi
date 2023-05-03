package org.example.GUI;

import org.example.BusinessLogic.SimulationManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private View view;

    public Controller(View v) {
        this.view = v;
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        switch (s) {
            case "gata":
                SimulationManager gen = new SimulationManager(view.gettTSimulation(), view.gettTServiceMax(), view.gettTServiceMin(), view.gettTArrivalMax(), view.gettTArrivalMin(), view.gettNrQueue(), view.gettNrClienti());
               // SimulationManager gen = new SimulationManager(200, 9, 3,100, 10, 20, 1000);

                Thread t = new Thread(gen);
                t.start();
                break;
            default:
        }
    }
}
