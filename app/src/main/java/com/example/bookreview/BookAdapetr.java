package com.example.bookreview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookAdapetr extends RecyclerView.Adapter<BookHolder> {


    Context context;
    List<Book> bookList;

    public BookAdapetr(Context context, List<Book> bookList){
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_event,viewGroup, false);
        return new BookHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder BookHolder, int i) {

        final Book book = bookList.get(i);
        BookHolder.book_title.setText(book.getBookname());
        Picasso.get().load(book.getImage()).into(BookHolder.book_img);
        /*
        BookHolder.book_img.setImageDrawable(context.getResources().
                getDrawable(book.getImage()));*/


        BookHolder.book_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookComment = new Intent(context, BookCommentActivity.class);
                bookComment.putExtra("booktitle", book.getBookname());
                bookComment.putExtra("bookImage", book.getImage());
                bookComment.putExtra("author", book.getAuthor());
                bookComment.putExtra("desc", book.getDesc());
                context.startActivity(bookComment);
            }
        });

    }

    @Override
    public int getItemCount() {
      /*  int a ;

        if(bookList != null && !bookList.isEmpty()) {

            a = bookList.size();
        }
        else {

            a = 0;

        }

        return a;*/
      return bookList.size();
    }



}
