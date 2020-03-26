package com.example.bookreview;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryView extends RecyclerView.ViewHolder{

    ImageView bookimage;
    TextView bookTitle;
    LinearLayout linearLayout;

        public CategoryView(@NonNull View itemView) {
        super(itemView);

            bookimage = (ImageView)itemView.findViewById(R.id.image_furniture);
            bookTitle = (TextView)itemView.findViewById(R.id.text_title);
            linearLayout = itemView.findViewById(R.id.eventparent );
        }

}
