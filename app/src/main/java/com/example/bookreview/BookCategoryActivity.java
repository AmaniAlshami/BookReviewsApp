package com.example.bookreview;


import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
import java.util.HashMap;
import java.util.List;


public class BookCategoryActivity extends AppCompatActivity {
    Toolbar toolbarHome;
    RecyclerView recyclerHome;


    List<String> imageList = new ArrayList<>();
    List<String> titleList = new ArrayList<>();

    List<HashMap<String,Object>> furnitureList = new ArrayList<>();
    CategoryAdapter categoryAdapter;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_category);



        toolbarHome = (Toolbar) findViewById(R.id.toolbar_home);
        recyclerHome = (RecyclerView) findViewById(R.id.recycler_home);

        toolbarHome.setTitle("كتب دينية");
        toolbarHome.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbarHome);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerHome.setLayoutManager(layoutManager);


        imageList.add("\"https://firebasestorage.googleapis.com/v0/b/bookreview-d9b02.appspot.com/o/book1.jpeg?alt=media&token=5f028e73-1475-4353-87ed-0f20067a4e5a\"");
        imageList.add("\"https://firebasestorage.googleapis.com/v0/b/bookreview-d9b02.appspot.com/o/book1.jpeg?alt=media&token=5f028e73-1475-4353-87ed-0f20067a4e5a\"");

        titleList.add("أقوم قيلا");
        titleList.add("أول مرة اتلذذ بالدعاء");

        for (int i = 0; i < imageList.size(); i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("bookImage", imageList.get(i));
            map.put("Booktitle", titleList.get(i));
            furnitureList.add(map);
        }
        categoryAdapter = new CategoryAdapter(getApplicationContext(), furnitureList);
        recyclerHome.setAdapter(categoryAdapter);

        //---------------------------

        ref= FirebaseDatabase.getInstance().getReference();
        DatabaseReference BookType=ref.child("Islamic");
        ref.keepSynced(true);
        BookType.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        //Book type = snap.getValue(Book.class);
                        String bookName = snap.child("bookName").getValue(String.class);
                        String bookImage = snap.child("image").getValue(String.class);
                        imageList.add(bookImage);
                        titleList.add(bookName);
                    }
//نجرب مره داخل ومره برا
                    for (int i = 0; i < imageList.size(); i++) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("bookImage", imageList.get(i));
                        map.put("Booktitle", titleList.get(i));
                        furnitureList.add(map);
                    }
                    categoryAdapter = new CategoryAdapter(getApplicationContext(), furnitureList);
                    recyclerHome.setAdapter(categoryAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

 }
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
    }



}

