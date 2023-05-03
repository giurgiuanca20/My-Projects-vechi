package Magazin;

public class RowInTable {
    String tip;
    String sizeNume;
    String culoare;
    String messZodie;
    int nrBuc;
    int pret;

    public RowInTable(String tip, String sizeNume, String culoare, String messZodie, int nrBuc, int pret) {
        this.tip = tip;
        this.sizeNume = sizeNume;
        this.culoare = culoare;
        this.messZodie = messZodie;
        this.nrBuc = nrBuc;
        this.pret = pret;
    }

    public String getTip() {
        return tip;
    }

    public String getSizeNume() {
        return sizeNume;
    }

    public String getCuloare() {
        return culoare;
    }

    public String getMessZodie() {
        return messZodie;
    }

    public String getNrBuc() {
        return String.valueOf(nrBuc);
    }

    public String getPret() {
        return String.valueOf(pret);
    }
}
