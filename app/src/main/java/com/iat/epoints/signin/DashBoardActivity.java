package com.iat.epoints.signin;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.iat.epoints.R;

/**
 * Created by Manikanta .
 */

public class DashBoardActivity extends AppCompatActivity
{
    String email,firstName;


    Toolbar toolbar;

    TextView tvName,tvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        toolbar = findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setNavigationIcon(R.drawable.ic_action_left_chevron);
        }
        tvTitle = findViewById(R.id.toolbar_title);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        tvTitle.setText("DashBoard");
        tvName = findViewById(R.id.tvName);
        email = getIntent().getExtras().getString("Email");
        firstName = getIntent().getExtras().getString("fName");
        tvName.setText("Hello "+firstName);
    }
}