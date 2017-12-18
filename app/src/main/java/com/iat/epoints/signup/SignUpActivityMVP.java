package com.iat.epoints.signup;

import android.text.style.ClickableSpan;
import android.widget.TextView;

public interface SignUpActivityMVP {

    interface View{

        String getFirstName();
        String getLastName();
        String getEmail();
        String getPassword();


        void showUserNotAvailable();

        void showUserAvailable();

        void showInputError();

        void validationError();

        void setFirstName(String firstName);

        void setLastName(String lastName);

        void setEmail(String email);

        void setPassword(String password);

        void showUserSavedMessage();

        boolean validate();

        void signUpUser();

        void verifyExistingUser();

        void checkOutEmail();

        void slideUp();

        void clearText();

        void createDialog();

        void clickableText();

        void gotoPrivacy();

        void removeError();

        void gotoTOC();

        void makeLinks(TextView textView, String[] links, ClickableSpan[] clickableSpans);


    }

    interface Presenter {

        void setView(View view);

        void loginButtonClicked();

        void getCurrentUser();

        void saveUser();

    }

    interface Model {

        void createUser(String name, String lastName, String email, String password);

        User getUser();

    }
}
