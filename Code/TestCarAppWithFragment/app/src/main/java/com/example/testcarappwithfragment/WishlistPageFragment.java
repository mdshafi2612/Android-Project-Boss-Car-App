package com.example.testcarappwithfragment;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcarappwithfragment.Adapter.NewCarsListAdapter;
import com.example.testcarappwithfragment.Adapter.WishlistPageAdapter;
import com.example.testcarappwithfragment.Model.Car;
import com.example.testcarappwithfragment.Model.Customer;
import com.example.testcarappwithfragment.Model.Wishlist;
import com.example.testcarappwithfragment.Model.WishlistCarProperty;
import com.example.testcarappwithfragment.SessionManager.CarSessionManagement;
import com.example.testcarappwithfragment.SessionManager.UserSessionManagement;
import com.example.testcarappwithfragment.data.DatabaseHandler;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class WishlistPageFragment extends Fragment {

    private int clickedCarId;
    private int loggedUserId;
    private Button continueShopping;
    private TextView wishlistPageIntro;
    private Wishlist wishList;
    private List<WishlistCarProperty> wishListTableInfo;
    private RecyclerView recyclerView;
    private WishlistPageAdapter recyclerViewAdapter;

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        clickedCarId = getArguments().getInt("SELECTEDCARID2");
//        loggedUserId = getArguments().getInt("LOGGEDUSERID2");
//
//    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_my_wishlist_page, container, false);


        continueShopping = root.findViewById(R.id.GoToNewCarListFragmentId);
        wishlistPageIntro = root.findViewById(R.id.IntroTextViewID);

        continueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new NewCarsListFragment()).commit();
            }
        });


        DatabaseHandler db = new DatabaseHandler(getContext());
//
        CarSessionManagement carSessionManagement = new CarSessionManagement(getContext());
        clickedCarId = carSessionManagement.getSession();
//
        UserSessionManagement userSessionManagement = new UserSessionManagement(getContext());
        loggedUserId = userSessionManagement.getSession();
//
//        Car car = new Car();
//        car = db.getCar(clickedCarId);
//        Customer customer = new Customer();
//        customer = db.getCustomerBasedOnId(loggedUserId);
//
//
//
//
//
//        /*Car car = db.get*/
//
//        Log.d("Wishlist Details Page", "ID OF Car: " + clickedCarId);
//        Log.d("Wishlist Details Page", "ID OF User: " + loggedUserId);
//        /*Now I have add the cars in the wishlist* if not already exists/
//         */
//        Cursor cursor = db.alreayExistedCarInWishlist(loggedUserId, clickedCarId);
//        if (cursor.getCount() > 0) {
//            //Snackbar.make(getView(), "Car "+car.getModel()+"already exists in !"+customer.getCustomerName()+"'s wishlist!!!, can't add it again", Snackbar.LENGTH_LONG).show();
//            Toast.makeText(getActivity(), "Car " + car.getModel() + "already exists in " + customer.getCustomerName() + "'s wishlist!!!, can't add it again", Toast.LENGTH_SHORT).show();
//        } else {
//            wishList = new Wishlist();
//            long result = db.addCarsToWishlist(loggedUserId, clickedCarId);
//
//            if (result != -1) {
////                Snackbar.make(getView(), "Car "+car.getModel()+" has been successfully added in the "
////                        +customer.getCustomerName()+"'s wishlist!!!", Snackbar.LENGTH_LONG).show();
//                Toast.makeText(getActivity(),
//                        "Car " + car.getModel() + " has been successfully added in the "
//                                + customer.getCustomerName() + "'s wishlist!!!",
//                        Toast.LENGTH_SHORT).show();
//            } else {
////                Snackbar.make(getView(), "Insertion in the wishlist table Failed !", Snackbar.LENGTH_LONG).show();
//                Toast.makeText(getActivity(), "Insertion in the wishlist table Failed !", Toast.LENGTH_SHORT).show();
//            }
//        }



        /*Now we have to create a WishlistCar Object*/
        wishListTableInfo = new ArrayList<>();

        wishListTableInfo = db.getAllWishlistedCarsProperty(loggedUserId);
        /*Now initialize the Recycler View with the wishListTableInfo*/

        recyclerView = root.findViewById(R.id.WishlistPageRecyclerViewId);


        recyclerView.setHasFixedSize(true); //ensure that everything inside the RecyclerView is aligned correctly
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        /*Now instantiate the RecyclerView and set the adapter*/
        /*Here itemList contains all the items, we are passing the full
         * arrayList of items to the RecyclerViewAdapter constructor
         * the recycler view will show them in a List manner in the UI.*/
        recyclerViewAdapter = new WishlistPageAdapter(getActivity(), wishListTableInfo, loggedUserId, clickedCarId);
        recyclerView.setAdapter(recyclerViewAdapter);
        /*We have to notify the RecylerView in case there is a change*/
        recyclerViewAdapter.notifyDataSetChanged();

        return root;
    }
}
