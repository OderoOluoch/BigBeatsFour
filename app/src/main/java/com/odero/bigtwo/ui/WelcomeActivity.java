package com.odero.bigtwo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.odero.bigtwo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends  AppCompatActivity implements View.OnClickListener {
    FirebaseAuth mAuth;

    //Using BIndView from ButterKnife.
    @BindView(R.id.goToLoginScreen) Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mAuth = FirebaseAuth.getInstance();
        //BindViews
        ButterKnife.bind(this);

        //Implemented because of the onclick interface
        login.setOnClickListener(this);


    }




    @Override
    public void onClick(View v) {
        //Helps us navigate between the log in and the register button.
        if(v == login){
            FirebaseUser user = mAuth.getCurrentUser();
            if(user == null){
                startActivity(new Intent(WelcomeActivity.this, LogInActivity.class));
            }else{
                startActivity(new Intent(WelcomeActivity.this, SearchActivity.class));
            }
        }

    }
}