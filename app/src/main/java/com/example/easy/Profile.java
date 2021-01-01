package com.example.easy;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.mikhaellopez.circularimageview.CircularImageView;


public class Profile extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private FirebaseAuth firebaseAuth;
    private String userNameAuth ;
    private TextView usernametxt, emailTxt ;
    private CircularImageView circularImageView ;
    private ImageView logoutImageView ;
    private MaterialCardView cardEmail, cardScore, cardLogout, cardSuggestion ;

    public Profile() {
        // Required empty public constructor
    }
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
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
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        usernametxt = view.findViewById(R.id.userName);
        circularImageView = view.findViewById(R.id.profileImage);
        cardEmail = view.findViewById(R.id.emailCard);
        emailTxt = view.findViewById(R.id.emailtxt);
        cardScore = view.findViewById(R.id.scoreCard);
        cardLogout = view.findViewById(R.id.logoutCard);
        cardSuggestion = view.findViewById(R.id.suggestionCard);
        firebaseAuth = FirebaseAuth.getInstance();
        userNameAuth = firebaseAuth.getCurrentUser().getDisplayName();

        Glide.with(getContext())
                .load(firebaseAuth.getCurrentUser().getPhotoUrl())
                .into(circularImageView);
        if(userNameAuth == null){
            Toast.makeText(getContext(), "User Name and information is not available", Toast.LENGTH_LONG).show();
        }else{
            usernametxt.setText(userNameAuth);
            emailTxt.setText(firebaseAuth.getCurrentUser().getEmail());
        }

        cardLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });




        return view ;
    }
}