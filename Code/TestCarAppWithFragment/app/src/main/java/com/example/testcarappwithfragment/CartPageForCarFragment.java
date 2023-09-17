package com.example.testcarappwithfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcarappwithfragment.Adapter.CarMasterAdapter;
import com.example.testcarappwithfragment.Adapter.CartPageForCarAdapter;
import com.example.testcarappwithfragment.Model.Car;
import com.example.testcarappwithfragment.Model.Cart;
import com.example.testcarappwithfragment.SessionManager.UserSessionManagement;
import com.example.testcarappwithfragment.data.DatabaseHandler;
import com.example.testcarappwithfragment.data.Utils;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class CartPageForCarFragment extends Fragment {

/*    private ImageView carImageView;
    private TextView carBrandName;
    private TextView carModelName;
    private TextView carUnitPrice;
    private TextView carQuantity;
    private TextView carTotalPrice;
    private Button decreseButton;
    private Button increaseButton;
    private Button updateQuantity;
    private Button removeCarFromCart;*/
    private Button continueShopping;
    private Button goToCheckoutPage;
    private RecyclerView recyclerView;
    private CartPageForCarAdapter recyclerViewAdapter;
    private List<Cart> loggedUsersCartList;
    private int loggedUserId;
    private DatabaseHandler databaseHandler;
    private int currentQuantity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_cart_page_for_car, container, false);

        /*Finding the widget*/
//        carImageView = root.findViewById(R.id.CarImageViewForCartId);
//        carBrandName = root.findViewById(R.id.carBrandNameForCartId);
//        carModelName = root.findViewById(R.id.carModelNameForCartId);
//        carUnitPrice = root.findViewById(R.id.CarPriceForCartId);
//        carQuantity = root.findViewById(R.id.CarQuantityForCartId);
//        carTotalPrice = root.findViewById(R.id.CarTotalPriceForCartId);
//        decreseButton = root.findViewById(R.id.decreaseCarQuantityButtonId);
//        increaseButton = root.findViewById(R.id.increaseCarQuantityButtonId);
//        updateQuantity = root.findViewById(R.id.updateCarQuantityForCartId);
//        removeCarFromCart = root.findViewById(R.id.RemoveCarForCartId);
        continueShopping = root.findViewById(R.id.ContinueShoppingId);
        goToCheckoutPage = root.findViewById(R.id.GoToCheckoutPageId);

        recyclerView = root.findViewById(R.id.CartPageRecyclerViewId);

        databaseHandler = new DatabaseHandler(getActivity());
        recyclerView.setHasFixedSize(true); //ensure that everything inside the RecyclerView is aligned correctly
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        /*Get the user ID*/
        UserSessionManagement loggedUser = new UserSessionManagement(getContext());

        loggedUserId = loggedUser.getSession();
        /*Fetch the Cart object based on loggedUserId*/
        databaseHandler = new DatabaseHandler(getContext());

        loggedUsersCartList = new ArrayList<>();
//        Cart loggedUsersCart = new Cart();
        loggedUsersCartList = databaseHandler.getAllCartItemBasedOnLoggedUserId(loggedUserId);

        /*Now instantiate the RecyclerView and set the adapter*/
        /*Here itemList contains all the items, we are passing the full
         * arrayList of items to the RecyclerViewAdapter constructor
         * the recycler view will show them in a List manner in the UI.*/
        recyclerViewAdapter = new CartPageForCarAdapter(getActivity(), loggedUsersCartList, loggedUserId);
        recyclerView.setAdapter(recyclerViewAdapter);
        /*We have to notify the RecylerView in case there is a change*/
        recyclerViewAdapter.notifyDataSetChanged();

        /*Now set the widgets*/
//        carImageView.setImageBitmap(Utils.getImage(loggedUsersCart.getCarImage()));
//        carBrandName.setText(loggedUsersCart.getCarBrand());
//        carModelName.setText(loggedUsersCart.getCarModel());
//        carUnitPrice.setText((int) loggedUsersCart.getUnitPrice());
//        carQuantity.setText(loggedUsersCart.getQuantity());
//        carTotalPrice.setText((int) loggedUsersCart.getTotalPrice());


        /*Now add listener with the button*/
//        decreseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                currentQuantity = Integer.parseInt(carQuantity.getText().toString().trim());
//                if (currentQuantity > 0) {
//                    currentQuantity -= 1;
//                    carQuantity.setText(currentQuantity);
//                } else {
//                    Toast.makeText(getActivity(), "You can not decrease beyond 0!!!", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });

//        increaseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                currentQuantity = Integer.parseInt(carQuantity.getText().toString().trim());
//                currentQuantity += 1;
//                carQuantity.setText(currentQuantity);
//            }
//        });
//        int carId = loggedUsersCart.getCarId();
//        Car car = databaseHandler.getCar(carId);
//        int inStockMaxQuantity = car.getQuantity();

//        updateQuantity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(currentQuantity <= inStockMaxQuantity){
//                    carQuantity.setText(currentQuantity);
//                }else{
//                    Toast.makeText(getActivity(), "Quantity Not Available !!!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        removeCarFromCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                databaseHandler.deleteLoggedUsersCartItem(loggedUserId, carId);
//                Toast.makeText(getActivity(), "The Car has been deleted from the cart", Toast.LENGTH_LONG).show();
//            }
//        });
//
        continueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new NewCarsListFragment()).commit();
            }
        });

        goToCheckoutPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CheckoutPageForCarFragment()).commit();
            }
        });


        return root;
    }
}
