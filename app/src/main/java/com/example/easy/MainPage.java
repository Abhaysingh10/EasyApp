package com.example.easy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainPage extends AppCompatActivity {


    private ChipNavigationBar chipNavigationBar ;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        chipNavigationBar  = findViewById(R.id.chipNavigationBar);
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null ;
                switch (i){
                    case R.id.home :
                        fragment = new RssFeedHome() ;
                        break;
                    case R.id.category :
                        fragment = new MoviesCategory();
                        break;
                    case R.id.profile:
                        fragment = new Profile();
                        break;
                }

                if (fragment!=null){
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.workspace, fragment)
                            .commit();
                }
                else{

                }
            }
        });

    }
}