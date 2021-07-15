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

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.goToSeeMusciFRomRegister)
    Button seeMusic;
    @BindView(R.id.nameInputRegisterView)
    EditText mRegisterUserName;
    @BindView(R.id.emailInputRegisterView) EditText mRegisterUserEmail;
    @BindView(R.id.passwordInputRegisterView) EditText mRegisterUserPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //BindViews
        ButterKnife.bind(this);

        //Implemented because of the onclick interface
        seeMusic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == seeMusic){
            if( mRegisterUserName.getText().toString().length() == 0 ){
                mRegisterUserName.setError( "Name is required!" );
            }else if(mRegisterUserEmail.getText().toString().length() == 0){
                mRegisterUserEmail.setError( "Email is required!" );
            }else if(mRegisterUserPassword.getText().toString().length() == 0){
                mRegisterUserPassword.setError( "Password is required!" );
            }else {
                String userName = mRegisterUserName.getText().toString();
                Intent intent = new Intent(SignInActivity.this, SearchActivity.class);
                intent.putExtra("userName", userName);
                startActivity(intent);
            }
        }
    }
}