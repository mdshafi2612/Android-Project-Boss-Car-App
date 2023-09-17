package com.example.testcarappwithfragment.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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

import com.example.testcarappwithfragment.FragmentCommunication;
import com.example.testcarappwithfragment.FragmentCommunication2;
import com.example.testcarappwithfragment.Model.Car;
import com.example.testcarappwithfragment.Model.CarParts;
import com.example.testcarappwithfragment.Model.Customer;
import com.example.testcarappwithfragment.NewCarsDetailsPageFragment;
import com.example.testcarappwithfragment.R;
import com.example.testcarappwithfragment.SessionManager.CarPartsSessionManagement;
import com.example.testcarappwithfragment.SessionManager.CarSessionManagement;
import com.example.testcarappwithfragment.SessionManager.UserSessionManagement;
import com.example.testcarappwithfragment.WishlistPageFragment;
import com.example.testcarappwithfragment.data.DatabaseHandler;
import com.example.testcarappwithfragment.data.Utils;
import com.google.android.material.snackbar.Snackbar;

import java.text.MessageFormat;
import java.util.List;

public class NewCarsPartsListAdapter extends RecyclerView.Adapter<NewCarsPartsListAdapter.ViewHolder> {

    private Context context;
    private List<CarParts> carPartsList;
    private int loggedUserId;
    private int clickedCarId;


    public NewCarsPartsListAdapter(Context context, List<CarParts> carPartsList, int loggedUserId) {
        this.context = context;
        this.carPartsList = carPartsList;
        this.loggedUserId = loggedUserId;

    }

    public void setFilteredList(List<CarParts> filteredList) {
        this.carPartsList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewCarsPartsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        /*Here we inflate the list_row.xml into the Android view object and pass that
         * view into the ViewHolder class constructor*/
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sample_layout_for_new_car_parts_list,
                        viewGroup,
                        false);
        return new NewCarsPartsListAdapter.ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull NewCarsPartsListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CarParts carParts = carPartsList.get(position);
        CarPartsSessionManagement carPartsSessionManagement = new CarPartsSessionManagement(context);
        carPartsSessionManagement.saveSession(carParts);

        holder.carPartsImageView.setImageBitmap(Utils.getImage(carParts.getCarPartsImage()));
        holder.carPartsName.setText(MessageFormat.format("Car Parts name: {0}", carParts.getCarPartsName()));
        holder.carPartsQuantity.setText(MessageFormat.format("Car Parts Quantity: {0}", carParts.getQuantity()));
        holder.carPartsPrice.setText(MessageFormat.format("Price: {0}", String.valueOf(carParts.getPrice())));

    }

    @Override
    public int getItemCount() {
        return carPartsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView carPartsImageView;
        public TextView carPartsName;
        public TextView carPartsQuantity;
        public TextView carPartsPrice;
        public TextView quantityToBuy;
        public Button BuyCarParts;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            carPartsImageView = itemView.findViewById(R.id.NewCarPartsListImageViewId);
            carPartsName = itemView.findViewById(R.id.CarPartsNameId);
            carPartsQuantity = itemView.findViewById(R.id.CarPartsQuantity);
            carPartsPrice = itemView.findViewById(R.id.CarPartsPrice);
            BuyCarParts = itemView.findViewById(R.id.BuyCarParts);
            quantityToBuy = itemView.findViewById(R.id.Quantity);

            BuyCarParts.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            /*First when the user clicks on a item, that item's position
             * can be found by the getAdapterPosition() method*/
            DatabaseHandler db = new DatabaseHandler(context);
            int position;
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            position = getAdapterPosition();
            CarParts carParts = carPartsList.get(position);
            CarPartsSessionManagement carPartsSessionManagement = new CarPartsSessionManagement(context);
            carPartsSessionManagement.saveSession(carParts);
            int carPartsId = carPartsSessionManagement.getSession();
//            CarSessionManagement carSessionManagement = new CarSessionManagement(activity.getApplicationContext());
//            carSessionManagement.saveSession(car);

            switch (view.getId()) {
                case R.id.BuyCarParts:
                    /*Here we will update the parts quantity and show a Toast message*/
                    int quantity = Integer.parseInt(quantityToBuy.getText().toString().trim());
                    if (quantity > carParts.getQuantity()) {
                        Snackbar.make(view, "Please enter valid quantity !!! Quantity not available !!!", Snackbar.LENGTH_LONG).show();
                        quantityToBuy.requestFocus();
                    } else if (quantity <= 0) {
                        Snackbar.make(view, "Please enter valid quantity !!! Quantity can't less than or equal to zero !!!", Snackbar.LENGTH_LONG).show();
                        quantityToBuy.requestFocus();
                    } else if(quantity == carParts.getQuantity()){
                        //We delete the carParts
//                        db.deleteCarParts(carParts.getCarPartsId());
//                        carParts.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        Snackbar.make(view, "Thanks for buying from our app!!!", Snackbar.LENGTH_SHORT).show();
                        Snackbar.make(view, "Car parts has been deleted from the DB !!!, Out Of Stock!!!", Snackbar.LENGTH_LONG).show();

                    }else{
                        //Update the quantity
                        int totalQuantiy = carParts.getQuantity();
                        int quantitySold = quantity;
                        int updatedQuantityAfterSell = totalQuantiy - quantitySold;
                        long result = db.updateCarPartsQuantityAfterCustomerBuy(carParts, updatedQuantityAfterSell);
                        if(result != -1){
                            Snackbar.make(view, "Quantity updated successfully!!!", Snackbar.LENGTH_SHORT).show();
                            Snackbar.make(view, "Thanks for buying from our app!!!", Snackbar.LENGTH_LONG).show();
                        }else {
                            Snackbar.make(view, "Update failed, check logcat!!!", Snackbar.LENGTH_LONG).show();
                        }
                    }

                    break;
            }

        }
    }
}
