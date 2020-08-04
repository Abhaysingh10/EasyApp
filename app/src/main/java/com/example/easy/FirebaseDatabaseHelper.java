package com.example.easy;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.SoftReference;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
     private FirebaseDatabase mDatabase;
     private DatabaseReference mReferenceQuestions;
     private List<questionList> questionLists = new ArrayList<>();

     public interface DataStatus{
         void DataIsLoaded(List<questionList> lists, List<String> keys  );
    }

    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceQuestions = mDatabase.getReference("Questions");


    }

    public void  readQuestions(final DataStatus dataStatus){
        mReferenceQuestions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                questionLists.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : snapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    questionList question = keyNode.getValue(questionList.class);
                    questionLists.add(question);
                }
                dataStatus.DataIsLoaded(questionLists,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
