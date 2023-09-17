package com.example.testcarappwithfragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcarappwithfragment.Model.Car;
import com.example.testcarappwithfragment.Model.CarParts;
import com.example.testcarappwithfragment.R;
import com.example.testcarappwithfragment.SessionManager.CarPartsSessionManagement;
import com.example.testcarappwithfragment.data.DatabaseHandler;
import com.example.testcarappwithfragment.data.Utils;
import com.google.android.material.snackbar.Snackbar;

import java.text.MessageFormat;
import java.util.List;

public class CarPartsMasterAdapter extends RecyclerView.Adapter<CarPartsMasterAdapter.ViewHolder> {

    private Context context;
    private List<CarParts> carPartsList;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    public CarPartsMasterAdapter(Context context, List<CarParts> carPartsList) {
        this.context = context;
        this.carPartsList = carPartsList;
    }

    @NonNull
    @Override
    public CarPartsMasterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        /*Here we inflate the list_row.xml into the Android view object and pass that
         * view into the ViewHolder class constructor*/
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sample_layout_for_fragment_car_parts_master,
                        viewGroup,
                        false);
        return new CarPartsMasterAdapter.ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull CarPartsMasterAdapter.ViewHolder holder, int position) {
        CarParts carParts = carPartsList.get(position);

        holder.carPartsImageView.setImageBitmap(Utils.getImage(carParts.getCarPartsImage()));
        holder.carPartsId.setText(MessageFormat.format("ID: {0}", carParts.getCarPartsId()));
        holder.carPartsName.setText(MessageFormat.format("Car Parts Name: {0}", carParts.getCarPartsName()));
        holder.carParstQuantity.setText(MessageFormat.format("Quantity: {0}", String.valueOf(carParts.getQuantity())));
        holder.carPartsPrice.setText(MessageFormat.format("Price: {0}", String.valueOf(carParts.getPrice())));
        holder.carPartsVoucher.setText(MessageFormat.format("Voucher No: {0}", carParts.getVoucher()));


    }

    @Override
    public int getItemCount() {
        return carPartsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public ImageView carPartsImageView;
        public TextView carPartsId;
        public TextView carPartsName;
        public TextView carParstQuantity;
        public TextView carPartsPrice;
        public TextView carPartsVoucher;
        public Button editButton;
        public Button deleteButton;
        public Button activeDeactiveButton;


        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            carPartsImageView = itemView.findViewById(R.id.carPartsImageViewId);
            carPartsId = itemView.findViewById(R.id.carPartId);
            carPartsName = itemView.findViewById(R.id.carPartsNameId);
            carParstQuantity = itemView.findViewById(R.id.carPartsQuantityId);
            carPartsPrice = itemView.findViewById(R.id.carPartsPriceId);
            carPartsVoucher = itemView.findViewById(R.id.carPartsVoucherId);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            activeDeactiveButton = itemView.findViewById(R.id.activedeactiveButton);

            /*Add a listener with the editButton and the deleteButton*/
//            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
//            activeDeactiveButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            /*First when the user clicks on a item, that item's position
             * can be found by the getAdapterPosition() method*/
            int position;
            position = getAdapterPosition();
            CarParts carParts = carPartsList.get(position);
            switch(view.getId()){
//                case R.id.activedeactiveButton:
//                    //Active or Deactivate the car product
//                    //If the car is active, the button color is green
//                    //Otherwise(if deactive), the button color is red
//                    if(activeDeactiveButton.getText().equals("Active")){
//                        deactivateCarparts(carParts);
//                    }else if(activeDeactiveButton.getText().equals("Deactive")){
//                        activateCarParts(carParts);
//                    }
//
//                    break;
//                case R.id.editButton:
//                    //Edit Car
//                    editCarParts(carParts);
//                    break;
                case R.id.deleteButton:
                    //Delete Car
//                    position = getAdapterPosition();
//                    The clicked position's object is now in item variable
//                    Car car = carList.get(position);
                    deleteCarParts(carParts.getCarPartsId());
                    CarPartsSessionManagement carPartsSessionManagement = new CarPartsSessionManagement(context);
                    carPartsSessionManagement.removeSession();
                    break;
            }
        }

//        private void activateCarParts(CarParts carParts) {
//            activeDeactiveButton.setText("Active");
//            DatabaseHandler databaseHandler = new DatabaseHandler(context);
//
//            databaseHandler.updateCarPartsStatus("Active", carParts);
//        }
//
//        private void deactivateCarparts(CarParts carParts) {
//            activeDeactiveButton.setText("Deactive");
//
//            DatabaseHandler databaseHandler = new DatabaseHandler(context);
//
//            databaseHandler.updateCarPartsStatus("Deactive", carParts);
//        }
//
//        private void editCarParts(CarParts newCarParts) {
//            //Todo: populate the popup with the current object data
//            /*We will use the itemList object value to
//             * set the babyItem, itemQuantity, itemColor and itemSize */
//            //final Item item = itemList.get(getAdapterPosition());
//
//
//            builder = new AlertDialog.Builder(context);
//            inflater = LayoutInflater.from(context);
//            View view = inflater.inflate(R.layout.pop_up_for_editing_car, null);
//
//            EditText carPlateNo;
//            EditText carModel;
//            EditText carPrice;
//            EditText carOwnerType;
//            EditText carShortDescription;
//            EditText carQuantity;
//            byte[] carImages;
//            Button updateCarImageButton;
//            Button updateCarButton;
//
//            carPlateNo = view.findViewById(R.id.editCarPlateNo);
//            carModel = view.findViewById(R.id.editCarModel);
//            carPrice = view.findViewById(R.id.editCarPrice);
//            carOwnerType = view.findViewById(R.id.editCarOwnerType);
//            carShortDescription = view.findViewById(R.id.editCarShortDescription);
//            carQuantity = view.findViewById(R.id.editCarQuantity);
//            /*updateCarImageButton = view.findViewById(R.id.updateCarImagebtn);*/
//            updateCarButton = view.findViewById(R.id.updateCarButton);
//
//
//            carPlateNo.setText(newCarParts.getPlate_no());
//            carModel.setText(newCarParts.getModel());
//            carPrice.setText(String.valueOf(newCarParts.getPrice()));
//            carOwnerType.setText(newCarParts.getOwner());
//            carShortDescription.setText(newCarParts.getShortDescription());
//            carQuantity.setText(String.valueOf(newCarParts.getQuantity()));
//
//
///*
//            itemQuantity.setText(String.valueOf(newItem.getItemQuantity()));
//            itemColor.setText(newItem.getItemColor());
//            itemSize.setText(String.valueOf(newItem.getItemSize()));*/
//
//
//            builder.setView(view);
//            dialog = builder.create();
//            dialog.show();
//
//            updateCarButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //update Our Item
//                    DatabaseHandler databaseHandler = new DatabaseHandler(context);
//
//
//
//                    if(!carPlateNo.getText().toString().isEmpty()
//                            && !carModel.getText().toString().isEmpty()
//                            && !carPrice.getText().toString().isEmpty()
//                            && !carOwnerType.getText().toString().isEmpty()
//                            && !carShortDescription.getText().toString().isEmpty()
//                            && !carQuantity.getText().toString().isEmpty()){
//                        /*If all fields are no empty, then invoke the updateItem() in the DBHandler class*/
//                        //Update item
//                        /*Set the newItem object with EditText value entered by the user*/
//                        newCar.setPlate_no(carPlateNo.getText().toString());
//                        newCar.setModel(carModel.getText().toString());
//                        newCar.setPrice(Double.valueOf(carPrice.getText().toString()));
//                        newCar.setOwner(carOwnerType.getText().toString());
//                        newCar.setShortDescription(carShortDescription.getText().toString());
//                        newCar.setQuantity(Integer.parseInt(carQuantity.getText().toString()));
//
//                        databaseHandler.updateCar(newCar);
//                        /*If we do not write the following line, then to see the updated result,
//                         * we have to restart the app, which is ridiculous
//                         * The first parameter is the row number of the recyclerView that we want to update
//                         * The second parameter is the item that we want to replace with */
//                        notifyItemChanged(getAdapterPosition(), newCar);
//                    }else{
//                        Snackbar.make(v, "Fields Empty", Snackbar.LENGTH_SHORT).show();
//                    }
//
//                    /*After clicking the update button, we want to dismiss the dialog box*/
//                    dialog.dismiss();
//
//                }
//            });
//        }

        private void deleteCarParts(int id) {

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
                    db.deleteCarParts(id);
                    /*Now when we delete a item, we want to make sure that the item is also deleted
                     * from the CardView*/
                    carPartsList.remove(getAdapterPosition());
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
