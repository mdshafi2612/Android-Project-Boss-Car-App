package com.example.testcarappwithfragment.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcarappwithfragment.Model.CarBrand;
import com.example.testcarappwithfragment.Model.Customer;
import com.example.testcarappwithfragment.R;
import com.example.testcarappwithfragment.data.DatabaseHandler;

import java.text.MessageFormat;
import java.util.List;

public class UserMasterAdpater extends RecyclerView.Adapter<UserMasterAdpater.ViewHolder> {
    private Context context;
    private List<Customer> customerList;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    public UserMasterAdpater(Context context, List<Customer> customerList) {
        this.context = context;
        this.customerList = customerList;
    }

    @NonNull
    @Override



    public UserMasterAdpater.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        /*Here we inflate the list_row.xml into the Android view object and pass that
         * view into the ViewHolder class constructor*/
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sample_layout_for_fragment_user_master,
                        viewGroup,
                        false);

        return new UserMasterAdpater.ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull UserMasterAdpater.ViewHolder holder, int position) {

        Customer customer = customerList.get(position);

        holder.userName.setText(MessageFormat.format("Username: {0}", customer.getCustomerName()));
        holder.userEmail.setText(MessageFormat.format("User Email: {0}", customer.getEmail()));
        holder.userMobile.setText(MessageFormat.format("User Mobile: {0}", String.valueOf(customer.getPhoneNumber())));
        holder.userAddress.setText(MessageFormat.format("Address: {0}", String.valueOf(customer.getAddress())));
        holder.userPassword.setText(MessageFormat.format("Password: {0}", customer.getPassword()));

    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView userName;
        public TextView userEmail;
        public TextView userMobile;
        public TextView userAddress;
        public TextView userPassword;
        public Button deleteButton;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            userName = itemView.findViewById(R.id.usernameId);
            userEmail = itemView.findViewById(R.id.useremailId);
            userMobile = itemView.findViewById(R.id.usermobileId);
            userAddress = itemView.findViewById(R.id.useraddressId);
            userPassword = itemView.findViewById(R.id.userpasswordId);
            deleteButton = itemView.findViewById(R.id.deleteuserButton);

            deleteButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            /*First when the user clicks on a item, that item's position
             * can be found by the getAdapterPosition() method*/
            int position;
            position = getAdapterPosition();
            /*The clicked position's object is now in item variable*/
            Customer customer = customerList.get(position);
            if (view.getId() == R.id.deleteuserButton) {//Delete Item
                deleteCustomer(customer.getEmail());
            }

        }

        private void deleteCustomer(String email) {
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
                    db.deleteCustomer(email);
                    /*Now when we delete a item, we want to make sure that the item is also deleted
                     * from the CardView*/
                    customerList.remove(getAdapterPosition());
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
