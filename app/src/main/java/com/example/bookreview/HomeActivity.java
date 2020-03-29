package com.example.bookreview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView toProfile ;
    RecyclerView booksplace;
    BookAdapetr bookAdapter ;
    List<Book> booktList= new ArrayList<>();
    Book bok ;
    LinearLayout a , b, c  ,d  ,e ,f ;



    // connect to database

    DatabaseReference ref;
    FirebaseRecyclerOptions<Book> firebaseRecyclerOptions;
    FirebaseRecyclerAdapter<Book, BookHolder> firebaseRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toProfile = findViewById(R.id.toProfile);
        booksplace = findViewById(R.id.eventsplace);
        a = findViewById(R.id.iemSlid);
        b = findViewById(R.id.iemSlid1);
        c = findViewById(R.id.iemSlid2);
        d = findViewById(R.id.iemSlid3);
        e = findViewById(R.id.iemSlid4);
        f = findViewById(R.id.iemSlid5);
        mAuth = FirebaseAuth.getInstance();

        toProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                }

            }
        });



        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    toastMessage("أهلًا بك");
                } else {
                    // User is signed out
                    //toastMessage("Successfully signed out.");
                }
                // ...
            }
        };


        // ----------------------------------------------------------------------- //

         booktList = new ArrayList<>();
        // Data from database .


        ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference bookRef = ref.child("Book");
        ref.keepSynced(true);
        bookRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                         bok=ds.getValue(Book.class);
                         booktList.add(bok);

                    }
                    bookAdapter = new BookAdapetr(getApplicationContext(), booktList);
                    booksplace.setAdapter(bookAdapter);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("The read failed: ", databaseError.getMessage());

            }
        });


        // ----------------------------------------------------------------------- //

        booktList.add(
                new Book(
                        "شقة زبيدة", "book1.ipg"));


        // Recycle part

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager
                (this, LinearLayoutManager.HORIZONTAL, false);

        booksplace.setLayoutManager(linearLayoutManager);
        booksplace.setHasFixedSize(true);

        bookAdapter = new BookAdapetr(this, booktList);
        booksplace.setAdapter(bookAdapter);

        // snapping the scroll items
        final SnapHelper snapHelper = new GravitySnapHelper(Gravity.START);
        snapHelper.attachToRecyclerView(booksplace);

        // set a timer for default item
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 1ms = 100ms
                RecyclerView.ViewHolder viewHolderDefault = booksplace.
                        findViewHolderForAdapterPosition(0);
                LinearLayout eventparentDefault = viewHolderDefault.itemView.
                        findViewById(R.id.eventparent);
                eventparentDefault.animate().scaleY(1).scaleX(1).setDuration(350).
                        setInterpolator(new AccelerateInterpolator()).start();
            }
        }, 100);


        // add animate scroll
        booksplace.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    View view = snapHelper.findSnapView(linearLayoutManager);
                    int pos = linearLayoutManager.getPosition(view);

                    RecyclerView.ViewHolder viewHolder =
                            booksplace.findViewHolderForAdapterPosition(pos);

                    final LinearLayout eventparent = viewHolder.itemView.findViewById(R.id.eventparent);
                    eventparent.animate().scaleY(1).scaleX(1).setDuration(350).
                            setInterpolator(new AccelerateInterpolator()).start();
                } else {

                    View view = snapHelper.findSnapView(linearLayoutManager);
                    int pos = linearLayoutManager.getPosition(view);

                    RecyclerView.ViewHolder viewHolder =
                            booksplace.findViewHolderForAdapterPosition(pos);

                    LinearLayout eventparent = viewHolder.itemView.findViewById(R.id.eventparent);
                    eventparent.animate().scaleY(0.7f).scaleX(0.7f).
                            setInterpolator(new AccelerateInterpolator()).setDuration(350).start();
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }


        public void move(View v){

            Intent i = new Intent(getApplicationContext(),BookCategoryActivity.class);

            if ( v == a){
                    i.putExtra("title","كتب اسلامية");
                    i.putExtra("child","ديني");
                }
                else if(v==b){
                    i.putExtra("title","كتب تطوير ذات");
                    i.putExtra("child","تطوير");

            }
                else if(v==d){
                i.putExtra("title","كتب تاريخ");
                i.putExtra("child","تاريخ");
                }
                else if(v==e){
                i.putExtra("title","كتب ريادة الأعمال ");
                i.putExtra("child","أعمال");
                }
                else if(v==f){
                i.putExtra("title","كتب إجتماعية");
                i.putExtra("child","اجتماعي");
                }
                else if(v==c){
                i.putExtra("title","قصص وروايات");
                i.putExtra("child","روايات");

                }
            startActivity(i);


        }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


}

