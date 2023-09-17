package com.example.testcarappwithfragment;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcarappwithfragment.Adapter.CarBrandMasterAdapter;
import com.example.testcarappwithfragment.Adapter.CarMasterAdapter;
import com.example.testcarappwithfragment.Model.Car;
import com.example.testcarappwithfragment.Model.CarBrand;
import com.example.testcarappwithfragment.data.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class CarBrandMasterFragment extends Fragment {


    private RecyclerView recyclerView;
    private CarBrandMasterAdapter recyclerViewAdapter;
    private List<CarBrand> carBrandList;
    private DatabaseHandler databaseHandler;
    private FloatingActionButton floatingActionButton;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private Button saveButton;
    private EditText carBrandName;
    private EditText carBrandStatus;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_car_brand_master, container, false);


        /*Here we will write the ListActivity code(Baby Need CRUD App) for showing the RecyclerView and
         * also adding new Cars and Used Cars*/

        recyclerView = root.findViewById(R.id.carBrandRecyclerViewId);
        /*When we click on the Fab, a pop up window will be showed.And where we
         * add baby items for the app.*/
        floatingActionButton = root.findViewById(R.id.carBrandFabId);


        databaseHandler = new DatabaseHandler(getActivity());
        recyclerView.setHasFixedSize(true); //ensure that everything inside the RecyclerView is aligned correctly
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        /*Instantiate the itemList*/
        carBrandList = new ArrayList<>();

        /*Get items from the db*/
        carBrandList = databaseHandler.getAllCarBrands();

        /*Now instantiate the RecyclerView and set the adapter*/
        /*Here itemList contains all the items, we are passing the full
         * arrayList of items to the RecyclerViewAdapter constructor
         * the recycler view will show them in a List manner in the UI.*/
        recyclerViewAdapter = new CarBrandMasterAdapter(getActivity(), carBrandList);
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
        View view = getLayoutInflater().inflate(R.layout.pop_up_for_adding_car_brand, null);

        /*Now we can use all the widgets in the AlertDialog*/
        carBrandName = view.findViewById(R.id.carBrandName);
        carBrandStatus = view.findViewById(R.id.carBrandStatus);
        /*We can no use findViewById() directly here because the saveButton is in the popup xml
         * and we translate the xml file into a view inside the createPopUpDialog() method, that's
         * why we need to use the view*/
        saveButton = view.findViewById(R.id.saveButton);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!carBrandName.getText().toString().isEmpty()
                        && !carBrandStatus.getText().toString().isEmpty()) {
                    /*Why do we need to pass the view as the parameter in the saveItem() method?
                     * because we use the Snackbar to show a message which needs the view as parameter.*/
                    saveCarBrand(view);
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

    private void saveCarBrand(View view) {
        //Todo: save each baby item to db
        /*First we need to get the items from the AlertDailog EditText
         * Then we need to save the data on the database*/
        CarBrand carBrand = new CarBrand();
        /*Extract the value from the EditTexts*/
        String cBrandName = carBrandName.getText().toString().trim();
        int cBrandStatus = Integer.parseInt(carBrandStatus.getText().toString().trim());



        /*Now set the value in the Item object */
        carBrand.setCarBrandName(cBrandName);
        carBrand.setStatus(cBrandStatus);

        /*Now we save the Item object value in the database by passing the item object in the addItem()
         * method in the DatabaseHandler class*/
        databaseHandler.addCarBrand(carBrand);

        /*Now showing a message in the snackbar*/
        Snackbar.make(view, "New Car Brand is added", Snackbar.LENGTH_SHORT)
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
                        new CarBrandMasterFragment()).commit();
                /*And kill all the previous activities.*/
                /*getActivity().finish();*/
            }
        }, 1200);



    }
}
