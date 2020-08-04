package com.example.easy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import java.util.ArrayList;

public class Questionnaire extends AppCompatActivity {

    public ListView listView;
    public TextView Question, movieName;
    Button optionOne, optionTwo, optionThree, optionFour, backBtn, nextBtn;

    String[] country = {"India", "China", "America", "Russia", "France"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        //txt = findViewById(R.id.demotxt);
        movieName = findViewById(R.id.Question);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        //listView = findViewById(R.id.list);

        //myRef.setValue("Hello World") ;
       // ArrayAdapter adapterC = new ArrayAdapter<String>(this, R.layout.list_item,R.id.textView, country);

        final ArrayList<String> list = new ArrayList();
        final ArrayAdapter adapter = new ArrayAdapter<String>(Questionnaire.this, R.layout.list_item, list);
//       listView.setAdapter(adapter);

         myRef.child("QuestionsOne").child("Option 2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                movieName.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



      /* myRef.addValueEventListener(new ValueEventListener() {
          @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    list.add(snapshot1 .getValue().toString());
                }
                adapter.notifyDataSetChanged();

             }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
            */
    }
}
  