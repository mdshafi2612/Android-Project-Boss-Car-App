package com.example.testcarappwithfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testcarappwithfragment.Model.Customer;
import com.example.testcarappwithfragment.SessionManager.UserSessionManagement;
import com.example.testcarappwithfragment.data.DatabaseHandler;
import com.google.android.material.snackbar.Snackbar;

public class ResetPasswordFragment extends Fragment {

    private TextView username;
    private EditText password;
    private EditText retypePassword;
    private Button confirmButton;
    DatabaseHandler db;
    Customer customer;
    private String username1;

    public ResetPasswordFragment() {
//        Required on public constructor for data passing
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.reset_password_fragment, container, false);

        username = root.findViewById(R.id.usernameTextViewId);
        password = root.findViewById(R.id.PasswordEditText);
        retypePassword = root.findViewById(R.id.RetypePasswordEditText);
        confirmButton = root.findViewById(R.id.ConfirmButtonId);

        db = new DatabaseHandler(getContext());
        Bundle bundle = this.getArguments();
        String typedUsername = bundle.getString("usernameKey");
        customer = db.getCustomerBasedOnUsername(typedUsername);
        Log.d("Reset Fragment", "onCreateView: "+customer.getCustomerName());

//        UserSessionManagement userName = new UserSessionManagement(getActivity());
//        int loggedUserID = userName.getSession();
//
//        Customer customer = db.getCustomerBasedOnId(loggedUserID);
//
        username.setText(customer.getCustomerName());

        /*Add a listener with the confirm button*/
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = password.getText().toString().trim();
                String repass = retypePassword.getText().toString().trim();

                if(pass.isEmpty() && repass.isEmpty()){
                    Snackbar.make(view, "Please enter the all the fields!!!", Snackbar.LENGTH_LONG).show();
                    password.requestFocus(); //set the cursor to the password EditText
                }else{
                    if(pass.equals(repass)){
                        //Here we reset the password of the logged user
                        long result = db.resetPassword(customer, pass);
                        customer = db.getCustomerBasedOnUsername(typedUsername);
                        if(result != -1){
                            Snackbar.make(view, "New password is set successfully !!!"+"The new " +
                                    "password : "+customer.getPassword(), Snackbar.LENGTH_LONG).show();
//                                   /*Navigate to the Login Fragment*/
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, new LoginCustomerFragment()).commit();
                        }else{
                            Snackbar.make(view, "Password reset unsuccessful. Try again !!!", Snackbar.LENGTH_LONG).show();
//
                        }
                    }else{
                        Snackbar.make(view, "Password and Retyped password don't match!!!", Snackbar.LENGTH_LONG).show();
                        password.requestFocus();
                    }
                }
            }
        });


        return root;
    }

//    public void updateEditText(CharSequence newText){
//        /*In this way we get data from the activity into our fragment*/
//        username.setText(newText);
//
//        customer = db.getCustomerBasedOnUsername(String.valueOf(newText));
//    }
}
