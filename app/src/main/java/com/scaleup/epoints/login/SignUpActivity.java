package com.scaleup.epoints.login;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.scaleup.epoints.R;

/**
 * Created by Abhijit on 13-11-2017.
 */

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_action_left_chevron);
        mTitle.setText(getString(R.string.text_createAccount));

        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}
