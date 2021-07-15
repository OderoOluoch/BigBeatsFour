package com.odero.bigtwo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.odero.bigtwo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends  AppCompatActivity implements View.OnClickListener {


    //Using BIndView from ButterKnife.
    @BindView(R.id.goToLoginScreen)
    Button login;
    @BindView(R.id.goToSigInScreen) Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //BindViews
        ButterKnife.bind(this);

        //Implemented because of the onclick interface
        login.setOnClickListener(this);
        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //Helps us navigate between the log in and the register button.
        if(v == login){
            Intent intent = new Intent(WelcomeActivity.this, LogInActivity.class);
            startActivity(intent);
        }else if(v == register){
            Intent intent = new Intent(WelcomeActivity.this, SignInActivity.class);
            startActivity(intent);
        }

    }
}