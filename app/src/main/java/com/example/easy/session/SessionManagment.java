package com.example.easy.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.firestore.auth.User;

public class SessionManagment {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user" ;

    public SessionManagment(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(UserSessionModel userSessionModel){
        // save session of user whenever he is logged in
        int id = userSessionModel.getID();

        editor.putInt(SESSION_KEY, id).commit();


    }

    public int getSession(){
        return sharedPreferences.getInt(SESSION_KEY, -1);
    }

}
