package com.example.testcarappwithfragment.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.testcarappwithfragment.Model.ServiceAndRepair;
import com.example.testcarappwithfragment.R;

import java.util.List;

public class ServiceAndRepairAdapter extends RecyclerView.Adapter<ServiceAndRepairAdapter.BestSellerViewHolder>{

    private List<ServiceAndRepair> carPartsList;
    public ServiceAndRepairAdapter(List<ServiceAndRepair> carPartsList){
        this.carPartsList = carPartsList;
    }
    @NonNull
    @Override
    public BestSellerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carparts_layout , parent , false);
        return new BestSellerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BestSellerViewHolder holder, int position) {
        holder.mText.setText(carPartsList.get(position).getOffer());
        holder.mImageview.setImageResource(carPartsList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return carPartsList.size();
    }

    public class BestSellerViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImageview;
        private TextView mText;
        public BestSellerViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageview = itemView.findViewById(R.id.carPartsImage);
            mText = itemView.findViewById(R.id.carPartsText);
        }
    }
}
