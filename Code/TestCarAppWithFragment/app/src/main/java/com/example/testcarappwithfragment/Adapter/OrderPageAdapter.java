package com.example.testcarappwithfragment.Adapter;

import android.content.Context;
import android.media.Image;
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

public class OrderPageAdapter extends RecyclerView.Adapter<OrderPageAdapter.ViewHolder> {
    private Context context;
    private List<Cart> cartList;
    private int loggedUserId;

    public OrderPageAdapter(Context context, List<Cart> cartList, int loggedUserId) {
        this.context = context;
        this.cartList = cartList;
        this.loggedUserId = loggedUserId;
    }

    @NonNull
    @Override
    public OrderPageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        /*Here we inflate the list_row.xml into the Android view object and pass that
         * view into the ViewHolder class constructor*/
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sample_layout_for_recycler_view_container_for_checkout,
                        viewGroup,
                        false);

        return new OrderPageAdapter.ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderPageAdapter.ViewHolder holder, int position) {
        DatabaseHandler db = new DatabaseHandler(context);
        Cart cart = cartList.get(position);

        /*Set up the widgets*/
        holder.carImageView.setImageBitmap(Utils.getImage(cart.getCarImage()));
        holder.carModel.setText(cart.getCarModel());
        holder.carPrice.setText(String.valueOf(cart.getTotalPrice()));

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView carImageView;
        public TextView carModel;
        public TextView carPrice;
        public Button removeOrderButton;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            carImageView = itemView.findViewById(R.id.carImageViewForCheckoutId);
            carModel = itemView.findViewById(R.id.CarModelNameForCheckoutId);
            carPrice = itemView.findViewById(R.id.CarTotalPriceTextViewIdForChcekout);
            removeOrderButton = itemView.findViewById(R.id.RemoveOrderForCheckoutId);

            /*Add a listener with the button*/
            removeOrderButton.setOnClickListener(this);

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
            if (view.getId() == R.id.RemoveOrderForCheckoutId) {
                int carId = cart.getCarId();
                /*Remove it from the database*/
                databaseHandler.deleteLoggedUsersCartItem(loggedUserId, carId);
                Snackbar.make(view, "The ordered car "+cart.getCarModel()+" has been deleted from the cart", Snackbar.LENGTH_LONG).show();
                /*Also remove the cart from the recycler View*/
                cartList.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
            }
        }
    }
}
