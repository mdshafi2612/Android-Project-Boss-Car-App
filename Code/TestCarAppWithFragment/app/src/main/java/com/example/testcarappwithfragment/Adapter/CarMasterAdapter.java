package com.example.testcarappwithfragment.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcarappwithfragment.AdminDashboardActivity;
import com.example.testcarappwithfragment.Model.Car;
import com.example.testcarappwithfragment.R;
import com.example.testcarappwithfragment.data.DatabaseHandler;
import com.example.testcarappwithfragment.data.Utils;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.List;

public class CarMasterAdapter extends RecyclerView.Adapter<CarMasterAdapter.ViewHolder> {

    private static final int SELECT_PICTURE = 100;
    private Context context;
    private List<Car> carList;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    public CarMasterAdapter(Context context, List<Car> itemList) {
        this.context = context;
        this.carList = itemList;
    }

    @NonNull
    @Override
    public CarMasterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        /*Here we inflate the list_row.xml into the Android view object and pass that
         * view into the ViewHolder class constructor*/
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sample_layout_for_fragment_car_master,
                        viewGroup,
                        false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull CarMasterAdapter.ViewHolder holder, int position) {
        /*Here we bind the view and the data source and show it on the
         * display*/
        String carStatus = null;
        DatabaseHandler db = new DatabaseHandler(context);
        Car car = carList.get(position); //Get a single Item object
        /*Now set the ViewHolder views with that Item object value*/
        /*holder.carImageView.setImageResource(Integer.parseInt(MessageFormat.format("Car Brand: {0}", car.getCarImage())));*/
        Cursor c=db.getCarStatus(car);

        while(c.moveToNext())
        {
            carStatus=c.getString(0);
            if(carStatus.equals("1")){
                carStatus = "Active";
            }else{
                carStatus = "Deactive";
            }
        }
        holder.carImageView.setImageBitmap(Utils.getImage(car.getCarImage()));
        holder.carBrand.setText(MessageFormat.format("Car Brand: {0}", car.getBrand()));
        holder.carModel.setText(MessageFormat.format("Car Model: {0}", car.getModel()));
        holder.carPrice.setText(MessageFormat.format("Price: {0}", String.valueOf(car.getPrice())));
        holder.carOwnerType.setText(MessageFormat.format("Owner Type: {0}", String.valueOf(car.getOwner())));
        holder.carPlateNo.setText(MessageFormat.format("Plate No: {0}", car.getPlate_no()));
        holder.carShortDescription.setText(MessageFormat.format("Short Desc.: {0}", car.getShortDescription()));
        /*Here quantity is not shown when adding !!! problem need to be solved*/
        holder.carQuantity.setText(MessageFormat.format("Quantity: {0}", car.getQuantity()));
        holder.activeDeactiveButton.setText(carStatus);
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView carImageView;
        public TextView carBrand;
        public TextView carModel;
        public TextView carPrice;
        public TextView carOwnerType;
        public TextView carPlateNo;
        /*Car description will be added later*/
        public TextView carShortDescription;
        public TextView carQuantity;
        public Button editButton;
        public Button deleteButton;
        public Button activeDeactiveButton;
        public int id;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;
            /*Set up the list_row widgets*/
            carImageView = itemView.findViewById(R.id.carBrandImageViewId);
            carBrand = itemView.findViewById(R.id.carBrandId);
            carModel = itemView.findViewById(R.id.carModelId);
            carPrice = itemView.findViewById(R.id.carPriceId);
            carOwnerType = itemView.findViewById(R.id.carOwnerId);
            carPlateNo = itemView.findViewById(R.id.carPlateNoId);
            carShortDescription = itemView.findViewById(R.id.carShortDescriptionId);
            carQuantity = itemView.findViewById(R.id.carQuantityId);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            activeDeactiveButton = itemView.findViewById(R.id.activedeactiveButton);

            /*Add a listener with the editButton and the deleteButton*/
            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
            activeDeactiveButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            /*First when the user clicks on a item, that item's position
             * can be found by the getAdapterPosition() method*/
            int position;
            position = getAdapterPosition();
            Car car = carList.get(position);
            switch(view.getId()){
                case R.id.activedeactiveButton:
                    //Active or Deactivate the car product
                    //If the car is active, the button color is green
                    //Otherwise(if deactive), the button color is red
                    if(activeDeactiveButton.getText().equals("Active")){
                        deactivateCar(car);
                    }else if(activeDeactiveButton.getText().equals("Deactive")){
                        activateCar(car);
                    }

                    break;
                case R.id.editButton:
                    //Edit Car
                    editCar(car);
                    break;
                case R.id.deleteButton:
                    //Delete Car
//                    position = getAdapterPosition();
//                    The clicked position's object is now in item variable
//                    Car car = carList.get(position);
                    deleteCar(car.getId());
                    break;
            }
        }

        private void activateCar(Car car) {
            activeDeactiveButton.setText("Active");
            DatabaseHandler databaseHandler = new DatabaseHandler(context);

            databaseHandler.updateCarStatus("Active", car);
        }

        private void deactivateCar(Car car) {
            activeDeactiveButton.setText("Deactive");

            DatabaseHandler databaseHandler = new DatabaseHandler(context);

            databaseHandler.updateCarStatus("Deactive", car);
        }

        private void editCar(Car newCar) {
            //Todo: populate the popup with the current object data
            /*We will use the itemList object value to
             * set the babyItem, itemQuantity, itemColor and itemSize */
            //final Item item = itemList.get(getAdapterPosition());


            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.pop_up_for_editing_car, null);

            EditText carPlateNo;
            EditText carModel;
            EditText carPrice;
            EditText carOwnerType;
            EditText carShortDescription;
            EditText carQuantity;
            byte[] carImages;
            Button updateCarImageButton;
            Button updateCarButton;

            carPlateNo = view.findViewById(R.id.editCarPlateNo);
            carModel = view.findViewById(R.id.editCarModel);
            carPrice = view.findViewById(R.id.editCarPrice);
            carOwnerType = view.findViewById(R.id.editCarOwnerType);
            carShortDescription = view.findViewById(R.id.editCarShortDescription);
            carQuantity = view.findViewById(R.id.editCarQuantity);
            /*updateCarImageButton = view.findViewById(R.id.updateCarImagebtn);*/
            updateCarButton = view.findViewById(R.id.updateCarButton);


            carPlateNo.setText(newCar.getPlate_no());
            carModel.setText(newCar.getModel());
            carPrice.setText(String.valueOf(newCar.getPrice()));
            carOwnerType.setText(newCar.getOwner());
            carShortDescription.setText(newCar.getShortDescription());
            carQuantity.setText(String.valueOf(newCar.getQuantity()));


/*
            itemQuantity.setText(String.valueOf(newItem.getItemQuantity()));
            itemColor.setText(newItem.getItemColor());
            itemSize.setText(String.valueOf(newItem.getItemSize()));*/


            builder.setView(view);
            dialog = builder.create();
            dialog.show();

            updateCarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //update Our Item
                    DatabaseHandler databaseHandler = new DatabaseHandler(context);



                    if(!carPlateNo.getText().toString().isEmpty()
                            && !carModel.getText().toString().isEmpty()
                            && !carPrice.getText().toString().isEmpty()
                            && !carOwnerType.getText().toString().isEmpty()
                            && !carShortDescription.getText().toString().isEmpty()
                            && !carQuantity.getText().toString().isEmpty()){
                        /*If all fields are no empty, then invoke the updateItem() in the DBHandler class*/
                        //Update item
                        /*Set the newItem object with EditText value entered by the user*/
                        newCar.setPlate_no(carPlateNo.getText().toString());
                        newCar.setModel(carModel.getText().toString());
                        newCar.setPrice(Double.valueOf(carPrice.getText().toString()));
                        newCar.setOwner(carOwnerType.getText().toString());
                        newCar.setShortDescription(carShortDescription.getText().toString());
                        newCar.setQuantity(Integer.parseInt(carQuantity.getText().toString()));

                        databaseHandler.updateCar(newCar);
                        /*If we do not write the following line, then to see the updated result,
                         * we have to restart the app, which is ridiculous
                         * The first parameter is the row number of the recyclerView that we want to update
                         * The second parameter is the item that we want to replace with */
                        notifyItemChanged(getAdapterPosition(), newCar);
                    }else{
                        Snackbar.make(v, "Fields Empty", Snackbar.LENGTH_SHORT).show();
                    }

                    /*After clicking the update button, we want to dismiss the dialog box*/
                    dialog.dismiss();

                }
            });
        }

        private void deleteCar(int id) {

            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);

            /*Now we inflate the confirmation_pop into a view object*/
            View view = inflater.inflate(R.layout.confirmation_pop_for_delete, null);

            /*Now we need to find the yes and no button*/
            Button yesButton = view.findViewById(R.id.conf_yes_button);
            Button noButton = view.findViewById(R.id.conf_no_button);



            /*Set the builder with the view*/
            builder.setView(view);
            dialog = builder.create();
            dialog.show();

            /*Add the OnClickListener with the yes and No button*/
            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*The code for deleting the item*/
                    /*We need to create the DatabaseHandler object because we are in the RecyclerViewAdapter class
                     * no in the DatabaseHandler class*/
                    DatabaseHandler db = new DatabaseHandler(context);
                    /*Now we invoke the delete() method */
                    db.deleteCar(id);
                    /*Now when we delete a item, we want to make sure that the item is also deleted
                     * from the CardView*/
                    carList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());//This will notify the RecyclerView that
                    //the deleted item's CardView needed to be removed from the RecyclerView also
                    /*Finally after deleting dismiss the AlertDialog*/
                    dialog.dismiss();
                }
            });

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*Just dismiss the dialog*/
                    dialog.dismiss();
                }
            });

        }
    }
}
