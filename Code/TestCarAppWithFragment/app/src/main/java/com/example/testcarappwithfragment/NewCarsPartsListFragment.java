package com.example.testcarappwithfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcarappwithfragment.Adapter.NewCarsListAdapter;
import com.example.testcarappwithfragment.Adapter.NewCarsPartsListAdapter;
import com.example.testcarappwithfragment.Model.Car;
import com.example.testcarappwithfragment.Model.CarParts;
import com.example.testcarappwithfragment.data.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class NewCarsPartsListFragment extends Fragment {

    private RecyclerView recyclerView;
    private NewCarsPartsListAdapter recyclerViewAdapter;
    private List<CarParts> carPartsList;
    private DatabaseHandler databaseHandler;
    private SearchView searchView;
    private int loggedUserId;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_new_cars_parts_list, container, false);

        /*Later we will add a search view filter with the newCarsListRecyclerViewId
        * and also a expandable RecyclerView to filter the product based on Price and other options*/

        recyclerView = root.findViewById(R.id.newCarPartsListRecyclerViewId);
        searchView = root.findViewById(R.id.searchView);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });

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
        recyclerViewAdapter = new NewCarsPartsListAdapter(getActivity(), carPartsList, loggedUserId);
        recyclerView.setAdapter(recyclerViewAdapter);
        /*We have to notify the RecylerView in case there is a change*/
        recyclerViewAdapter.notifyDataSetChanged();


        return root;
    }

    private void filterList(String newText) {
        List<CarParts> filteredList = new ArrayList<>();
        for (CarParts carParts: carPartsList) {
            if(carParts.getCarPartsName().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(carParts);
            }

        }

        if(filteredList.isEmpty()){
            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
        }else{
            recyclerViewAdapter.setFilteredList(filteredList);
        }
    }


}
