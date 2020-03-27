package com.example.bookreview;

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

public class LoginActivity extends AppCompatActivity {

    private EditText userMail, userPassword;
    private Button btnLogin;
    private TextView signUp;
    // private ProgressBar loginProgress;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


       auth = FirebaseAuth.getInstance();

        initializeUI();
        //HomeActivity = new Intent(this,com.example.aws.blogapp.Activities.Home.class);

        signUp = findViewById(R.id.tvNewuser);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent registerActivity = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(registerActivity);
                finish();


            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });
    }


    private void loginUserAccount() {
        //loginProgress.setVisibility(View.VISIBLE);

        String email, password;
        email = userMail.getText().toString();
        password = userPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                            // btnLogin.setVisibility(View.INVISIBLE);
                            //   loginProgress.setVisibility(View.VISIBLE);


                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();
                            // btnLogin.setVisibility(View.INVISIBLE);
                            // loginProgress.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }


    private void initializeUI() {
        userMail = findViewById(R.id.etUsername);
        userPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btLogin);
        //loginProgress = findViewById(R.id.login_progress);
    }
}

