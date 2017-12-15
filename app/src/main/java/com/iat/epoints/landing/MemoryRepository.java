package com.iat.epoints.landing;


/**
 * Created by Abhijit.
 */

public class MemoryRepository implements LandingRepository {

    private User user;

    @Override
    public User getUser() {

        if (user == null) {
            User user = new User(false, false);

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
