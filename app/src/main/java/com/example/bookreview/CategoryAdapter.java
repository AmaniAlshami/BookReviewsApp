package com.example.bookreview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryView> implements Filterable{



    CustomFilter customFilter;
    Context context;
    private List<Book> bookList = new ArrayList<>();
    private List<Book> filteredList = new ArrayList<>();

   public CategoryAdapter(Context context, List<Book> bookListt){
       filteredList = bookList;
       customFilter = new CustomFilter();
       this.context = context;
       this.bookList = bookListt;
    }


    @NonNull
    @Override
    public CategoryView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_book,viewGroup,false);

        return new CategoryView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryView categoryView, final int i) {

        final Book book = bookList.get(i);
        categoryView.bookTitle.setText(book.getBookname());
        Picasso.get().load(book.getImage()).into(categoryView.bookimage);


        categoryView.linearLayout.setOnClickListener(new View.OnClickListener() {
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

        return bookList.size();
    }

    @Override
    public Filter getFilter() {
        return customFilter;
    }




    public class CustomFilter extends Filter{


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults filterResults = new FilterResults();

              List<Book> newList = bookList ;
              List<Book> resultList = new ArrayList<>();

            String searchValue = constraint.toString().toLowerCase();

           for(int i=0;i<newList.size();i++){

                Book book = newList.get(i);
                String title = book.getBookname();

                if(title.toLowerCase().contains(searchValue)){
                    resultList.add(book);
                }

            }


            filterResults.count = resultList.size();
            filterResults.values = resultList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            filteredList = (List<Book>) results.values;
            notifyDataSetChanged();

        }
    }






}