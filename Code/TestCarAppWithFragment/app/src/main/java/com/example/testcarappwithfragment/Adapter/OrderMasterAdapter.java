package com.example.testcarappwithfragment.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcarappwithfragment.FragmentCommunication;
import com.example.testcarappwithfragment.FragmentCommunicationForOrderDetailsInAdminPanel;
import com.example.testcarappwithfragment.Model.Car;
import com.example.testcarappwithfragment.Model.Cart;
import com.example.testcarappwithfragment.Model.Order;
import com.example.testcarappwithfragment.Model.OrderDetail;
import com.example.testcarappwithfragment.Model.OrderMasterList;
import com.example.testcarappwithfragment.NewCarsListFragment;
import com.example.testcarappwithfragment.OrderDetailsPageInAdminPanelFragment;
import com.example.testcarappwithfragment.R;
import com.example.testcarappwithfragment.SessionManager.CarSessionManagement;
import com.example.testcarappwithfragment.SessionManager.OrderSessionManagement;
import com.example.testcarappwithfragment.data.DatabaseHandler;
import com.example.testcarappwithfragment.data.Utils;

import java.text.MessageFormat;
import java.util.List;

public class OrderMasterAdapter extends RecyclerView.Adapter<OrderMasterAdapter.ViewHolder> {

    private List<OrderMasterList> orderMasterList;
    private Context context;
//    private FragmentCommunicationForOrderDetailsInAdminPanel mCommunicator;

    public OrderMasterAdapter(List<OrderMasterList> orderMasterList, Context context) {
        this.orderMasterList = orderMasterList;
        this.context = context;

    }

    @NonNull
    @Override
    public OrderMasterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        /*Here we inflate the list_row.xml into the Android view object and pass that
         * view into the ViewHolder class constructor*/
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sample_layout_for_fragment_order_master,
                        viewGroup,
                        false);
        return new OrderMasterAdapter.ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderMasterAdapter.ViewHolder holder, int position) {
        DatabaseHandler db = new DatabaseHandler(context);
        OrderMasterList orderMasterList1 = orderMasterList.get(position);


//        DatabaseHandler db = new DatabaseHandler(context);
        Order order = db.getOrderBasedOnSelectedOrderedID(orderMasterList1.getOrderID());
        OrderSessionManagement orderSessionManagement = new OrderSessionManagement(context);
        orderSessionManagement.saveSession(order);

        /*Set up the widgets*/
        holder.orderID.setText(MessageFormat.format("Order ID: {0}", String.valueOf(orderMasterList1.getOrderID())));
        holder.orderDate.setText(MessageFormat.format("Order Date: {0}", orderMasterList1.getOrderDate()));
        holder.address.setText(MessageFormat.format("Address: {0}", orderMasterList1.getAddress()));
        holder.paymentType.setText(MessageFormat.format("Payment Type: {0}", orderMasterList1.getPaymentType()));
        holder.paymentStatus.setText(MessageFormat.format("Payment Status: {0}", orderMasterList1.getPaymentStatus()));
        holder.orderStatus.setText(MessageFormat.format("Order Status: {0}", orderMasterList1.getOrderStatus()));

//        OrderDetailsPageInAdminPanelFragment orderDetailsPageInAdminPanelFragment = new OrderDetailsPageInAdminPanelFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt("SELECTEDORDERID", orderMasterList1.getOrderID());;
//        orderDetailsPageInAdminPanelFragment.setArguments(bundle);

    }

    @Override
    public int getItemCount() {
        return orderMasterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView orderID;
        public TextView orderDate;
        public TextView address;
        public TextView paymentType;
        public TextView paymentStatus;
        public TextView orderStatus;
//        public Button createPDFButton;



        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            orderID = itemView.findViewById(R.id.OrderIdTextViewId);
            orderDate = itemView.findViewById(R.id.OrderDateTextViewId);
            address = itemView.findViewById(R.id.AddressTextViewId);
            paymentType = itemView.findViewById(R.id.PaymentTypeTextViewId);
            paymentStatus = itemView.findViewById(R.id.PaymentStatusTextViewId);
            orderStatus = itemView.findViewById(R.id.OrderStatusTextViewId);
//            createPDFButton = itemView.findViewById(R.id.CreatePDFButtonId);

            /*Add a listener with the PDF button*/

            orderID.setOnClickListener(this);
//            createPDFButton.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
//            int position;
//            position = getAdapterPosition();
//            OrderMasterList orderList = orderMasterList.get(position);
//            AppCompatActivity activity = (AppCompatActivity) view.getContext();
//            DatabaseHandler db = new DatabaseHandler(context);
//            OrderDetail orderDetail = db.getOrderDetailsBasedOnOrderID(orderList.getOrderID());
//            Car car = db.getCar(orderDetail.getCarId());
//            CarSessionManagement carSessionManagement = new CarSessionManagement(activity.getApplicationContext());
//            carSessionManagement.saveSession(car);

            switch(view.getId()){
                case R.id.OrderIdTextViewId:
                    int position;
                    position = getAdapterPosition();
                    OrderMasterList orderList = orderMasterList.get(position);
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    DatabaseHandler db = new DatabaseHandler(context);
                    Order order = db.getOrderBasedOnSelectedOrderedID(orderList.getOrderID());
//                    OrderDetail orderDetail = db.getOrderDetailsBasedOnOrderID(orderList.getOrderID());
//                    order.setId(orderDetail.getOrderId());
                    OrderSessionManagement orderSessionManagement = new OrderSessionManagement(activity.getApplicationContext());
                    orderSessionManagement.saveSession(order);
                    //mCommunicator.respond(getAdapterPosition(), orderMasterList.get(getAdapterPosition()).getOrderID());
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.admin_panel_fragment_container,
                            new OrderDetailsPageInAdminPanelFragment()).commit();
                    break;
//                case R.id.CreatePDFButtonId:
//                    //The code for printing a PDF
//                    break;
            }

        }
    }
}
