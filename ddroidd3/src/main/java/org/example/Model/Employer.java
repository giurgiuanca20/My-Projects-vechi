package org.example.Model;


public class Employer {
    private int id;
    private String employerFirstName;
    private String employerLastName;
    private String employerEmail;
    private String employerCompanyName;
    private int account;

    public Employer() {

    }

    public void setAccount(int account) {
        this.account = account;
    }

    public void setEmployerFirstName(String employerFirstName) {
        this.employerFirstName = employerFirstName;
    }

    public void setEmployerLastName(String employerLastName) {
        this.employerLastName = employerLastName;
    }

    public void setEmployerEmail(String employerEmail) {
        this.employerEmail = employerEmail;
    }

    public void setEmployerCompanyName(String employerCompanyName) {
        this.employerCompanyName = employerCompanyName;
    }

}

