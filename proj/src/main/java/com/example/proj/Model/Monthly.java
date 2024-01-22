package com.example.proj.Model;

public class Monthly {
    private String image;
    private String text;
    private String hour;
    private String nrDay;

    public Monthly() {

    }

    public void setNrDay(String nrDay) {
        this.nrDay = nrDay;
    }

    public String getNrDay() {
        return nrDay;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
}
