package com.example.easy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;

import org.w3c.dom.Text;

import java.util.ConcurrentModificationException;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.holder> {

    String[] category;
    public Context context ;
    public categoryAdapter(String[] category, Context context) {

         this.category = category ;
         this.context = context ;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext()) ;
        View view = layoutInflater.inflate(R.layout.fragment_cat1,parent,false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {


        holder.description.setText(category[position]);
        holder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, moviesList.class );
                intent.putExtra("Genre Name", holder.description.getText().toString()) ;
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return category.length ;
    }

    public static class holder extends RecyclerView.ViewHolder {

        CircularImageView poster;
        TextView description ;
        RelativeLayout posterLayout ;
        
        public holder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.poster);
            description = itemView.findViewById(R.id.description);
            posterLayout = itemView.findViewById(R.id.posterLayout);

        }




    }




}
