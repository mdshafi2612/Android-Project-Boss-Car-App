package com.example.testcarappwithfragment;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcarappwithfragment.Adapter.ContactUsMasterAdpater;
import com.example.testcarappwithfragment.Adapter.UserMasterAdpater;
import com.example.testcarappwithfragment.Model.Customer;
import com.example.testcarappwithfragment.Model.contact_us;
import com.example.testcarappwithfragment.data.DatabaseHandler;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ContactMasterFragment extends Fragment {



    private RecyclerView recyclerView;
    private ContactUsMasterAdpater recyclerViewAdapter;
    private List<contact_us> contact_usList;
    private DatabaseHandler databaseHandler;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_contact_master, container, false);

        recyclerView = root.findViewById(R.id.contactUsMasterRecyclerViewId);


        databaseHandler = new DatabaseHandler(getActivity());
        recyclerView.setHasFixedSize(true); //ensure that everything inside the RecyclerView is aligned correctly
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));

        /*Instantiate the itemList*/
        contact_usList = new ArrayList<>();

        /*Get items from the db*/
        contact_usList = databaseHandler.getAllContactUs();

        /*Now instantiate the RecyclerView and set the adapter*/
        /*Here itemList contains all the items, we are passing the full
         * arrayList of items to the RecyclerViewAdapter constructor
         * the recycler view will show them in a List manner in the UI.*/
        recyclerViewAdapter = new ContactUsMasterAdpater(getActivity(), contact_usList);
        recyclerView.setAdapter(recyclerViewAdapter);
        /*We have to notify the RecylerView in case there is a change*/
        recyclerViewAdapter.notifyDataSetChanged();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        return root;
    }


    contact_us deletedComment = null;
    contact_us tempDeletedComment = null;

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
                    deletedComment = contact_usList.get(position);
                    /*Before deleting the customer, store it in a temporary variable so
                     * that if the user clicks undo, the customer can be reinserted.*/
                    databaseHandler = new DatabaseHandler(getContext());
                    tempDeletedComment = databaseHandler.getContactUsBasedOnID(deletedComment.getId());
                    /*Now delete the user from the database*/

                    databaseHandler.deleteCommentFromDB(deletedComment.getId());
                    /*Now remove from the Recycler View*/
                    contact_usList.remove(position);
                    recyclerViewAdapter.notifyItemChanged(position);
                    Snackbar.make(recyclerView, deletedComment.getName() + "'s comment will be deleted from the database shortly", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    /*Now add the user in the database*/
                                    /*Here probelm is when adding the cusstomer is given a new id*/
                                    databaseHandler.addtoContactUs(tempDeletedComment.getName(), tempDeletedComment.getEmail()
                                    ,tempDeletedComment.getMobile(), tempDeletedComment.getComment(), tempDeletedComment, tempDeletedComment.getAddedOn());
                                    /*The following code segment is for possible solution of the problem*/
//                                    Customer deletedCustomer = databaseHandler.getCustomer(tempDeletedUser.getEmail());
//                                    /*Now also save the session of the user*/
//                                    int userId = deletedCustomer.getCustomerId();
//                                    UserSessionManagement user = new UserSessionManagement(getContext());
//                                    user.saveSession(deletedCustomer);
                                    contact_usList.add(position, deletedComment);
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
