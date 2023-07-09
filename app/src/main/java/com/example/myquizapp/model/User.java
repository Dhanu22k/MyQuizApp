package com.example.myquizapp.model;

public class User {
    String mail;
    String fullName;

    public User() {
    }

    String password;

    public User(String mail, String fullName, String password) {
        this.mail = mail;
        this.fullName = fullName;
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
