package com.example.testcarappwithfragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcarappwithfragment.CheckoutPageForCarFragment;
import com.example.testcarappwithfragment.Model.Car;
import com.example.testcarappwithfragment.Model.Cart;
import com.example.testcarappwithfragment.NewCarsListFragment;
import com.example.testcarappwithfragment.R;
import com.example.testcarappwithfragment.data.DatabaseHandler;
import com.example.testcarappwithfragment.data.Utils;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class CartPageForCarAdapter extends RecyclerView.Adapter<CartPageForCarAdapter.ViewHolder> {

    private Context context;
    private List<Cart> cartList;
    private int loggedUserId;

    public CartPageForCarAdapter(Context context, List<Cart> cartList, int loggedUserId) {
        this.context = context;
        this.cartList = cartList;
        this.loggedUserId = loggedUserId;
    }

    @NonNull
    @Override
    public CartPageForCarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        /*Here we inflate the list_row.xml into the Android view object and pass that
         * view into the ViewHolder class constructor*/
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sample_layout_for_cart_page_for_car_fragment,
                        viewGroup,
                        false);

        return new CartPageForCarAdapter.ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull CartPageForCarAdapter.ViewHolder holder, int position) {
        DatabaseHandler db = new DatabaseHandler(context);
        Cart cart = cartList.get(position);

        holder.carImageView.setImageBitmap(Utils.getImage(cart.getCarImage()));
        holder.carBrandName.setText(cart.getCarBrand());
        holder.carModelName.setText(cart.getCarModel());
        holder.carUnitPrice.setText(String.valueOf(cart.getUnitPrice()));
        holder.carQuantity.setText(String.valueOf(cart.getQuantity()));
        holder.carTotalPrice.setText(String.valueOf(cart.getTotalPrice()));

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView carImageView;
        public TextView carBrandName;
        public TextView carModelName;
        public TextView carUnitPrice;
        public TextView carQuantity;
        public TextView carTotalPrice;
        public Button decreaseButton;
        public Button increaseButton;
        public Button updateQuantity;
        public Button removeCarFromCart;
//        public Button continueShopping;
//        public Button goToCheckoutPage;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            carImageView = itemView.findViewById(R.id.CarImageViewForCartId);
            carBrandName = itemView.findViewById(R.id.carBrandNameForCartId);
            carModelName = itemView.findViewById(R.id.carModelNameForCartId);
            carUnitPrice = itemView.findViewById(R.id.CarPriceForCartId);
            carQuantity = itemView.findViewById(R.id.CarQuantityForCartId);
            carTotalPrice = itemView.findViewById(R.id.CarTotalPriceForCartId);
            decreaseButton = itemView.findViewById(R.id.decreaseCarQuantityButtonId);
            increaseButton = itemView.findViewById(R.id.increaseCarQuantityButtonId);
            updateQuantity = itemView.findViewById(R.id.updateCarQuantityForCartId);
            removeCarFromCart = itemView.findViewById(R.id.RemoveCarForCartId);
//            continueShopping = itemView.findViewById(R.id.ContinueShoppingId);
//            goToCheckoutPage = itemView.findViewById(R.id.GoToCheckoutPageId);


            decreaseButton.setOnClickListener(this);
            increaseButton.setOnClickListener(this);
            updateQuantity.setOnClickListener(this);
            removeCarFromCart.setOnClickListener(this);
//            continueShopping.setOnClickListener(this);
//            goToCheckoutPage.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            /*First when the user clicks on a item, that item's position
             * can be found by the getAdapterPosition() method*/
            int position;
            position = getAdapterPosition();
            Cart cart = cartList.get(position);
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            DatabaseHandler databaseHandler = new DatabaseHandler(activity);
            int currentQuantity = -1;
            switch(view.getId()){
                case R.id.decreaseCarQuantityButtonId:

//                    decreaseQuantity(currentQuantity);
                    currentQuantity = Integer.parseInt(carQuantity.getText().toString().trim());
                    if(currentQuantity > 0){
                        currentQuantity -= 1;
                        /*We could not find the string resource, problem !!!
                        * Problem was currentQuantity returns int, but setText() method expects String type value
                        * That's why we wrap the retuen value using String.valueOf() method */
                        carQuantity.setText(String.valueOf(currentQuantity));
                    }else {
                        Snackbar.make(view, "You can not decrease beyond 0!!!", Snackbar.LENGTH_LONG).show();
                    }

                    break;
                case R.id.increaseCarQuantityButtonId:
                    //Edit Car
//                    increaseQuantity(currentQuantity);
                    currentQuantity = Integer.parseInt(carQuantity.getText().toString().trim());
                    currentQuantity += 1;
                    carQuantity.setText(String.valueOf(currentQuantity));
                    break;
                case R.id.updateCarQuantityForCartId:
                    int carID = cart.getCarId();
                    Car car = databaseHandler.getCar(carID);
                    double unitPriceForCar = car.getPrice();
                    int inStockMaxQuantity = car.getQuantity();
//                    updateCarQuantity(inStockMaxQuantity, currentQuantity, view);
                    currentQuantity = Integer.parseInt(carQuantity.getText().toString().trim());
                    if(currentQuantity <= inStockMaxQuantity){
                        carQuantity.setText(String.valueOf(currentQuantity));
                        double totalPrice = unitPriceForCar * currentQuantity;
                        carTotalPrice.setText(String.valueOf(totalPrice));

//                        /*Here we need to update the cartitem quantity*/
                    }else{
//
//                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
//                        DatabaseHandler databaseHandler = new DatabaseHandler(activity);
//                        Toast.makeText((AppCompatActivity) view.getContext(), "Quantity Not Available !!!", Toast.LENGTH_SHORT).show();
                        Snackbar.make(view, "Quantity Not Available !!!", Snackbar.LENGTH_LONG).show();

                    }

                    break;
                case R.id.RemoveCarForCartId:
                    int carId = cart.getCarId();
                    /*Remove it from the database*/
                    databaseHandler.deleteLoggedUsersCartItem(loggedUserId, carId);
                    Toast.makeText((AppCompatActivity) view.getContext(), "The Car has been deleted from the cart", Toast.LENGTH_LONG).show();
                    /*Also remove the cart from the recycler View*/
                    cartList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    break;
//                case R.id.ContinueShoppingId:
//                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                            new NewCarsListFragment()).commit();
//                    break;
//                case R.id.GoToCheckoutPageId:
//                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                            new CheckoutPageForCarFragment()).commit();
//                    break;
            }
        }

//        public void decreaseQuantity(int currentQuantity) {
//
//        }

//        public void increaseQuantity(int currentQuantity) {
//
//        }

//        public void updateCarQuantity(int maxQuantity,int currentQuantity, View view) {
//
//        }




    }
}
