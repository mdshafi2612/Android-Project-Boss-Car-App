package com.example.testcarappwithfragment.SessionManager;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.testcarappwithfragment.Model.Admin;
import com.example.testcarappwithfragment.Model.Customer;

public class AdminSessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_admin";

    public AdminSessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(Admin admin){
        //save session of user whenever user is logged in
        String email = admin.getAdminEmail();

        editor.putString(SESSION_KEY,email).commit();
    }

    /*Later we will update the Admin table to contain Admin Id as the primary key,
    * then the following method will change*/

    public String getSession(){
        //return user id whose session is saved
        return sharedPreferences.getString(SESSION_KEY, "Admin session is destroyed");
    }

    public void removeSession(){
        editor.putInt(SESSION_KEY,-1).commit();
    }
}
