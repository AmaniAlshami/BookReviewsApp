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
import android.widget.ProgressBar;
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
    TextView welcom , repassword  ;
    EditText Username,useremail;
    ProgressBar progressBar3 ;
    Button save , btLogin;
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
        edit = findViewById(R.id.edit);
        Username = findViewById(R.id.etUsername);
        useremail = findViewById(R.id.email);
        btLogin = findViewById(R.id.btLogin);
        save = findViewById(R.id.save);
        progressBar3 = findViewById(R.id.progressBar3);
        repassword.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                Intent a = new Intent(ProfileActivity.this, RePassActivity.class);
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
                Username.setFocusableInTouchMode(true);
                useremail.setEnabled(true);
                useremail.setFocusableInTouchMode(true);
                save.setEnabled(true);
                save.setFocusableInTouchMode(true);

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
                welcom.setText("أهلًا "+name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // name


                if(!Username.getText().toString().equals(name)&&!Username.getText().toString().equals("")) {
                    Info info = new Info();
                    name = Username.getText().toString();
                    info.setName(name);
                    info.setEmail(email);
                    ref = FirebaseDatabase.getInstance().getReference();
                    ref.child("info").child(uid).setValue(info);
                    Toast.makeText(getApplicationContext(), "تم تحديث الاسم", Toast.LENGTH_SHORT).show();
                } else if (Username.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "لايمكن تركه فارغًا", Toast.LENGTH_SHORT).show();

            }

                //email

                FirebaseUser user = auth.getCurrentUser();

                if (user != null && !useremail.getText().toString().equals("")&&!useremail.getText().toString().equals(email)) {
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
                                        Toast.makeText(getApplicationContext(), "تم تحديث البريد الإلكتروني ، سجل من جديد", Toast.LENGTH_LONG).show();
                                        signOut();
                                        finish();
                                        Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "نعتذر لم تتم العملية حاول مرة أخرى", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                } else if (useremail.getText().toString().trim().equals("")) {
                    useremail.setError("Enter email");
                }
            }
        });



        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar3.setVisibility(View.VISIBLE);
                btLogin.setVisibility(View.INVISIBLE);
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext() , HomeActivity.class));
                finish();
                Toast.makeText(getApplicationContext(), "تم تسجيل الخروج ، ننتظر عودتك قريبًا", Toast.LENGTH_SHORT).show();

            }
        });



    }
    public void signOut() {auth.signOut();}

    public void moveToHome(View v){

        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);

    }
}
