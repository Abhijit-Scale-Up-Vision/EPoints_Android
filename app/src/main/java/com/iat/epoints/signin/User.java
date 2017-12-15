package com.iat.epoints.signin;

/**
 * Created by Ramya.
 */

public class User {


   public User(String email,String password){
       this.email = email;
       this.password = password;
   }
    private  String email;
    private  String password;
    private int id;
    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
    }
}
