package com.iat.epoints.signin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.iat.epoints.BuildConfig;
import com.iat.epoints.R;
import com.iat.epoints.http.api.SignInAPI;
import com.iat.epoints.http.apimodel.SignInResult;
import com.iat.epoints.http.apimodel.SignInSucess;
import com.iat.epoints.root.App;
import com.iat.epoints.service.LoginExpiryService;

import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Manikanta.
 */

public class SignInActivity extends AppCompatActivity implements SignInActivityMVP.View, View.OnClickListener, TextWatcher, View.OnFocusChangeListener
{

    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;
    @Inject
    SignInActivityMVP.Presenter presenter;
    @Inject
    SignInAPI signInAPI;
    @BindView(R.id.toolbar_title)
    TextView title;
    @BindView(R.id.textview_signin)
    TextView tvSignIn;
    @BindView(R.id.textview_forgot_password)
    TextView tvFPassword;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.til_email)
    TextInputLayout tilEmail;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.editText_signin_email)
    EditText email;
    @BindView(R.id.editText_signin_password)
    EditText password;
    private AlertDialog alertDialog;
    private TextView cancelDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseCrash.report(new Exception());
        setContentView(R.layout.activity_sign_in);

        ((App) getApplication()).getComponent().inject(SignInActivity.this);
        ButterKnife.bind(this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setNavigationIcon(R.drawable.ic_action_left_chevron);
        }
        mSharedPreferences = getSharedPreferences("epointsPrefFile",MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        title.setText(getString(R.string.text_signIn));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tvSignIn.setOnClickListener(this);

        email.addTextChangedListener(this);
        password.addTextChangedListener(this);
        email.setOnFocusChangeListener(this);
        password.setOnFocusChangeListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.textview_signin:

                presenter.loginButtonClicked();
//                Toast.makeText(getApplicationContext(),"Working fine",Toast.LENGTH_SHORT).show();
                break;
            case R.id.dialog_cancel:
                alertDialog.dismiss();
                clearText();
                break;

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        clearText();
    }

    @Override
    public String getEmail() {
        return email.getText().toString().trim();
    }

    @Override
    public void setEmail(String email) {
        this.email.setText(email);
    }

    @Override
    public String getPassword() {
        return password.getText().toString().trim();
    }

    @Override
    public String getToken() {
        return mSharedPreferences.getString("ACCESS_TOKEN",null);
    }

    @Override
    public void setPassword(String password) {
        this.password.setText(password);
    }

    @Override
    public void showInputError() {
        Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void signInSuccess(String token,int expTime)
    {

        retrofit2.Call<SignInSucess> call = signInAPI.signInUserSucess("Bearer "+token);
        call.enqueue(new Callback<SignInSucess>() {
            @Override
            public void onResponse(retrofit2.Call<SignInSucess> call, Response<SignInSucess> response)
            {

                if (response.isSuccessful())
                {
                    boolean verifiedVal = response.body().getVerified();

                    if (verifiedVal == false)
                    {
                        createDialog();
//                        Toast.makeText(getApplicationContext(),"Need to Verify Account",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        String email = response.body().getEmail();
                        String firstName = response.body().getFirstName();

                        gotoDashBoard(email,firstName);
                        Intent serviceIntent = new Intent(SignInActivity.this, LoginExpiryService.class);
                        serviceIntent.putExtra("expiryTime",expTime);
                        startService(serviceIntent);
                    }
                }
                else if (response.code() == 202)
                {
                    Log.i("Res Password",""+response.body());
                    gotoChangePassword();
                }

            }
            @Override
            public void onFailure(retrofit2.Call<SignInSucess> call, Throwable t)
            {
                Log.e("Excpetion",""+t);
            }
        });
    }

    @Override
    public void validationError()
    {
        Toast.makeText(this,"SignIn has failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean validate()
    {
        boolean valid = true;
        Pattern pattern;
/*

        if (TextUtils.isEmpty(getEmail()))
        {
            tilEmail.setError("You haven’t entered an Email");
        }

        if (TextUtils.isEmpty(getPassword()))
        {
            tilPassword.setError("You haven’t entered a Password");
        }*/


        removeError();
        if (getEmail().isEmpty() /*|| !Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches()*/)
        {
            //email.setError("Please enter valid email address");
            tilEmail.setError("You haven’t entered a email");
            valid = false;
        }
         /* (?=.*[0-9]) a digit must occur at least once
            (?=.*[a-z]) a lower case letter must occur at least once
            (?=.*[A-Z]) an upper case letter must occur at least once
            (?=.*[@#$%^&+=]) a special character must occur at least once
            (?=\\S+$) no whitespace allowed in the entire string
            .{8,} at least 8 characters */

        String PASSWORD_PATTERN  = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,15}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        if (getPassword().isEmpty() /*|| !pattern.matcher(getPassword()).matches()*/) {
            //password.setError("Please enter valid password");
            tilPassword.setError("You haven’t entered a password");
            valid = false;
        }
        return valid;
    }

    @Override
    public void signInUser() {

        retrofit2.Call<SignInResult> call = signInAPI.signInUser(BuildConfig.GRANT_TYPE,getEmail(),getPassword(), BuildConfig.CLIENT_ID,BuildConfig.CLIENT_SECRET);
        call.enqueue(new Callback<SignInResult>() {
            @Override
            public void onResponse(retrofit2.Call<SignInResult> call, Response<SignInResult> response)
            {
                if (response.code() == 200)
                {
                    Log.i("Response One",""+response.body().getAccessToken() +"\n"+response.body().getRefreshToken());
                    mEditor.putString("ACCESS_TOKEN",response.body().getAccessToken());
                    mEditor.putString("REFRESH_TOKEN",response.body().getRefreshToken());
                    mEditor.commit();

                    signInSuccess(response.body().getAccessToken(),response.body().getExpiresIn());
                }else if(response.code() == 400){
                    createErrorDialog();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<SignInResult> call, Throwable t)
            {
                Log.e("Excpetion",""+t);
            }
        });
    }

    /*@Override
    public void onClick()
    {




    }*/

    @Override
    public void clearText() {

        email.getText().clear();
        password.getText().clear();
        email.requestFocus();
        removeError();

    }

    @Override
    public void removeError() {
        tilEmail.setError(null);
        tilPassword.setError(null);
    }

    @Override
    public void gotoDashBoard(String email,String firstName)
    {

        Intent i = new Intent(SignInActivity.this,DashBoardActivity.class);
        i.putExtra("Email",email);
        i.putExtra("fName",firstName);
        startActivity(i);


    }

    @Override
    public void gotoChangePassword() {

        startActivity(new Intent(getApplicationContext(),ChangePasswordActivity.class));

    }

    @Override
    public void showUserNotAvailable() {
        Toast.makeText(SignInActivity.this,"No such user",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUserPasswordIsWrong() {
        Toast.makeText(SignInActivity.this,"Sorry, that password isn't right",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmailAndPAsswordAreEmpty() {
        Toast.makeText(SignInActivity.this,"You haven’t entered an email or password",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void createErrorDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SignInActivity.this);

        View child = getLayoutInflater().inflate(R.layout.create_error_dialog, null);
        //ButterKnife.bind(this,child);
        cancelDialog = ButterKnife.findById(child, R.id.error_dialog_cancel);
        alertDialogBuilder.setView(child);

        alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog.show();
        cancelDialog.setOnClickListener(this);
    }

    @Override
    public void createDialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SignInActivity.this);

        View child = getLayoutInflater().inflate(R.layout.verify_email_dialog, null);
        //ButterKnife.bind(this,child);
        cancelDialog = ButterKnife.findById(child, R.id.dialog_cancel);
        alertDialogBuilder.setView(child);

        alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog.show();
        cancelDialog.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {

        boolean valid = true;
        Pattern pattern;
        String PASSWORD_PATTERN  = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,15}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);

        if (email.hasFocus()&&(getEmail().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches())) {
            //email.setError("Please enter valid email address");
            tilEmail.setError(getString(R.string.text_email_error));
            valid = false;

        }else if(email.hasFocus() && valid){tilEmail.setError(null); /*verifyExistingUser();*/}

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

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }
}

