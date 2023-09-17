package com.example.testcarappwithfragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testcarappwithfragment.Model.Customer;
import com.example.testcarappwithfragment.SessionManager.UserSessionManagement;
import com.example.testcarappwithfragment.data.DatabaseHandler;

import java.util.regex.Pattern;

public class ForgetPasswordFragment extends Fragment {

    private EditText usernameEditText;
    private Button resetPasswordButton;
    DatabaseHandler db;
//    private FragmentForgetPasswordListener listener;


//    /*In order to communicate between the fragment and the underlying Activity, we need to
//     * implement an interface*/
//    public interface FragmentForgetPasswordListener{
//        /*In our MainActivity we need to implement the FragmentAListener interface and
//         * also implement the abstract method onInputASent(). Then we call this method
//         * from the onCreateView() method below*/
//        void onInputUsernameSent(CharSequence input);
//    }
    public ForgetPasswordFragment(){

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_forget_password_page, container, false);


        usernameEditText = root.findViewById(R.id.UserNameEditTextId);
        resetPasswordButton = root.findViewById(R.id.ResetPasswordButtonId);


        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });


        return root;
    }

    private void resetPassword() {
        String typedUsername = usernameEditText.getText().toString();
        db = new DatabaseHandler(getContext());

        if(typedUsername.isEmpty()){
            usernameEditText.setError("Username is required!");
            usernameEditText.requestFocus();
        }else{
            /*This will not worked here, because when the user logs out, his session get destroyed*/
//            UserSessionManagement userName = new UserSessionManagement(getActivity());
//            int loggedUserID = userName.getSession();

            Customer user = db.getCustomerBasedOnUsername(typedUsername);

//            Customer user = db.getCustomerBasedOnId(loggedUserID);
            String username = user.getCustomerName();

            if(!typedUsername.equals(username)){
                usernameEditText.setError("Please enter valid username!!!");
                Toast.makeText(getActivity(), "User does not exists !", Toast.LENGTH_SHORT).show();
                usernameEditText.requestFocus();

            }else{
                /*Here we extract the value from the EditText and pass that to
                 * the underlying activity(in this case MainActivity) and the underlying activity will
                 * pass that to the second Fragment*/
//                CharSequence input = typedUsername;
//                listener.onInputUsernameSent(input);
                Bundle bundle = new Bundle();
                bundle.putString("usernameKey",typedUsername);

                ResetPasswordFragment fragment = new ResetPasswordFragment();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

                //Go to ResetPassword Fragment
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_container, new ResetPasswordFragment()).commit();
            }
        }






    }


//    /*This method will be invoked when the Fragment is attached
//     * to our MainActivity.First we need to check if the MainActivity
//     * implements the listener interface*/
//    @Override
//    public void onAttach(@NonNull Context context) {
//        /*Here the context is the MainActivity and
//         * we are checking if the activity implements the FragmentAListener interface
//         * Then the if condition evaluates to true*/
//        if(context instanceof FragmentForgetPasswordListener){
//            listener = (FragmentForgetPasswordListener) context;
//        }else{
//            /*If we forgot to implement the interface then a runtime exception
//             * will be thrown.*/
//            throw new RuntimeException(context.toString()
//                    +" must implement FragmentForgetPasswordListener");
//        }
//        super.onAttach(context);
//
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        /*When we remove the fragment from our activity
//         * we need to make the reference as null because we do not need this
//         * activity anymore.*/
//        listener = null;
//    }
}
