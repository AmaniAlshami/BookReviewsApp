package com.example.bookreview;


import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class BookCategoryActivity extends AppCompatActivity {
    Toolbar toolbarHome;
    RecyclerView recyclerHome;



    List<Book> booktList= new ArrayList<>();

    CategoryAdapter categoryAdapter;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_category);



        toolbarHome = (Toolbar) findViewById(R.id.toolbar_home);
        recyclerHome = (RecyclerView) findViewById(R.id.recycler_home);

        String title = getIntent().getStringExtra("title");
        toolbarHome.setTitle(title);
        String child = getIntent().getStringExtra("child");



        toolbarHome.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbarHome);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerHome.setLayoutManager(layoutManager);



        ref= FirebaseDatabase.getInstance().getReference();
        DatabaseReference BookType=ref.child(child);
        ref.keepSynced(true);
        BookType.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {

                       Book bok=snap.getValue(Book.class);
                        booktList.add(bok);


                    }
                    categoryAdapter = new CategoryAdapter(getApplicationContext(), booktList);
                    recyclerHome.setAdapter(categoryAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

 }/*
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home_menu,menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                categoryAdapter.getFilter().filter(s);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }*/



}

