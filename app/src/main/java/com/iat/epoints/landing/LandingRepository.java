package com.iat.epoints.landing;



/**
 * Created by Abhijit.
 */

public interface LandingRepository
{
    User getUser();

    void saveUser(User user);
}
