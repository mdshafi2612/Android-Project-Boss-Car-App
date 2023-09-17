package com.example.testcarappwithfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcarappwithfragment.Adapter.CarMasterAdapter;
import com.example.testcarappwithfragment.Adapter.MyOrderListAdapter;
import com.example.testcarappwithfragment.Model.Car;
import com.example.testcarappwithfragment.Model.Customer;
import com.example.testcarappwithfragment.Model.Order;
import com.example.testcarappwithfragment.Model.OrderDetail;
import com.example.testcarappwithfragment.Model.OrderMasterDetailList;
import com.example.testcarappwithfragment.SessionManager.UserSessionManagement;
import com.example.testcarappwithfragment.data.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class MyOrderListFragment extends Fragment {

    private RecyclerView recyclerView;
    private MyOrderListAdapter recyclerViewAdapter;
    private List<OrderMasterDetailList> orderMasterDetailLists;
    private DatabaseHandler databaseHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_my_order_list, container, false);

        /*Here we will write the ListActivity code(Baby Need CRUD App) for showing the RecyclerView and
         * also adding new Cars and Used Cars*/

        recyclerView = root.findViewById(R.id.myOrderListRecyclerViewId);



        databaseHandler = new DatabaseHandler(getActivity());
        recyclerView.setHasFixedSize(true); //ensure that everything inside the RecyclerView is aligned correctly
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        /*Instantiate the itemList*/

        OrderMasterDetailList orderMasterDetailList = new OrderMasterDetailList();



        /*Get items from the db*/
        UserSessionManagement user = new UserSessionManagement(getContext());
        int customerId = user.getSession();
//        Customer customer = databaseHandler.getCustomerBasedOnId(customerId);
//        List<Car> carList = databaseHandler.getAllCars();
//        Order order = databaseHandler.getOrderBasedOnSelectedLoggedUserID(customerId);
             /*"select distinct(order_detail.id) ,order_detail.*,product.name,
        product.image,`order`.address,`order`.city,`order`.pincode
        from order_detail,product ,`order` where order_detail.order_id='$order_id'
        and  order_detail.product_id=product.id GROUP by order_detail.id")*/
        /*orderMasterDetailList = databaseHandler.addToOrderMasterDetailList(order.getId());*/
        List<Order> allOrdersOfLoggedUserId = databaseHandler.getAllOrdersOfLoggedUserId(customerId);

        orderMasterDetailLists = new ArrayList<>(allOrdersOfLoggedUserId.size());
        Log.d("My Order List Fragment", "onCreateView: "+allOrdersOfLoggedUserId.size());
/**/
        for(int i = 0; i < allOrdersOfLoggedUserId.size(); i++){
            //Then for each order, call the addToOrderMasterDetailList Method and store that return value in OrderMasterDetailsList variable.
            Order order = databaseHandler.getOrderBasedOnSelectedOrderedID(allOrdersOfLoggedUserId.get(i).getId());
            Log.d("My Order List Fragment", "onCreateView: "+order.getId());
            OrderDetail orderDetail = databaseHandler.getOrderDetailsBasedOnOrderID(order.getId());
            Car car = databaseHandler.getCar(orderDetail.getCarId());
            OrderMasterDetailList orderMasterDetailList1 = databaseHandler.addToOrderMasterDetailList(allOrdersOfLoggedUserId.get(i).getId());
//            OrderDetail orderDetail = databaseHandler.getOrderDetailsBasedOnOrderID(orderMasterDetailList1.)
            orderMasterDetailList1.setCarModelName(car.getModel());
            orderMasterDetailList1.setCarImage(car.getCarImage());
            Log.d("My Order List Fragment", "onCreateView: "+orderMasterDetailList1.getCarModelName());

            orderMasterDetailLists.add(orderMasterDetailList1);

        }




        /*Now instantiate the RecyclerView and set the adapter*/
        /*Here itemList contains all the items, we are passing the full
         * arrayList of items to the RecyclerViewAdapter constructor
         * the recycler view will show them in a List manner in the UI.*/
        recyclerViewAdapter = new MyOrderListAdapter(getActivity(), orderMasterDetailLists);
        recyclerView.setAdapter(recyclerViewAdapter);
        /*We have to notify the RecylerView in case there is a change*/
        recyclerViewAdapter.notifyDataSetChanged();

        return root;
    }
}
