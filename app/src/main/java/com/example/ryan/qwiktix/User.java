package com.example.ryan.qwiktix;

/**
 * Created by brade_000 on 2/18/2017.
 */

public class User {

    private String email;
    private String payPalEmail;
    private String password;
    private String first_name;
    private String last_name;

    public User(String email, String password,String first_name,String last_name,String payPalEmail){
        this.email = email;
        this.payPalEmail = payPalEmail;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
    }
    public User(String email, String password,String first_name,String last_name){
        this.email = email;
        this.payPalEmail = "no@paypal.com";
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
    }
    public User(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setPayPalEmail (String payPalEmail) { this.payPalEmail = payPalEmail;}
    public String getPayPalEmail() {return  payPalEmail;}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }
    public String getLastName() {
        return last_name;
    }

        public void setLastName(String last_name) {
        this.last_name = last_name;
    }



}
