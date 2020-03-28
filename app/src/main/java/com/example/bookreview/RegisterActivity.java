package com.example.bookreview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText userEmail,userPassword,userPAssword2,userName;
   // private ProgressBar loadingProgress;
    private Button regBtn;

     private FirebaseAuth mAuth;
     private DatabaseReference ref ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //ini views
        userEmail = findViewById(R.id.etEmail);
        userPassword = findViewById(R.id.etPassword);
        userPAssword2 = findViewById(R.id.etRePassword);
        userName = findViewById(R.id.etUsername);
        // loadingProgress = findViewById(R.id.regProgressBar);
        regBtn = findViewById(R.id.btsignup);
        // loadingProgress.setVisibility(View.INVISIBLE);


        mAuth = FirebaseAuth.getInstance();



        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //regBtn.setVisibility(View.INVISIBLE);
                //loadingProgress.setVisibility(View.VISIBLE);
                final String email = userEmail.getText().toString();
                final String password = userPassword.getText().toString();
                final String password2 = userPAssword2.getText().toString();
                final String name = userName.getText().toString();

                if( email.isEmpty() || name.isEmpty() || password.isEmpty()  || !password.equals(password2)) {

                    // something goes wrong : all fields must be filled
                    // we need to display an error message
                    showMessage("Please Verify all fields") ;
                    //regBtn.setVisibility(View.VISIBLE);
                    //loadingProgress.setVisibility(View.INVISIBLE);
                }
                else {
                    // everything is ok and all fields are filled now we can start creating user account
                    // CreateUserAccount method will try to create the user if the email is valid

                    CreateUserAccount(email,name,password);
                }
            }
        });


    }
    // to create new user

    private void CreateUserAccount(String email, final String name, String password) {


        // this method create user account with specific email and password

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // user account created successfully
                            showMessage("Account created");
                            info();
                            updateUI();
                        }
                        else
                        {
                            // account creation failed
                            showMessage("account creation failed" + task.getException().getMessage());
                            // regBtn.setVisibility(View.VISIBLE);
                            // loadingProgress.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }



    private void updateUI() {
        Intent homeActivity = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(homeActivity);
        finish();


    }

    // simple method to show toast message
    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

    }

// new user info
    private void info(){
        String name = userName.getText().toString().trim();
        String email = userEmail.getText().toString().trim();
        Info info = new Info();
        FirebaseUser user = mAuth.getCurrentUser();
        info.setName(name);
        info.setEmail(email);
        ref = FirebaseDatabase.getInstance().getReference();
        ref.child("info").child(user.getUid()).setValue(info);
    }

}

