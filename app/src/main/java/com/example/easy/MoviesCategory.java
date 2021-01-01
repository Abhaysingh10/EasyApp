package com.example.easy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoviesCategory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoviesCategory extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;

    public MoviesCategory() {
        // Required empty public constructor
    }

    public static MoviesCategory newInstance(String param1, String param2) {
        MoviesCategory fragment = new MoviesCategory();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies_category, container, false);
        recyclerView =view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        String[] category = {"scifi", "Thriller", "comedy", "Romantic", "Horror", "Erotic", "Biography", "Patriotic"};
        // firebaserecycleradapterforcategory firebaserecycleradapterforcategory =  new firebaserecycleradapterforcategory(options);
        recyclerView.setAdapter(new categoryAdapter(category,getContext()));
        return view ;
    }
}