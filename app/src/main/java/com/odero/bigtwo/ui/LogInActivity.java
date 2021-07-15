package com.odero.bigtwo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.odero.bigtwo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    //Using BIndView from ButterKnife.
    @BindView(R.id.goToSeeMusicFromLogIn)
    Button seeMusic;
    @BindView(R.id.nameInputLoginView)
    EditText mUserName;
    @BindView(R.id.passwordInputLoginView) EditText mUserPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //BindViews
        ButterKnife.bind(this);

        //Implemented because of the onclick interface
        seeMusic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == seeMusic){
            if( mUserName.getText().toString().length() == 0 ){
                mUserName.setError( "Name is required!" );
            }else if(mUserPassword.getText().toString().length() == 0){
                mUserPassword.setError( "Password is required!" );
            }else {
                String userName = mUserName.getText().toString();
                Intent intent = new Intent(LogInActivity.this, SearchActivity.class);
                intent.putExtra("userName", userName);
                startActivity(intent);
            }
        }
    }
}