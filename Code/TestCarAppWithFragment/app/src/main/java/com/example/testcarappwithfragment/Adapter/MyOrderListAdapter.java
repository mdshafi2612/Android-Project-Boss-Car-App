package com.example.testcarappwithfragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcarappwithfragment.Model.Car;
import com.example.testcarappwithfragment.Model.OrderDetail;
import com.example.testcarappwithfragment.Model.OrderMasterDetailList;
import com.example.testcarappwithfragment.R;
import com.example.testcarappwithfragment.SessionManager.OrderSessionManagement;
import com.example.testcarappwithfragment.data.DatabaseHandler;
import com.example.testcarappwithfragment.data.Utils;

import java.text.MessageFormat;
import java.util.List;

public class MyOrderListAdapter extends RecyclerView.Adapter<MyOrderListAdapter.ViewHolder> {

    private Context context;
    private List<OrderMasterDetailList> orderMasterDetailLists;

    public MyOrderListAdapter(Context context, List<OrderMasterDetailList> orderMasterDetailLists) {
        this.context = context;
        this.orderMasterDetailLists = orderMasterDetailLists;
    }

    @NonNull
    @Override
    public MyOrderListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        /*Here we inflate the list_row.xml into the Android view object and pass that
         * view into the ViewHolder class constructor*/
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sample_layout_for_fragment_my_order_list,
                        viewGroup,
                        false);
        return new MyOrderListAdapter.ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderListAdapter.ViewHolder holder, int position) {

        DatabaseHandler db = new DatabaseHandler(context);


        OrderMasterDetailList orderMasterDetailList = orderMasterDetailLists.get(position);
        Car car = db.getCar(orderMasterDetailList.getCarModelName());

//        Order order = db.get
//        OrderDetail orderDetail = db.getOrderDetailsBasedOnOrderID(orderMasterDetailList.get)

        holder.productImageView.setImageBitmap(Utils.getImage(car.getCarImage()));
        holder.orderItemName.setText(MessageFormat.format("Ordered Item Name: {0}", car.getModel()));
        holder.orderItemQuantity.setText(MessageFormat.format("Quantity: {0}", orderMasterDetailList.getQuantity()));
        holder.orderItemPrice.setText(MessageFormat.format("Price: {0}", String.valueOf(orderMasterDetailList.getUnitPrice())));
        holder.orderItemTotalPrice.setText(MessageFormat.format("Total Price: {0}", String.valueOf(orderMasterDetailList.getTotalPrice())));

    }

    @Override
    public int getItemCount() {
        return orderMasterDetailLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

      public ImageView productImageView;
        public TextView orderItemName;
        public TextView orderItemQuantity;
        public TextView orderItemPrice;
        public TextView orderItemTotalPrice;
//        public Button createPDFButton;




        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            productImageView = itemView.findViewById(R.id.OrderedProductImageViewId) ;
            orderItemName = itemView.findViewById(R.id.OrderItemNameId) ;
            orderItemQuantity = itemView.findViewById(R.id.OrderItemQuantityId) ;
            orderItemPrice = itemView.findViewById(R.id.PriceTextViewId) ;
            orderItemTotalPrice = itemView.findViewById(R.id.TotalPriceTextViewId) ;
//            createPDFButton = itemView.findViewById(R.id.CreatePDFButtonId) ;
//
//            createPDFButton.setOnClickListener(this);



        }

        @Override
        public void onClick(View view) {
            /*First when the user clicks on a item, that item's position
             * can be found by the getAdapterPosition() method*/
            int position;
            position = getAdapterPosition();
            OrderMasterDetailList orderMasterDetailList = orderMasterDetailLists.get(position);
            OrderSessionManagement orderSessionManagement = new OrderSessionManagement(context);

            switch(view.getId()){
//                case R.id.CreatePDFButtonId:
//
//
//                    break;

            }
        }
    }
}
