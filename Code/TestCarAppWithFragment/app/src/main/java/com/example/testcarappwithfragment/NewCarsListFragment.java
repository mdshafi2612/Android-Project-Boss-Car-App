package com.example.testcarappwithfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcarappwithfragment.Adapter.CarMasterAdapter;
import com.example.testcarappwithfragment.Adapter.NewCarsListAdapter;
import com.example.testcarappwithfragment.Model.Car;
import com.example.testcarappwithfragment.data.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NewCarsListFragment extends Fragment {

    private RecyclerView recyclerView;
    private NewCarsListAdapter recyclerViewAdapter;
    private List<Car> carList;
//    private List<Car> carListAll = new ArrayList<>();
    private DatabaseHandler databaseHandler;
    private SearchView searchView;

    private int loggedUserId;
    private static final String ARG_TEXT = "looged_ID";

    public static NewCarsListFragment newInstance(int loggedId) {
        NewCarsListFragment fragment = new NewCarsListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TEXT, loggedId);
        fragment.setArguments(args);
        /*Here we returning an instance of ExampleFragment*/
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_new_cars_list, container, false);

        /*Later we will add a search view filter with the newCarsListRecyclerViewId
        * and also a expandable RecyclerView to filter the product based on Price and other options*/

        recyclerView = root.findViewById(R.id.newCarsListRecyclerViewId);
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
        carList = new ArrayList<>();

        /*Get items from the db*/
        carList = databaseHandler.getAllActiveCars();

        if (getArguments() != null) {
            /*Get the value that are passed from the MainActivity based on key*/
            loggedUserId = getArguments().getInt(ARG_TEXT);
        }





        /*Now instantiate the RecyclerView and set the adapter*/
        /*Here itemList contains all the items, we are passing the full
         * arrayList of items to the RecyclerViewAdapter constructor
         * the recycler view will show them in a List manner in the UI.*/
        recyclerViewAdapter = new NewCarsListAdapter(getActivity(), carList, loggedUserId, communication);
        recyclerView.setAdapter(recyclerViewAdapter);
        /*We have to notify the RecylerView in case there is a change*/
        recyclerViewAdapter.notifyDataSetChanged();




        return root;
    }

    private void filterList(String newText) {
        List<Car> filteredList = new ArrayList<>();
        for (Car car: carList) {
            if(car.getModel().toLowerCase().contains(newText.toLowerCase()) || car.getBrand().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(car);
            }

        }

        if(filteredList.isEmpty()){
            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
        }else{
            recyclerViewAdapter.setFilteredList(filteredList);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_search){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.search_bar_menu_layout, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerViewAdapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    /*    FragmentCommunication communication=new FragmentCommunication() {
                    @Override
                    public void respond(int position,String name,String job) {
                        FragmentB fragmentB=new FragmentB();
                        Bundle bundle=new Bundle();
                        bundle.putString("NAME",name);
                        bundle.putString("JOB",job);
                        fragmentB.setArguments(bundle);
                        FragmentManager manager=getFragmentManager();
                        FragmentTransaction transaction=manager.beginTransaction();
                        transaction.replace(R.id.dumper,fragmentB).commit();
                    }

                };*/
    FragmentCommunication communication = new FragmentCommunication() {
        @Override
        public void respond(int position, int clickedCarId, int loggedUserId) {
            NewCarsDetailsPageFragment newCarsDetailsPageFragment=new NewCarsDetailsPageFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("SELECTEDCARID",clickedCarId);
            bundle.putInt("LOGGEDUSERID", loggedUserId);
            newCarsDetailsPageFragment.setArguments(bundle);
            FragmentManager manager=getFragmentManager();
            FragmentTransaction transaction=manager.beginTransaction();
            transaction.replace(R.id.fragment_container,newCarsDetailsPageFragment).commit();
        }
    };

//    FragmentCommunication2 communication2 = new FragmentCommunication2() {
//        @Override
//        public void respond2(int position, int clickedCarId, int loggedUserId) {
//            WishlistPageFragment wishlistPageFragment=new WishlistPageFragment();
//            Bundle bundle2=new Bundle();
//            bundle2.putInt("SELECTEDCARID2",clickedCarId);
//            bundle2.putInt("LOGGEDUSERID2", loggedUserId);
//            wishlistPageFragment.setArguments(bundle2);
//            FragmentManager manager=getFragmentManager();
//            FragmentTransaction transaction=manager.beginTransaction();
//            transaction.replace(R.id.fragment_container,wishlistPageFragment).commit();
//        }
//    };
}
