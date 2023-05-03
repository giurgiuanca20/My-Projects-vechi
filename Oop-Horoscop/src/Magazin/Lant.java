package Magazin;

import Horoscop.gui.View;
import database.Connect;

import java.sql.*;

public class Lant {
    int idLant;
    String dimensiune;
    String culoare;
    String mesaj;
    int idCos;
    int nrBucati;
    Connect q=new Connect("lant");

    public Lant(String dimensiune, String culoare, String mesaj, int nrBucati) {
        this.dimensiune = dimensiune;
        this.culoare = culoare;
        this.mesaj = mesaj;
        this.nrBucati = nrBucati;
    }
    public int adaugaLant(int k){      ///returneaza id-ul lantului
        idLant=q.ultimul_index("lant")+1;
        idCos=k;
        int dim=q.indexInFunctieDeDimensiune(dimensiune);
        int cul=q.indexInFunctieDeCuloare(culoare);
        q.addLant(idLant,dim,cul,mesaj,idCos,nrBucati);
        return idLant;
    }



}
