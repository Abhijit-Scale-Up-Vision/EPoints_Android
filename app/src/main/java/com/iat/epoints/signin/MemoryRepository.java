package com.iat.epoints.signin;

/**
 * Created by Abhijit.
 */

public class MemoryRepository implements SignInRepository {

    private User user;
    private Token token;

    @Override
    public User getUser() {

        if (user == null) {
            User user = new User("", "");
            user.setId(0);
            return user;
        } else {
            return user;
        }

    }

    @Override
    public void saveUser(User user) {

        if (user == null) {
            user = getUser();
        }

        this.user = user;

    }

    @Override
    public Token getAccessToken() {
        if(token == null) {
            Token token = new Token("");
            return token;
        }else{
            return token;
        }
    }

    @Override
    public void saveToken(Token token) {

        if(token == null){
            token = getAccessToken();
        }
    }
}
