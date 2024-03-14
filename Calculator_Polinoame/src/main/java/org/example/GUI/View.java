package org.example.GUI;

import org.example.Polynmial.Monom;
import org.example.Polynmial.Polinom;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private  JFrame frame;
    private final Color culButoane = new Color(155,6,60);
    private final Color culLabel= new Color(100,5,60);
    private final Color culTextField = new Color(205,70,60);
    private static final Font bigFont = new Font("PT Sans",Font.PLAIN,26);
    private JLabel lPolinom1, lPolinom2 ,lRezultat,lRezultat2;
    private JTextField tPolinom1,tPolinom2;
    private JButton bAdunare, bScadere,bInmultire, bImpartire,bDerivare,bIntegrare;
    Controller controller = new Controller(this);

    public Polinom gettPolinom1() {
        return parsePolinom(tPolinom1.getText());
    }

    public Polinom gettPolinom2() {
        return parsePolinom(tPolinom2.getText());
    }
    public void setlRezultat(String lRezultat) {
        this.lRezultat.setText(lRezultat);
    }
    public void setlRezultat2(String lRezultat) {
        this.lRezultat2.setText(lRezultat);
    }
    private static Monom parseMonom(String m) {
        double coef;
        int power;
        if (m.contains("x")) {
            if (m.charAt(0) == 'x')     //daca pimul caracter e "x" pune 1 la coeficient
                coef = 1;
             else if (m.charAt(0) == '-' && m.charAt(1) == 'x')     //daca primul caracter e "-" si al doile "x" coeficientul e -1
                coef = -1;
            else
                coef = Double.parseDouble(m.substring(0, m.indexOf('x')));        //altfel coeficientu e ce se afla pana la "x"
            if (m.contains("^"))
                power = Integer.parseInt(m.substring(m.indexOf('^') + 1));      //puterea e ce e dupa "^"
            else
                power = 1;
            return new Monom(coef, power);
        } else {
            coef = Integer.parseInt(m);     //daca nu exista "x" in string rezulta ca avem puterea x^0
            return new Monom(coef, 0);
        }
    }
    public static Polinom parsePolinom(String s) {
        Polinom p = new Polinom();
        if (s.charAt(0) != '+' && s.charAt(0) != '-')       //verifica primul termen daca are semn in fata, daca nu are pune "+"
            s = "+" + s;
        int i = 0;
        while (i < s.length()) {
            int j = i + 1;
            while (j < s.length() && s.charAt(j) != '+' && s.charAt(j) != '-')    //numara cate caractere sunt intre semne
                j++;
            Monom m = parseMonom(s.substring(i, j));     // creaza monomul gasit intre semne
            p.adaugaMonom(m);
            i = j;
        }
        return p;
    }
    public View(String name){
        frame = new JFrame(name);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600,900);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        this.prepareGui();
    }
    public JButton creazaButon(ImageIcon newImage, String newAction, String newText, Color newColor)
    {
        JButton aux=new JButton(newText);
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
    public JLabel creazaLabel(String newText, Font newFont)
    {
        JLabel newLabel=new JLabel(newText);
        newLabel.setFont(newFont);
        newLabel.setForeground(culLabel);
        return newLabel;
    }
    public JTextField creazaTextField(int coloane)
    {
        JTextField newTextField=new JTextField(coloane);
        newTextField.setBackground(culTextField);
        newTextField.setForeground(Color.white);
        newTextField.setBorder(null);
        return newTextField;
    }
    public void prepareGui() {

        lPolinom1 = creazaLabel("Polinom 1:", bigFont);
        lPolinom2 = creazaLabel("Polinom 2:", bigFont);
        lRezultat=creazaLabel("",bigFont);
        lRezultat2=creazaLabel("",bigFont);
        tPolinom1 = creazaTextField(30);
        tPolinom2 = creazaTextField(30);
        bAdunare=creazaButon(null,"adunare","  +  ",culButoane);
        bScadere=creazaButon(null,"scadere","  -  ",culButoane);
        bInmultire=creazaButon(null,"inmultire","  *  ",culButoane);
        bImpartire=creazaButon(null,"impartire","  :  ",culButoane);
        bDerivare=creazaButon(null,"derivare","  '  ",culButoane);
        bIntegrare=creazaButon(null,"integrare",null,culButoane);
        bIntegrare.setIcon(new ImageIcon("image.png"));

        frame.setLayout(new FlowLayout());
        frame.add(lPolinom1);
        frame.add(tPolinom1);
        frame.add(lPolinom2);
        frame.add(tPolinom2);
        frame.add(bAdunare);
        frame.add(bScadere);
        frame.add(bInmultire);
        frame.add(bImpartire);
        frame.add(bDerivare);
        frame.add(bIntegrare);
        frame.add(lRezultat);
        frame.add(lRezultat2);

    }
    public void showMessage()
    {
        JOptionPane.showMessageDialog(frame,"Nu se poate realiza impartirea");
    }
}
