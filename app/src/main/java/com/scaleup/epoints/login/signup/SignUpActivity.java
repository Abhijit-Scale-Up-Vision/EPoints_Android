package com.scaleup.epoints.login.signup;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.crash.FirebaseCrash;
import com.scaleup.epoints.R;
import com.scaleup.epoints.http.api.SignUpAPI;
import com.scaleup.epoints.http.apimodel.ResultStatus;
import com.scaleup.epoints.root.App;
import com.scaleup.epoints.thankyou.ThankYouActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements SignUpActivityMVP.View, TextWatcher, View.OnFocusChangeListener {

    @Inject
    SignUpActivityMVP.Presenter presenter;
    @Inject
    SignUpAPI signUpAPI;

    @BindView(R.id.editText_firstName)
    EditText firstName;
    @BindView(R.id.editText_lastName)
    EditText lastName;
    @BindView(R.id.editText_email)
    EditText email;
    @BindView(R.id.editText_password)
    EditText password;
    @BindView(R.id.button_createNewAccount)
    Button login;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView title;
    @BindView(R.id.textview_privacy)
    TextView privacy;
    @BindView(R.id.til_email)
    TextInputLayout tilEmail;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.til_firstName)
    TextInputLayout tilFirstName;
    @BindView(R.id.til_lastName)
    TextInputLayout tilLastName;
    @BindView(R.id.linearLayout_signUp)
    LinearLayout linearLayoutSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        FirebaseCrash.report(new Exception());
        ((App) getApplication()).getComponent().inject(this);

        ButterKnife.bind(this);

        slideUp();

        privacy.setText(Html.fromHtml(getString(R.string.text_privacy)));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setNavigationIcon(R.drawable.ic_action_left_chevron);
        }
        title.setText(getString(R.string.text_createAccount));


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                presenter.loginButtonClicked();

            }
        });

        email.addTextChangedListener(this);
        password.addTextChangedListener(this);
        firstName.addTextChangedListener(this);
        lastName.addTextChangedListener(this);
        email.setOnFocusChangeListener(this);
        password.setOnFocusChangeListener(this);
        firstName.setOnFocusChangeListener(this);
        lastName.setOnFocusChangeListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.getCurrentUser();
        clearText();
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
        Pattern pattern;

        tilEmail.setError(null);
        tilPassword.setError(null);
        tilFirstName.setError(null);
        tilLastName.setError(null);
        if (getEmail().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches()) {
            //email.setError("Please enter valid email address");
            tilEmail.setError(getString(R.string.text_email_error));
            valid = false;
        }
       /*   (?=.*[0-9]) a digit must occur at least once
            (?=.*[a-z]) a lower case letter must occur at least once
            (?=.*[A-Z]) an upper case letter must occur at least once
            (?=.*[@#$%^&+=]) a special character must occur at least once
            (?=\\S+$) no whitespace allowed in the entire string
            .{8,} at least 8 characters*/
        String PASSWORD_PATTERN  = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,15}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        if (getPassword().isEmpty() || !pattern.matcher(getPassword()).matches()) {
            //password.setError("Please enter valid password");
            tilPassword.setError(getString(R.string.text_password_error));
            valid = false;
        }
        if (getFirstName().isEmpty()) {
            //firstName.setError("Please enter firstname");
            tilFirstName.setError(getString(R.string.text_firstName_error));
            valid = false;
        }
        if (getLastName().isEmpty()) {
            //lastName.setError("Please enter lastname");
            tilLastName.setError(getString(R.string.text_lastName_error));
            valid = false;
        }
       /* if (promocode.isEmpty()) {
            edtText_promocode.setError("Please enter promocode");
            valid = false;
        }*/
        return valid;
    }

    @Override
    public void signUpUser() {

        // simplified call to request the news with already initialized service
        Call<ResultStatus> call = signUpAPI.registerUser(getEmail(), getFirstName(), getLastName(), getPassword());
        call.enqueue(new Callback<ResultStatus>() {
            @Override
            public void onResponse(Call<ResultStatus> call, Response<ResultStatus> response) {

                if(response.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    checkOutEmail();}

                else if(response.code()==400){
                   // Toast.makeText(SignUpActivity.this, "User already registered.", Toast.LENGTH_SHORT).show();
                    createDialog();
                }
            }

            @Override
            public void onFailure(Call<ResultStatus> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void checkOutEmail() {
        Intent intent = new Intent(SignUpActivity.this, ThankYouActivity.class);
        intent.putExtra("email",getEmail());
        startActivity(intent);

    }

    @Override
    public void slideUp() {
        //Load animation
        Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);

        Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);

        // Start animation
        linearLayoutSignUp.startAnimation(slide_up);
    }

    @Override
    public void clearText() {
        email.getText().clear();
        password.getText().clear();
        firstName.getText().clear();
        lastName.getText().clear();
    }

    @Override
    public void createDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SignUpActivity.this);

        View child = getLayoutInflater().inflate(R.layout.dialog_box, null);
        alertDialogBuilder.setView(child);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        boolean valid = true;
        Pattern pattern;
        String PASSWORD_PATTERN  = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,15}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);

        if (email.hasFocus()&&(getEmail().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches())) {
            //email.setError("Please enter valid email address");
            tilEmail.setError(getString(R.string.text_email_error));
            valid = false;

        }else if(email.hasFocus() && valid){tilEmail.setError(null);
        signUpUser();}
       /*   (?=.*[0-9]) a digit must occur at least once
            (?=.*[a-z]) a lower case letter must occur at least once
            (?=.*[A-Z]) an upper case letter must occur at least once
            (?=.*[@#$%^&+=]) a special character must occur at least once
            (?=\\S+$) no whitespace allowed in the entire string
            .{8,} at least 8 characters*/

        if (password.hasFocus() && (getPassword().isEmpty() || !pattern.matcher(getPassword()).matches())) {
            //password.setError("Please enter valid password");
            tilPassword.setError(getString(R.string.text_password_error));
            valid = false;
        }else if(password.hasFocus() && valid){
            tilPassword.setError(null);
        }
        if (firstName.hasFocus()&&(getFirstName().isEmpty())) {
            //firstName.setError("Please enter firstname");
            tilFirstName.setError(getString(R.string.text_firstName_error));
            valid = false;
        }else if(firstName.hasFocus() && valid){
            tilFirstName.setError(null);
        }
        if (lastName.hasFocus() && (getLastName().isEmpty())) {
            //lastName.setError("Please enter lastname");
            tilLastName.setError(getString(R.string.text_lastName_error));
            valid = false;
        }else if(lastName.hasFocus() && valid){
            tilLastName.setError(null);
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }
}
