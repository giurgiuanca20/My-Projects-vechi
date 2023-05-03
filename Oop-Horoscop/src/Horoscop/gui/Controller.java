package Horoscop.gui;
import Horoscop.altele.Numerologie;
import Horoscop.altele.Zodie;
import Magazin.Lant;
import Magazin.Tablou;
import database.Connect;
import database.DataNastere;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.time.Year;

import static javax.swing.JOptionPane.showMessageDialog;

//import static javax.swing.JOptionPane.showMessageDialog;

public class Controller implements ActionListener {

    private final View view;
    private Zodie zodie;
    private Numerologie numerologie;
    private String nume,parola,mail,utilizator,prenume,cUser,cParola;;
    private int y= Year.now().getValue();
    private DataNastere struct=new DataNastere(" ", " ", " ");
    private Lant l;
    private Tablou t;
    public Controller(View v) throws SQLException {
        this.view = v;
    }
    ImageIcon resizeImagine(String numeFisier, int h, int w) {
        ImageIcon aux=new ImageIcon(numeFisier);
        Image img = aux.getImage();        //resize pt o imagine
        BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        g.drawImage(img, 0, 0, w, h, null);
        ImageIcon newIcon = new ImageIcon(bi);
        return newIcon;
    }
public Connect q=new Connect("cont");

    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();
        switch (command){
            case "Home":
                this.view.tabbedPane.setSelectedIndex(1);
                view.tabel.setVisible(false);
                break;
            case "Back": //Nu merge chiar cum trebuie
                this.view.tabbedPane.setSelectedIndex(this.view.tabbedPane.getSelectedIndex()-1);
                view.tabel.setVisible(false);
                break;
            case "Zodiac":
                    this.view.tabbedPane.setSelectedIndex(2);
                break;
            case "Magazin_tablou":
                this.view.tabbedPane.setSelectedIndex(6);
                break;
            case "Magazin_lant":
                this.view.tabbedPane.setSelectedIndex(5);
                break;
                ///////////////////////////HOROSCOP//////////////////////////////////////////////
            case "ButonLogare":
                cUser= view.getUser();
                cParola= view.getParola();
                if(q.cautare_cont(cUser, cParola)) {
                    this.view.tabbedPane.setSelectedIndex(1);
                    int i = q.cautareDataExistenta(cUser, cParola);
                    if (i != 0) {
                        struct = q.dataInFunctieDeId(i);
                        view.setDay(struct.getZi());
                        view.setMonth(struct.getLuna());
                        view.setYear(struct.getAn());
                    }
                }
                else
                    showMessageDialog(null,"User sau parola incorecta!");
                break;
            case "Inregistreaza_cont":
                this.view.tabbedPane.setSelectedIndex(3);
                break;
            case "Dupa_ce_am_introdus_datele":
                nume=this.view.getNume();
                prenume=this.view.getPrenume();
                mail=this.view.getAdresaEmail();
                this.view.setCont();
                break;
            case "SignInDupaDate":
                utilizator=this.view.getUser_nou();
                parola=this.view.getParola_nou();
                q.add_cont(nume,prenume,mail,utilizator,parola);
                this.view.tabbedPane.setSelectedIndex(1);
                break;
            case "Calculeaza_zodie":
                if(0<view.getDay() && view.getDay()<32 && 0<view.getMonth() && view.getMonth()<13 && 1850<view.getYear() && view.getDay()<=y)
                {
                    this.view.tabbedPane.setSelectedIndex(4);
                    zodie=new Zodie(view.getDay(), view.getMonth());
                    numerologie=new Numerologie(zodie.zodia(),view.getDay(),view.getMonth(),view.getYear());
                    view.setlZodie(zodie.zodia());
                    view.setlOra(String.valueOf(zodie.ora()));
                    view.setlElement(numerologie.elemente());
                    view.setlNumar(String.valueOf(numerologie.norocos()));
                    view.setl_personalitate(zodie.personalitate());
                    view.setl_dupa_an(numerologie.numAn());
                    view.setl_dupa_luna(zodie.numLuna());
                    view.setl_chinezesc_dragon(numerologie.chinezescdragon());
                    view.setl_chinezesc_lucky(numerologie.chinezesclucky());
                    view.setl_chinezesc_magic(numerologie.chinezescmagic());
                    view.setPoze(zodie.PozaZodie(), numerologie.PozaElement());
                }
                else
                    showMessageDialog(null, "Datele introduse sunt incorecte.");
                view.setDay("");
                view.setMonth("");
                view.setYear("");
                break;
            case "Salveaza_data":
                if(0<view.getDay() && view.getDay()<32 && 0<view.getMonth() && view.getMonth()<13 && 1850<view.getYear() && view.getDay()<=y) {
                    q.salveazaDataNastere(cUser,cParola,view.getDay(),view.getMonth(),view.getYear());
                }
                break;
            case "Sterge_data":
                 q.stergeDataNastere(cUser,cParola);
                break;
            ///////////////////////////HOROSCOP//////////////////////////////////////////////

            ///////////////////////////MAGAZIN//////////////////////////////////////////////
            case "adaugaLant":
                int idCos=q.cautaCos(cUser,cParola);
                if(idCos==0)
                    q.addCos(cUser,cParola);
                l=new Lant(view.getDimensiune(),view.getCuloareLant(),view.getMesaj(),view.getNrBucati());
                int idLant=l.adaugaLant(idCos);
                q.addLantCos(idLant,idCos);//cautaLantInCos: returneaza 1 2 3 sau 0 in functie de in ce coloana se adauga id-ul lanului
                break;
            case "adaugaTablou":
                idCos=q.cautaCos(cUser,cParola);
                if(idCos==0)
                    q.addCos(cUser,cParola);
                t=new Tablou(view.getNumeTablou(),view.getZodie(),view.getCuloareTablou());
                int idTablou=t.adaugaTablou(idCos);
                q.addTablouCos(idTablou,idCos);
                break;
            case "Cos":
                idCos=q.cautaCos(cUser,cParola);
                if(idCos!=0){
                    view.setNumeUtilizator(q.numeInFuncteDeCont(cUser,cParola));
                    q.returneazaCosul(cUser,cParola);
                    view.scrieInTabel();
                }
                view.setlPretulFinal();
                this.view.tabbedPane.setSelectedIndex(7);
                break;
            default:
        }

        }
}
