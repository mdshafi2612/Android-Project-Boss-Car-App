package com.example.testcarappwithfragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcarappwithfragment.Model.Customer;
import com.example.testcarappwithfragment.Model.contact_us;
import com.example.testcarappwithfragment.R;
import com.example.testcarappwithfragment.data.DatabaseHandler;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.text.MessageFormat;
import java.util.List;

public class ContactUsMasterAdpater extends RecyclerView.Adapter<ContactUsMasterAdpater.ViewHolder> {

    private Context context;
    private List<contact_us> contact_usList;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;


    public ContactUsMasterAdpater(Context context, List<contact_us> contact_usList) {
        this.context = context;
        this.contact_usList = contact_usList;
    }

    @NonNull
    @Override
    public ContactUsMasterAdpater.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        /*Here we inflate the list_row.xml into the Android view object and pass that
         * view into the ViewHolder class constructor*/
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sample_layout_for_fragment_contact_master,
                        viewGroup,
                        false);

        return new ContactUsMasterAdpater.ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactUsMasterAdpater.ViewHolder holder, int position) {
        contact_us contactUs = contact_usList.get(position);

        holder.contactUsID.setText(MessageFormat.format("ID: {0}", contactUs.getId()));
        holder.userName.setText(MessageFormat.format("User Name: {0}", contactUs.getName()));
        holder.userEmail.setText(MessageFormat.format("User Email: {0}", contactUs.getEmail()));
        holder.userMobile.setText(MessageFormat.format("User Mobile: {0}", String.valueOf(contactUs.getMobile())));
        holder.userComment.setText(MessageFormat.format("User Comment: {0}", contactUs.getComment()));
        holder.commentAddedOn.setText(MessageFormat.format("Added On: {0}", contactUs.getAddedOn()));
    }

    @Override
    public int getItemCount() {
        return contact_usList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView contactUsID;
        public TextView userName;
        public TextView userEmail;
        public TextView userMobile;
        public TextView userComment;
        public TextView commentAddedOn;
        public Button deleteComment;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            contactUsID = itemView.findViewById(R.id.ContactUsId);
            userName = itemView.findViewById(R.id.usernameId);
            userEmail = itemView.findViewById(R.id.useremailId);
            userMobile = itemView.findViewById(R.id.usermobileId);
            userComment = itemView.findViewById(R.id.userCommentId);
            commentAddedOn = itemView.findViewById(R.id.CommentAddedOnId);
            deleteComment = itemView.findViewById(R.id.deleteCommentButton);

            deleteComment.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            /*First when the user clicks on a item, that item's position
             * can be found by the getAdapterPosition() method*/
            int position;
            position = getAdapterPosition();
            DatabaseHandler db = new DatabaseHandler(context);
            /*The clicked position's object is now in item variable*/
            contact_us contactUs = contact_usList.get(position);
            if (view.getId() == R.id.deleteCommentButton) {//Delete Item
//                long result = db.deleteCommentFromDB(contactUs.getId());
//                if(result != -1){
//                    Snackbar.make(view, "Comment is deleted successfully", Snackbar.LENGTH_SHORT).show();
//                }else{
//                    Snackbar.make(view, "Deletion is unsuccessful!!!, check logcat", Snackbar.LENGTH_LONG).show();
//                }

                deleteCOmmentMethod(contactUs.getId());
            }
        }

        private void deleteCOmmentMethod(int id) {
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
                    long result = db.deleteCommentFromDB(id);
                    if (result != -1) {
                        Snackbar.make(view, "Comment is deleted successfully", Snackbar.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(view, "Deletion is unsuccessful!!!, check logcat", Snackbar.LENGTH_LONG).show();
                    }
                    /*Now when we delete a item, we want to make sure that the item is also deleted
                     * from the CardView*/
                    contact_usList.remove(getAdapterPosition());
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
