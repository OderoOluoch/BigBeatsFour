package com.odero.bigtwo.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.odero.bigtwo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInActivity extends AppCompatActivity  {
    FirebaseAuth mAuth;
    @BindView(R.id.goToSeeMusciFRomRegister) Button seeMusic;
    @BindView(R.id.emailInputRegisterView) EditText mRegisterUserEmail;
    @BindView(R.id.passwordInputRegisterView) EditText mRegisterUserPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //BindViews
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        seeMusic.setOnClickListener(view ->{
            createUser();
        });

        //Implemented because of the onclick interface
        //seeMusic.setOnClickListener(this);
    }

    private void createUser(){
        String email = mRegisterUserEmail.getText().toString();
        String password = mRegisterUserPassword.getText().toString();
        if(TextUtils.isEmpty(email)){
            mRegisterUserEmail.setError("Email is required");
            mRegisterUserEmail.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            mRegisterUserPassword.setError("Password is required");
            mRegisterUserPassword.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull  Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(SignInActivity.this,"Registration successful ",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SignInActivity.this,LogInActivity.class));
                    }else{
                        Toast.makeText(SignInActivity.this,"Error occurred : " + task.getException().getMessage() ,Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }


//    @Override
//    public void onClick(View v) {
//        if(v == seeMusic){
//            if( mRegisterUserName.getText().toString().length() == 0 ){
//                mRegisterUserName.setError( "Name is required!" );
//            }else if(mRegisterUserEmail.getText().toString().length() == 0){
//                mRegisterUserEmail.setError( "Email is required!" );
//            }else if(mRegisterUserPassword.getText().toString().length() == 0){
//                mRegisterUserPassword.setError( "Password is required!" );
//            }else {
//                String userName = mRegisterUserName.getText().toString();
//                Intent intent = new Intent(SignInActivity.this, SearchActivity.class);
//                intent.putExtra("userName", userName);
//                startActivity(intent);
//            }
//        }
//    }
}