package com.example.testcarappwithfragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testcarappwithfragment.Model.Car;
import com.example.testcarappwithfragment.Model.Order;
import com.example.testcarappwithfragment.Model.OrderDetail;
import com.example.testcarappwithfragment.Model.OrderMasterDetailList;
import com.example.testcarappwithfragment.SessionManager.CarSessionManagement;
import com.example.testcarappwithfragment.SessionManager.OrderSessionManagement;
import com.example.testcarappwithfragment.data.DatabaseHandler;
import com.example.testcarappwithfragment.data.Utils;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsPageInAdminPanelFragment extends Fragment {
    private TextView carModel;
    private ImageView carImageView;
    private TextView carQuantity;
    private TextView carPrice;
    private TextView carTotalPrice;
    private TextView carTotalPriceShow;
    private TextView streetAddress;
    private TextView cityState;
    private TextView pinCode;
    private TextView orderStatusValue;
    private Spinner orderStatusUpdateSpinner;
    private Button submitButton;
    private Order clickedOrderedCarItem;
    ArrayAdapter<String> adapter;
    ArrayList<String> quantityforSentinal = new ArrayList<>();
    private int clickedOrderId;
    private int clickedOrderId2;

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        clickedOrderId = getArguments().getInt("SELECTEDORDERID");
//    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_order_details_page_in_admin_panel, container, false);

        carModel = root.findViewById(R.id.CarModelForOrderDetailTextViewId);
        carImageView = root.findViewById(R.id.SelectedCarImageView);
        carQuantity = root.findViewById(R.id.CarQuantityForOrderDetailTextViewId);
        carPrice = root.findViewById(R.id.CarPriceForOrderDetailTextViewId);
        carTotalPrice = root.findViewById(R.id.CarTotalPriceForOrderDetailTextViewId);
        carTotalPriceShow = root.findViewById(R.id.CarTotalPriceShowForOrderDetailTextViewId);
        streetAddress = root.findViewById(R.id.StreetAddressForOrderDetailTextViewId);
        cityState = root.findViewById(R.id.CityStateForOrderDetailTextViewId);
        pinCode = root.findViewById(R.id.PinCodeForOrderDetailTextViewId);
        orderStatusValue = root.findViewById(R.id.OrderStatusValueTextViewId);
        orderStatusUpdateSpinner = root.findViewById(R.id.OrderStatusUpdateSpinnerId);
        submitButton = root.findViewById(R.id.SubmitButtonIdForOrderDetails);

        DatabaseHandler db = new DatabaseHandler(getContext());
        OrderSessionManagement orderSessionManagement = new OrderSessionManagement(getContext());
        clickedOrderId = orderSessionManagement.getSession();
//        clickedOrderId += 1; // Because we increment the orderdetail order ID by 1 in checkout page, for consistency
        clickedOrderId2 = orderSessionManagement.getSession();
//        clickedOrderId2 += 1;

        Log.d("Order Details", "ID OF Order: " + clickedOrderId);
        OrderDetail orderDetail = db.getOrderDetailsBasedOnOrderID(clickedOrderId);
        Car car = db.getCar(orderDetail.getCarId());

        Log.d("Order Details", "ID OF Order: " + clickedOrderId);
        Log.d("Order Details", "Product ID : " + orderDetail.getCarId());


        OrderMasterDetailList orderBasedOnID = db.addToOrderMasterDetailList(clickedOrderId);

        /*Now set the widgets*/
        carModel.setText(car.getModel());
        carImageView.setImageBitmap(Utils.getImage(car.getCarImage()));
        carQuantity.setText(String.valueOf(orderBasedOnID.getQuantity()));
        carPrice.setText(String.valueOf(orderBasedOnID.getUnitPrice()));
        carTotalPrice.setText(String.valueOf(orderBasedOnID.getTotalPrice()));
        carTotalPriceShow.setText(String.valueOf(orderBasedOnID.getTotalPrice()));

        Order order = db.getOrderBasedOnSelectedOrderedID(clickedOrderId2);
        streetAddress.setText(order.getAddress());
        cityState.setText(order.getCity());
        pinCode.setText(String.valueOf(order.getPincode()));
//        int statusValue = order.getOrderStatus();
//
//        switch (statusValue) {
//            case 1:
//                orderStatusValue.setText("pending");
//                break;
//            case 2:
//                orderStatusValue.setText("processing");
//                break;
//            case 3:
//                orderStatusValue.setText("shipped");
//                break;
//            case 4:
//                orderStatusValue.setText("canceled");
//                break;
//            default:
//                orderStatusValue.setText("complete");
//
//        }

        String statusValue = db.getOrderStatusBasedOnSelectedOrderedId(clickedOrderId);
        orderStatusValue.setText(statusValue);

        List<String> statusList = new ArrayList<>();
        statusList = db.getAllStatusNames();
        /*To add a hint in the spinner*/
        statusList.add(0, "Select a status value");


        /*For loading the spinner value*/
        //adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,statusList);


        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapter
                = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                statusList
        ) {
            @Override
            public boolean isEnabled(int position) {
                // Disable the first item from Spinner
                // First item will be use for hint
                return position != 0;
            }

            @Override
            public View getDropDownView(
                    int position, View convertView,
                    @NonNull ViewGroup parent) {

                // Get the item view
                View view = super.getDropDownView(
                        position, convertView, parent);
                TextView textView = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    textView.setTextColor(Color.GRAY);
                } else {
                    textView.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        // Set the drop down view resource
        spinnerArrayAdapter.setDropDownViewResource(
                android.R.layout.simple_dropdown_item_1line
        );

        // Spinner on item selected listener
        orderStatusUpdateSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent, View view,
                            int position, long id) {

                        // Get the spinner selected item text
                        String selectedItemText = (String) parent
                                .getItemAtPosition(position);

                        // If user change the default selection
                        // First item is disable and
                        // it is used for hint
                        if(position > 0){
                            // Notify the selected item text
                            Toast.makeText(
                                    getActivity().getApplicationContext(),
                                    "Selected : "
                                            + selectedItemText,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onNothingSelected(
                            AdapterView<?> parent) {
                    }
                });

        // Finally, data bind the spinner object with adapter
        orderStatusUpdateSpinner.setAdapter(spinnerArrayAdapter);

        /*Add a listener
         */
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //int flagPending = -1;
                //int flagprocessing = -1;
                //int flagshipped = -1;
                //int flagcanceled = -1;
                //int flagcomplete = -1;

                /*First Fetch the Selected item*/
                String updatedStatus = orderStatusUpdateSpinner.getSelectedItem().toString();
                int updatedStatusId;
                /*Now we will update the order table based on the selected status
                 * from the spinner*/
                switch (updatedStatus) {
                    case "pending":
                        updatedStatusId = 1;
                        break;
                    case "processing":
                        updatedStatusId = 2;
                        break;
                    case "shipped":
                        updatedStatusId = 3;
                        break;
                    case "canceled":
                        updatedStatusId = 4;
                        break;
                    case "complete":
                        updatedStatusId = 5;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + updatedStatus);
                }

                /*First get the current order status value*/
                String currentOrderStatus = orderStatusValue.getText().toString().trim();
                if(currentOrderStatus.equals("processing") && updatedStatusId != 1){
                    db.updateStatusValueBasedOnOrderId(clickedOrderId, updatedStatusId);
                    /*Set the updated status value in the TextView*/
                    orderStatusValue.setText(updatedStatus);
                    Snackbar.make(getView(), "Order Status has been updated !!!", Snackbar.LENGTH_LONG).show();
                }else if(currentOrderStatus.equals("shipped") && updatedStatusId != 1 && updatedStatusId != 2){
                    db.updateStatusValueBasedOnOrderId(clickedOrderId, updatedStatusId);
                    /*Set the updated status value in the TextView*/
                    orderStatusValue.setText(updatedStatus);
                    Snackbar.make(getView(), "Order Status has been updated !!!", Snackbar.LENGTH_LONG).show();

                }else if(currentOrderStatus.equals("complete") && updatedStatusId != 1 && updatedStatusId != 2 && updatedStatusId != 3 && updatedStatusId != 4){
                    db.updateStatusValueBasedOnOrderId(clickedOrderId, updatedStatusId);
                    /*Set the updated status value in the TextView*/
                    orderStatusValue.setText(updatedStatus);
                    Snackbar.make(getView(), "Order Status has been updated !!!", Snackbar.LENGTH_LONG).show();

                }else if(currentOrderStatus.equals("canceled")){
                    Snackbar.make(getView(), "Order Status can't be updated. It has already been canceled!!!", Snackbar.LENGTH_LONG).show();
                }else{
                    Snackbar.make(getView(), "Can't update. Wrong order chosen !!!", Snackbar.LENGTH_LONG).show();
                }
                //db.updateStatusValueBasedOnOrderId(clickedOrderId, updatedStatusId);
                /*Set the updated status value in the TextView*/
                //orderStatusValue.setText(updatedStatus);
                //Snackbar.make(getView(), "Order Status has been updated !!!", Snackbar.LENGTH_LONG).show();


                /*Also remove the orderId Session*/
                orderSessionManagement.removeSession();

            }
        });


        return root;
    }
}
