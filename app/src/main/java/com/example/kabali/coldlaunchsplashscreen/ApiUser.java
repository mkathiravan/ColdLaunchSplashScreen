package com.example.kabali.coldlaunchsplashscreen;

public class ApiUser {

    public long id;
    public String firstname;
    public String lastname;


    @Override
    public String toString() {
        return "ApiUser{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
