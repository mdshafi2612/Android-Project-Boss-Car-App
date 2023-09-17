package com.example.testcarappwithfragment.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcarappwithfragment.Model.Car;
import com.example.testcarappwithfragment.Model.CarBrand;
import com.example.testcarappwithfragment.R;
import com.example.testcarappwithfragment.data.DatabaseHandler;
import com.example.testcarappwithfragment.data.Utils;
import com.google.android.material.snackbar.Snackbar;

import java.text.MessageFormat;
import java.util.List;

public class CarBrandMasterAdapter extends RecyclerView.Adapter<CarBrandMasterAdapter.ViewHolder> {
    private Context context;
    private List<CarBrand> carBrandList;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    public CarBrandMasterAdapter(Context context, List<CarBrand> carBrandList) {
        this.context = context;
        this.carBrandList = carBrandList;
    }

    @NonNull
    @Override
    public CarBrandMasterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        /*Here we inflate the list_row.xml into the Android view object and pass that
         * view into the ViewHolder class constructor*/
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sample_layout_for_fragment_car_brand_master,
                        viewGroup,
                        false);

        return new CarBrandMasterAdapter.ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull CarBrandMasterAdapter.ViewHolder holder, int position) {
        /*Here we bind the view and the data source and show it on the
         * display*/
        String carBrandStatus = null;
        DatabaseHandler db = new DatabaseHandler(context);
        CarBrand carBrand = carBrandList.get(position);


        Cursor c=db.getCarBrandStatus(carBrand);

        while(c.moveToNext())
        {
            carBrandStatus=c.getString(0);
            if(carBrandStatus.equals("1")){
                carBrandStatus = "Active";
            }else{
                carBrandStatus = "Deactive";
            }
        }

        //Get a single Item object
        /*Now set the ViewHolder views with that Item object value*/
        holder.carBrandName.setText(MessageFormat.format("Car Brand Name: {0}", carBrand.getCarBrandName()));
//        holder.carBrandStatus.setText(carBrandStatus);
        holder.carBrandActiveDeactiveButton.setText(carBrandStatus);
    }

    @Override
    public int getItemCount() {
        return carBrandList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView carBrandName;
        public TextView carBrandStatus;
        public Button editButton;
        public Button deleteButton;
        private Button carBrandActiveDeactiveButton;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;
            /*Set up the list_row widgets*/
            carBrandName = itemView.findViewById(R.id.carBrandNameId);
            /*carBrandStatus = itemView.findViewById(R.id.carBrandActiveDeactiveStatusId);*/
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            carBrandActiveDeactiveButton = itemView.findViewById(R.id.carBrandactivedeactiveButton);
            /*Add a listener with the editButton and the deleteButton*/
            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
            carBrandActiveDeactiveButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            /*First when the user clicks on a item, that item's position
             * can be found by the getAdapterPosition() method*/
            int position;
            position = getAdapterPosition();
            /*The clicked position's object is now in item variable*/
            CarBrand carBrand = carBrandList.get(position);
            switch(view.getId()){
                case R.id.carBrandactivedeactiveButton:
                    //Active CarBrand
                    if(carBrandActiveDeactiveButton.getText().equals("Active")){
                        deactivateCarBrand(carBrand);
                    }else if(carBrandActiveDeactiveButton.getText().equals("Deactive")){
                        activateCarBrand(carBrand);
                    }
                    break;
                case R.id.editButton:
                    //Edit Item
                    updateCarBrand(carBrand);
                    break;
                case R.id.deleteButton:
                    //Delete Item

                    deleteCarBrand(carBrand.getCarBrandId());
                    break;
            }
        }

        private void activateCarBrand(CarBrand carBrand) {
            carBrandActiveDeactiveButton.setText("Active");
            DatabaseHandler databaseHandler = new DatabaseHandler(context);

            databaseHandler.updateCarBrandStatus("Active", carBrand);
        }

        private void deactivateCarBrand(CarBrand carBrand) {
            carBrandActiveDeactiveButton.setText("Deactive");

            DatabaseHandler databaseHandler = new DatabaseHandler(context);

            databaseHandler.updateCarBrandStatus("Deactive", carBrand);
        }

        private void updateCarBrand(CarBrand newCarBrand) {
            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.pop_up_for_editing_car_brand, null);

            EditText carBrand;
            Button updateCarBrandButton;

            carBrand = view.findViewById(R.id.editCarBrandName);
            /*updateCarImageButton = view.findViewById(R.id.updateCarImagebtn);*/
            updateCarBrandButton = view.findViewById(R.id.updateCarBrandButton);


            carBrand.setText(newCarBrand.getCarBrandName());



            builder.setView(view);
            dialog = builder.create();
            dialog.show();

            updateCarBrandButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //update Our Item
                    DatabaseHandler databaseHandler = new DatabaseHandler(context);



                    if(!carBrand.getText().toString().isEmpty()){
                        /*If all fields are no empty, then invoke the updateItem() in the DBHandler class*/
                        //Update item
                        /*Set the newItem object with EditText value entered by the user*/
                        newCarBrand.setCarBrandName(carBrand.getText().toString());

                        databaseHandler.updateCarBrand(newCarBrand);
                        /*If we do not write the following line, then to see the updated result,
                         * we have to restart the app, which is ridiculous
                         * The first parameter is the row number of the recyclerView that we want to update
                         * The second parameter is the item that we want to replace with */
                        notifyItemChanged(getAdapterPosition(), newCarBrand);
                    }else{
                        Snackbar.make(v, "Fields Empty", Snackbar.LENGTH_SHORT).show();
                    }

                    /*After clicking the update button, we want to dismiss the dialog box*/
                    dialog.dismiss();

                }
            });
        }

        private void deleteCarBrand(int id) {

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
                    db.deleteCarBrand(id);
                    /*Now when we delete a item, we want to make sure that the item is also deleted
                     * from the CardView*/
                    carBrandList.remove(getAdapterPosition());
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
