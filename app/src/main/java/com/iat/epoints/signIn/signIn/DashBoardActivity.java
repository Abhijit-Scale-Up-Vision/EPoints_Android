package com.iat.epoints.signIn.signIn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.iat.epoints.R;
import com.iat.epoints.Utils.BaseActivity;

/**
 * Created by Manikanta .
 */

public class DashBoardActivity extends AppCompatActivity
{
    String email,firstName;

    TextView tvName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tvName = findViewById(R.id.tvName);
        email = getIntent().getExtras().getString("Email");
        firstName = getIntent().getExtras().getString("fName");
        tvName.setText("Hello "+firstName+"\n This is My Email ID"+email);
    }
}