package com.example.ryan.qwiktix;

/**
 * Created by brade_000 on 2/18/2017.
 */

public class User {

    public String email;
    public String password;
    public String first_name;
    public String last_name;

    public User(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
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
