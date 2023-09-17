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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcarappwithfragment.FragmentCommunication;
import com.example.testcarappwithfragment.FragmentCommunication2;
import com.example.testcarappwithfragment.Model.Car;
import com.example.testcarappwithfragment.Model.Customer;
import com.example.testcarappwithfragment.NewCarsDetailsPageFragment;
import com.example.testcarappwithfragment.R;
import com.example.testcarappwithfragment.SessionManager.CarSessionManagement;
import com.example.testcarappwithfragment.SessionManager.UserSessionManagement;
import com.example.testcarappwithfragment.WishlistPageFragment;
import com.example.testcarappwithfragment.data.DatabaseHandler;
import com.example.testcarappwithfragment.data.Utils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class NewCarsListAdapter extends RecyclerView.Adapter<NewCarsListAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<Car> carList;
    private List<Car> carListAll;
    private int loggedUserId;
    private int clickedCarId;
    private FragmentCommunication mCommunicator;
    private FragmentCommunication2 fragmentCommunication2;


    public NewCarsListAdapter(Context context, List<Car> carList, int loggedUserId, FragmentCommunication communication) {
        this.context = context;
        this.carList = carList;
        this.carListAll= new ArrayList<>(carList);
        this.loggedUserId = loggedUserId;
        mCommunicator = communication;


    }

    public void setFilteredList(List<Car> filteredList) {
        this.carList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewCarsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        /*Here we inflate the list_row.xml into the Android view object and pass that
         * view into the ViewHolder class constructor*/
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sample_layout_for_new_car_list,
                        viewGroup,
                        false);
        return new NewCarsListAdapter.ViewHolder(view, context, mCommunicator);
    }

    @Override
    public void onBindViewHolder(@NonNull NewCarsListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Car car = carList.get(position);

        holder.carImageView.setImageBitmap(Utils.getImage(car.getCarImage()));
        holder.carBrandName.setText(MessageFormat.format("Car Brand: {0}", car.getBrand()));
        holder.carModelName.setText(MessageFormat.format("Car Model: {0}", car.getModel()));
        holder.carPrice.setText(MessageFormat.format("Price: {0}", String.valueOf(car.getPrice())));

        NewCarsDetailsPageFragment newCarsDetailsPageFragment=new NewCarsDetailsPageFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("SELECTEDCARID",car.getId());
        bundle.putInt("LOGGEDUSERID", loggedUserId);
        newCarsDetailsPageFragment.setArguments(bundle);

        WishlistPageFragment wishlistPageFragment=new WishlistPageFragment();
        Bundle bundle2=new Bundle();
        bundle2.putInt("SELECTEDCARID2",car.getId());
        bundle2.putInt("LOGGEDUSERID2", loggedUserId);
        wishlistPageFragment.setArguments(bundle);
/*        holder.carImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Say you want to pass an example item back to the NewCarDetailsPage Fragment
                Car data = carList.get(position);
                listener.onImageClick(data);

            }
        });*/
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    //runs on a background thread
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Car> filteredList = new ArrayList<>();

            if(charSequence.toString().isEmpty()){
                filteredList.addAll(carListAll);
            }else {
                for(Car car : carListAll){
                    if(car.getModel().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(car);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        //runs on a UI thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            carList.clear();
            carList.addAll((Collection<? extends Car>) results.values);
            notifyDataSetChanged();;
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView carImageView;
        public TextView carBrandName;
        public TextView carModelName;
        public TextView carPrice;
        public Button addCarToWishlist;
        public Button goToWishListPage;

        public ViewHolder(@NonNull View itemView, Context ctx, FragmentCommunication communicator) {
            super(itemView);
            context = ctx;

            carImageView = itemView.findViewById(R.id.NewCarListImageViewId);
            carBrandName = itemView.findViewById(R.id.NewCarBrandId);
            carModelName = itemView.findViewById(R.id.NewCarModelId);
            carPrice = itemView.findViewById(R.id.NewCarPriceId);
            addCarToWishlist = itemView.findViewById(R.id.AddToWishlistTableId);
            goToWishListPage = itemView.findViewById(R.id.GoToWishListPageId);
            mCommunicator = communicator;

            carImageView.setOnClickListener(this);
            addCarToWishlist.setOnClickListener(this);
            goToWishListPage.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            /*First when the user clicks on a item, that item's position
             * can be found by the getAdapterPosition() method*/
            DatabaseHandler db = new DatabaseHandler(context);
            int position;
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            position = getAdapterPosition();
            Car car = carList.get(position);
            CarSessionManagement carSessionManagement = new CarSessionManagement(activity.getApplicationContext());
            carSessionManagement.saveSession(car);

            switch(view.getId()){
                case R.id.NewCarListImageViewId:
                    mCommunicator.respond(getAdapterPosition(), carList.get(getAdapterPosition()).getId(), loggedUserId);
//                    mCommunicator.respond(getAdapterPosition(), carList.get(getAdapterPosition()).getId(), loggedUserId);
                    /*Here we pass the Car id whose imageView is clicked*/
//                    DatabaseHandler db = new DatabaseHandler(context);
//                    car = db.getCar(car.getId());
//                    int clickedCarId = car.getId();
//                    Intent intent = new Intent(activity, MainActivity.class);
//                    /*Passing the data "Bangladesh is my motherland." from MainActivity using putExtra() method*/
//                    intent.putExtra("ClickedCarId",clickedCarId);
//                    activity.startActivity(intent);

//                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                            new NewCarsDetailsPageFragment()).commit();
                    break;
                case R.id.AddToWishlistTableId:
                    //fragmentCommunication2.respond2(getAdapterPosition(), carList.get(getAdapterPosition()).getId(), loggedUserId);
                    //Edit Car
                    /*We will add the product in wishlist later by going to the Wishlist Fragment*/
//                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                            new WishlistPageFragment()).commit();

                    UserSessionManagement userSessionManagement = new UserSessionManagement(activity.getApplicationContext());
                    loggedUserId = userSessionManagement.getSession();

                    clickedCarId = car.getId();

                    Customer customer = new Customer();
                    customer = db.getCustomerBasedOnId(loggedUserId);

                    /*Car car = db.get*/

                    Log.d("Wishlist Details Page", "ID OF Car: " + clickedCarId);
                    Log.d("Wishlist Details Page", "ID OF User: " + loggedUserId);
                    /*Now I have add the cars in the wishlist* if not already exists/
                     */
                    Cursor cursor = db.alreayExistedCarInWishlist(loggedUserId, clickedCarId);
                    if (cursor.getCount() > 0) {
                        //Snackbar.make(getView(), "Car "+car.getModel()+"already exists in !"+customer.getCustomerName()+"'s wishlist!!!, can't add it again", Snackbar.LENGTH_LONG).show();
                        Toast.makeText(activity.getApplicationContext(), "Car " + car.getModel() + "already exists in " + customer.getCustomerName() + "'s wishlist!!!, can't add it again", Toast.LENGTH_SHORT).show();
                    } else {
//                        wishList = new Wishlist();
                        long result = db.addCarsToWishlist(loggedUserId, clickedCarId);

                        if (result != -1) {
//                Snackbar.make(getView(), "Car "+car.getModel()+" has been successfully added in the "
//                        +customer.getCustomerName()+"'s wishlist!!!", Snackbar.LENGTH_LONG).show();
                            Toast.makeText(activity.getApplicationContext(),
                                    "Car " + car.getModel() + " has been successfully added in the "
                                            + customer.getCustomerName() + "'s wishlist!!!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
//                Snackbar.make(getView(), "Insertion in the wishlist table Failed !", Snackbar.LENGTH_LONG).show();
                            Toast.makeText(activity.getApplicationContext(), "Insertion in the wishlist table Failed !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    /*addToWishlist(car);*/
                    break;
                case R.id.GoToWishListPageId:

                    //fragmentCommunication2.respond2(getAdapterPosition(), carList.get(getAdapterPosition()).getId(), loggedUserId);
                    /*Passing the logged user id*/


                    /*Go to wishlist fragment*/
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new WishlistPageFragment()).commit();
                    break;
            }
        }
    }

/*    public interface onImageClickListener{
        void onImageClick(Car car);
    }*/
}
