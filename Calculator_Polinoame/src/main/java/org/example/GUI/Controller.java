package org.example.GUI;

import org.example.Operation.Operations;
import org.example.Result;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private View view;
    private Operations op=new Operations();
    public Controller(View v){
        this.view = v;
    }
    public void actionPerformed(ActionEvent e) {
       String s=e.getActionCommand();
       switch (s)
       {
           case "adunare":
               view.setlRezultat(op.aduna(view.gettPolinom1(),view.gettPolinom2()).toString());
               break;
           case "scadere":
               view.setlRezultat(op.scadere(view.gettPolinom1(),view.gettPolinom2()).toString());
               break;
           case "inmultire":
               view.setlRezultat(op.inmultire(view.gettPolinom1(),view.gettPolinom2()).toString());
               break;
           case "impartire":
               try{
                   Result rez=op.impartire(view.gettPolinom1(),view.gettPolinom2());
                   view.setlRezultat("Catul:  "+rez.cat.toString());
                   view.setlRezultat2("Restul:  "+rez.rest.toString());
               } catch (Exception exception)
               {
                   view.showMessage();
               }
               break;
           case "derivare":
               view.setlRezultat("Polinom 1:  "+op.derivare(view.gettPolinom1()).toString());
               view.setlRezultat2("Polinom 2:  "+op.derivare(view.gettPolinom2()).toString());
               break;
           case "integrare":
               view.setlRezultat("Polinom 1:"+op.integrare(view.gettPolinom1()).toString()+"+c");
               view.setlRezultat2("Polinom 2:"+op.integrare(view.gettPolinom2()).toString()+"+c");
               break;
           case "help":
               break;
           default:
       }



    }
}
