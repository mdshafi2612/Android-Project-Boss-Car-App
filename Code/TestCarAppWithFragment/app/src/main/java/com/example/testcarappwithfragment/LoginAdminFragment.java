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

import com.example.testcarappwithfragment.data.DatabaseHandler;
import com.google.android.material.textfield.TextInputLayout;

public class LoginAdminFragment extends Fragment {

    private TextInputLayout adminEmailInput;
    private TextInputLayout adminnameInput;
    private TextInputLayout passwordInput;
    private Button signUp;
    private Button signIn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_login_admin, container, false);

        adminEmailInput = root.findViewById(R.id.text_input_admin_email);
        adminnameInput = root.findViewById(R.id.text_input_adminname);
        passwordInput = root.findViewById(R.id.text_input_admin_password);
        signUp = root.findViewById(R.id.GoToAdminRegisterPageId);
        signIn = root.findViewById(R.id.AdminloginButtonId);



        DatabaseHandler db = new DatabaseHandler(getActivity());

        signIn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String adminEmail = adminEmailInput.getEditText().getText().toString().trim();
                String adminName = adminnameInput.getEditText().getText().toString().trim();
                String password = passwordInput.getEditText().getText().toString().trim();

                if(adminEmail.isEmpty() || adminName.isEmpty() || password.isEmpty())
                    Toast.makeText(getActivity(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkadminpass = db.checkadminnamepassword(adminName, password);
                    if(checkadminpass){
                        Toast.makeText(getActivity(), "Sign in successfully", Toast.LENGTH_SHORT).show();
                        /*Intent intent  = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);*/
                        /*getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.admin_panel_fragment_container,
                                new AdminDashboardFragment()).commit();*/
                        Intent intent = new Intent(getActivity(), AdminDashboardActivity.class);
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
                        new RegisterAdminFragment()).commit();
            }
        });

        return root;
    }
}
