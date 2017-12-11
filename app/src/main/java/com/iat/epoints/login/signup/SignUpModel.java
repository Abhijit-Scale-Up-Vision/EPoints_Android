package com.iat.epoints.login.signup;

public class SignUpModel implements SignUpActivityMVP.Model {


    private SignUpRepository repository;

    public SignUpModel(SignUpRepository repository) {
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
