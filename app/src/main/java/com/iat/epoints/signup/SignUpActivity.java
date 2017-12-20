package com.iat.epoints.signup;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
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
import com.iat.epoints.R;
import com.iat.epoints.Utils.BaseActivity;
import com.iat.epoints.http.api.SignUpAPI;
import com.iat.epoints.http.apimodel.ResultStatus;
import com.iat.epoints.signup.privacypolicy.PrivacyPolicyActivity;
import com.iat.epoints.root.App;
import com.iat.epoints.signup.termsconditions.TermsConditionsActivity;
import com.iat.epoints.thankyou.ThankYouActivity;

import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;
import static android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;

public class SignUpActivity extends BaseActivity implements SignUpActivityMVP.View, TextWatcher, View.OnFocusChangeListener, View.OnClickListener {

    private AlertDialog alertDialog;
    private TextView cancelDialog;
    private boolean visible = false;

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
    @BindView(R.id.toggle_button)
    TextView toggleButton;
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

    /*@BindView(R.id.dialog_cancel)
    TextView cancel;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        FirebaseCrash.report(new Exception());
        ((App) getApplication()).getComponent().inject(this);

        ButterKnife.bind(this);

        slideUp();

        privacy.setText(Html.fromHtml(getString(R.string.text_privacy)));
        clickableText();

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
        toggleButton.setOnClickListener(this);
        //privacy.setOnClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.getCurrentUser();
        email.requestFocus();
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
        Toast.makeText(this,"Signup has failed", Toast.LENGTH_SHORT).show();
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

        removeError();
        if (getEmail().isEmpty()/* || !Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches()*/) {
            //email.setError("Please enter valid email address");
            tilEmail.setError(getString(R.string.text_email_empty));
            valid = false;
        }
       /*   (?=.*[0-9]) a digit must occur at least once
            (?=.*[a-z]) a lower case letter must occur at least once
            (?=.*[A-Z]) an upper case letter must occur at least once
            (?=.*[@#$%^&+=]) a special character must occur at least once
            (?=\\S+$) no whitespace allowed in the entire string
            .{8,} at least 8 characters*/
        String PASSWORD_PATTERN  = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,15}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        if (getPassword().isEmpty() /*|| !pattern.matcher(getPassword()).matches()*/) {
            //password.setError("Please enter valid password");
            tilPassword.setError(getString(R.string.text_password_empty));
            valid = false;
        }else if(!(password.length()>=6 && password.length()<=15))
        {
            tilPassword.setErrorEnabled(true);
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
    public void verifyExistingUser() {
        // simplified call to request the news with already initialized service
        Call<ResultStatus> call = signUpAPI.alreadyRegisterUser(getEmail());
        call.enqueue(new Callback<ResultStatus>() {
            @Override
            public void onResponse(Call<ResultStatus> call, Response<ResultStatus> response) {

                if(response.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    checkOutEmail();}

                else if(response.code()==400){
                    // Toast.makeText(SignUpActivity.this, "User already registered.", Toast.LENGTH_SHORT).show();
                    clearText();
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
        email.requestFocus();
        removeError();
    }

    @Override
    public void createDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SignUpActivity.this);

        View child = getLayoutInflater().inflate(R.layout.dialog_box, null);
        //ButterKnife.bind(this,child);
        cancelDialog = ButterKnife.findById(child, R.id.dialog_cancel);
        alertDialogBuilder.setView(child);

        alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog.show();
        cancelDialog.setOnClickListener(this);

    }

    @Override
    public void clickableText() {

        ClickableSpan clickableSpanPrivacyString = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                gotoPrivacy();
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.colorgreen));
                ds.setUnderlineText(false);
            }
        };

        ClickableSpan clickableSpanTocString = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                gotoTOC();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.colorgreen));
                ds.setUnderlineText(false);
            }
        };

        makeLinks(privacy, new String[] { "Privacy Policy", "terms and conditions" }, new ClickableSpan[] {
                clickableSpanPrivacyString, clickableSpanTocString
        });
    }

    @Override
    public void makeLinks(TextView textView, String[] links, ClickableSpan[] clickableSpans) {
        SpannableString spannableString = new SpannableString(textView.getText());
        for (int i = 0; i < links.length; i++) {
            ClickableSpan clickableSpan = clickableSpans[i];
            String link = links[i];

            int startIndexOfLink = textView.getText().toString().indexOf(link);
            spannableString.setSpan(clickableSpan, startIndexOfLink, startIndexOfLink + link.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(spannableString, TextView.BufferType.SPANNABLE);
    }

    @Override
    public void gotoPrivacy() {
        Intent intent = new Intent(SignUpActivity.this, PrivacyPolicyActivity.class);
        startActivity(intent);
    }

    @Override
    public void gotoTOC() {
        Intent intent = new Intent(SignUpActivity.this, TermsConditionsActivity.class);
        startActivity(intent);
    }

    @Override
    public void removeError() {
        tilEmail.setErrorEnabled(false);
        tilEmail.setError(null);
        tilPassword.setErrorEnabled(false);
        tilPassword.setError(null);
        tilFirstName.setErrorEnabled(false);
        tilFirstName.setError(null);
        tilLastName.setErrorEnabled(false);
        tilLastName.setError(null);
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

        Pattern pattern;
        String PASSWORD_PATTERN  = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,15}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);

        if (email.hasFocus()&&!Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches()) {
            //email.setError("Please enter valid email address");
            tilEmail.setErrorEnabled(true);
            tilEmail.setError(getString(R.string.text_email_error));

        }else if(email.hasFocus()){
            tilEmail.setErrorEnabled(false);
            tilEmail.setError(null); /*verifyExistingUser();*/}

       /*   (?=.*[0-9]) a digit must occur at least once
            (?=.*[a-z]) a lower case letter must occur at least once
            (?=.*[A-Z]) an upper case letter must occur at least once
            (?=.*[@#$%^&+=]) a special character must occur at least once
            (?=\\S+$) no whitespace allowed in the entire string
            .{8,} at least 8 characters*/

        if (password.hasFocus() && !(password.length()>=6 && password.length()<=15)) {
            //password.setError("Please enter valid password");
            tilPassword.setErrorEnabled(true);
            tilPassword.setError(getString(R.string.text_password_error));
        }else if(password.hasFocus()){
            tilPassword.setErrorEnabled(false);
            tilPassword.setError(null);
        }
        if (firstName.hasFocus()&&(getFirstName().isEmpty())) {
            //firstName.setError("Please enter firstname");
            tilFirstName.setErrorEnabled(true);
            tilFirstName.setError(getString(R.string.text_firstName_error));

        }else if(firstName.hasFocus()){
            tilFirstName.setErrorEnabled(false);
            tilFirstName.setError(null);
        }
        if (lastName.hasFocus() && (getLastName().isEmpty())) {
            //lastName.setError("Please enter lastname");
            tilLastName.setErrorEnabled(true);
            tilLastName.setError(getString(R.string.text_lastName_error));
        }else if(lastName.hasFocus()){
            tilLastName.setErrorEnabled(false);
            tilLastName.setError(null);
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

           /* case R.id.textview_privacy:
                gotoPrivacy();
                break;*/
            case R.id.dialog_cancel:
                alertDialog.dismiss();
                clearText();
                break;
            case R.id.toggle_button:
                visible = !visible;
                if (visible) {
                    toggleButton.setText(getString(R.string.text_hide));
                    password.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    toggleButton.setText(getString(R.string.text_show));
                    password.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;
        }
    }
}
