package com.iat.epoints.signIn.signIn;

/**
 * Created by Manikanta.
 */

public interface SignInActivityMVP
{
    interface View
    {
        String getEmail();
        String getPassword();

        void showInputError();

        void signInSuccess(String token);

        void validationError();

        void setEmail(String email);

        void setPassword(String password);

        boolean validate();

        void signInUser();

        void clearText();

        void createDialog();

        void removeError();

    }


    interface Presenter
    {
        void setView(View view);
        void loginButtonClicked();

    }



    interface Model
    {
        void signInUser(String email, String password);

        void signInSuccess(String token);
    }
}
