package com.scaleup.epoints.landing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.crash.FirebaseCrash;
import com.scaleup.epoints.R;
import com.scaleup.epoints.login.signup.SignUpActivity;
import com.scaleup.epoints.root.App;

import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LandingActivity extends AppCompatActivity implements LandingActivityMVP.View {

    @BindView(R.id.button_create_account_email)
    Button buttonCreateAccountEmail;

    @Inject
    LandingActivityMVP.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        FirebaseCrash.report(new Exception());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_action_left_chevron);
        ((App) getApplication()).getComponent().inject(this);

        ButterKnife.bind(this);

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void gotoSignUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}
