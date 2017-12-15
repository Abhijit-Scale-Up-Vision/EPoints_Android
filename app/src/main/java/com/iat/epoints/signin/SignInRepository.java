package com.iat.epoints.signin;

/**
 * Created by Manikanta.
 */

public interface SignInRepository
{
    User getUser();
    void saveUser(User user);
    Token getAccessToken();
    void saveToken(Token token);
}
