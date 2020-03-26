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
import java.util.HashMap;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryView> implements Filterable{


    List<HashMap<String,Object>> furnitureList = new ArrayList<>();
    List<HashMap<String,Object>> filteredList = new ArrayList<>();
    CustomFilter customFilter;
    Context context;

    public CategoryAdapter(Context context, List<HashMap<String, Object>> fList) {
        this.furnitureList = fList;
        filteredList = furnitureList;
        customFilter = new CustomFilter();
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_book,viewGroup,false);

        return new CategoryView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryView categoryView, final int position) {

        final HashMap<String,Object> map = filteredList.get(position);
        Picasso.get().load(String.valueOf(map.get(position))).into(categoryView.bookimage);
        //categoryView.bookimage.setText(String.valueOf(map.get("bookImage")));
        categoryView.bookTitle.setText(String.valueOf(map.get("Booktitle")));

        /*  categoryView.linearLayout.setOnClickListener( new View.OnClickListener() {

          // TO comment activity

           @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),BookCommentActivity.class);
                intent.putExtra("Booktitle",categoryView.bookTitle.getText());
                intent.putExtra("bookImage",((Integer) map.get( "bookImage" )));
                context.startActivity(intent);
            }
        } );*/



    }

    @Override
    public int getItemCount() {
        int a ;

        if(filteredList != null && !filteredList.isEmpty()) {

            a = filteredList.size();
        }
        else {

            a = 0;

        }

        return a;
    }

    @Override
    public Filter getFilter() {
        return customFilter;
    }




    public class CustomFilter extends Filter{


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults filterResults = new FilterResults();

            List<HashMap<String,Object>> newList = furnitureList;
            List<HashMap<String,Object>> resultList = new ArrayList<>();

            String searchValue = constraint.toString().toLowerCase();

            for(int i=0;i<newList.size();i++){

                HashMap<String,Object> map = newList.get(i);
                String title = String.valueOf(map.get("Title"));

                if(title.toLowerCase().contains(searchValue)){
                    resultList.add(map);
                }

            }


            filterResults.count = resultList.size();
            filterResults.values = resultList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            filteredList = (List<HashMap<String, Object>>) results.values;
            notifyDataSetChanged();

        }
    }






}