package com.iat.epoints.landing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.crash.FirebaseCrash;
import com.iat.epoints.R;
import com.iat.epoints.Utils.BaseActivity;
import com.iat.epoints.login.signup.SignUpActivity;
import com.iat.epoints.root.App;
import com.iat.epoints.signIn.signIn.SignInActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LandingActivity extends BaseActivity implements LandingActivityMVP.View {

    @BindView(R.id.button_create_account_email)
    Button buttonCreateAccountEmail;

    @Inject
    LandingActivityMVP.Presenter presenter;

    @BindView(R.id.toolbar_title)
    TextView toobarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
