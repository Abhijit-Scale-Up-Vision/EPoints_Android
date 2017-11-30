package com.scaleup.epoints.login;

public class LoginModel implements LoginActivityMVP.Model {


    private LoginRepository repository;

    public LoginModel(LoginRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createUser(String name, String lastName, String email, String password) {


        repository.saveUser(new User(name, lastName, email, password));


    }

    @Override
    public User getUser() {

        return repository.getUser();
    }
}
