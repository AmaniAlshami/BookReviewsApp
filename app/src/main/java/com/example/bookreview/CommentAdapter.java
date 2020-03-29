package com.example.bookreview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context mContext;
    private List<Comment> mData ;
    BookCommentActivity b ;
    String bookT2 ;


    public CommentAdapter(Context mContext, List<Comment> mData,String bookT) {
        this.mContext = mContext;
        this.mData = mData;
        this.bookT2 = bookT;

    }

   /* public CommentAdapter(Context mContext, String bookT) {
        this.mContext = mContext;
        this.bookT2 = bookT;
    }*/

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.row_comment,viewGroup, false);
        return new CommentViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder CommentViewHolder, int position) {
        final Comment comment = mData.get(position);
        CommentViewHolder.tv_name.setText(comment.getUname());
        CommentViewHolder.tv_content.setText(comment.getContent());

   /*     CommentViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myuid.equals(uid)){

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                    builder.setTitle("Delete");
                    builder.setMessage("Are you sure ? ");
                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                deletecomment();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name,tv_content,tv_del;

        public CommentViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.comment_username);
            tv_content = itemView.findViewById(R.id.comment_content);
            tv_del =itemView.findViewById(R.id.btndelete);

            tv_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"Comment deleted",Toast.LENGTH_LONG).show();
                    delcomment();

                }
            });

        }

        public void delcomment(){
            //Intent i = new Intent();
           // String bookT = i.getStringExtra("Title");
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Comment");
            ref.child(bookT2).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        String id = ds.getKey();
                        String content = ds.child("content").getValue(String.class);

                        if (content.equals(tv_content.getText().toString())) {
                            DatabaseReference removeRef = dataSnapshot.getRef();
                            removeRef.child(id).removeValue();
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("cancel","onCancelled",databaseError.toException());
                    Toast.makeText(mContext,databaseError.getMessage(),Toast.LENGTH_LONG);

                }
            });

        }



    }





}
