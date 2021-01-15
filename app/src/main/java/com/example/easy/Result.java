package com.example.easy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.StreetViewPanoramaOptions;

import pl.droidsonroids.gif.GifImageView;

public class Result extends AppCompatActivity {

    private TextView result;
    private GifImageView celeb1;
    private GifImageView sad;
    private ImageView trophy;
    private AppCompatButton high_score_banner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        celeb1 = findViewById(R.id.celebration);
        sad = findViewById(R.id.sad);
        result = findViewById(R.id.score);
        high_score_banner = findViewById(R.id.HIGH_SCORE_BANNER);
        trophy = findViewById(R.id.trophy);
        int res = (int) getIntent().getExtras().get("currentScore");
        SharedPreferences sharedPreferences = getSharedPreferences("scoreCardFile", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int temp = sharedPreferences.getInt("score", 0);
        result.setText(String.valueOf(res));

        celeb1.setVisibility(View.INVISIBLE);
        high_score_banner.setVisibility(View.INVISIBLE);
        trophy.setVisibility(View.INVISIBLE);
        sad.setVisibility(View.INVISIBLE);


        if (res <= 2) {
            sad.setVisibility(View.VISIBLE);
        }
        else if (res > temp){
            high_score_banner.setVisibility(View.VISIBLE);
            celeb1.setVisibility(View.VISIBLE);
            trophy.setVisibility(View.VISIBLE);
            editor.putInt("highscore", res);
            editor.apply();
        }
        else{
            celeb1.setVisibility(View.VISIBLE);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}