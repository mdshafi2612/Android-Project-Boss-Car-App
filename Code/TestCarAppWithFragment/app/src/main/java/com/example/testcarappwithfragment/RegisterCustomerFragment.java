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
import com.example.testcarappwithfragment.data.DatabaseHandler;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterCustomerFragment extends Fragment {

    private TextInputLayout userName;
    private TextInputLayout email;
    private TextInputLayout password;
    private TextInputLayout retypePassword;
    private TextInputLayout mobile;
    private TextInputLayout address;
    private Button signUp;
    private Button signIn;
    private DatabaseHandler DB;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_register_customer, container, false);

        userName = root.findViewById(R.id.text_name_Id);
        email = root.findViewById(R.id.text_email_Id);
        password = root.findViewById(R.id.text_password_Id);
        retypePassword = root.findViewById(R.id.text_retype_password_Id);
        mobile = root.findViewById(R.id.text_mobile_no_Id);
        address = root.findViewById(R.id.text_address_Id);
        signUp = root.findViewById(R.id.SignUpButtonId);
        signIn = root.findViewById(R.id.GotoLoginPageButtonId);

        /*Initialize the database*/
        /*For fragment, we need to convert the activity's this with the getActivity()
        * and we need to find the views via the viewGroup variable
        * And when we need to switch between fragments, then
        * we need to write the following code snippet:
        *getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeBeforeLoginFragment()).commit();
                        * Here the first parameter is the fragment_container(which is the frame layout in xml)
                        * which we need to replace with the actual fragment object,
                        * and the second paramter is the object of the actual fragment we are replacing
        * */
        DB = new DatabaseHandler(getActivity());

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Create Customer object*/
                Customer customer = new Customer();
                /*Boolean flag variable for mobileNo, initially set to true*/
                boolean flagForMobile = true;
                int mobileNo;

                String user = userName.getEditText().getText().toString().trim();
                String pass = password.getEditText().getText().toString().trim();
                String repass = retypePassword.getEditText().getText().toString().trim();
                /*We have a bug if we don't input mobile no field, NumberFormatException*/
                if(mobile.getEditText().getText().toString().trim().equals("")){
                    flagForMobile = false;
                }else{
                    mobileNo = Integer.parseInt(mobile.getEditText().getText().toString().trim());
                    customer.setPhoneNumber(mobileNo);
                }
                String emailNo = email.getEditText().getText().toString().trim();
                String addressNumber = address.getEditText().getText().toString().trim();

                /*Customer customer = new Customer();*/
                customer.setCustomerName(user);
                customer.setEmail(emailNo);
                customer.setPassword(pass);
                customer.setAddress(addressNumber);
                /*customer.setDateOfBirth(date);*/

                if(!validateEmail() | !validateUsername() | !validatePassword() | !validateRetypePassword()
                        | !flagForMobile | !validateMobile() | !validateaddress()){
                    Toast.makeText(getActivity(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }else{
                    if(pass.equals(repass)){
                        Boolean checkuser = DB.checkusername(user);
                        if (!checkuser) {
                            Boolean insert = DB.addCustomer(customer);
                            if (insert) {
                                Toast.makeText(getActivity(), "Registered successfully", Toast.LENGTH_SHORT).show();
                                /*Intent intent = new Intent(getApplicationContext(), LoginCustomerActivity.class);
                                startActivity(intent);*/
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                        new LoginCustomerFragment()).commit();
                            } else {
                                Toast.makeText(getActivity(), "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "User already exists! please sign in", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getActivity(), "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getApplicationContext(), LoginCustomerActivity.class);
                startActivity(intent);*/
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LoginCustomerFragment()).commit();
            }
        });

        return root;
    }

    private boolean validateEmail() {
        String emailInput = email.getEditText().getText().toString().trim();

        if(emailInput.isEmpty()){
            email.setError("Field can't be empty");
            return false;
        }else{
            email.setError(null);
            /*We can also remove the error space by writing the following code*/
//            textInputEmail.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateUsername() {
        String userInput = userName.getEditText().getText().toString().trim();

        if(userInput.isEmpty()){
            userName.setError("Field can't be empty");
            return false;
        }else{
            userName.setError(null);
            /*We can also remove the error space by writing the following code*/
//            textInputEmail.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validatePassword() {
        String passwordInput = password.getEditText().getText().toString().trim();

        if(passwordInput.isEmpty()){
            password.setError("Field can't be empty");
            return false;
        }else{
            password.setError(null);
            /*We can also remove the error space by writing the following code*/
//            textInputEmail.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateRetypePassword() {
        String retypePasswordInput = retypePassword.getEditText().getText().toString().trim();

        if(retypePasswordInput.isEmpty()){
            retypePassword.setError("Field can't be empty");
            return false;
        }else{
            retypePassword.setError(null);
            /*We can also remove the error space by writing the following code*/
//            textInputEmail.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateMobile() {
        String MobileInput = mobile.getEditText().getText().toString().trim();

        if(MobileInput.isEmpty()){
            mobile.setError("Field can't be empty");
            return false;
        }else{
            mobile.setError(null);
            /*We can also remove the error space by writing the following code*/
//            textInputEmail.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateaddress() {
        String addressInput = address.getEditText().getText().toString().trim();

        if(addressInput.isEmpty()){
            address.setError("Field can't be empty");
            return false;
        }else{
            address.setError(null);
            /*We can also remove the error space by writing the following code*/
//            textInputEmail.setErrorEnabled(false);
            return true;
        }

    }
}
