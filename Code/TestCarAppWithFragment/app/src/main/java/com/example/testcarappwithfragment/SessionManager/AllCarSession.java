package com.example.testcarappwithfragment.SessionManager;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testcarappwithfragment.Model.Car;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AllCarSession {
//    SharedPreferences sharedPreferences;
//    SharedPreferences.Editor editor;
//    String SHARED_PREF_NAME = "session";
//    List<String> SESSION_KEY = Arrays.asList("session_car_list1", "session_car_list2", "session_car_list3", "se");
//
//    public AllCarSession(Context context){
//        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
//        editor = sharedPreferences.edit();
//    }
//
//    public void saveSession(Car car){
//        //save session of user whenever user is logged in
//        int id = car.getId();
//
//        editor.putInt(SESSION_KEY,id).commit();
//    }
//
//    public int getSession(){
//        //return user id whose session is saved
//        return sharedPreferences.getInt(SESSION_KEY, -1);
//    }
//
//    //-1 means no user(User Not logged in or User logged out)
//    public void removeSession(){
//        editor.putInt(SESSION_KEY,-1).commit();
//    }
}
