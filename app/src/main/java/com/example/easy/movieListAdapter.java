package com.example.easy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class movieListAdapter extends RecyclerView.Adapter<movieListAdapter.holder> {

    List<movieListModel> listModels ;
    String movieNam,  description ;



    public movieListAdapter(List<movieListModel> listModels) {
        this.listModels = listModels;

    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_movies_layout,parent,false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

        String movieName = listModels.get(position).getMovieName();
        String description = listModels.get(position).getDescription();
        holder.setValues(movieName, description);


    }

    @Override
    public int getItemCount() {
        return listModels.size() ;
    }

    public class holder extends RecyclerView.ViewHolder{

        TextView movieName;
        TextView description;
        ConstraintLayout moviesListPoster;
        public holder(@NonNull View itemView) {
            super(itemView);

            movieName = itemView.findViewById(R.id.movieName);
            description = itemView.findViewById(R.id.description);
            moviesListPoster = itemView.findViewById(R.id.moviesListPoster);

        }

        private void setValues(String moviesName, String movieDescription){
            movieName.setText(moviesName);
            description.setText(movieDescription);
        }



    }
}
