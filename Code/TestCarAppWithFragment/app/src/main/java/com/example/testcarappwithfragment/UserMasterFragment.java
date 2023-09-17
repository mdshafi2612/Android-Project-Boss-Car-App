package com.example.testcarappwithfragment;

import android.Manifest;
import android.database.Cursor;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcarappwithfragment.Adapter.CarMasterAdapter;
import com.example.testcarappwithfragment.Adapter.UserMasterAdpater;
import com.example.testcarappwithfragment.Model.Car;
import com.example.testcarappwithfragment.Model.Customer;
import com.example.testcarappwithfragment.SessionManager.UserSessionManagement;
import com.example.testcarappwithfragment.data.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class UserMasterFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserMasterAdpater recyclerViewAdapter;
    private List<Customer> customerList;
    private DatabaseHandler databaseHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_user_master, container, false);

        /*Here we will write the ListActivity code(Baby Need CRUD App) for showing the RecyclerView and
         * also adding new Cars and Used Cars*/

        recyclerView = root.findViewById(R.id.userRecyclerViewId);
        /*When we click on the Fab, a pop up window will be showed.And where we
         * add baby items for the app.*/
        /*floatingActionButton = root.findViewById(R.id.fabId);*/


        databaseHandler = new DatabaseHandler(getActivity());
        recyclerView.setHasFixedSize(true); //ensure that everything inside the RecyclerView is aligned correctly
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        /*Instantiate the itemList*/
        customerList = new ArrayList<>();

        /*Get items from the db*/
        customerList = databaseHandler.getAllCustomers();

        /*Now instantiate the RecyclerView and set the adapter*/
        /*Here itemList contains all the items, we are passing the full
         * arrayList of items to the RecyclerViewAdapter constructor
         * the recycler view will show them in a List manner in the UI.*/
        recyclerViewAdapter = new UserMasterAdpater(getActivity(), customerList);
        recyclerView.setAdapter(recyclerViewAdapter);
        /*We have to notify the RecylerView in case there is a change*/
        recyclerViewAdapter.notifyDataSetChanged();

/*        *//*Add a listener with the fab*//*
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopDialog();
            }
        });*/
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);



        return root;
    }

    Customer deletedUser = null;
    Customer tempDeletedUser = null;

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();

            switch (direction) {
                case ItemTouchHelper.LEFT:
                    /*deletedUser now is the clicked position's user*/
                    deletedUser = customerList.get(position);
                    /*Before deleting the customer, store it in a temporary variable so
                    * that if the user clicks undo, the customer can be reinserted.*/
                    tempDeletedUser = databaseHandler.getCustomer(deletedUser.getEmail());
                    /*Now delete the user from the database*/
                    databaseHandler = new DatabaseHandler(getContext());
                    databaseHandler.deleteCustomer(deletedUser.getEmail());
                    /*Now remove from the Recycler View*/
                    customerList.remove(position);
                    recyclerViewAdapter.notifyItemChanged(position);
                    Snackbar.make(recyclerView, deletedUser.getCustomerName() + " will be deleted from the database shortly", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    /*Now add the user in the database*/
                                    /*Here probelm is when adding the cusstomer is given a new id*/
                                    databaseHandler.addCustomer(tempDeletedUser);
                                    /*The following code segment is for possible solution of the problem*/
//                                    Customer deletedCustomer = databaseHandler.getCustomer(tempDeletedUser.getEmail());
//                                    /*Now also save the session of the user*/
//                                    int userId = deletedCustomer.getCustomerId();
//                                    UserSessionManagement user = new UserSessionManagement(getContext());
//                                    user.saveSession(deletedCustomer);
                                    customerList.add(position, deletedUser);
                                    recyclerViewAdapter.notifyItemInserted(position);
                                }
                            }).show();

                    break;
                case ItemTouchHelper.RIGHT:
                    //DO nothing
                    /*Later we will create a archive table to store the user*/
                    break;

            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(getActivity(), c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorAccent))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };



}
