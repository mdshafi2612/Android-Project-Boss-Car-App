package com.example.testcarappwithfragment.SessionManager;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.testcarappwithfragment.Model.Customer;
import com.example.testcarappwithfragment.Model.Wishlist;

public class WishlistSessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_wishlist";

    public WishlistSessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(Wishlist wishlist){
        //save session of user whenever user is logged in
        int id = wishlist.getId();

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
