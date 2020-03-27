package com.example.bookreview;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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


    private ImageButton edit;
    TextView welcom , repassword ,delete ;
    EditText Username,useremail;
    Button save;
    private String email , name ;
    private FirebaseAuth auth;
    DatabaseReference ref;
    FirebaseUser user;
    String uid;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        repassword = findViewById(R.id.tvRepass);
        welcom = findViewById(R.id.welcom);
        delete = findViewById(R.id.delete);
        edit = findViewById(R.id.edit);
        Username = findViewById(R.id.etUsername);
        useremail = findViewById(R.id.email);
       // save = findViewById(R.id.save);

        repassword.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                Intent a = new Intent(ProfileActivity.this, RegisterActivity.class);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(welcom, "login");
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(ProfileActivity.this, pairs);
                startActivity(a, activityOptions.toBundle());

            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Username.setEnabled(true);
                useremail.setEnabled(true);
                save.setEnabled(true);
            }
        });





        //-----------------------------------------------------------//
     //   DATA FROM DB

       auth = FirebaseAuth.getInstance();

        user=FirebaseAuth.getInstance().getCurrentUser();
        uid=user.getUid();

        ref = FirebaseDatabase.getInstance().getReference();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                name = dataSnapshot.child("info").child(uid).child("name").getValue().toString();
                email = dataSnapshot.child("info").child(uid).child("email").getValue().toString();

                Username.setText(name);
                useremail.setText(email);
                welcom.setText(name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // name
                name = Username.getText().toString();
                Info info = new Info();
                info.setName(name);
                info.setEmail(email);
                ref = FirebaseDatabase.getInstance().getReference();
                ref.child("info").child(uid).setValue(info);
                Toast.makeText(getApplicationContext(), "Name Updated", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getApplicationContext() , ProfileActivity.class));


                //email

                FirebaseUser user = auth.getCurrentUser();

                if (user != null && !useremail.getText().toString().equals("")) {
                    user.updateEmail(useremail.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Info info = new Info();
                                        info.setName(name);
                                        info.setEmail(useremail.getText().toString());
                                        ref = FirebaseDatabase.getInstance().getReference();
                                        ref.child("info").child(uid).setValue(info);
                                        Toast.makeText(getApplicationContext(), "Email address is updated. Please sign in with new email id!", Toast.LENGTH_LONG).show();
                                        signOut();
                                        Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                                        startActivity(i);
                                        //progressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Failed to update email!", Toast.LENGTH_LONG).show();
                                        // progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                } else if (useremail.getText().toString().trim().equals("")) {
                    useremail.setError("Enter email");
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





    }
    public void signOut() {auth.signOut();}


}
