package Horoscop.altele;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Zodie {
    private int zi;
    private int luna;
    private ImageIcon pozaZodie;

    public Zodie (int zi, int luna) {
        this.zi = zi;
        this.luna = luna;
    }

    public String zodia() {
        String s;
        if (this.luna == 1)
            if (this.zi < 20)
                s ="CAPRICORN";
            else
                s ="VARSATOR";
        else if (this.luna == 2)
            if (this.zi < 19)
                s ="VARSATOR";
            else
                s = "PESTI";
        else if (this.luna == 3)
            if (this.zi < 21)
                s = "PESTI";
            else
                s = "BERBEC";
        else if (this.luna == 4)
            if (this.zi < 21)
                s = "BERBEC";
            else
                s = "TAUR";
        else if (this.luna == 5)
            if (this.zi < 21)
                s = "TAUR";
            else
                s = "GEMENI";
        else if (this.luna == 6)
            if (this.zi < 22)
                s = "GEMENI";
            else
                s = "RAC";
        else if (this.luna == 7)
            if (this.zi < 23)
                s = "RAC";
            else
                s = "LEU";
        else if (this.luna == 8)
            if (this.zi < 23)
                s = "LEU";
            else
                s = "FECIOARA";
        else if (this.luna == 9)
            if (this.zi < 23)
                s = "FECIOARA";
            else
                s = "BALANTA";
        else if (this.luna == 10)
            if (this.zi < 23)
                s = "BALANTA";
            else
                s = "SCORPION";
        else if (this.luna == 11)
            if (this.zi < 22)
                s = "SCORPION";
            else
                s = "SAGETATOR";
        else if (this.zi < 21)
            s = "SAGETATOR";
        else
            s = "CAPRICORN";

        return s;
    }
    public int ora()
    {
        Random r= new Random();
        int int_random = r.nextInt(23);
        return int_random;
    }

    public String personalitate() {
        String s;
        int n;
        int nu = 0;
        int nu2 = 0;

        n = this.zi + this.luna;
        while (n != 0) {
            nu = nu + n % 10;
            n = n / 10;
        }
        if (nu > 9) {
            nu2 = nu2 + nu % 10;
            nu = nu / 10;
            nu2 = nu2 + nu % 10;
            nu = nu2;
        }

        if (nu == 2)
            s = "CONCENTRAT";
        else if (nu == 3)
            s = "HAIOS";
        else if (nu == 4)
            s = "LINISTIT";
        else if (nu == 5)
            s = "DISTRACTIV";
        else if (nu == 6)
            s = "GRIJULIU";
        else if (nu == 7)
            s = "RETINUT";
        else if (nu == 8)
            s = "SINCER";
        else
            s = "LIDER";

        return s;
    }

    public String numLuna() {
        String s;
        if (this.luna == 1)
            s = "INDEPENDENT";
        else if (this.luna == 2)
            s = "INTUITIV";
        else if (this.luna == 3)
            s = "NOROCOS";
        else if (this.luna == 4)
            s = "AMABIL";
        else if (this.luna == 5)
            s = "COMUNICATIV";
        else if (this.luna == 6)
            s = "BUN";
        else if (this.luna == 7)
            s = "ONEST";
        else if (this.luna == 8)
            s = "RESPONSABIL";
        else if (this.luna == 9)
            s = "ADAPTABIL";
        else if (this.luna == 10)
            s = "NOROCOS";
        else if (this.luna == 11)
            s = "SENSIBIL";
        else
            s = "PUTERNIC";
        return s;
    }

    public ImageIcon PozaZodie() {
        String s=zodia();
        pozaZodie=new ImageIcon();
        if (s== "FECIOARA")
            pozaZodie=new ImageIcon("virgo_white.png");
        else
        if (s== "TAUR")
            pozaZodie=new ImageIcon("taurus_white.png");
        else
        if (s== "SCORPION")
            pozaZodie=new ImageIcon("scorpio_white.png");
        else
        if (s== "SAGETATOR")
            pozaZodie=new ImageIcon("sagittarius_white.png");
        else
        if (s=="PESTI")
            pozaZodie=new ImageIcon("pisces_white.png");
        else
        if (s== "BALANTA")
            pozaZodie=new ImageIcon("libra_white.png");
        else
        if (s== "LEU")
            pozaZodie=new ImageIcon("leo_white.png");
        else
        if (s== "GEMENI")
            pozaZodie=new ImageIcon("gemini_white.png");
        else
        if (s== "CAPRICORN")
            pozaZodie=new ImageIcon("capricorn_white.png");
        else
        if (s=="RAC")
            pozaZodie=new ImageIcon("cancer_white.png");
        else
        if (s== "BERBEC")
            pozaZodie=new ImageIcon("aries_white.png");
        else
        if (s== "VARSATOR")
            pozaZodie=new ImageIcon("aquarius_white.png");
       Image img = pozaZodie.getImage();        //resize pt o imagine
        BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
         Graphics g = bi.createGraphics();
        g.drawImage(img, 0, 0, 120, 120, null);
        ImageIcon newIcon = new ImageIcon(bi);
        return newIcon;

    }
}

