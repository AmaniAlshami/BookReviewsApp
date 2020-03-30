package com.example.bookreview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;



import java.util.ArrayList;
import java.util.List;

public class BookCommentActivity extends AppCompatActivity {

    TextView bookname, bookauthor, bookdesc, del;
    ImageView image;
    Button btnAddComment;
    String bookTitle;
    EditText editTextComment;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    RecyclerView RvComment;
    CommentAdapter commentAdapter;
    List<Comment> listComment;
    Comment comment;
    FirebaseUser user;
    String uid,  cid , name;
    ProgressBar progressBar4 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_comment);

        RvComment = findViewById(R.id.rv_comment);
        listComment = new ArrayList<>();

        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),1);
        RvComment.setLayoutManager(layoutManager);



        del = findViewById(R.id.btndelete);
        bookname = findViewById(R.id.tx);
        bookauthor = findViewById(R.id.post_detail_date_name);
        bookdesc = findViewById(R.id.post_detail_desc);
        image = findViewById(R.id.im);
        btnAddComment = findViewById(R.id.post_detail_add_comment_btn);
        editTextComment = findViewById(R.id.post_detail_comment);
        progressBar4 = findViewById(R.id.progressBar4);



        String bookImage = getIntent().getStringExtra("bookImage");
        Picasso.get().load(bookImage).into(image);
        bookTitle = getIntent().getStringExtra("booktitle");
        bookname.setText(bookTitle);
        String author = getIntent().getStringExtra("author");
        bookauthor.setText(author);
        String desc = getIntent().getStringExtra("desc");
        bookdesc.setText(desc);



        firebaseDatabase = FirebaseDatabase.getInstance();




        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAddComment.setVisibility(View.INVISIBLE);
                progressBar4.setVisibility(View.VISIBLE);
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseUser = firebaseAuth.getCurrentUser();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    uid = firebaseUser.getUid();
                    btnAddComment.setVisibility(View.INVISIBLE);
                DatabaseReference commentReference = FirebaseDatabase.getInstance().getReference().child("Comment").child(bookTitle).push();

                String comment_content = editTextComment.getText().toString();
                String uid = firebaseUser.getUid();
                String uname = displayName(uid) ;
                cid = commentReference.getKey();

                comment = new Comment(comment_content, uid, uname, cid);
                commentReference.setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        showMessage("شكرًا لإنارتك");
                        editTextComment.setText("");
                        btnAddComment.setVisibility(View.VISIBLE);
                        progressBar4.setVisibility(View.INVISIBLE);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showMessage("عذٌرا هناك مشكلة حاول مرة أخرى");
                        btnAddComment.setVisibility(View.VISIBLE);
                        progressBar4.setVisibility(View.INVISIBLE);

                    }
                });

                }

                else {
                    showMessage("سجل معنا لتشاركنا إضاءتك");
                    btnAddComment.setVisibility(View.VISIBLE);
                    progressBar4.setVisibility(View.INVISIBLE);
                }


            }
        });
        iniRvComment();


    }

    private void iniRvComment() {

        DatabaseReference commentRef = firebaseDatabase.getInstance().getReference().child("Comment").child(bookTitle);
        commentRef.keepSynced(true);
        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    listComment = new ArrayList<>();
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        // Log.e("Count ", "" + snap.getChildrenCount());
                        Comment comment = snap.getValue(Comment.class);
                        listComment.add(comment);


                    }

                    commentAdapter = new CommentAdapter(getApplicationContext(), listComment,bookTitle,uid);
                    RvComment.setAdapter(commentAdapter);



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private String displayName(String uid1) {
        DatabaseReference ref;
        ref = firebaseDatabase.getReference();
        ref.keepSynced(true);
        DatabaseReference useRef = ref.child("info").child(uid1);
        useRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

              name = dataSnapshot.child("name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        return name;

    }

    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

    }



}









