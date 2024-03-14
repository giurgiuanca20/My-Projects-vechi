package org.example.Polynmial;

public class Monom {
     private double coeficient;
    private int putere;

    public double getCoeficient() {
        return coeficient;
    }

    public void setCoeficient(double coeficient) {
        this.coeficient = coeficient;
    }

    public int getPutere() {
        return putere;
    }

    public Monom(double coeficient, int putere) {
        this.coeficient = coeficient;
        this.putere = putere;
    }

}
