package com.iat.epoints.signIn.signIn;

import android.content.Context;

import com.iat.epoints.login.signup.User;


/**
 * Created by Manikanta.
 */

public class SignInModel implements SignInActivityMVP.Model
{
    private SignInRepository repository;

    public SignInModel(SignInRepository repository) {
        this.repository = repository;
    }

    @Override
    public void signInUser(String email, String password)
    {
        repository.saveUser(new User(email,password));
    }

    @Override
    public void signInSuccess(String token) {

    }

}
