package com.example.testcarappwithfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testcarappwithfragment.Model.Customer;
import com.example.testcarappwithfragment.SessionManager.UserSessionManagement;
import com.example.testcarappwithfragment.data.DatabaseHandler;
import com.google.android.material.textfield.TextInputLayout;

public class LoginCustomerFragment extends Fragment {

    private TextInputLayout emailInput;
    private TextInputLayout usernameInput;
    private TextInputLayout passwordInput;
    private Button signUp;
    private Button signIn;
    private Button forgetPassword;
    private DatabaseHandler databaseHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_login_customer, container, false);

        emailInput = root.findViewById(R.id.text_input_email);
        usernameInput = root.findViewById(R.id.text_input_username);
        passwordInput = root.findViewById(R.id.text_input_password);
        signUp = root.findViewById(R.id.GoToRegisterPageId);
        signIn = root.findViewById(R.id.loginButtonId);
        forgetPassword = root.findViewById(R.id.GoToForgetPasswordPage);




        DatabaseHandler db = new DatabaseHandler(getActivity());

        signIn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String email = emailInput.getEditText().getText().toString().trim();
                String username = usernameInput.getEditText().getText().toString().trim();
                String password = passwordInput.getEditText().getText().toString().trim();

                if(email.isEmpty() || username.isEmpty() || password.isEmpty())
                    Toast.makeText(getActivity(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkuserpass = db.checkusernamepassword(username, password);
                    if(checkuserpass){
                        //1. login and save session
                        databaseHandler = new DatabaseHandler(getActivity());
                        /*Fetch the customer based on the customer email provided in the EditText
                        * and create a new customer and pass the customer object in the saveSession()
                        * method for setting up the session for that logged customer*/
                        Customer customer = databaseHandler.getCustomer(email);
                        UserSessionManagement sessionManagement = new UserSessionManagement(getActivity());
                        sessionManagement.saveSession(customer);


                        Toast.makeText(getActivity(), "Sign in successfully", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), "Session has been established successfully for the user", Toast.LENGTH_SHORT).show();
                        /*Intent intent  = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);*/
                        //2. step: Move to Main Activity
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(getActivity(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getApplicationContext(), RegisterCustomerActivity.class);
                startActivity(intent);*/
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new RegisterCustomerFragment()).commit();
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ForgetPasswordFragment()).commit();
            }
        });

        return root;


    }
}
