package com.iat.epoints.landing;

/**
 * Created by Abhijit.
 */

public class User {


    private final boolean email;
    private final boolean facebook;

    public boolean isEmail() {
        return email;
    }

    public boolean isFacebook() {
        return facebook;
    }

    public User(boolean email, boolean facebook){
        this.email = email;
        this.facebook = facebook;
    }

}

