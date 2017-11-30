package com.scaleup.epoints.login;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.scaleup.epoints.R;
import com.scaleup.epoints.root.App;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity implements LoginActivityMVP.View {

    @Inject
    LoginActivityMVP.Presenter presenter;


    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private Button login;
    private Toolbar toolbar;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ((App) getApplication()).getComponent().inject(this);

        firstName = (EditText) findViewById(R.id.editText_firstName);
        lastName = (EditText) findViewById(R.id.editText_lastName);
        email = (EditText) findViewById(R.id.editText_email);
        password = (EditText) findViewById(R.id.editText_password);
        login = (Button) findViewById(R.id.button_createNewAccount);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) toolbar.findViewById(R.id.toolbar_title);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setNavigationIcon(R.drawable.ic_action_left_chevron);
        }
        title.setText(getString(R.string.text_createAccount));

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                presenter.loginButtonClicked();

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.getCurrentUser();
    }

    @Override
    public String getFirstName() {
        return firstName.getText().toString();
    }

    @Override
    public String getLastName() {
        return lastName.getText().toString();
    }

    @Override
    public String getEmail() {
        return email.getText().toString();
    }

    @Override
    public String getPassword() {
        return password.getText().toString();
    }

    @Override
    public void showUserNotAvailable() {
        Toast.makeText(this, "Error the user is not available", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUserAvailable() {
        Toast.makeText(this, "User is available", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showInputError() {
        Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void validationError() {
        Toast.makeText(this,"Signup has failed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName.setText(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName.setText(lastName);
    }

    @Override
    public void setEmail(String email) {
            this.email.setText(email);
    }

    @Override
    public void setPassword(String password) {
            this.password.setText(password);
    }

    @Override
    public void showUserSavedMessage() {
        Toast.makeText(this, "User Register successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean validate() {
        boolean valid = true;
        if (getEmail().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches()) {
            email.setError("Please enter valid email address");
            valid = false;
        }
        if (getPassword().isEmpty()) {
            password.setError("Please enter password");
            valid = false;
        }
        if (getFirstName().isEmpty()) {
            firstName.setError("Please enter firstname");
            valid = false;
        }
        if (getLastName().isEmpty()) {
            lastName.setError("Please enter lastname");
            valid = false;
        }
       /* if (promocode.isEmpty()) {
            edtText_promocode.setError("Please enter promocode");
            valid = false;
        }*/
        return valid;
    }

}
