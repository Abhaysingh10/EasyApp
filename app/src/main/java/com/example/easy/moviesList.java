package com.example.easy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.FirestoreClient;

import org.w3c.dom.Document;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class moviesList extends AppCompatActivity {

    private static final String TAG = ACCESSIBILITY_SERVICE; ;
    private RecyclerView recyclerViewMoviesList ;
    private FirebaseFirestore firebaseFirestore;
    private DocumentReference noteRef;
    private CollectionReference notebookRef ;
    private List<movieListModel> movieList ;
    private movieListAdapter movieListAdapter;
    private String movieName, description, documentID ;
    private String movieGenre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);

        movieGenre = getIntent().getExtras().get("Genre Name").toString();
        recyclerViewMoviesList = findViewById(R.id.moviesList);
        recyclerViewMoviesList.setLayoutManager(new LinearLayoutManager(this));
        movieList = new ArrayList<>();
        movieListAdapter = new movieListAdapter(movieList, getApplicationContext()) ;
        recyclerViewMoviesList.setAdapter(movieListAdapter);
        firebaseFirestore = FirebaseFirestore.getInstance();
        notebookRef = firebaseFirestore.collection(movieGenre);
      //  Toast.makeText(getApplicationContext(), movieGenre, Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences = getSharedPreferences("GenreName", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Genre", movieGenre);
        editor.apply();
        movieListModel movieListModel = new movieListModel(movieName, description, documentID) ;
        notebookRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot :task.getResult()){
                                movieList.add(new movieListModel(
                                        documentSnapshot.get("movieName").toString(),
                                        documentSnapshot.get("description").toString(),
                                        documentSnapshot.getId().toString()
                                ));
                            }
                         movieListAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}