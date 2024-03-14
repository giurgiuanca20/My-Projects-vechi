package org.example.Operation;

import org.example.Polynmial.Monom;
import org.example.Polynmial.Polinom;
import org.example.Result;

public class Operations {
    public Polinom aduna(Polinom p1, Polinom p2) {
        Polinom rez = new Polinom();
        for (Monom m : p1.getMon().values())
        rez.adaugaMonom(m);       //copiem primul polinom la rezultat
        for (Monom m1 : p2.getMon().values())    //pentru fiecare monom din al doilea polinom
                rez.adaugaMonom(m1);                //adaugam monoml la polinom
        return rez;
    }
    public Polinom scadere(Polinom p1, Polinom p2) {
        Polinom rez = new Polinom();
        for (Monom m : p1.getMon().values())    //trece prin toate monoamele polinomuli 1
        {
            Monom m2=p2.getMon().get(m.getPutere());        //cauta monoamele cu aceeasi putere din polinomul2
            if(m2!=null)        //daca exista
            {
                m2=new Monom(m.getCoeficient()-m2.getCoeficient(),m.getPutere());   //face scaderea dintre coeficienti
                if(m2.getCoeficient()!=0)       //adauga monomul doar daca coeficientul e diferit de 0
                    rez.adaugaMonom(m2);
            }
            else
                rez.adaugaMonom(new Monom(m.getCoeficient(),m.getPutere()));    //altfel adauga monomu primului polinom asa cum e
        }
        for(Monom m:p2.getMon().values())       //cauta prin polinomul 2 nonoamele care nu au fost adaugate(cu putere diferita de puterile din polinomul1
        {
            Monom m2 =p1.getMon().get(m.getPutere());
            if(m2==null)
            {
                m2=new Monom(-m.getCoeficient(), m.getPutere());
                if(m2.getCoeficient()!=0)
                    rez.adaugaMonom(m2);
            }
        }
        return rez;
    }
    public Polinom inmultire(Polinom p1, Polinom p2) {
        Polinom rez = new Polinom();
        for (Monom m1 : p1.getMon().values())
           for (Monom m2 : p2.getMon().values()) {
            double c=m1.getCoeficient()*m2.getCoeficient();         //inmulteste fiecare coeficient din polinomul1 cu fiecare din polinomul2
            int p=m1.getPutere()+m2.getPutere();            ////aduna fiecare putere din polinomul1 cu fiecare din polinomul2
            Monom aux=new Monom(c,p);
            rez.adaugaMonom(aux);
        }
        return rez;
    }
    public Result impartire(Polinom p1, Polinom p2) {
        Polinom poli = new Polinom();
        if (p1.getGrad() < p2.getGrad())            //verific dac se poate face impartirea
            return new Result(null,null);
        Polinom aux = new Polinom();
        aux.adaugaMonom(p1.getMaxMonom());    //aux ia valoare monomului cu cel mai mare grad
        while (aux.getGrad() >= p2.getGrad()) {     //cat timp monomului cu cel mai mare grad are gradul mai mare ca gradul polinomului2
            double c = aux.getMaxMonom().getCoeficient() / p2.getMaxMonom().getCoeficient(); //se impart coeficientii
            int p = aux.getMaxMonom().getPutere() - p2.getMaxMonom().getPutere();       //se scad puterile
            Monom m_aux = new Monom(c, p);
            Polinom t = new Polinom();
            t.adaugaMonom(m_aux);   //se adauga noul monom
            for(Monom m:t.getMon().values())
            poli.adaugaMonom(m);            //se copiaza t in poli
            Polinom temp2 = new Polinom();
            for (Monom m : p2.getMon().values()) {      //se inmulteste polinomul 2 cu polinomul rezultat
                double coef = m.getCoeficient() * c;
                int power = m.getPutere() + p;
                Monom mon = new Monom(coef, power);
                temp2.adaugaMonom(mon);
            }
            p1=scadere(p1,temp2);        //se scade din polinomul1 polinomul rezultat
            aux = new Polinom();
            aux.adaugaMonom(p1.getMaxMonom());      //se actualizeaza aux-ul
        }
        Result rez=new Result(poli,p1);     //in urma scaderii p1 are valoarea restului
        return rez;
    }

    public Polinom derivare(Polinom p1) {
        Polinom rez = new Polinom();
        Monom m1;                       //noul monom dupa derivare
        for (Monom m : p1.getMon().values())     //parcurgem polinomul
        {
            int p=m.getPutere();
            double c=m.getCoeficient();
            if(m.getPutere() !=0)
            {
                c= c * p;           //inmultim coeficientu cu puterea
                p--;                //scadem din gradul monomului 1
            }
            else
                c=0;
            m1=new Monom(c,p);
            rez.adaugaMonom(m1);
        }
        return rez;
    }
    public Polinom integrare(Polinom p1) {
        Polinom rez = new Polinom();
        for (Monom m : p1.getMon().values())
        {
            double c=m.getCoeficient()/(m.getPutere()+1);    //impartim coeficientul cu p+1 deoarece astfel aflam vechiul coeficient
            int p=m.getPutere()+1;
            Monom aux=new Monom(c, p);
            rez.adaugaMonom(aux);

        }
        return rez;         //la afisare am adaugat un "+c" deoarece nu stim daca exista inainte de derivare o constanta care acum nu o putem afla
    }
}
