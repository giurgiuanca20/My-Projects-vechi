package Horoscop.altele;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Numerologie {

    private String zodie;
    private int zi;
    private int luna;
    private int an;
    private ImageIcon pozaElement;
    public Numerologie(String zodie, int zi, int luna, int an) {
        this.zodie = zodie;
        this.zi = zi;
        this.luna = luna;
        this.an = an;
    }

    public String elemente()
    {
        String s;
        if (this.zodie=="LEU" || this.zodie=="SAGETATOR" || this.zodie== "BERBEC")
            s = "FOC";
        else
        if (this.zodie=="GEMENI" || this.zodie=="BALANTA" || this.zodie== "VARSATOR")
            s = "AER";
        else
        if (this.zodie=="TAUR" || this.zodie=="FECIOARA"|| this.zodie=="CAPRICORN")
            s = "PAMANT";
        else
            s ="APA";
        return s;

    }

    public ImageIcon PozaElement() {
        String s=elemente();
        pozaElement=new ImageIcon();
        if (s== "AER")
            pozaElement=new ImageIcon("air_white.png");
        else
        if (s=="PAMANT")
            pozaElement=new ImageIcon("earth_white.png");
        else
        if (s=="FOC")
            pozaElement=new ImageIcon("fire_white.png");
        else
        if (s=="APA")
            pozaElement=new ImageIcon("water_white.png");

        Image img = pozaElement.getImage();        //resize pt o imagine
        BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        g.drawImage(img, 0, 0, 140, 140, null);
        ImageIcon newIcon = new ImageIcon(bi);
        return newIcon;
    }
    public int norocos()
    {
        int n = 0;
        int nor = 0;

        while (this.zi != 0)
        {
            n = n + this.zi % 10;
            this.zi = this.zi / 10;
        }
        while (this.luna != 0)
        {
            n = n + this.luna % 10;
            this.luna = this.luna / 10;
        }
        while (this.an != 0)
        {
            n = n + this.an % 10;
            this.an = this.an / 10;
        }
        while (n != 0)
        {
            nor = nor + n % 10;
            n = n / 10;
        }
        return nor;

    }

    public String chinezescdragon() //anul dragonului;
    {
        String s="";
        int n = 2012;
        while (n >= 1904)
        {
            if (an == n)
                if (this.zodie=="TAUR"|| this.zodie== "BERBEC")
                {
                    s = "Sunteti mereu ocrotiti de stele.";
                    return s;

                }
            n = n - 12;
        }
        if (s== "")
            s = "Stelele nu va ocrotesc intotdeauna.";
        return s;
    }


    public String chinezescmagic() //iepure+4;
    {
        String s="";
        if (this.zi == 11 && this.luna == 11)
            s = "Sunteti o persoana cu adevarat ghinionista.";
        else
        if (this.luna == 4 || this.zi == 4 || this.zodie=="BERBEC" || this.zodie=="PESTI")
            s = "Magia va inconjoara.";
        else
        {
            int n = 2011;
            while (n >= 1905)
            {
                if (an == n)

                    s = "Magia este o parte din viata dumneavoastra.";

                n = n - 12;
            }
            if (s=="")
                s = "Nu aveti tangenta cu magia.";


        }
        return s;
    }


    public String chinezesclucky() //porc+8;
    {
        String s = "";
        if (luna == 8 || zi == 8 || this.zodie=="RAC" || this.zodie=="LEU")
        {
            s = "Sunteti o persoana foarte norocoasa.";
            return s;
        }
        else
        {
            int n = 2019;
            while (n >= 1911)
            {
                if (an == n)
                {
                    s = "Aveți noroc.";
                    return s;
                }
                n = n - 12;
            }
            s = "Nu aveți noroc.";
            return s;

        }
    }

    public String numAn()
    {

        int n = 0;
        int nu = 0;
        while (an != 0)
        {
            n = n + an % 10;
            an = an / 10;
        }
        while (n != 0)
        {
            nu = nu + n % 10;
            n = n / 10;
        }
        String t;
        if (nu == 1)
            t = "DEOSEBIT";
        else
        if (nu == 2)
            t ="IUBITOR";
        else
        if (nu == 3)
            t ="ADVENTUROS";
        else
        if (nu == 4)
            t = "ENERGIC";
        else
        if (nu == 5)
            t = "DESCHIS";
        else
        if (nu == 6)
            t = "PLACUT";
        else
        if (nu == 7)
            t = "SPECIAL";
        else
        if (nu == 8)
            t = "SERIOS";
        else
            t = "SCHIMBATOR";

        return t;



    }

}
