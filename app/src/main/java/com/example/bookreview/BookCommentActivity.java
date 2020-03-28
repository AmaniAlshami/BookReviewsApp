package com.example.bookreview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

    TextView bookname , bookauthor ,bookdesc ;
    ImageView image ;
    Button btnAddComment ;
    String bookTitle;
    EditText editTextComment;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    RecyclerView RvComment;
    CommentAdapter commentAdapter;
    List<Comment> listComment;
    Comment comment;
    static String COMMENT_KEY = "Comment" ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_comment);


        RvComment = findViewById(R.id.rv_comment);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        RvComment.setLayoutManager(layoutManager);

        bookname = findViewById(R.id.tx);
        bookauthor = findViewById(R.id.post_detail_date_name);
        bookdesc = findViewById(R.id.post_detail_desc);
        image = findViewById(R.id.im);
        btnAddComment = findViewById(R.id.post_detail_add_comment_btn);
        editTextComment = findViewById(R.id.post_detail_comment);
        RvComment.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        String bookImage = getIntent().getStringExtra("bookImage") ;
        Picasso.get().load(bookImage).into(image);
         bookTitle =getIntent().getStringExtra("booktitle");
        bookname.setText(bookTitle);
        String author =getIntent().getStringExtra("author");
        bookauthor.setText(author);
        String desc = getIntent().getStringExtra("desc");
        bookdesc.setText(desc);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();


        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnAddComment.setVisibility(View.INVISIBLE);
                DatabaseReference commentReference = FirebaseDatabase.getInstance().getReference();

                String comment_content = editTextComment.getText().toString();
                String uid = firebaseUser.getUid();
                String uname = firebaseUser.getDisplayName();

                comment = new Comment(comment_content,uid,uname);
            commentReference.child("Comment").child(bookTitle).setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        showMessage("comment added");
                        editTextComment.setText("");
                        btnAddComment.setVisibility(View.VISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showMessage("fail to add comment : "+e.getMessage());
                    }
                });



            }
        });
        iniRvComment();
    }

    private void iniRvComment() {

        RvComment.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        listComment = new ArrayList<>();
        DatabaseReference commentRef = firebaseDatabase.getReference().child("Comment").child(bookTitle);
        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snap:dataSnapshot.getChildren()) {
                    Log.e("Count ", "" + snap.getChildrenCount());

                    Comment comment = snap.getValue(Comment.class);
                    listComment.add(comment) ;


                }

                commentAdapter = new CommentAdapter(getApplicationContext(),listComment);
                RvComment.setAdapter(commentAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

    }




}









