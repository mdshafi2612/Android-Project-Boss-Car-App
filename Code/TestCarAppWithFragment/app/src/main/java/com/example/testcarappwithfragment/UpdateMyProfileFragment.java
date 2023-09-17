package com.example.testcarappwithfragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testcarappwithfragment.Model.Customer;
import com.example.testcarappwithfragment.SessionManager.UserSessionManagement;
import com.example.testcarappwithfragment.data.DatabaseHandler;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

public class UpdateMyProfileFragment extends Fragment {

    private EditText customerNameEditText;
    private Button updateProfile;
    private Button updatePassword;
    private DatabaseHandler db;
    private String updatedName;
    private EditText currentPassword;
    private EditText newPassword;
    private EditText confirmNewPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_my_account_profile_page, container, false);

        customerNameEditText = root.findViewById(R.id.CustomerNameEditTextId);
        updateProfile = root.findViewById(R.id.updateProfileButtonID);
        currentPassword = root.findViewById(R.id.currentPasswwordEditTextId);
        newPassword = root.findViewById(R.id.NewPasswordEditTextId);
        confirmNewPassword = root.findViewById(R.id.ConfirmNewPasswordEditTextId);
        updatePassword = root.findViewById(R.id.updatePasswordButtonId);

        /*updatedName = customerNameEditText.getText().toString(); this line
        * must be added inside the onCLick() method, otherwise it won't work !!!*/

//       /*Add a listener with the update button*/
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updatedName = customerNameEditText.getText().toString();
                Log.d("Update Profile", "onCreateView: "+updatedName);


                if(!updatedName.isEmpty()){
                    //First fetch the logged user Id via the session
                    UserSessionManagement loggedUser = new UserSessionManagement(getContext());
                    int loggedUserId = loggedUser.getSession();

                    //Now update the customer name in the database
                    /*Get the logged customer*/
                    db = new DatabaseHandler(getContext());
                    Customer loggedCustomer = db.getCustomerBasedOnId(loggedUserId);
                    /*Now pass the customer object in the update() method*/
                    long result = db.updateCustomerName(loggedCustomer, updatedName, loggedUserId);
                    if(result != -1){
                        Snackbar.make(v, "Profie name updated successfully !!!", Snackbar.LENGTH_LONG).show();
                        /*GO to the Main Activity*/
//                        Intent intent = new Intent(getActivity(), MainActivity.class);
//                        startActivity(intent);
                    }else{
                        Snackbar.make(v, "Updation is unsuccessfull. Try again !!!", Snackbar.LENGTH_LONG).show();
//                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                                new UpdateMyProfileFragment()).commit();
                    }
                }else{
                    Snackbar.make(v, "Please fill up the edit Text!", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        /*Functionality for Change Password*/
        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*First fetch the value from the EditText*/
                String typedCurrentPass = currentPassword.getText().toString().trim();
                String typedNewPass = newPassword.getText().toString().trim();
                String typedConfirmNewPass = confirmNewPassword.getText().toString().trim();

                /*Initialize the database*/
                db = new DatabaseHandler(getContext());

                /*Now checking if the current password of the logged user is correct*/
                //First fetch the logged user Id via the session
                UserSessionManagement loggedUser = new UserSessionManagement(getContext());
                int loggedUserId = loggedUser.getSession();

                String loggedUserCurrentPass = db.getLoggedUserCurrentPassword(loggedUserId);

                if(!typedCurrentPass.isEmpty()){
                    if(!typedCurrentPass.equals(loggedUserCurrentPass)){
                        Snackbar.make(view, "Please enter the current valid password !!!", Snackbar.LENGTH_LONG).show();
                    }else{
                        /*Current password match, so now we have to check
                         * first: if the two editText value is empty or not
                         * Second: If not, then compare the typedNewPass with the confirmNewPass
                         * if both match, then proceed to update the password, otherwise show error messages*/
                        if(!typedNewPass.isEmpty() && !typedConfirmNewPass.isEmpty()){
                            if(typedNewPass.equals(typedConfirmNewPass)){
                                //Update the password in the customer table based on loggedUserId
                                Customer customer = db.getCustomerBasedOnId(loggedUserId);
                                /*Now pass the object, User ID and the new Changed password*/
                                long result = db.updateLoggedUserPassword(customer, loggedUserId, typedNewPass);
                                if(result != -1){
                                    Snackbar.make(view, "Password updated successfully !!!", Snackbar.LENGTH_LONG).show();
//                                /*GO to the Main Activity*/
//                                Intent intent = new Intent(getActivity(), MainActivity.class);
//                                startActivity(intent);
                                }else{
                                    Snackbar.make(view, "Password is not updated. Try again !!!", Snackbar.LENGTH_LONG).show();
//                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                                new UpdateMyProfileFragment()).commit();
                                }
                            }else{
                                Snackbar.make(view, "New password does not match the Confirm new password, please" +
                                        "enter correct info !!!", Snackbar.LENGTH_LONG).show();
                            }
                        }else{
                            Snackbar.make(view, "Please fill up the new password and confirm new password field !!!", Snackbar.LENGTH_LONG).show();

                        }
                    }
                }else{
                    Snackbar.make(view, "Please fill up the current password field !!!", Snackbar.LENGTH_LONG).show();
                }
            }
        });


        return root;
    }
}
