package com.example.login;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileActivity extends AppCompatActivity {
    private Button btnChangeEmail;
    private EditText newEmail;
    private Button btnChName , delete , chPass;
    private TextView nameview ;
    private EditText newName , newPass ;
    private String email , name ;
    private FirebaseAuth auth;
    DatabaseReference ref;
    FirebaseUser user;
    String uid;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        nameview = findViewById(R.id.tvUsername);
        btnChName = findViewById(R.id.btnChName);
        newName = findViewById(R.id.edNewName);
        newEmail = (EditText) findViewById(R.id.editTextemail);
        btnChangeEmail = (Button) findViewById(R.id.buttonsend);
        newPass = findViewById(R.id.etnewpass);
        delete = findViewById(R.id.btndel);
        chPass = findViewById(R.id.btChPass);

        auth = FirebaseAuth.getInstance();

        user=FirebaseAuth.getInstance().getCurrentUser();
        uid=user.getUid();

        ref = FirebaseDatabase.getInstance().getReference();




        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                name = dataSnapshot.child("info").child(uid).child("name").getValue().toString();
                email = dataSnapshot.child("info").child(uid).child("email").getValue().toString();

                nameview.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnChName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = newName.getText().toString();
                Info info = new Info();
                info.setName(name);
                info.setEmail(email);
                ref = FirebaseDatabase.getInstance().getReference();
                ref.child("info").child(uid).setValue(info);
                Toast.makeText(getApplicationContext(), "Name Updated", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getApplicationContext() , ProfileActivity.class));
            }
        });

        btnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = auth.getCurrentUser();

                if (user != null && !newEmail.getText().toString().equals("")) {
                    user.updateEmail(newEmail.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Info info = new Info();
                                        info.setName(name);
                                        info.setEmail(newEmail.getText().toString());
                                        ref = FirebaseDatabase.getInstance().getReference();
                                        ref.child("info").child(uid).setValue(info);
                                        Toast.makeText(getApplicationContext(), "Email address is updated. Please sign in with new email id!", Toast.LENGTH_LONG).show();
                                        signOut();
                                        Intent i = new Intent(getApplicationContext(),loginnActivity.class);
                                        startActivity(i);
                                        //progressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Failed to update email!", Toast.LENGTH_LONG).show();
                                        // progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                } else if (newEmail.getText().toString().trim().equals("")) {
                    newEmail.setError("Enter email");
                    // progressBar.setVisibility(View.GONE);
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //progressBar.setVisibility(View.VISIBLE);
                FirebaseUser user = auth.getCurrentUser();
                if (user != null) {
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        finish();
                                        Toast.makeText(getApplicationContext(), "Your profile is deleted:( Create a account now!", Toast.LENGTH_SHORT).show();
                                        ref.child("info").child(uid).removeValue();
                                        startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                                        finish();

                                        //  progressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Failed to delete your account!", Toast.LENGTH_SHORT).show();
                                        //  progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                }
            }
        });

        chPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //progressBar.setVisibility(View.VISIBLE);
                if (user != null && !newPass.getText().toString().trim().equals("")) {
                    if (newPass.getText().toString().trim().length() < 6) {
                        newPass.setError("Password too short, enter minimum 6 characters");
                        //progressBar.setVisibility(View.GONE);
                    } else {
                        user.updatePassword(newPass.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "Password is updated, sign in with new password!", Toast.LENGTH_SHORT).show();
                                            signOut();
                                            Intent i = new Intent(getApplicationContext(),loginnActivity.class);
                                            startActivity(i);
                                            //progressBar.setVisibility(View.GONE);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Failed to update password!", Toast.LENGTH_SHORT).show();
                                           // progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                    }
                } else if (newPass.getText().toString().trim().equals("")) {
                    newPass.setError("Enter password");
                    //progressBar.setVisibility(View.GONE);
                }
            }
        });


    }
    public void signOut() {
        auth.signOut();
    }
}
