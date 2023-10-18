package org.example.Model;

public class Account {
    private int id;
    private String username;
    private String password;
    int type;

    public Account() {

    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(int type) {
        this.type = type;
    }

}
