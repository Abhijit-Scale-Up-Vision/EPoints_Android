package com.iat.epoints.signIn.signIn;


import com.iat.epoints.login.signup.User;

/**
 * Created by Manikanta.
 */

public interface SignInRepository
{
    User getUser();
    void saveUser(User user);
}
