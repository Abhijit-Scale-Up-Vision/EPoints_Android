package com.iat.epoints.signin;


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
    public User getUser() {
        return null;
    }

    @Override
    public void getSignAccessToken(String token) {

        repository.saveToken(new Token(token));
    }


    @Override
    public void signInSuccess(String token) {

    }

}
