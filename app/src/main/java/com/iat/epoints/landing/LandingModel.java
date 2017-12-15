package com.iat.epoints.landing;


public class LandingModel implements LandingActivityMVP.Model
{
    private LandingRepository repository;

    public LandingModel(LandingRepository repository) {
        this.repository = repository;
    }

    @Override
    public void signUpUser(boolean email, boolean facebook)
    {
        repository.saveUser(new User(email,facebook));
    }

    @Override
    public User getUser() {
        return null;
    }


}
