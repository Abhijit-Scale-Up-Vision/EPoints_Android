package com.iat.epoints.login.signup;

public interface SignUpRepository {

    User getUser();

    void saveUser(User user);
}
