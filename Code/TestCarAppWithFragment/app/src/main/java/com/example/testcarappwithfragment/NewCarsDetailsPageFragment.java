package com.example.testcarappwithfragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testcarappwithfragment.Adapter.NewCarsListAdapter;
import com.example.testcarappwithfragment.Model.Car;
import com.example.testcarappwithfragment.Model.Customer;
import com.example.testcarappwithfragment.SessionManager.CarSessionManagement;
import com.example.testcarappwithfragment.SessionManager.UserSessionManagement;
import com.example.testcarappwithfragment.data.DatabaseHandler;
import com.example.testcarappwithfragment.data.Utils;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class NewCarsDetailsPageFragment extends Fragment{


    private Car clickedCarObject;
    private ImageView carImageView;
    private TextView carBrandName;
    private TextView carModelName;
    private TextView inStockSHow;
    private TextView carDescription;
    private Spinner carQuantity;
    private Button addToCart;
    ArrayAdapter<String> adapter;
    ArrayList<String> quantityforSentinal = new ArrayList<>();



//    private static final String CAR_ID = "clicked_car_id";
//    private static final String LoggedUserId = "LoggedUserId";
    private int clickedCarId;
    private int loggedUserId;

/*    public static NewCarsDetailsPageFragment newInstance(int clickedCarId, int loggedUserId) {
        NewCarsDetailsPageFragment fragment = new NewCarsDetailsPageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CAR_ID, clickedCarId);
        bundle.putInt(LoggedUserId, loggedUserId);
        fragment.setArguments(bundle);

        return fragment;
    }*/

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        clickedCarId = getArguments().getInt("SELECTEDCARID");
//        loggedUserId = getArguments().getInt("LOGGEDUSERID");
//    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_new_cars_details_page, container, false);



        carImageView = root.findViewById(R.id.SelectedCarImageViewId);
        carBrandName = root.findViewById(R.id.CarBrandNameTextViewId);
        carModelName = root.findViewById(R.id.CarModelNameTextViewId);
        inStockSHow = root.findViewById(R.id.InStockTextViewId);
        carDescription = root.findViewById(R.id.DescriptioneditTextMultiLineId);
        carQuantity = root.findViewById(R.id.QuantitySpinnerId);
        addToCart = root.findViewById(R.id.AddToCartButtonId);


//        if(getArguments() != null){
//            /*Get the value that are passed from the MainActivity based on key*/
//            clickedCarId = getArguments().getInt("carId");
//            loggedUserId = getArguments().getInt("loggedUser");
//
//        }

/*        Intent i = getActivity().getIntent();

        clickedCarObject = (Car) i.getSerializableExtra("ClickedCarObject");*/

        //Now set the views with the object value
//        Serializable clickedCarObjet = mDescribable;
/*        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream
                    ((InputStream) mDescribable);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Method for deserialization of object
        try {
            clickedCarObject = (Car)in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        DatabaseHandler db = new DatabaseHandler(getContext());

        UserSessionManagement user = new UserSessionManagement(getContext());
        loggedUserId = user.getSession();

        CarSessionManagement car = new CarSessionManagement(getContext());
        clickedCarId = car.getSession();

        Log.d("New Cars Details Page", "ID OF Car: "+clickedCarId);
        Log.d("New Cars Details Page", "ID OF User: "+loggedUserId);
        clickedCarObject = db.getCar(clickedCarId);
        carImageView.setImageBitmap(Utils.getImage(clickedCarObject.getCarImage()));
        carBrandName.setText(clickedCarObject.getBrand());
        carModelName.setText(clickedCarObject.getModel());
        int quantity = clickedCarObject.getQuantity();
        if(quantity > 0){
            inStockSHow.setText("In Stock");
        }else{
            inStockSHow.setText("Out Of Stock");
        }
        carDescription.setText(clickedCarObject.getShortDescription());
        int quantitySentinal = clickedCarObject.getQuantity();
        for (int i = 1; i <= quantitySentinal; i++) {

            quantityforSentinal.add(String.valueOf(i));
        }
        quantityforSentinal.add(0, "Select the quantity");
        //adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,quantityforSentinal);
        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapter
                = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                quantityforSentinal
        ){
            @Override
            public boolean isEnabled(int position){
                // Disable the first item from Spinner
                // First item will be use for hint
                return position != 0;
            }
            @Override
            public View getDropDownView(
                    int position, View convertView,
                    @NonNull ViewGroup parent) {

                // Get the item view
                View view = super.getDropDownView(
                        position, convertView, parent);
                TextView textView = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    textView.setTextColor(Color.GRAY);
                }
                else { textView.setTextColor(Color.BLACK); }
                return view;
            }
        };


        // Set the drop down view resource
        spinnerArrayAdapter.setDropDownViewResource(
                android.R.layout.simple_dropdown_item_1line
        );


        // Spinner on item selected listener
        carQuantity.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent, View view,
                            int position, long id) {

                        // Get the spinner selected item text
                        String selectedItemText = (String) parent
                                .getItemAtPosition(position);

                        // If user change the default selection
                        // First item is disable and
                        // it is used for hint
                        if(position > 0){
                            // Notify the selected item text
                            Toast.makeText(
                                    getActivity().getApplicationContext(),
                                    "Selected : "
                                            + selectedItemText,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onNothingSelected(
                            AdapterView<?> parent) {
                    }
                });
        carQuantity.setAdapter(spinnerArrayAdapter);
        /*Now set the quantity with the spinner selected item*/


        Log.d("New Cars Details Page", "ID OF User: "+loggedUserId);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*First fetch the car object and the customer object based
                 * on the clickedCarId and the logged user id*/
                Customer loggedCustomer = db.getCustomerBasedOnId(loggedUserId);
                Car clickedCarObject = db.getCar(clickedCarId);
                int quantity = Integer.parseInt(String.valueOf(carQuantity.getSelectedItem()));

                //Add the Car in the cart table
                //The user id, car id, quantity must be in the cart table
                long isInserted = db.addCarsToLoggedUserCart(clickedCarObject, loggedCustomer, quantity);
                if(isInserted != -1){
                    Snackbar.make(v, "Car has been added successfully in the "+loggedCustomer.getCustomerName()+"'s cart!", Snackbar.LENGTH_LONG).show();

                }else{
                    Snackbar.make(v, "Error in inserting data!!!", Snackbar.LENGTH_LONG).show();
                }

            }
        });

        return root;
    }

/*    @Override
    public void onImageClick(Car car) {

    }*/
}
