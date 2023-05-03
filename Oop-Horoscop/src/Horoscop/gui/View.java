package Horoscop.gui;

import database.Connect;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

import static javax.swing.JOptionPane.showMessageDialog;

public class View extends JFrame {


    //////////////////////////////////HOROSCOP/////////////////////////////////////////////////////////////////////

    private final Color navy = new Color(0,0,90);
    private final Color purple = new Color(100,19,80);
    private static final Font bigFont = new Font("PT Sans",Font.PLAIN,26);
    private static final Font zodiacFont = new Font("PT Sans",Font.PLAIN,30);
    Controller controller = new Controller(this);
    public final JFrame home;              ///ce are actiune se declara aici ///obiect
    private JButton bHome,bBack, bLant, bZodiac, bTablou, bLogIn, bZodie, bSignIn, bDatePersonale, bContNou, bSalveazaData,bStergeData;
    JPanel p1,p2,p3, p4, p5,p6,p7,p8;
    public JTabbedPane tabbedPane; ///daca e privat nu ma pot juca cu el in controller
    JTextField parola,user,day,month,year,adresaEmail,nume,prenume,user_nou,parola_nou;
    private JLabel lZodie, lElement, lOra, lNumar, lPersonalitate, lDupaLuna, lDupaAn, lChinezescDragon, lChinezescLucky, lChinezescMagic,pozaZodie,pozaElement,pozaChinezesc, lNume, lPrenume, lAdresa, lUserNou, lParolaNou, lDataGresita;


    //////////////////////////////////HOROSCOP/////////////////////////////////////////////////////////////////////




    //////////////////////////////////MAGAZIN/////////////////////////////////////////////////////////////////////



    private JLabel l_dimensiuneLant,l_culoareLant,l_mesajLant,l_bucatiLant,l_numeTablou,l_zodiaTablou,l_culoareTablou;
    private JRadioButton mic,mediu,mare,alb,albastru,mov,galben,rosu,verde,fecioara,taur,scorpion,sagetator,pesti,balanta,leu,gemeni,capricorn,rac,berbec,varsator,albt,albastrut,movt,galbent,rosut,verdet;
    JTextField mesaj,nrBucati, numeTablou;
    private JButton b_adaugaLant,b_adaugaTablou,bCos;
    private ButtonGroup dim,culoare,culoareTablou,zodieTablou;

    //////////////////////////////////MAGAZIN/////////////////////////////////////////////////////////////////////





    /////////////////////////COS///////////////////////////
    public Connect q=new Connect("cont");
    private JLabel numeUtilizator,lPret,lPretulFinal;
    public JTable tabel;

    /////////////////////////COS///////////////////////////
    public View(String name) throws SQLException {
        home = new JFrame(name);
        home.getContentPane().setBackground( navy );
        home.getContentPane().setLayout(new BorderLayout());
        home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        home.setSize(1600,850);
        home.setLocationRelativeTo(null);
        home.setVisible(true);
        this.prepareGui();
    }

    public void setNumeUtilizator(String numeUtilizator) {
        this.numeUtilizator.setText(numeUtilizator);
    }



    public String getDimensiune() {
        if(mic.isSelected()==true)
            return "mic";
        else if (mediu.isSelected()==true) {
            return "mediu";
        }
        else
            return"mare";
    }
    public String getCuloareLant() {
        if(alb.isSelected()==true)
            return "alb";
            if (albastru.isSelected()==true)
                return "albastru";
                if (rosu.isSelected()==true)
                return "rosu";
                if (mov.isSelected()==true)
                    return "violet";
            if (verde.isSelected()==true)
                        return "verde";

            return "galben";

    }
    public String getCuloareTablou() {
        if(albt.isSelected()==true)
            return "alb";
        if (albastrut.isSelected()==true)
            return "albastru";
        if (rosut.isSelected()==true)
            return "rosu";
        if (movt.isSelected()==true)
            return "violet";
        if (verdet.isSelected()==true)
            return "verde";
        return "galben";
    }
    public String getZodie() {
        if(capricorn.isSelected()==true)
            return "capricorn";
        if (pesti.isSelected()==true)
            return "pesti";
        if (leu.isSelected()==true)
            return "leu";
        if (rac.isSelected()==true)
            return "rac";
        if (gemeni.isSelected()==true)
            return "gemeni";
        if (fecioara.isSelected()==true)
            return "fecioara";
        if (varsator.isSelected()==true)
            return "varsator";
        if (sagetator.isSelected()==true)
            return "sagetator";
        if (taur.isSelected()==true)
            return "taur";
        if (scorpion.isSelected()==true)
            return "scorpion";
        if (berbec.isSelected()==true)
            return "berbec";
        return "balanta";

    }

    public String getNumeTablou() {
        return numeTablou.getText();
    }

    public String getMesaj() {
        return mesaj.getText();
    }
    public int getNrBucati() {
        return Integer.parseInt(nrBucati.getText());
    }
    private String[][] rec;
    private int pretTotal;
    public void scrieInTabel(){
        int k=controller.q.nrLant*10+controller.q.nrTablou;
        switch (k){
            case 0:///toate
                rec = new String[][]{
                        {controller.q.l1.getTip(), controller.q.l1.getSizeNume(), controller.q.l1.getCuloare(), controller.q.l1.getMessZodie(), controller.q.l1.getNrBuc(), controller.q.l1.getPret()},
                        {controller.q.l2.getTip(), controller.q.l2.getSizeNume(), controller.q.l2.getCuloare(), controller.q.l2.getMessZodie(), controller.q.l2.getNrBuc(),controller.q.l2.getPret()},
                        {controller.q.l3.getTip(), controller.q.l3.getSizeNume(), controller.q.l3.getCuloare(), controller.q.l3.getMessZodie(), controller.q.l3.getNrBuc(), controller.q.l3.getPret()},
                        {controller.q.t1.getTip(), controller.q.t1.getSizeNume(), controller.q.t1.getCuloare(), controller.q.t1.getMessZodie(), controller.q.t1.getNrBuc(), controller.q.t1.getPret()},
                        {controller.q.t2.getTip(), controller.q.t2.getSizeNume(), controller.q.t2.getCuloare(), controller.q.t2.getMessZodie(), controller.q.t2.getNrBuc(), controller.q.t2.getPret()},
                        {controller.q.t3.getTip(), controller.q.t3.getSizeNume(), controller.q.t3.getCuloare(), controller.q.t3.getMessZodie(), controller.q.t3.getNrBuc(), controller.q.t3.getPret()},
                };
                pretTotal=Integer.parseInt(controller.q.l1.getPret())+Integer.parseInt(controller.q.l2.getPret())+Integer.parseInt(controller.q.l3.getPret())+900;
                break;
            case 2:///are 1
                rec = new String[][]{
                        {controller.q.l1.getTip(), controller.q.l1.getSizeNume(), controller.q.l1.getCuloare(), controller.q.l1.getMessZodie(), controller.q.l1.getNrBuc(), controller.q.l1.getPret()},
                        {controller.q.l2.getTip(), controller.q.l2.getSizeNume(), controller.q.l2.getCuloare(), controller.q.l2.getMessZodie(), controller.q.l2.getNrBuc(), controller.q.l2.getPret()},
                        {controller.q.l3.getTip(), controller.q.l3.getSizeNume(), controller.q.l3.getCuloare(), controller.q.l3.getMessZodie(), controller.q.l3.getNrBuc(), controller.q.l3.getPret()},
                        {controller.q.t1.getTip(), controller.q.t1.getSizeNume(), controller.q.t1.getCuloare(), controller.q.t1.getMessZodie(), controller.q.t1.getNrBuc(), controller.q.t1.getPret()},
                         };
                pretTotal=Integer.parseInt(controller.q.l1.getPret())+Integer.parseInt(controller.q.l2.getPret())+Integer.parseInt(controller.q.l3.getPret())+300;
                break;
            case 3:///are2
                rec = new String[][]{
                        {controller.q.l1.getTip(), controller.q.l1.getSizeNume(), controller.q.l1.getCuloare(), controller.q.l1.getMessZodie(), controller.q.l1.getNrBuc(), controller.q.l1.getPret()},
                        {controller.q.l2.getTip(), controller.q.l2.getSizeNume(), controller.q.l2.getCuloare(), controller.q.l2.getMessZodie(), controller.q.l2.getNrBuc(), controller.q.l2.getPret()},
                        {controller.q.l3.getTip(), controller.q.l3.getSizeNume(), controller.q.l3.getCuloare(), controller.q.l3.getMessZodie(), controller.q.l3.getNrBuc(), controller.q.l3.getPret()},
                        {controller.q.t1.getTip(), controller.q.t1.getSizeNume(), controller.q.t1.getCuloare(), controller.q.t1.getMessZodie(), controller.q.t1.getNrBuc(), controller.q.t1.getPret()},
                        {controller.q.t2.getTip(), controller.q.t2.getSizeNume(), controller.q.t2.getCuloare(), controller.q.t2.getMessZodie(), controller.q.t2.getNrBuc(), controller.q.t2.getPret()},
                          };
                pretTotal=Integer.parseInt(controller.q.l1.getPret())+Integer.parseInt(controller.q.l2.getPret())+Integer.parseInt(controller.q.l3.getPret())+600;
                break;
            case 1:///nimic
                rec = new String[][]{
                        {controller.q.l1.getTip(), controller.q.l1.getSizeNume(), controller.q.l1.getCuloare(), controller.q.l1.getMessZodie(), controller.q.l1.getNrBuc(), controller.q.l1.getPret()},
                        {controller.q.l2.getTip(), controller.q.l2.getSizeNume(), controller.q.l2.getCuloare(), controller.q.l2.getMessZodie(), controller.q.l2.getNrBuc(), controller.q.l2.getPret()},
                        {controller.q.l3.getTip(), controller.q.l3.getSizeNume(), controller.q.l3.getCuloare(), controller.q.l3.getMessZodie(), controller.q.l3.getNrBuc(), controller.q.l3.getPret()},
                       };
                pretTotal=Integer.parseInt(controller.q.l1.getPret())+Integer.parseInt(controller.q.l2.getPret())+Integer.parseInt(controller.q.l3.getPret());
                break;
            case 10:
                rec = new String[][]{
                        {controller.q.t1.getTip(), controller.q.t1.getSizeNume(), controller.q.t1.getCuloare(), controller.q.t1.getMessZodie(), controller.q.t1.getNrBuc(), controller.q.t1.getPret()},
                        {controller.q.t2.getTip(), controller.q.t2.getSizeNume(), controller.q.t2.getCuloare(), controller.q.t2.getMessZodie(), controller.q.t2.getNrBuc(), controller.q.t2.getPret()},
                        {controller.q.t3.getTip(), controller.q.t3.getSizeNume(), controller.q.t3.getCuloare(), controller.q.t3.getMessZodie(), controller.q.t3.getNrBuc(), controller.q.t3.getPret()},
                };
                pretTotal=900;
                break;
            case 20:
                rec = new String[][]{
                        {controller.q.l1.getTip(), controller.q.l1.getSizeNume(), controller.q.l1.getCuloare(), controller.q.l1.getMessZodie(), controller.q.l1.getNrBuc(), controller.q.l1.getPret()},
                        {controller.q.t1.getTip(), controller.q.t1.getSizeNume(), controller.q.t1.getCuloare(), controller.q.t1.getMessZodie(), controller.q.t1.getNrBuc(), controller.q.t1.getPret()},
                        {controller.q.t2.getTip(), controller.q.t2.getSizeNume(), controller.q.t2.getCuloare(), controller.q.t2.getMessZodie(), controller.q.t2.getNrBuc(), controller.q.t2.getPret()},
                        {controller.q.t3.getTip(), controller.q.t3.getSizeNume(), controller.q.t3.getCuloare(), controller.q.t3.getMessZodie(), controller.q.t3.getNrBuc(), controller.q.t3.getPret()},
                };
                pretTotal=Integer.parseInt(controller.q.l1.getPret())+900;
                break;
            case 30:
                rec = new String[][]{
                        {controller.q.l1.getTip(), controller.q.l1.getSizeNume(), controller.q.l1.getCuloare(), controller.q.l1.getMessZodie(), controller.q.l1.getNrBuc(), controller.q.l1.getPret()},
                        {controller.q.l2.getTip(), controller.q.l2.getSizeNume(), controller.q.l2.getCuloare(), controller.q.l2.getMessZodie(), controller.q.l2.getNrBuc(), controller.q.l2.getPret()},
                        {controller.q.t1.getTip(), controller.q.t1.getSizeNume(), controller.q.t1.getCuloare(), controller.q.t1.getMessZodie(), controller.q.t1.getNrBuc(), controller.q.t1.getPret()},
                        {controller.q.t2.getTip(), controller.q.t2.getSizeNume(), controller.q.t2.getCuloare(), controller.q.t2.getMessZodie(), controller.q.t2.getNrBuc(), controller.q.t2.getPret()},
                        {controller.q.t3.getTip(), controller.q.t3.getSizeNume(), controller.q.t3.getCuloare(), controller.q.t3.getMessZodie(), controller.q.t3.getNrBuc(), controller.q.t3.getPret()},
                };
                pretTotal=Integer.parseInt(controller.q.l1.getPret())+Integer.parseInt(controller.q.l2.getPret())+900;
                break;
            case 11:
                showMessageDialog(null,"Cos gol!");
                break;
            case 12:
                rec = new String[][]{
                        {controller.q.t1.getTip(), controller.q.t1.getSizeNume(), controller.q.t1.getCuloare(), controller.q.t1.getMessZodie(), controller.q.t1.getNrBuc(), controller.q.t1.getPret()},
                        };
                pretTotal=300;
                break;
            case 13:
                rec = new String[][]{
                       {controller.q.t1.getTip(), controller.q.t1.getSizeNume(), controller.q.t1.getCuloare(), controller.q.t1.getMessZodie(), controller.q.t1.getNrBuc(), controller.q.t1.getPret()},
                        {controller.q.t2.getTip(), controller.q.t2.getSizeNume(), controller.q.t2.getCuloare(), controller.q.t2.getMessZodie(), controller.q.t2.getNrBuc(), controller.q.t2.getPret()},
                };
                pretTotal=600;
                break;
            case 21:
                rec = new String[][]{
                        {controller.q.l1.getTip(), controller.q.l1.getSizeNume(), controller.q.l1.getCuloare(), controller.q.l1.getMessZodie(), controller.q.l1.getNrBuc(), controller.q.l1.getPret()},
                       };
                pretTotal=Integer.parseInt(controller.q.l1.getPret());
                break;
            case 22:
                rec = new String[][]{
                        {controller.q.l1.getTip(), controller.q.l1.getSizeNume(), controller.q.l1.getCuloare(), controller.q.l1.getMessZodie(), controller.q.l1.getNrBuc(),controller.q.l1.getPret()},
                       {controller.q.t1.getTip(), controller.q.t1.getSizeNume(), controller.q.t1.getCuloare(), controller.q.t1.getMessZodie(), controller.q.t1.getNrBuc(), controller.q.t1.getPret()},
                        };
                pretTotal=Integer.parseInt(controller.q.l1.getPret())+300;
                break;
            case 23:
                rec = new String[][]{
                        {controller.q.l1.getTip(), controller.q.l1.getSizeNume(), controller.q.l1.getCuloare(), controller.q.l1.getMessZodie(), controller.q.l1.getNrBuc(), controller.q.l1.getPret()},
                        {controller.q.t1.getTip(), controller.q.t1.getSizeNume(), controller.q.t1.getCuloare(), controller.q.t1.getMessZodie(), controller.q.t1.getNrBuc(), controller.q.t1.getPret()},
                        {controller.q.t2.getTip(), controller.q.t2.getSizeNume(), controller.q.t2.getCuloare(), controller.q.t2.getMessZodie(), controller.q.t2.getNrBuc(), controller.q.t2.getPret()},
                        };
                pretTotal=Integer.parseInt(controller.q.l1.getPret())+600;
                break;
            case 31:
                rec = new String[][]{
                        {controller.q.l1.getTip(), controller.q.l1.getSizeNume(), controller.q.l1.getCuloare(), controller.q.l1.getMessZodie(), controller.q.l1.getNrBuc(),controller.q.l1.getPret()},
                        {controller.q.l2.getTip(), controller.q.l2.getSizeNume(), controller.q.l2.getCuloare(), controller.q.l2.getMessZodie(), controller.q.l2.getNrBuc(), controller.q.l2.getPret()},
                       };
                pretTotal=Integer.parseInt(controller.q.l1.getPret())+Integer.parseInt(controller.q.l2.getPret());
                break;
            case 32:
                rec = new String[][]{
                        {controller.q.l1.getTip(), controller.q.l1.getSizeNume(), controller.q.l1.getCuloare(), controller.q.l1.getMessZodie(), controller.q.l1.getNrBuc(), controller.q.l1.getPret()},
                        {controller.q.l2.getTip(), controller.q.l2.getSizeNume(), controller.q.l2.getCuloare(), controller.q.l2.getMessZodie(), controller.q.l2.getNrBuc(), controller.q.l2.getPret()},
                        {controller.q.t1.getTip(), controller.q.t1.getSizeNume(), controller.q.t1.getCuloare(), controller.q.t1.getMessZodie(), controller.q.t1.getNrBuc(), controller.q.t1.getPret()},
                        };
                pretTotal=Integer.parseInt(controller.q.l1.getPret())+Integer.parseInt(controller.q.l2.getPret())+Integer.parseInt(controller.q.l3.getPret())+300;
                break;
            case 33:
                rec = new String[][]{
                        {controller.q.l1.getTip(), controller.q.l1.getSizeNume(), controller.q.l1.getCuloare(), controller.q.l1.getMessZodie(), controller.q.l1.getNrBuc(), controller.q.l1.getPret()},
                        {controller.q.l2.getTip(), controller.q.l2.getSizeNume(), controller.q.l2.getCuloare(), controller.q.l2.getMessZodie(), controller.q.l2.getNrBuc(), controller.q.l2.getPret()},
                        {controller.q.t1.getTip(), controller.q.t1.getSizeNume(), controller.q.t1.getCuloare(), controller.q.t1.getMessZodie(), controller.q.t1.getNrBuc(), controller.q.t1.getPret()},
                        {controller.q.t2.getTip(), controller.q.t2.getSizeNume(), controller.q.t2.getCuloare(), controller.q.t2.getMessZodie(), controller.q.t2.getNrBuc(), controller.q.t2.getPret()},
                        };
                pretTotal=Integer.parseInt(controller.q.l1.getPret())+Integer.parseInt(controller.q.l2.getPret())+Integer.parseInt(controller.q.l3.getPret())+300;
                break;
        }

        String[] header = { "Rank", "Player", "Country","sdsf","gfd","hgfd"};
        tabel=new JTable(rec,header);
        p8.add(tabel);

    }

    public void setlPretulFinal() {
        this.lPretulFinal.setText(String.valueOf(pretTotal));
    }
    //////////////////////////////////HOROSCOP/////////////////////////////////////////////////////////////////////


    public void setParola(String parola) {
        this.parola.setText(parola);
    }

    public void setUser(String user) {
        this.user.setText(user);
    }
    public String getParola() { return parola.getText();}

    public String getUser() { return user.getText();}

    public String getAdresaEmail() {
        return adresaEmail.getText();
    }

    public String getNume() {
        return nume.getText();
    }

    public String getPrenume() {
        return prenume.getText();
    }

    public String getUser_nou() {
        return user_nou.getText();
    }

    public String getParola_nou() {
        return parola_nou.getText();
    }

    public void setDay(String day) {
        this.day.setText(day);
    }

    public void setMonth(String month) {
        this.month.setText(month);
    }

    public void setYear(String year) {
        this.year.setText(year);
    }
    public int getDay() {
        return Integer.parseInt(day.getText());
    }
    public int getMonth() {
        return Integer.parseInt(month.getText());
    }
    public int getYear() {
        return Integer.parseInt(year.getText());
    }
    public void setlZodie(String lZodie) {
        this.lZodie.setText(lZodie);
    }
    public void setlElement(String lElement) {
        this.lElement.setText(lElement);
    }
    public void setlOra(String lOra) {
        this.lOra.setText(lOra);
    }
    public void setlNumar(String lNumar) {
        this.lNumar.setText(lNumar);
    }
    public void setl_personalitate(String l_personalitate) {
        this.lPersonalitate.setText(l_personalitate);
    }
    public void setl_dupa_luna(String l_dupa_luna) {
        this.lDupaLuna.setText(l_dupa_luna);
    }

    public void setl_dupa_an(String l_dupa_an) {
        this.lDupaAn.setText(l_dupa_an);
    }
    public void setl_chinezesc_dragon(String l_chinezesc_dragon) {
        this.lChinezescDragon.setText(l_chinezesc_dragon);
    }
    public void setl_chinezesc_lucky(String l_chinezesc_lucky) {
        this.lChinezescLucky.setText(l_chinezesc_lucky);
    }
    public void setl_chinezesc_magic(String l_chinezesc_magic) {
        this.lChinezescMagic.setText(l_chinezesc_magic);
    }
    public void setPoze(ImageIcon pozaZodie,ImageIcon pozaElement)
    {
        this.pozaZodie.setIcon(pozaZodie);
        this.pozaElement.setIcon(pozaElement);

    }
    public void setCont()
    {
        lUserNou.setVisible(true);
        user_nou.setVisible(true);
        lParolaNou.setVisible(true);
        parola_nou.setVisible(true);
        bContNou.setVisible(true);
        lNume.setVisible(false);
        nume.setVisible(false);
        lPrenume.setVisible(false);
        prenume.setVisible(false);
        lAdresa.setVisible(false);
        adresaEmail.setVisible(false);
        bDatePersonale.setVisible(false);
    }
    //////////////////////////////////HOROSCOP/////////////////////////////////////////////////////////////////////



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
        return aux;
    }
    public JLabel creazaLabel(String newText, Font newFont, Color newColor)
    {
        JLabel newLabel=new JLabel(newText);
        newLabel.setFont(newFont);
        newLabel.setForeground(newColor);
        return newLabel;
    }
    public JRadioButton creazaRadioButon(String newAction, String newText, Color newColor)
    {
        JRadioButton aux=new JRadioButton(newText);
        aux.setBackground(newColor);
        aux.setForeground(Color.white);
        aux.setFont(bigFont);
        aux.setBorder(null);
        aux.setActionCommand(newAction);
        aux.addActionListener(this.controller);
        return aux;
    }



    public void prepareGui() {

        tabbedPane = new JTabbedPane();
        tabbedPane.setVisible(true);
        home.add(tabbedPane);
        ImageIcon image;
//PAGINA PRINCIPALA
        image=new ImageIcon("necklace1.png");
        bLant = creazaButon(image,"Magazin_lant",null,navy);
        image=new ImageIcon("signs_color.png");
        bZodiac= creazaButon(image,"Zodiac",null,navy);
        image=new ImageIcon("painting1.png");
        bTablou = creazaButon(image,"Magazin_tablou",null,navy);

        bHome=new JButton();
        bHome.setPreferredSize(new Dimension(50,50));
        bHome.setIcon(new ImageIcon("home_white.png"));
        bHome.setBackground(navy);
        bHome.setBorder(null);
        bHome.setActionCommand("Home");
        bHome.addActionListener(this.controller);

        bBack=new JButton();
        bBack.setPreferredSize(new Dimension(50,50));
        bBack.setIcon(new ImageIcon("back_white.png"));
        bBack.setBackground(navy);
        bBack.setBorder(null);
        bBack.setActionCommand("Back");
        bBack.addActionListener(this.controller);

        bCos=new JButton();
        bCos.setPreferredSize(new Dimension(50,50));
        bCos.setIcon(new ImageIcon("cart3.png"));
        bCos.setBackground(navy);
        bCos.setBorder(null);
        bCos.setActionCommand("Cos");
        bCos.addActionListener(this.controller);

        p1 = new JPanel();
        p1.add(bTablou);
        p1.add(bZodiac);
        p1.add(bLant);
        p1.add(bHome);
        p1.add(bBack);
        p1.add(bCos);
        p1.setBackground(navy);



        //////////////////////////////////HOROSCOP/////////////////////////////////////////////////////////////////////
//CONECTAREA LA CONT



        bHome=new JButton();
        bHome.setPreferredSize(new Dimension(50,50));
        bHome.setIcon(new ImageIcon("home_white.png"));
        bHome.setBackground(navy);
        bHome.setBorder(null);
        bHome.setActionCommand("Home");
        bHome.addActionListener(this.controller);

        bBack=new JButton();
        bBack.setPreferredSize(new Dimension(50,50));
        bBack.setIcon(new ImageIcon("back_white.png"));
        bBack.setBackground(navy);
        bBack.setBorder(null);
        bBack.setActionCommand("Back");
        bBack.addActionListener(this.controller);

        JLabel l_titlu=creazaLabel("Bine ati revenit!",zodiacFont,Color.white);

        JLabel l_creati_cont=creazaLabel("Creati aici un CONT NOU:",bigFont,Color.white);

        lUserNou =creazaLabel("User:",bigFont,Color.white);
        user = new JTextField(15);
        user.setBounds(450, 500, 240, 150);

        lParolaNou =creazaLabel("Parola:",bigFont,Color.white);
        parola = new JTextField(15);
        parola.setBounds(450, 600, 240, 150);


        bLogIn = creazaButon(null,"ButonLogare"," Log in ",purple);
        bSignIn = creazaButon(null,"Inregistreaza_cont"," Sign in ",purple);

        p2 = new JPanel();
        p2.add(l_titlu);
        p2.add(lUserNou);
        p2.add(user);
        p2.add(lParolaNou);
        p2.add(parola);
        p2.add(bLogIn);
        p2.add(l_creati_cont);
        p2.add(bSignIn);
        p2.add(bHome);
        p2.add(bBack);
        p2.setBackground(navy);

//INTRODUCERE ZI DE NASTERE

        bHome=new JButton();
        bHome.setPreferredSize(new Dimension(50,50));
        bHome.setIcon(new ImageIcon("home_white.png"));
        bHome.setBackground(navy);
        bHome.setBorder(null);
        bHome.setActionCommand("Home");
        bHome.addActionListener(this.controller);

        bBack=new JButton();
        bBack.setPreferredSize(new Dimension(50,50));
        bBack.setIcon(new ImageIcon("back_white.png"));
        bBack.setBackground(navy);
        bBack.setBorder(null);
        bBack.setActionCommand("Back");
        bBack.addActionListener(this.controller);

        bSalveazaData =creazaButon(null,"Salveaza_data"," Salveaza data ",purple);;
        bStergeData =creazaButon(null,"Sterge_data"," Sterge data ",purple);;

        day=new JTextField(5);
        day.setBounds(715,420, 50,25);

        month=new JTextField(5);
        month.setBounds(775,420, 50,25);

        year=new JTextField(5);
        year.setBounds(835,420, 50,25);

        bZodie = creazaButon(null,"Calculeaza_zodie"," Gata ",purple);

        lDataGresita =creazaLabel("Data gresita",zodiacFont,Color.white);
        lDataGresita.setVisible(false);
        p3 = new JPanel();
        p3.add(day);
        p3.add(month);
        p3.add(year);
        p3.add(bZodie);
        p3.add(lDataGresita);
        p3.add(bSalveazaData);
        p3.add(bStergeData);
        p3.add(bHome);
        p3.add(bBack);
        p3.setBackground(navy);

//INREGISTRARE CONT


        bHome=new JButton();
        bHome.setPreferredSize(new Dimension(50,50));
        bHome.setIcon(new ImageIcon("home_white.png"));
        bHome.setBackground(navy);
        bHome.setBorder(null);
        bHome.setActionCommand("Home");
        bHome.addActionListener(this.controller);

        bBack=new JButton();
        bBack.setPreferredSize(new Dimension(50,50));
        bBack.setIcon(new ImageIcon("back_white.png"));
        bBack.setBackground(navy);
        bBack.setBorder(null);
        bBack.setActionCommand("Back");
        bBack.addActionListener(this.controller);

        lNume =creazaLabel("Nume:",bigFont,Color.white);

        nume=new JTextField(20);
        nume.setBounds(715,420, 50,25);

        lPrenume =creazaLabel("Prenume: ",bigFont,Color.white);

        prenume=new JTextField(20);
        prenume.setBounds(775,420, 50,25);

        lAdresa =creazaLabel("Mail: ",bigFont,Color.white);

        adresaEmail=new JTextField(20);
        adresaEmail.setBounds(835,420, 50,25);

        lUserNou =creazaLabel("User: ",bigFont,Color.white);

        user_nou=new JTextField(20);
        user_nou.setBounds(835,420, 50,25);
        user_nou.setVisible(false);
        lUserNou.setVisible(false);

        lParolaNou =creazaLabel("Parola: ",bigFont,Color.white);

        parola_nou=new JTextField(20);
        parola_nou.setBounds(835,420, 50,25);
        parola_nou.setVisible(false);
        lParolaNou.setVisible(false);




        image=controller.resizeImagine("next_white.png",50,50);
        bDatePersonale = creazaButon(image,"Dupa_ce_am_introdus_datele",null,navy);

        bContNou = creazaButon(null,"SignInDupaDate"," SIGN IN ",purple);
        bContNou.setVisible(false);

        p4 = new JPanel();
        p4.add(lNume);
        p4.add(nume);
        p4.add(lPrenume);
        p4.add(prenume);
        p4.add(lAdresa);
        p4.add(adresaEmail);
        p4.add(lUserNou);
        p4.add(user_nou);
        p4.add(lParolaNou);
        p4.add(parola_nou);
        p4.add(bDatePersonale);
        p4.add(bContNou);
        p4.add(bHome);
        p4.add(bBack);
        p4.setBackground(navy);

//CALCULARE ZODIE

        bHome=new JButton();
        bHome.setPreferredSize(new Dimension(50,50));
        bHome.setIcon(new ImageIcon("home_white.png"));
        bHome.setBackground(navy);
        bHome.setBorder(null);
        bHome.setActionCommand("Home");
        bHome.addActionListener(this.controller);

        bBack=new JButton();
        bBack.setPreferredSize(new Dimension(50,50));
        bBack.setIcon(new ImageIcon("back_white.png"));
        bBack.setBackground(navy);
        bBack.setBorder(null);
        bBack.setActionCommand("Back");
        bBack.addActionListener(this.controller);

        lZodie = lParolaNou =creazaLabel(null,zodiacFont,Color.white);
        lElement =creazaLabel(null,zodiacFont,Color.white);
        lOra =creazaLabel(null,zodiacFont,Color.white);
        lNumar =creazaLabel(null,zodiacFont,Color.white);
        lPersonalitate =creazaLabel(null,zodiacFont,Color.white);
        lDupaLuna =creazaLabel(null,zodiacFont,Color.white);
        lDupaAn =creazaLabel(null,zodiacFont,Color.white);
        lChinezescDragon =creazaLabel(null,zodiacFont,Color.white);
        lChinezescLucky =creazaLabel(null,zodiacFont,Color.white);
        lChinezescMagic =creazaLabel(null,zodiacFont,Color.white);

        JLabel labelZodie=creazaLabel("Zodie:",zodiacFont,Color.white);
        JLabel labelElement=creazaLabel("Element:",zodiacFont,Color.white);
        JLabel labelOra=creazaLabel("Ora norocoasa:",zodiacFont,Color.white);
        JLabel labelNumar=creazaLabel("Numar norocos:",zodiacFont,Color.white);
        JLabel labelPersonalitate=creazaLabel("Personalitatea ta:",zodiacFont,Color.white);
        JLabel labelDupaLuna=creazaLabel("Dupa luna:",zodiacFont,Color.white);
        JLabel labelDupaAn=creazaLabel("Dupa an:",zodiacFont,Color.white);
        JLabel labelChinezesc=creazaLabel("Dupa zodiacul chinezesc:",zodiacFont,Color.white);

        pozaZodie=new JLabel();
        pozaElement=new JLabel();
        pozaChinezesc=new JLabel();
        pozaChinezesc.setIcon(new ImageIcon("dragon_white.png"));


        //ACESEAZA UN LINK CAND CLICK PE DRAGON
        pozaChinezesc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        pozaChinezesc.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 0) {
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            URI uri = new URI("https://ro.wikipedia.org/wiki/Zodiacul_chinezesc");
                            desktop.browse(uri);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        } catch (URISyntaxException ex) {
                            ex.printStackTrace();
                        }

                    }
                }
            }
        });


        p5 = new JPanel();
        p5.add(labelZodie);
        p5.add(lZodie);
        p5.add(this.pozaZodie);
        p5.add(labelElement);
        p5.add(lElement);
        p5.add(this.pozaElement);
        p5.add(labelOra);
        p5.add(lOra);
        p5.add(labelNumar);
        p5.add(lNumar);
        p5.add(labelPersonalitate);
        p5.add(lPersonalitate);
        p5.add(labelDupaLuna);
        p5.add(lDupaLuna);
        p5.add(labelDupaAn);
        p5.add(lDupaAn);
        p5.add(labelChinezesc);
        p5.add(lChinezescDragon);
        p5.add(lChinezescLucky);
        p5.add(lChinezescMagic);
        p5.add(this.pozaChinezesc);
        p5.add(bHome);
        p5.add(bBack);
        p5.setBackground(navy);
        //////////////////////////////////HOROSCOP/////////////////////////////////////////////////////////////////////
//LANT

        bHome=new JButton();
        bHome.setPreferredSize(new Dimension(50,50));
        bHome.setIcon(new ImageIcon("home_white.png"));
        bHome.setBackground(navy);
        bHome.setBorder(null);
        bHome.setActionCommand("Home");
        bHome.addActionListener(this.controller);

        bBack=new JButton();
        bBack.setPreferredSize(new Dimension(50,50));
        bBack.setIcon(new ImageIcon("back_white.png"));
        bBack.setBackground(navy);
        bBack.setBorder(null);
        bBack.setActionCommand("Back");
        bBack.addActionListener(this.controller);

        bCos=new JButton();
        bCos.setPreferredSize(new Dimension(50,50));
        bCos.setIcon(new ImageIcon("cart3.png"));
        bCos.setBackground(navy);
        bCos.setBorder(null);
        bCos.setActionCommand("Cos");
        bCos.addActionListener(this.controller);

        l_dimensiuneLant=creazaLabel("Dimensiunea medalionului:",bigFont,Color.white);
        l_culoareLant=creazaLabel("Culoarea medalionului:",bigFont,Color.white);
        l_mesajLant=creazaLabel("Alege un mesaj:",bigFont,Color.white);
        l_bucatiLant=creazaLabel("Numarul de bucati:",bigFont,Color.white);

        mic=creazaRadioButon(null,"mic",navy);
        mediu=creazaRadioButon(null,"mediu",navy);
        mare=creazaRadioButon(null,"mare",navy);
        alb=creazaRadioButon(null,"alb",navy);
        albastru=creazaRadioButon(null,"albastru",navy);
        mov=creazaRadioButon(null,"violet",navy);
        verde=creazaRadioButon(null,"verde",navy);
        galben=creazaRadioButon(null,"galben",navy);
        rosu=creazaRadioButon(null,"rosu",navy);

        dim=new ButtonGroup();
        dim.add(mic);
        dim.add(mediu);
        dim.add(mare);

        culoare=new ButtonGroup();
        culoare.add(alb);
        culoare.add(albastru);
        culoare.add(mov);
        culoare.add(verde);
        culoare.add(galben);
        culoare.add(rosu);

        mesaj=new JTextField(10);
        mesaj.setBounds(715,420, 50,25);
        nrBucati=new JTextField(5);
        nrBucati.setBounds(775,420, 50,25);

        b_adaugaLant=creazaButon(null,"adaugaLant","Adauga in cos",purple);


        p6 = new JPanel();
        p6.add(l_dimensiuneLant);
        p6.add(mic);
        p6.add(mediu);
        p6.add(mare);
        p6.add(l_culoareLant);
        p6.add(alb);
        p6.add(albastru);
        p6.add(mov);
        p6.add(verde);
        p6.add(rosu);
        p6.add(galben);
        p6.add(l_mesajLant);
        p6.add(mesaj);
        p6.add(l_bucatiLant);
        p6.add(nrBucati);
        p6.add(b_adaugaLant);
        p6.add(bHome);
        p6.add(bBack);
        p6.add(bCos);
        p6.setBackground(navy);

//TABLOU

        bHome=new JButton();
        bHome.setPreferredSize(new Dimension(50,50));
        bHome.setIcon(new ImageIcon("home_white.png"));
        bHome.setBackground(navy);
        bHome.setBorder(null);
        bHome.setActionCommand("Home");
        bHome.addActionListener(this.controller);

        bBack=new JButton();
        bBack.setPreferredSize(new Dimension(50,50));
        bBack.setIcon(new ImageIcon("back_white.png"));
        bBack.setBackground(navy);
        bBack.setBorder(null);
        bBack.setActionCommand("Back");
        bBack.addActionListener(this.controller);

        bCos=new JButton();
        bCos.setPreferredSize(new Dimension(50,50));
        bCos.setIcon(new ImageIcon("cart3.png"));
        bCos.setBackground(navy);
        bCos.setBorder(null);
        bCos.setActionCommand("Cos");
        bCos.addActionListener(this.controller);



        l_numeTablou=creazaLabel("Numele tabloului:",bigFont,Color.white);
        l_culoareTablou=creazaLabel("Alege culoare de fundal:",bigFont,Color.white);
        l_zodiaTablou=creazaLabel("Alege zodia:",bigFont,Color.white);

        albt=creazaRadioButon(null,"alb",navy);
        albastrut=creazaRadioButon(null,"albastru",navy);
        movt=creazaRadioButon(null,"violet",navy);
        verdet=creazaRadioButon(null,"verde",navy);
        galbent=creazaRadioButon(null,"galben",navy);
        rosut=creazaRadioButon(null,"rosu",navy);

        culoareTablou=new ButtonGroup();
        culoareTablou.add(albt);
        culoareTablou.add(albastrut);
        culoareTablou.add(movt);
        culoareTablou.add(verdet);
        culoareTablou.add(galbent);
        culoareTablou.add(rosut);

        fecioara=creazaRadioButon(null,"fecioara",navy);
        berbec=creazaRadioButon(null,"berbec",navy);
        scorpion=creazaRadioButon(null,"scorpion",navy);
        gemeni=creazaRadioButon(null,"gemeni",navy);
        leu=creazaRadioButon(null,"leu",navy);
        pesti=creazaRadioButon(null,"pesti",navy);
        capricorn=creazaRadioButon(null,"capricorn",navy);
        rac=creazaRadioButon(null,"rac",navy);
        varsator=creazaRadioButon(null,"varsator",navy);
        balanta=creazaRadioButon(null,"balanta",navy);
        taur=creazaRadioButon(null,"taur",navy);
        sagetator=creazaRadioButon(null,"sagetator",navy);

        zodieTablou=new ButtonGroup();
        zodieTablou.add(fecioara);
        zodieTablou.add(berbec);
        zodieTablou.add(scorpion);
        zodieTablou.add(gemeni);
        zodieTablou.add(leu);
        zodieTablou.add(pesti);
        zodieTablou.add(capricorn);
        zodieTablou.add(rac);
        zodieTablou.add(varsator);
        zodieTablou.add(balanta);
        zodieTablou.add(taur);
        zodieTablou.add(sagetator);

        numeTablou =new JTextField(10);
        numeTablou.setBounds(715,420, 50,25);

        b_adaugaTablou=creazaButon(null,"adaugaTablou","Adauga in cos",purple);

        p7 = new JPanel();
        p7.add(l_numeTablou);
        p7.add(numeTablou);
        p7.add(l_zodiaTablou);
        p7.add(fecioara);
        p7.add(berbec);
        p7.add(scorpion);
        p7.add(gemeni);
        p7.add(leu);
        p7.add(pesti);
        p7.add(capricorn);
        p7.add(rac);
        p7.add(varsator);
        p7.add(balanta);
        p7.add(taur);
        p7.add(sagetator);
        p7.add(l_culoareTablou);
        p7.add(albt);
        p7.add(albastrut);
        p7.add(movt);
        p7.add(verdet);
        p7.add(rosut);
        p7.add(galbent);
        p7.add(b_adaugaTablou);
        p7.add(bHome);
        p7.add(bBack);
        p7.add(bCos);
        p7.setBackground(navy);

//////////COS
        bHome=new JButton();
        bHome.setPreferredSize(new Dimension(50,50));
        bHome.setIcon(new ImageIcon("home_white.png"));
        bHome.setBackground(navy);
        bHome.setBorder(null);
        bHome.setActionCommand("Home");
        bHome.addActionListener(this.controller);

        bBack=new JButton();
        bBack.setPreferredSize(new Dimension(50,50));
        bBack.setIcon(new ImageIcon("back_white.png"));
        bBack.setBackground(navy);
        bBack.setBorder(null);
        bBack.setActionCommand("Back");
        bBack.addActionListener(this.controller);

        numeUtilizator=creazaLabel(null,bigFont,Color.white);
        lPret=creazaLabel("PRET:  ",bigFont,Color.white);
        lPretulFinal=creazaLabel(null,bigFont,Color.white);
        p8 = new JPanel();
        p8.add(numeUtilizator);
        p8.add(lPret);
        p8.add(lPretulFinal);
        p8.add(bHome);
        p8.add(bBack);
        p8.setBackground(navy);



        tabbedPane.add("LOG_IN", p2);
        tabbedPane.add("HOME", p1);
        tabbedPane.add("CALC_ZODIE", p3);
        tabbedPane.add("SIGN_IN", p4);
        tabbedPane.add("Zodie_calculata", p5);
        tabbedPane.add("Magazin_Lant", p6);
        tabbedPane.add("Magazin_Tablou", p7);
        tabbedPane.add("Cos", p8);
        tabbedPane.setBackground(navy);
    }


}

