package com.example.testcarappwithfragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testcarappwithfragment.Model.contact_us;
import com.example.testcarappwithfragment.data.DatabaseHandler;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ContactUsFragment extends Fragment {

    private EditText userName;
    private EditText userEmail;
    private EditText userMobile;
    private EditText userComment;
    private Button sendMessageBurron;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_contact_us, container, false);

        userName = root.findViewById(R.id.UserNameId);
        userEmail = root.findViewById(R.id.UserEmailId);
        userMobile = root.findViewById(R.id.UserMobileId);
        userComment = root.findViewById(R.id.Comment);
        sendMessageBurron = root.findViewById(R.id.SendCommentToContactUs);


        sendMessageBurron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = userName.getText().toString().trim();
                String email = userEmail.getText().toString().trim();
                String mobile = userMobile.getText().toString().trim();
                String comment = userComment.getText().toString().trim();

                DatabaseHandler db = new DatabaseHandler(getContext());

                if(!name.isEmpty()
                && !email.isEmpty()
                && !mobile.isEmpty()
                && !comment.isEmpty()){
                    int mobile1 = Integer.parseInt(mobile);
                    contact_us contactUs = new contact_us();
                    Date date1 = new Date();
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat DateFor1 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    String stringDate1 = DateFor1.format(date1);
                    long result = db.addtoContactUs(name, email, mobile1, comment, contactUs, stringDate1);
                    if(result != -1){
                        Snackbar.make(v, "Message is sent to the admin", Snackbar.LENGTH_SHORT).show();


                    }else{
                        Snackbar.make(getView(), "Message sent is failed!!!", Snackbar.LENGTH_LONG).show();
                    }
                    userName.setText("");
                    userEmail.setText("");
                    userMobile.setText("");
                    userComment.setText("");
                    userName.requestFocus();

                }else {
                    Toast.makeText(getContext(), "Please fill up the form", Toast.LENGTH_SHORT).show();
                    userName.requestFocus();
                }

            }
        });

        return root;
    }
}
