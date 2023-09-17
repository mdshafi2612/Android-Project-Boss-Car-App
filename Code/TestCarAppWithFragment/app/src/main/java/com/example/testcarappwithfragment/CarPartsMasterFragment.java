package com.example.testcarappwithfragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcarappwithfragment.Adapter.CarMasterAdapter;
import com.example.testcarappwithfragment.Adapter.CarPartsMasterAdapter;
import com.example.testcarappwithfragment.Model.Car;
import com.example.testcarappwithfragment.Model.CarParts;
import com.example.testcarappwithfragment.data.DatabaseHandler;
import com.example.testcarappwithfragment.data.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CarPartsMasterFragment extends Fragment {



    private static final int SELECT_PICTURE = 100;
    private RecyclerView recyclerView;
    private CarPartsMasterAdapter recyclerViewAdapter;
    private List<CarParts> carPartsList;
    private DatabaseHandler databaseHandler;
    private FloatingActionButton floatingActionButton;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private Button saveButton;
    private Button btnSelectImage;
    private EditText carPartsId;
    private Spinner carBrandName;
    private EditText carPartsName;
    private EditText carPartsPrice;
    private EditText carPartsVoucher;
    private EditText carPartsQuantity;
    private byte[] carPartsImage;
    ArrayList<String> carBrandNamesFromDatabase=new ArrayList<>();
    int carBrandId;
    ArrayAdapter<String> adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_car_parts_master, container, false);


        /*Here we will write the ListActivity code(Baby Need CRUD App) for showing the RecyclerView and
         * also adding new Cars and Used Cars*/

        recyclerView = root.findViewById(R.id.recyclerViewForCarPartsId);
        /*When we click on the Fab, a pop up window will be showed.And where we
         * add baby items for the app.*/
        floatingActionButton = root.findViewById(R.id.fabIdforCarParts);


        databaseHandler = new DatabaseHandler(getActivity());
        recyclerView.setHasFixedSize(true); //ensure that everything inside the RecyclerView is aligned correctly
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        /*Instantiate the itemList*/
        carPartsList = new ArrayList<>();

        /*Get items from the db*/
        carPartsList = databaseHandler.getAllCarParts();

        /*Now instantiate the RecyclerView and set the adapter*/
        /*Here itemList contains all the items, we are passing the full
         * arrayList of items to the RecyclerViewAdapter constructor
         * the recycler view will show them in a List manner in the UI.*/
        recyclerViewAdapter = new CarPartsMasterAdapter(getActivity(), carPartsList);
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
        View view = getLayoutInflater().inflate(R.layout.pop_up_for_adding_car_parts, null);

        /*Now we can use all the widgets in the AlertDialog*/
        carPartsName = view.findViewById(R.id.carPartsName);
        carPartsQuantity = view.findViewById(R.id.carPartsQuantity);
        carPartsPrice = view.findViewById(R.id.carPartsPrice);
        carPartsVoucher = view.findViewById(R.id.carPartsVoucher);
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



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!carPartsName.getText().toString().isEmpty()
                        && !carPartsQuantity.getText().toString().isEmpty()
                        && !carPartsPrice.getText().toString().isEmpty()
                        && !carPartsVoucher.getText().toString().isEmpty()) {
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
        CarParts carParts = new CarParts();


        /*Extract the value from the EditTexts*/
        String cPartsName = carPartsName.getText().toString().trim();
        int cPartsQuantity = Integer.parseInt(carPartsQuantity.getText().toString().trim());
        double cPartsPrice = Double.parseDouble(carPartsPrice.getText().toString().trim());
        String cPartsVoucher = carPartsVoucher.getText().toString().trim();


        /*Now set the value in the Item object */
        carParts.setCarPartsName(cPartsName);
        carParts.setQuantity(cPartsQuantity);
        carParts.setPrice(cPartsPrice);
        carParts.setVoucher(cPartsVoucher);


        /*Now we save the Item object value in the database by passing the item object in the addItem()
         * method in the DatabaseHandler class*/
        databaseHandler.addCarParts(carParts, carPartsImage);



        /*Now showing a message in the snackbar*/
        Snackbar.make(view, "Car Parts added", Snackbar.LENGTH_SHORT)
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
                        new CarPartsMasterFragment()).commit();
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
            carPartsImage = inputData;
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
