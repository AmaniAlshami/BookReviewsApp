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
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Constants;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {


    private TextView toProfile ;
    RecyclerView booksplace;
    BookAdapetr bookAdapter;
    List<Book> booktList;
    TextView bookSelected;
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

        toProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),BookCategoryActivity.class);
                startActivity(i);
            }
        });

        // ----------------------------------------------------------------------- //

        booktList = new ArrayList<>();
        // Data from database .

        retrieveData("Book");
       /* ref = FirebaseDatabase.getInstance().getReference();
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
        });*/





        // ----------------------------------------------------------------------- //
/*
        booktList.add(
                new Book(
                        "لأنك الله",
                        R.drawable.picone)
        );

        booktList.add(
                new Book(
                        "أسرار للبقاء حيًا",
                        R.drawable.picone)
        );

        booktList.add(
                new Book(
                        "اكتشف شغفك",
                        R.drawable.picone )
        );

        booktList.add(
                new Book(
                        "الخطوة الأولى",
                        R.drawable.picone )
        );

        booktList.add(
                new Book(
                        "مع النبي",
                        R.drawable.picone )
        );*/

        booktList.add(
                new Book(
                        "شقة زبيدة","book1.ipg"));




       // Recycle part

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager
                (this, LinearLayoutManager.HORIZONTAL, false);

        booksplace.setLayoutManager(linearLayoutManager);
        booksplace.setHasFixedSize(true);

        bookAdapter = new BookAdapetr(this,booktList);
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
                        setInterpolator(new AccelerateInterpolator()).start(); }
        }, 100);


        // add animate scroll
        booksplace.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    View view = snapHelper.findSnapView(linearLayoutManager);
                    int pos = linearLayoutManager.getPosition(view);

                    RecyclerView.ViewHolder viewHolder =
                            booksplace.findViewHolderForAdapterPosition(pos);

                    final LinearLayout eventparent = viewHolder.itemView.findViewById(R.id.eventparent);
                    eventparent.animate().scaleY(1).scaleX(1).setDuration(350).
                            setInterpolator(new AccelerateInterpolator()).start();
                }
                else {

                    View view = snapHelper.findSnapView(linearLayoutManager);
                    int pos = linearLayoutManager.getPosition(view);

                    RecyclerView.ViewHolder viewHolder =
                            booksplace.findViewHolderForAdapterPosition(pos);

                    LinearLayout eventparent = viewHolder.itemView.findViewById(R.id.eventparent);
                    eventparent.animate().scaleY(0.7f).scaleX(0.7f).
                            setInterpolator(new AccelerateInterpolator()).setDuration(350).start(); }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        //---------------------------------- NEW -----------------------------------------//
       /* booksplace.addOnItemTouchListener(
                new RecyclerItemClickListener(HomeActivity.this, booksplace, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever
//                        if (MySheredPreference.getBoolean(LockersArea.this, Constants.Keys.IS_ADMIN, false)) {
//                            Toast.makeText(LockersArea.this, "Sorry you can't access this now, Soon you will be able .."
//                                    ,
//                                    Toast.LENGTH_SHORT).show();
//                            return;
//
//                        }
                        bookSelected = findViewById(R.id.bookTitleRec);

                        bookSelected.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.e("Title SP:",""+bookSelected.getText().toString());
                               // MySharedPreference.putString(HomeActivity.this, Constants.Keys.BOOK_TITLE, bookSelected.getText().toString().replaceAll("\n", "").trim());
                               // startActivity(new Intent(HomeActivity.this, BookView.class));

                            }
                        });

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

*/
    } // END onCreate()


   /* @Override
    public void onStart(){
        super.onStart();

   firebaseRecyclerAdapter.startListening();

    }*/
/*
    private List<Book> retrieveData() {

        ref = FirebaseDatabase.getInstance().getReference().child("Book");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("Count ", "wslt" + dataSnapshot.getChildrenCount());
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String bookname = ds.getValue(String.class);
                        //toProfile.setText(bookname);
                        booktList.add(new Book(bookname, R.drawable.book1));
                        booktList.add(bok);
                        Log.e("Get Data", "Get Data" + bok.getBookname());



                    }
                    //  setBook(bok);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("The read failed: ", databaseError.getMessage());
            }


        });
        return booktList;
    }*/
/*
    public void setBook(Book book) {
        //TextView num,area,availability,size,price;

//        String n = num.getText() + "\t" + locker.getId();
//        num.setText(n);
//        String ar=locker.getArea()+"";
        if (bookSelected != null) {
            Log.e("Eeeeeee", "" + book.getBookname());
            bookSelected.setText(book.getBookname());
//        author.setText(book.getAuthor());
//        desc.setText(book.getDesc());

            //set img
//        title.setText(book.getTitle());
        }
    }//end setBook()

*/



//   LinearLayout a,b,c,d,e,f;


     // to get data from data base
    private List<Book> retrieveData(String child){
        ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference bookRef = ref.child(child);

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

        return booktList ;

    }



public void move(View v){


    Intent i = new Intent(getApplicationContext(),BookCategoryActivity.class);
    //i.putExtra("islamic")// معه اي دي علشان نميز التصنيف بس
    startActivity(i);
        if ( v == a){}
        else if(v==b){


        }
        else if(v==c){

        }
        else if(v==e){

        }
        else if(v==d){

        }
        else if(v==f){

        }



}




}

