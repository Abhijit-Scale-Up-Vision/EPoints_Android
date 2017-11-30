package com.scaleup.epoints.login;

public interface LoginRepository {

    User getUser();

    void saveUser(User user);
}
