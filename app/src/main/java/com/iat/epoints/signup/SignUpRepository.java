package com.iat.epoints.signup;

public interface SignUpRepository {

    User getUser();

    void saveUser(User user);
}
