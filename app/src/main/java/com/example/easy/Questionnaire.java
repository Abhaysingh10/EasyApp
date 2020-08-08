package com.example.easy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import java.util.ArrayList;

public class Questionnaire extends AppCompatActivity {

    public ListView listView;
    public TextView Question, question;
    Node node ;
    int branchAddress = 1 ;
    Button optionOne, optionTwo, optionThree, optionFour, backBtn, nextBtn;

    String[] country = {"India", "China", "America", "Russia", "France"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        //txt = findViewById(R.id.demotxt);
        question = findViewById(R.id.Question);
        optionOne = findViewById(R.id.optionOne);
        optionTwo = findViewById(R.id.optionTwo);
        optionThree = findViewById(R.id.optionThree);
        optionFour = findViewById(R.id.optionFour);
        backBtn = findViewById(R.id.backButton);
        nextBtn = findViewById(R.id.nextButton);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(String.valueOf(branchAddress));
        //listView = findViewById(R.id.list);

        //myRef.setValue("Hello World") ;
       // ArrayAdapter adapterC = new ArrayAdapter<String>(this, R.layout.list_item,R.id.textView, country);

        if(optionOne.isSelected());
        final ArrayList<String> list = new ArrayList();
        final ArrayAdapter adapter = new ArrayAdapter<String>(Questionnaire.this, R.layout.list_item, list);
//       listView.setAdapter(adapter);




            myRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    node = snapshot.getValue(Node.class);
                    question.setText(node.getQuestion());
                    optionOne.setText(node.getOptionOne());
                    optionTwo.setText(node.getOptionTwo());
                    optionThree.setText(node.getOptionThree());
                    optionFour.setText(node.getOptionFour());

                 nextBtn.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                        branchAddress++ ;
                        next(String.valueOf(branchAddress));
                     }
                 });
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
   /*     myRef.child("0").child("question").addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                question.setText(snapshot.getValue().toString());
                optionOne.setText(snapshot.getChildren().iterator().next().child("Option 1" +
                        "").toString());

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });    */

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
        });            */
    }

    void next(String branchaddress){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(branchaddress);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                node = snapshot.getValue(Node.class);
                question.setText(node.getQuestion());
                optionOne.setText(node.getOptionOne());
                optionTwo.setText(node.getOptionTwo());
                optionThree.setText(node.getOptionThree());
                optionFour.setText(node.getOptionFour());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
  