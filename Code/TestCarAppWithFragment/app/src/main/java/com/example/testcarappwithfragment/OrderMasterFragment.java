package com.example.testcarappwithfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcarappwithfragment.Adapter.OrderMasterAdapter;
import com.example.testcarappwithfragment.Adapter.OrderPageAdapter;
import com.example.testcarappwithfragment.Model.Cart;
import com.example.testcarappwithfragment.Model.OrderMasterList;
import com.example.testcarappwithfragment.SessionManager.UserSessionManagement;
import com.example.testcarappwithfragment.data.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class OrderMasterFragment extends Fragment {

    private RecyclerView recyclerView;
    private OrderMasterAdapter recyclerViewAdapter;
    private List<OrderMasterList> orderMasterLists;
    private int loggedUserId;
    private DatabaseHandler databaseHandler;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_order_master, container, false);

        recyclerView = root.findViewById(R.id.OrderMasterRecyclerViewId);
        databaseHandler = new DatabaseHandler(getActivity());
        recyclerView.setHasFixedSize(true); //ensure that everything inside the RecyclerView is aligned correctly
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        /*Get the user ID*/
        UserSessionManagement loggedUser = new UserSessionManagement(getContext());

        loggedUserId = loggedUser.getSession();
        /*Fetch the Cart object based on loggedUserId*/
        databaseHandler = new DatabaseHandler(getContext());

        orderMasterLists = new ArrayList<>();
        orderMasterLists = databaseHandler.addToOrderMasterList();

        /*Now instantiate the RecyclerView and set the adapter*/
        /*Here itemList contains all the items, we are passing the full
         * arrayList of items to the RecyclerViewAdapter constructor
         * the recycler view will show them in a List manner in the UI.*/
        recyclerViewAdapter = new OrderMasterAdapter(orderMasterLists, getActivity());
        recyclerView.setAdapter(recyclerViewAdapter);
        /*We have to notify the RecylerView in case there is a change*/
        recyclerViewAdapter.notifyDataSetChanged();

        return root;
    }

//    FragmentCommunicationForOrderDetailsInAdminPanel communication = new FragmentCommunicationForOrderDetailsInAdminPanel() {
//        @Override
//        public void respond(int position, int clickedOrderID) {
//            OrderDetailsPageInAdminPanelFragment orderDetailsPageInAdminPanelFragment=new OrderDetailsPageInAdminPanelFragment();
//            Bundle bundle = new Bundle();
//            bundle.putInt("SELECTEDORDERID", clickedOrderID);;
//            orderDetailsPageInAdminPanelFragment.setArguments(bundle);
//            FragmentManager manager=getFragmentManager();
//            FragmentTransaction transaction=manager.beginTransaction();
//            transaction.replace(R.id.admin_panel_fragment_container,orderDetailsPageInAdminPanelFragment).commit();
//        }
//    };
}
