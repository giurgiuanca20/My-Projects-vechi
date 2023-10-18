package org.example.Model;

public class Applicant {
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String address1;
    private String address2;
    private int country;
    private int state;
    private int city;
    private int account;

    public Applicant() {

    }

    public void setAccount(int account) {
        this.account = account;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {

        this.phone = phone;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

}
