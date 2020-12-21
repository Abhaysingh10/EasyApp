package com.example.easy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
    private RecyclerView recyclerView ;
    private FirebaseFirestore firebaseFirestore;
    private DocumentReference noteRef;
    private CollectionReference notebookRef ;
    private List<movieListModel> movieList ;
    private movieListAdapter movieListAdapter;
    private String movieName, description ;
    private String movieGenre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);

        movieGenre = getIntent().getExtras().get("Genre Name").toString();
        recyclerView = findViewById(R.id.moviesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieList = new ArrayList<>();
        movieListAdapter = new movieListAdapter(movieList) ;
        recyclerView.setAdapter(movieListAdapter);
        firebaseFirestore = FirebaseFirestore.getInstance();
        notebookRef = firebaseFirestore.collection(movieGenre);
        HashMap note = new HashMap();

        movieListModel movieListModel = new movieListModel(movieName, description) ;




        notebookRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot :task.getResult()){
                                movieList.add(new movieListModel(
                                   documentSnapshot.get("movieName").toString(),
                                    documentSnapshot.get("description").toString()
                                ));
                            }

                         movieListAdapter.notifyDataSetChanged();

                        }

                    }
                });


//        notebookRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                    String[] movieDocument ;
//                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
//                        movieListModel movieListModel1 = documentSnapshot.toObject(movieListModel.class);
//
//                        String movieName = documentSnapshot.getString("movieName");
//                        String descriptiom = documentSnapshot.getString("description");
//
//
//                    }
//                        recyclerView.setAdapter(new movieListAdapter());
//            }
//        });



    }
}