package com.iat.epoints.thankyou;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.crash.FirebaseCrash;
import com.iat.epoints.R;
import com.iat.epoints.Utils.BaseActivity;
import com.iat.epoints.login.signup.SignUpActivityMVP;
import com.iat.epoints.root.App;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ThankYouActivity extends BaseActivity implements ThankYouActivityMVP.View{

    private String EMAIL;

    @Inject
    ThankYouActivityMVP.Presenter presenter;
    @Inject
    SignUpActivityMVP.Presenter presenter_signUp;

    @BindView(R.id.textView_email)
    TextView textViewEmail;
    @BindView(R.id.button_check_email)
    Button buttonCheckEmail;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);
        FirebaseCrash.report(new Exception());
        ((App) getApplication()).getComponent().inject(this);

        ButterKnife.bind(this);
        EMAIL = getIntent().getStringExtra("email");
        textViewEmail.setText(EMAIL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setNavigationIcon(R.drawable.ic_action_left_chevron);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        buttonCheckEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                presenter.checkEmailButtonClicked();

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_thank_you;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
    }

    @Override
    public void gotoEmail() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_APP_EMAIL);
        startActivity(intent);
    }
}
