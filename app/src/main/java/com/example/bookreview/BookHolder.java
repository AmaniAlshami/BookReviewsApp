package com.example.bookreview;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


public class BookHolder extends RecyclerView.ViewHolder {


    TextView book_title;
    ImageView book_img;
    LinearLayout book_layout;
    Context context;
    View mview ;


    public BookHolder(View v) {
        super(v);
        mview = v ;
//        id=(Button) itemView.findViewById(R.id.lockerId);
        book_title = itemView.findViewById(R.id.eventtitle);
        book_img = itemView.findViewById(R.id.eventpicture);
        book_layout = itemView.findViewById(R.id.eventparent);



    }

    public void setBook(Book book) {

        book_title.setText(book.getBookname());

    }
}

