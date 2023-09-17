package com.example.testcarappwithfragment.SessionManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.testcarappwithfragment.MainActivity;
import com.example.testcarappwithfragment.Model.Customer;

public class UserSessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";

    public UserSessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(Customer user){
        //save session of user whenever user is logged in
        int id = user.getCustomerId();

        editor.putInt(SESSION_KEY,id).commit();
    }

    public int getSession(){
        //return user id whose session is saved
        return sharedPreferences.getInt(SESSION_KEY, -1);
    }

    //-1 means no user(User Not logged in or User logged out)
    public void removeSession(){
        editor.putInt(SESSION_KEY,-1).commit();
    }
}
