package com.iat.epoints.signIn.signIn;

import com.iat.epoints.login.signup.User;

/**
 * Created by Manikanta.
 */

public class MemoryRepository implements SignInRepository {

    private User user;

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
}
