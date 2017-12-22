package com.iat.epoints.signin;

/**
 * Created by Manikanta.
 */

public interface SignInActivityMVP
{
    interface View
    {
        String getEmail();
        String getPassword();
        String getToken();


        void signInSuccess(String token,int expTime);

        void validationError();

        void setEmail(String email);

        void setPassword(String password);

        boolean validate();

        void signInUser();

        void clearText();

        void createDialog();

        void removeError();

        void gotoDashBoard(String email,String firstName);
        void gotoChangePassword();

        void showUserNotAvailable();


        void showUserPasswordIsWrong();

        void showEmailAndPAsswordAreEmpty();
        void createErrorDialog();

    }
    interface Presenter
    {
        void setView(View view);
        void loginButtonClicked();
        void getCurrentUser();


    }
    interface Model
    {
        void signInUser(String email, String password);
        User getUser();
        void getSignAccessToken(String token);

    }
}
