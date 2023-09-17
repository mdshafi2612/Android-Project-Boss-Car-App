package com.example.testcarappwithfragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcarappwithfragment.Model.Car;
import com.example.testcarappwithfragment.Model.Wishlist;
import com.example.testcarappwithfragment.Model.WishlistCarProperty;
import com.example.testcarappwithfragment.NewCarsDetailsPageFragment;
import com.example.testcarappwithfragment.R;
import com.example.testcarappwithfragment.SessionManager.CarSessionManagement;
import com.example.testcarappwithfragment.WishlistPageFragment;
import com.example.testcarappwithfragment.data.DatabaseHandler;
import com.example.testcarappwithfragment.data.Utils;
import com.google.android.material.snackbar.Snackbar;

import java.text.MessageFormat;
import java.util.List;

public class WishlistPageAdapter extends RecyclerView.Adapter<WishlistPageAdapter.ViewHolder> {

    private Context context;
    private List<WishlistCarProperty> wishlist;
    int loggedUserId;
    int clickedCarID;

    public WishlistPageAdapter(Context context, List<WishlistCarProperty> wishlist, int loggedUserId, int clickedCarID) {
        this.context = context;
        this.wishlist = wishlist;
        this.loggedUserId = loggedUserId;
        this.clickedCarID = clickedCarID;
    }

    @NonNull
    @Override
    public WishlistPageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        /*Here we inflate the list_row.xml into the Android view object and pass that
         * view into the ViewHolder class constructor*/
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sample_layout_for_fragment_my_wishlist_page,
                        viewGroup,
                        false);
        return new WishlistPageAdapter.ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistPageAdapter.ViewHolder holder, int position) {
        WishlistCarProperty wish_list = wishlist.get(position);

        /*Here we will write a database query for fetching the widgets value*/

        holder.carImageView.setImageBitmap(Utils.getImage(wish_list.getCarImage()));
        holder.carModelName.setText(wish_list.getCarModelName());
        holder.carUnitPrice.setText(String.valueOf(wish_list.getCarPrice()));


    }

    @Override
    public int getItemCount() {
        return wishlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView carImageView;
        public TextView carModelName;
        public TextView carUnitPrice;
        public Button removeCarFromWishlist;
        public Button GoToCartButton;


        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            carImageView = itemView.findViewById(R.id.CarImageViewIdForWishlist);
            carModelName = itemView.findViewById(R.id.carModelNameForWishlistPageId);
            carUnitPrice = itemView.findViewById(R.id.carUnitPriceForWishlistPageId);
            removeCarFromWishlist = itemView.findViewById(R.id.removeCarFromWishlistButtonId);
            GoToCartButton = itemView.findViewById(R.id.GoToCartPageButtonId);

            /*Add a listener*/
            removeCarFromWishlist.setOnClickListener(this);
            GoToCartButton.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            /*First when the user clicks on a item, that item's position
             * can be found by the getAdapterPosition() method*/
            int position;
            position = getAdapterPosition();
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            WishlistCarProperty wishlist1 = wishlist.get(position);
            DatabaseHandler db = new DatabaseHandler(context);
            Car car = db.getCar(wishlist1.getWishlistCarID());
            CarSessionManagement carSessionManagement = new CarSessionManagement(activity.getApplicationContext());
            carSessionManagement.saveSession(car);
            clickedCarID = carSessionManagement.getSession();
            switch(view.getId()){
                case R.id.removeCarFromWishlistButtonId:
                    //Here we will invoke the database method for deleting the selected car
                    //from the logged user's wishlist
                    int count = db.deleteSelectedCarFromWishlist(loggedUserId, clickedCarID);
                    if(count != 0){
                        Snackbar.make(itemView.getRootView(), count+" products have been deleted from the wishlist !!!", Snackbar.LENGTH_LONG).show();
                    }else{
                        Snackbar.make(itemView.getRootView(), "Deletion failed !!!", Snackbar.LENGTH_LONG).show();
                    }
                    /*Now when we delete a item, we want to make sure that the item is also deleted
                     * from the CardView*/
                    wishlist.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    /*Also remove the car session*/
//                    CarSessionManagement carSessionManagement = new CarSessionManagement(activity.getApplicationContext());
                    carSessionManagement.removeSession();
                    break;
                case R.id.GoToCartPageButtonId:
                    //We will navigate to the clicked car's details page fragment,
                    //from there we can add that car to the logged user's wishlist
                    CarSessionManagement carSessionManagement2 = new CarSessionManagement(context);
                    carSessionManagement2.saveSession(car);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new NewCarsDetailsPageFragment()).commit();


                    break;

            }
        }
    }
}
