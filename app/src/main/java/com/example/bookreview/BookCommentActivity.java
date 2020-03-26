package com.example.bookreview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class BookCommentActivity extends AppCompatActivity {

    TextView bookname , bookauthor ,bookdesc ;
    ImageView image ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_comment);

        bookname = findViewById(R.id.tx);
        bookauthor = findViewById(R.id.post_detail_date_name);
        bookdesc = findViewById(R.id.post_detail_desc);
        image = findViewById(R.id.im);

        String bookImage = getIntent().getStringExtra("bookImage") ;
        Picasso.get().load(bookImage).into(image);
        String bookTitle =getIntent().getStringExtra("booktitle");
        bookname.setText(bookTitle);
        String author =getIntent().getStringExtra("author");
        bookauthor.setText(author);
        String desc = getIntent().getStringExtra("desc");
        bookdesc.setText(desc);


    }

}















    /*
    private void getIncomingIntent(){
        Bundle bundle = getIntent().getExtras();
        img =(ImageView)findViewById( R.id.im );
        name = (TextView)findViewById(R.id.tx);
        if(getIntent().hasExtra("booktitle")){
            String title = getIntent().getStringExtra("booktitle");
            int res = bundle.getInt("bookimage");
            name.setText(title);
            img.setImageResource( res );


        }*/


