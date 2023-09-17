package com.example.testcarappwithfragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.graphics.ImageDecoderKt;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.testcarappwithfragment.Adapter.CartPageForCarAdapter;
import com.example.testcarappwithfragment.Adapter.OrderPageAdapter;
import com.example.testcarappwithfragment.Model.Car;
import com.example.testcarappwithfragment.Model.Cart;
import com.example.testcarappwithfragment.Model.Customer;
import com.example.testcarappwithfragment.Model.Order;
import com.example.testcarappwithfragment.Model.OrderDetail;
import com.example.testcarappwithfragment.SessionManager.OrderSessionManagement;
import com.example.testcarappwithfragment.SessionManager.UserSessionManagement;
import com.example.testcarappwithfragment.data.DatabaseHandler;
import com.google.android.material.snackbar.Snackbar;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutPageForCarFragment extends Fragment {

    private RecyclerView recyclerView;
    private OrderPageAdapter recyclerViewAdapter;
    private List<Cart> loggedUsersCartList;
    private Cart LoggedUserCartItem;
    private int loggedUserId;
    private DatabaseHandler databaseHandler;
    private EditText streetAddress;
    private EditText cityStateAddress;
    private EditText zipCodeAmount;
    private Button decreaseZipCodeAmount;
    private Button increaseZipCodeAmount;
    private Button SubmitButton;
    private RadioGroup paymentMethodRadioGroup;
    private RadioButton paymentMethodRadioButton;
    private int pincode;
    /*Variable for online payment integration*/
    String SECRET_KEY = "sk_test_51LqMufC4SVJQFakZs71wLUkQNy1kaGyZs3pCrkC1XLLGM880IfGN16NYytlK182GV9Sh0g0RIopb7jT7WYtfB7Yf00TsXHEj2l";
    String PUBLISH_KEY = "pk_test_51LqMufC4SVJQFakZpkMTyh2oD88V1gWaohqeOhU9Dzq90Wu8k1eduCepcP930AX5uCFlmnqPBuylvu4PPltoDDrS00o5wNfypK";
    PaymentSheet paymentSheet;
    int totalPriceForOnlinePayment;
    String total;

    String customerID;
    String EphericalKey;
    String ClientSecret;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_checkout_page_for_car, container, false);

//        /*These two lines of code is needed if we want to access the included xml files widget
//        * in our java code*/
//        View includedLayout = root.findViewById(R.id.InnerRecyclerViewId);
//        recyclerView = includedLayout.findViewById(R.id.CheckoutPageRecyclerViewId);

        recyclerView = root.findViewById(R.id.CheckoutPageRecyclerViewId);
        databaseHandler = new DatabaseHandler(getActivity());
        recyclerView.setHasFixedSize(true); //ensure that everything inside the RecyclerView is aligned correctly
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        /*Get the user ID*/
        UserSessionManagement loggedUser = new UserSessionManagement(getContext());

        loggedUserId = loggedUser.getSession();
        /*Fetch the Cart object based on loggedUserId*/
        databaseHandler = new DatabaseHandler(getContext());

        loggedUsersCartList = new ArrayList<>();
//        Cart loggedUsersCart = new Cart();
        loggedUsersCartList = databaseHandler.getAllCartItemBasedOnLoggedUserId(loggedUserId);
        double totalPrice;
        //LoggedUserCartItem = databaseHandler.getCartItemBasedOnLoggedUsersId(loggedUserId);

        /*Now instantiate the RecyclerView and set the adapter*/
        /*Here itemList contains all the items, we are passing the full
         * arrayList of items to the RecyclerViewAdapter constructor
         * the recycler view will show them in a List manner in the UI.*/
        recyclerViewAdapter = new OrderPageAdapter(getActivity(), loggedUsersCartList, loggedUserId);
        recyclerView.setAdapter(recyclerViewAdapter);
        /*We have to notify the RecylerView in case there is a change*/
        recyclerViewAdapter.notifyDataSetChanged();

        PaymentConfiguration.init(getActivity(), PUBLISH_KEY);

        paymentSheet = new PaymentSheet(this, paymentSheetResult -> {
            onPaymentResult(paymentSheetResult);
        });

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/customers",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        /*Upon getting the response, we will retrieve our customer id*/
                        try {
                            JSONObject object = new JSONObject(response);
                            /*Here we fetch the customerID based on the key "id"
                             * We get this key from the Postman Authorization token bearer*/
                            customerID = object.getString("id");
                            Toast.makeText(getActivity(), "customerID value : " + customerID, Toast.LENGTH_SHORT).show();

                            getEphericalKey(customerID);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            /*Here we programmatically do the task of authorization using the Volley
             * library which we are easily doing in the Postman*/
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                /*This is our authorization bearer token*/
                header.put("Authorization", "Bearer " + SECRET_KEY);
                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


        streetAddress = root.findViewById(R.id.StreetAddressForCheckoutEditTextId);
        cityStateAddress = root.findViewById(R.id.CityStateForCheckoutEditTextId);
        zipCodeAmount = root.findViewById(R.id.ZipCodeAmountForCheckoutId);
        decreaseZipCodeAmount = root.findViewById(R.id.decreaseZipButtonId);
        increaseZipCodeAmount = root.findViewById(R.id.increaseIncreaseZipButtonId);
        SubmitButton = root.findViewById(R.id.SubmitButtonForCheckoutId);
        paymentMethodRadioGroup = root.findViewById(R.id.radioGroupForPaymentId);
//        /*First we get the id of the selected radio button*/
//        int selectedId = paymentMethodRadioGroup.getCheckedRadioButtonId();
//        /*Now we will assign the id in the radioButton variable*/
//        paymentMethodRadioButton = root.findViewById(selectedId);
//        String paymentMethod;

//        View view;
//        view = getView();
//
//        public void checkedButton(View v) {
//            int radioId = paymentMethodRadioGroup.getCheckedRadioButtonId();
//
//            paymentMethodRadioButton = root.findViewById(radioId);
//
//            Toast.makeText(this, "Selected Radio Button: " + radioButton.getText(),
//                    Toast.LENGTH_SHORT).show();
//        }


        decreaseZipCodeAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pincode = Integer.parseInt(zipCodeAmount.getText().toString());
                if (pincode > 0) {
                    pincode -= 1;
                    zipCodeAmount.setText(String.valueOf(pincode));
                } else {
                    Snackbar.make(getView(), "You can not decrease beyond 0!!!", Snackbar.LENGTH_LONG).show();
                }

            }
        });
        increaseZipCodeAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pincode = Integer.parseInt(zipCodeAmount.getText().toString());
                pincode += 1;
                zipCodeAmount.setText(String.valueOf(pincode));
            }
        });




        /*After clicking the submit button, we will insert data on the order table and order details table*/
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = paymentMethodRadioGroup.getCheckedRadioButtonId();

                paymentMethodRadioButton = root.findViewById(radioId);

                /* textView.setText("Your choice: " + radioButton.getText());*/
                String paymentMethod = paymentMethodRadioButton.getText().toString();


                if (paymentMethod.equals("COD")) {
                    /*For each cart item, we will insert data in the order table*/
                    for (int i = 0; i < loggedUsersCartList.size(); i++) {
                        String address = streetAddress.getText().toString().trim();
                        String city = cityStateAddress.getText().toString().trim();
                        int pinCode;

                        String paymentType = paymentMethodRadioButton.getText().toString();
                        if (!address.isEmpty() && !city.isEmpty()
                                && !zipCodeAmount.getText().toString().isEmpty()
                                && !paymentType.isEmpty()) {
                            pinCode = Integer.parseInt(zipCodeAmount.getText().toString());
                            double totalPrice = loggedUsersCartList.get(i).getTotalPrice();
                            /*Here we will insert data in the order table and order details table*/
                            /*Now create a Order object and set the attributes*/
                            Order order = new Order();
                            order.setUserId(loggedUserId);
                            order.setAddress(address);
                            order.setCity(city);
                            order.setPincode(pinCode);
                            order.setPaymentType(paymentType);
                            order.setTotalPrice(totalPrice);
                            order.setPaymentStatus("Cash on delivery");
                            /*Initially by default all payment status are pending*/
                            order.setOrderStatus(1);
                            Date date = new Date();
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat DateFor = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                            String stringDate = DateFor.format(date);
                            order.setAddedOn(stringDate);

                            long result = databaseHandler.addOrder(order);

                            if (result != -1) {
                                Snackbar.make(getView(), "Order has been inserted successfully for car " + loggedUsersCartList.get(i).getCarModel() + "!!!", Snackbar.LENGTH_LONG).show();
                            } else {
                                Snackbar.make(getView(), "Order insertion failed!!!", Snackbar.LENGTH_LONG).show();
                            }

                            //Here we will add data to the order details table
                            int latestOrderId = databaseHandler.getLatestOrderId();
//                        int uniqueOrderId = latestOrderId + 1;
                            /*Now save the order session */
                            OrderSessionManagement orderSessionManagement = new OrderSessionManagement(getContext());
                            Order order1 = databaseHandler.getOrderBasedOnSelectedOrderedID(latestOrderId);
                            orderSessionManagement.saveSession(order1);
                            OrderDetail orderDetail = new OrderDetail();
                            orderDetail.setOrderId(latestOrderId);
                            orderDetail.setCarId(loggedUsersCartList.get(i).getCarId());
                            orderDetail.setQuantity(loggedUsersCartList.get(i).getQuantity());
                            orderDetail.setPrice(loggedUsersCartList.get(i).getTotalPrice());
                            Date date1 = new Date();
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat DateFor1 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                            String stringDate1 = DateFor.format(date);
                            orderDetail.setAddedOn(stringDate1);
//                      /*Now add the data in the orderDetails Table*/
                            long result2 = databaseHandler.addOrderDetails(orderDetail);

                            if (result2 != -1) {
                                Snackbar.make(getView(), "Order details have been inserted successfully for car " + loggedUsersCartList.get(i).getCarModel() + "!!!", Snackbar.LENGTH_LONG).show();
                                /*Remove the session*/
                                orderSessionManagement.removeSession();

                                /*Also update the cart Item's quantity*/
                                Car car = databaseHandler.getCar(loggedUsersCartList.get(i).getCarId());

                                int carTotalQuantity = car.getQuantity();
                                Log.d("Checkout ", "onClick: " + carTotalQuantity);
                                int quantityToBeSubtracted = loggedUsersCartList.get(i).getQuantity();
                                Log.d("Checkout ", "onClick: " + quantityToBeSubtracted);
                                int quantityAfterOrder = carTotalQuantity - quantityToBeSubtracted;
                                Log.d("Checkout ", "onClick: " + quantityAfterOrder);
                                if (quantityAfterOrder == 0) {
                                    /*All cars of that model have been sold, so delete the car from the db */
                                    databaseHandler.deleteCar(loggedUsersCartList.get(i).getCarId());
                                    Snackbar.make(getView(), loggedUsersCartList.get(i).getCarModel() + " has been deleted from the DB, Out of stock!!!", Snackbar.LENGTH_LONG).show();

                                } else {
                                    /*Update the quantity*/
                                    Car car2 = databaseHandler.getCar(loggedUsersCartList.get(i).getCarId());
                                    long result3 = databaseHandler.updateLoggedUserCarItemQuantityAfterOrder(car2, loggedUserId, loggedUsersCartList.get(i).getCarId(), quantityAfterOrder);
                                    if (result3 != -1) {
                                        Snackbar.make(getView(), "Car  " + loggedUsersCartList.get(i).getCarModel() + " quantity has been updated!!!", Snackbar.LENGTH_LONG).show();
                                    } else {
                                        Snackbar.make(getView(), "Quantity update failed, Check logcat!!!", Snackbar.LENGTH_LONG).show();
                                    }
                                }

                                /*Also delete the cart item also*/

                                Customer user = databaseHandler.getCustomerBasedOnId(loggedUserId);
                                databaseHandler.deleteLoggedUsersCartItem(loggedUserId, loggedUsersCartList.get(i).getCarId());
                                Snackbar.make(getView(), "Car  " + loggedUsersCartList.get(i).getCarModel() + " has been removed from the " + user.getCustomerName() + "'s cart!!!", Snackbar.LENGTH_LONG).show();
                            } else {
                                Snackbar.make(getView(), "Order detail insertion failed!!!", Snackbar.LENGTH_LONG).show();
                            }
                        } else {
                            Snackbar.make(v, "Please enter all the fields!!!", Snackbar.LENGTH_SHORT).show();
                        }

//                        uniqueOrderId += 1;

                    }
                } else {
                    //Here we will perform the online payment using Stripe
                    double totalPrice = 0;
                    for (int i = 0; i < loggedUsersCartList.size(); i++) {
                        totalPrice += loggedUsersCartList.get(i).getTotalPrice();


                    }
                    totalPriceForOnlinePayment = (int) totalPrice;
                    total = String.valueOf(totalPriceForOnlinePayment);
                    if (total != null) {
                        PaymentFlow();
                    }

                }


            }
        });


        return root;


    }

    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {

        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            Toast.makeText(getActivity(), "Payment Success !!!", Toast.LENGTH_SHORT).show();

        } else if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            Toast.makeText(getActivity(), "Payment Canceled", Toast.LENGTH_SHORT).show();
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            Toast.makeText(getActivity(), "Payment Failed", Toast.LENGTH_SHORT).show();
        }

    }

    private void getEphericalKey(String customerID) {

        /*Here we perform another request to get the epherical Key*/

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/ephemeral_keys",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        /*Upon getting the response, we will retrieve our customer id*/
                        try {
                            JSONObject object = new JSONObject(response);
                            /*Here we fetch the customerID based on the key "id"
                             * We get this key from the Postman Authorization token bearer*/
                            EphericalKey = object.getString("id");
                            Toast.makeText(getActivity(), "EphericalKey value : " + EphericalKey, Toast.LENGTH_SHORT).show();

                            getClientSecret(customerID, EphericalKey);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            /*Here we programmatically do the task of authorization using the Volley
             * library which we are easily doing in the Postman*/
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                /*This is our authorization bearer token*/
                header.put("Authorization", "Bearer " + SECRET_KEY);
                header.put("Stripe-Version", "2022-08-01");
                return header;
            }

            /*When the getHeaders method is terminated, we will call the
             * getParams() method, because we have the header also for epherical key*/

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer", customerID);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }

    private void getClientSecret(String customerID, String ephericalKey) {
        /*Here in this method, we will launch our payment intent*/

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/payment_intents",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        /*Upon getting the response, we will retrieve our customer id*/
                        try {
                            JSONObject object = new JSONObject(response);
                            /*Here we fetch the customerID based on the key "id"
                             * We get this key from the Postman Authorization token bearer*/
                            ClientSecret = object.getString("client_secret");
                            Toast.makeText(getActivity(), "Client Secret value : " + ClientSecret, Toast.LENGTH_SHORT).show();

                            /*After fetching the client secret, we will launch
                             * the payment sheet*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            /*Here we programmatically do the task of authorization using the Volley
             * library which we are easily doing in the Postman*/
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                /*This is our authorization bearer token*/
                header.put("Authorization", "Bearer " + SECRET_KEY);
                header.put("Stripe-Version", "2022-08-01");
                return header;
            }

            /*When the getHeaders method is terminated, we will call the
             * getParams() method, because we have the header also for epherical key*/

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer", customerID);
                /*Stripe considers the last two digits as the decimal point
                 * that's why we need to concatenate two 00, otherwise,
                 * stripe will consider 10$, not 1000$
                 * Note: we will pass the value of our currency amount from the Checkout page
                 * for illustration, the value is set to 1000$*/
                params.put("amount", "1000" + "00");
                params.put("currency", "usd");
                params.put("automatic_payment_methods[enabled]", "true");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }

    private void PaymentFlow() {

        paymentSheet.presentWithPaymentIntent(
                ClientSecret, new PaymentSheet.Configuration("Boss Car Company"
                        , new PaymentSheet.CustomerConfiguration(
                        customerID,
                        EphericalKey
                ))
        );

    }
}
