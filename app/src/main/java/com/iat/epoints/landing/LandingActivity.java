package com.iat.epoints.landing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.crash.FirebaseCrash;
import com.iat.epoints.BuildConfig;
import com.iat.epoints.R;
import com.iat.epoints.Utils.BaseActivity;
import com.iat.epoints.http.api.FBLoginAPI;
import com.iat.epoints.http.api.SignInAPI;
import com.iat.epoints.http.apimodel.SignInResult;
import com.iat.epoints.http.apimodel.SignInSucess;
import com.iat.epoints.login.signup.SignUpActivity;
import com.iat.epoints.root.App;
import com.iat.epoints.signIn.signIn.DashBoardActivity;
import com.iat.epoints.signIn.signIn.SignInActivity;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Callback;
import retrofit2.Response;

/*
        Created by Abajith and Modifiactions done by Manikanta
*/

public class LandingActivity extends BaseActivity implements LandingActivityMVP.View {

    @BindView(R.id.button_create_account_email)
    Button buttonCreateAccountEmail;

    @BindView(R.id.button_continue_facebook)
    Button btnFacebookContinue;

    @BindView(R.id.login_button)
    LoginButton login_button;


    @Inject
    LandingActivityMVP.Presenter presenter;

    @Inject
    FBLoginAPI fbLoginAPI;

    @BindView(R.id.toolbar_title)
    TextView toobarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_landing);
        FirebaseCrash.report(new Exception());
        ((App) getApplication()).getComponent().inject(this);

        ButterKnife.bind(this);
        toolbar.setNavigationIcon(R.drawable.ic_action_left_chevron);
        setSupportActionBar(toolbar);

        toobarTitle.setText(getString(R.string.text_signIn));
        toobarTitle.setGravity(View.TEXT_ALIGNMENT_VIEW_END);


        buttonCreateAccountEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.createAccountEmailButtonClicked();

            }
        });

        btnFacebookContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.facebookLoginClicked();
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toobarTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                presenter.signInButtonClicked();
            }
        });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_landing;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
//        getInfoFromFB(Profile.getCurrentProfile());
    }

    @Override
    public void gotoSignUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    public void gotoSignIn() {
        Intent signInIntent = new Intent(this, SignInActivity.class);
        startActivity(signInIntent);
    }

    @Override
    public void gotoFBLogin()
    {
        login_button.performClick();
        login_button.setReadPermissions(Arrays.asList("public_profile", "email"));
        Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_SHORT).show();
        mCallbackManager = CallbackManager.Factory.create();


        // Callback registration
        login_button.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                if (loginResult !=null)
                {
                    String accessToken= loginResult.getAccessToken().getToken();
                    String userID = loginResult.getAccessToken().getUserId();

                    retrofit2.Call<SignInResult> call = fbLoginAPI.signInFBUser(BuildConfig.FB_GRANT_TYPE,accessToken,userID, BuildConfig.CLIENT_ID,BuildConfig.CLIENT_SECRET);
                    call.enqueue(new Callback<SignInResult>() {
                        @Override
                        public void onResponse(retrofit2.Call<SignInResult> call, Response<SignInResult> response)
                        {
                            if (response.code() == 200)
                            {
                                Log.i("Response One",""+response.body().getAccessToken() +"\n"+response.body().getRefreshToken());
                                signInSuccess(response.body().getAccessToken());
                            }
                        }

                        @Override
                        public void onFailure(retrofit2.Call<SignInResult> call, Throwable t)
                        {
                            Log.e("Excpetion",""+t);
                        }
                    });




                    Log.i("Access Token ::",""+accessToken+"\n"+userID);

                }
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    @Override
    public void signInSuccess(String token) {

        retrofit2.Call<SignInSucess> call = fbLoginAPI.signInUserSucess("Bearer "+token);
        call.enqueue(new Callback<SignInSucess>() {
            @Override
            public void onResponse(retrofit2.Call<SignInSucess> call, Response<SignInSucess> response)
            {

                if (response.isSuccessful())
                {
                    boolean verifiedVal = response.body().getVerified();

                    if (verifiedVal == true)
                    {
                        String email = response.body().getEmail();
                        String firstName = response.body().getFirstName();
                        gotoDashBoard(email,firstName);

                    }
                }
                else if (response.code() == 202)
                {
                    Log.i("Res Password",""+response.body());
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
    public void gotoDashBoard(String email, String fName)
    {
        Intent i = new Intent(LandingActivity.this,DashBoardActivity.class);
        i.putExtra("Email",email);
        i.putExtra("fName",fName);
        startActivity(i);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
