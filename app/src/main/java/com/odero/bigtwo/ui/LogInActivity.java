package com.odero.bigtwo.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.odero.bigtwo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogInActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    //Using BIndView from ButterKnife.
    @BindView(R.id.goToSeeMusicFromLogIn)
    Button seeMusic;
    @BindView(R.id.nameInputLoginView)
    EditText mUserName;
    @BindView(R.id.passwordInputLoginView)
    EditText mUserPassword;
    @BindView(R.id.registerInstead)
    TextView registerHereText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();

        //BindViews
        ButterKnife.bind(this);


        seeMusic.setOnClickListener(view -> {
            loginUser();
        });

        registerHereText.setOnClickListener(view -> {
            startActivity(new Intent(LogInActivity.this, SignInActivity.class));
        });

    }

    private void loginUser() {
        String email = mUserName.getText().toString();
        String password = mUserPassword.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mUserName.setError("Email is required");
            mUserName.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            mUserPassword.setError("Password is required");
            mUserPassword.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LogInActivity.this, "Registration successful ", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(LogInActivity.this, SearchActivity.class));
                    } else {
                        Toast.makeText(LogInActivity.this, "Error occurred : " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

}