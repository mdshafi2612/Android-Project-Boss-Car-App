package com.example.testcarappwithfragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcarappwithfragment.Adapter.CarMasterAdapter;
import com.example.testcarappwithfragment.Model.Car;
import com.example.testcarappwithfragment.Model.CarBrand;
import com.example.testcarappwithfragment.data.DatabaseHandler;
import com.example.testcarappwithfragment.data.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CarMasterFragment extends Fragment {


    private static final int SELECT_PICTURE = 100;
    private RecyclerView recyclerView;
    private CarMasterAdapter recyclerViewAdapter;
    private List<Car> carList;
    private DatabaseHandler databaseHandler;
    private FloatingActionButton floatingActionButton;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private Button saveButton;
    private Button btnSelectImage;
    private EditText carPlateNo;
    private Spinner carBrandName;
    private EditText carBrand;
    private EditText carBrandID;
    private EditText carModel;
    private EditText carPrice;
    private EditText carOwnerType;
    private EditText carShortDescription;
    private EditText carStatus;
    private EditText carQuantity;
    private byte[] carImages;
    ArrayList<String> carBrandNamesFromDatabase=new ArrayList<>();
    int carBrandId;
    ArrayAdapter<String> adapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_car_master, container, false);

        /*Here we will write the ListActivity code(Baby Need CRUD App) for showing the RecyclerView and
        * also adding new Cars and Used Cars*/

        recyclerView = root.findViewById(R.id.recyclerViewId);
        /*When we click on the Fab, a pop up window will be showed.And where we
         * add baby items for the app.*/
        floatingActionButton = root.findViewById(R.id.fabId);


        databaseHandler = new DatabaseHandler(getActivity());
        recyclerView.setHasFixedSize(true); //ensure that everything inside the RecyclerView is aligned correctly
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        /*Instantiate the itemList*/
        carList = new ArrayList<>();

        /*Get items from the db*/
        carList = databaseHandler.getAllCars();

        /*Now instantiate the RecyclerView and set the adapter*/
        /*Here itemList contains all the items, we are passing the full
         * arrayList of items to the RecyclerViewAdapter constructor
         * the recycler view will show them in a List manner in the UI.*/
        recyclerViewAdapter = new CarMasterAdapter(getActivity(), carList);
        recyclerView.setAdapter(recyclerViewAdapter);
        /*We have to notify the RecylerView in case there is a change*/
        recyclerViewAdapter.notifyDataSetChanged();

        /*Add a listener with the fab*/
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopDialog();
            }
        });

        return root;
    }

    private void createPopDialog(){
        builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.pop_up_for_adding_car, null);

        /*Now we can use all the widgets in the AlertDialog*/
        carPlateNo = view.findViewById(R.id.carPlateNo);
        carBrandName = view.findViewById(R.id.carBrand);
/*        carBrand = view.findViewById(R.id.carBrand);
        carBrandID = view.findViewById(R.id.carBrandIdno);*/
        carModel = view.findViewById(R.id.carModel);
        carPrice = view.findViewById(R.id.carPrice);
        carOwnerType = view.findViewById(R.id.carOwnerType);
        carShortDescription = view.findViewById(R.id.carShortDescription);
        carStatus = view.findViewById(R.id.carStatus);
        carQuantity = view.findViewById(R.id.carQuantity);
        /*We can no use findViewById() directly here because the saveButton is in the popup xml
         * and we translate the xml file into a view inside the createPopUpDialog() method, that's
         * why we need to use the view*/
        saveButton = view.findViewById(R.id.saveButton);

        btnSelectImage = view.findViewById(R.id.AddImagebtn);

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasStoragePermission(getActivity())){
                    openImageChooser();
                }else{
                    ActivityCompat.requestPermissions(((AppCompatActivity) getActivity()), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                }
            }
        });

        /*Now populate the spinner object with the database value*/
        //Set up the adapter with sample layout
        //adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,carBrandNamesFromDatabase);

        /*Call the database Handler Method*/
        //RETRIEVE

        Cursor c=databaseHandler.getAllActiveCarBrandNames();

        while(c.moveToNext())
        {
            String name=c.getString(1);
            /*Populate the data source array with with DB value*/
            carBrandNamesFromDatabase.add(name);
        }
        carBrandNamesFromDatabase.add(0, "Select a Car Brand");
        /*Code for Spinner Hint Starts here*/
        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapter
                = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                carBrandNamesFromDatabase
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
        carBrandName.setOnItemSelectedListener(
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



        //SET IT TO SPINNER
        carBrandName.setAdapter(spinnerArrayAdapter);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!carPlateNo.getText().toString().isEmpty()
                        && !carBrandName.isSelected()
                        /*&& !carBrandID.getText().toString().isEmpty()*/
                        && !carModel.getText().toString().isEmpty()
                        && !carPrice.getText().toString().isEmpty()
                        && !carOwnerType.getText().toString().isEmpty()
                        && !carShortDescription.getText().toString().isEmpty()
                        && !carStatus.getText().toString().isEmpty()
                        && !carQuantity.getText().toString().isEmpty()) {
                    /*Why do we need to pass the view as the parameter in the saveItem() method?
                     * because we use the Snackbar to show a message which needs the view as parameter.*/
                    saveCar(view);
                } else {
                    Snackbar.make(view, "Empty field is not allowed!", Snackbar.LENGTH_SHORT)
                            .show();
                }

            }
        });

        /*We need to set the view*/
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void saveCar(View view) {
        //Todo: save each baby item to db
        /*First we need to get the items from the AlertDailog EditText
         * Then we need to save the data on the database*/
        Car car = new Car();
        int brandId = -1;
        /*Extract the value from the EditTexts*/
        String cPlate = carPlateNo.getText().toString().trim();
        /*String cBrand = carBrand.getText().toString().trim();*/
        String cBrand = carBrandName.getSelectedItem().toString().trim();
        /*Call the getCarBrandId and pass the carBrand name*/
        Cursor c=databaseHandler.getActiveCarBrandId(cBrand);

        while(c.moveToNext())
        {
            String name=c.getString(0);
            /*Populate the data source array with with DB value*/
            brandId = Integer.parseInt(name);
        }
        String cModel = carModel.getText().toString().trim();
        String cShortDescription = carShortDescription.getText().toString().trim();
        String cOwnerType = carOwnerType.getText().toString().trim();
        /*int brandId = Integer.parseInt(carBrandID.getText().toString().trim());*/
        int cStatus = Integer.parseInt(carStatus.getText().toString().trim());
        double cPrice = Double.parseDouble(carPrice.getText().toString().trim());
        int cQuantity = Integer.parseInt(carQuantity.getText().toString().trim());


        /*Now set the value in the Item object */
        car.setPlate_no(cPlate);
        car.setBrand(cBrand);
        car.setModel(cModel);
        car.setShortDescription(cShortDescription);
        car.setOwner(cOwnerType);
        car.setBrandId(brandId);
        car.setStatus(cStatus);
        car.setPrice(cPrice);
        car.setQuantity(cQuantity);

        /*Now we save the Item object value in the database by passing the item object in the addItem()
         * method in the DatabaseHandler class*/
        databaseHandler.addCar(car, carImages);

        /*Now showing a message in the snackbar*/
        Snackbar.make(view, "Item added", Snackbar.LENGTH_SHORT)
                .show();
        /*What we want is that when we click on the save button, we
         * wait for some time, then we dismiss the AlertDailog popup and also at
         * the same time we go to another activity where the babyList items are
         * displayed in a ListView. That's why we implement the following
         * new Handler().postDelayed() where
         * we need to pass the a Runnable interface
         * and the delay in milliseconds*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Code to be run
                /*We dismiss the AlertDialog here*/
                alertDialog.dismiss();
                //Todo: remains in the same screen that is ListActivity
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.admin_panel_fragment_container,
                        new CarMasterFragment()).commit();
                /*And kill all the previous activities.*/
                /*getActivity().finish();*/
            }
        }, 1200);



    }

    private void openImageChooser() {
        Intent intent = new Intent();
        /*Only allow the image type file, not allow doc, pdf etc.*/
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectImageUri = data.getData();
                if (selectImageUri != null) {
                    if (saveImageInDB(selectImageUri)) {
                        /*showMessage("Image Saved in Database successfully!!!");*/
                        Toast.makeText(getActivity(), "Image Saved in the database successfully!!!", Toast.LENGTH_SHORT).show();
                        /*Setting the image in the ImageView from the Gallery*/
                        /*imageView.setImageURI(selectImageUri);*/
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                /*imageView.setVisibility(View.GONE);*/
                            }
                        }, 2000);
                    }

/*                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (loadImageFromDB()) {
                             *//*   imageView.setVisibility(View.VISIBLE);
                                showMessage("Image Loaded from database successfully!!!");*//*
                            }
                        }
                    }, 3500);*/
                }
            }
        }
    }

    private boolean saveImageInDB(Uri selectImageUri) {
        try {
            /*dbHelper.open();*/
            SQLiteDatabase db = this.databaseHandler.getWritableDatabase();
            InputStream iStream = getActivity().getContentResolver().openInputStream(selectImageUri);
            byte[] inputData = Utils.getBytes(iStream);
            carImages = inputData;
            /*dbHelper.insertImage(inputData);
            dbHelper.close();*/

            return true;
        }catch (Exception e){
            /*dbHelper.close();*/
            return false;
        }
    }

    private boolean loadImageFromDB() {
        try {
            /*dbHelper.open();*/
            DatabaseHandler dbHelper = new DatabaseHandler(getContext());
            SQLiteDatabase db = this.databaseHandler.getWritableDatabase();
            byte[] bytes = dbHelper.retrieveImageFromDB(db);
            dbHelper.close();
            /*imageView.setImageBitmap(Utils.getImage(bytes));*/
            return true;
        }catch (Exception e){
            /*dbHelper.close();*/
            return false;
        }
    }

    private boolean hasStoragePermission(Context context) {
        int read = ContextCompat.checkSelfPermission(context,Manifest.permission.READ_EXTERNAL_STORAGE);
        return read == PackageManager.PERMISSION_GRANTED;
    }
}
