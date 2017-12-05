package com.scaleup.epoints.login.signup;

public interface SignUpRepository {

    User getUser();

    void saveUser(User user);
}
