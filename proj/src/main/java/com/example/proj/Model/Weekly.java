package com.example.proj.Model;

public class Weekly {
    private String day;
    private String text;
    private String image;
    private String hour;

    public Weekly() {

    }

    public String getDay() {
        return day;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public String getHour() {
        return hour;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
}
