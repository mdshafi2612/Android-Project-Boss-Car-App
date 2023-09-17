package com.example.testcarappwithfragment.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcarappwithfragment.Model.NewCars;
import com.example.testcarappwithfragment.R;

import java.util.List;

public class NewCarAdapter extends RecyclerView.Adapter<NewCarAdapter.BestSellerViewHolder>{

    private List<NewCars> NewCarsList;
    public NewCarAdapter(List<NewCars> NewCarsList){
        this.NewCarsList = NewCarsList;
    }
    @NonNull
    @Override
    public BestSellerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top5newcars_layoutinrecyclerview , parent , false);
        return new BestSellerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BestSellerViewHolder holder, int position) {
        holder.mText.setText(NewCarsList.get(position).getOffer());
        holder.mImageview.setImageResource(NewCarsList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return NewCarsList.size();
    }

    public class BestSellerViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImageview;
        private TextView mText;
        public BestSellerViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageview = itemView.findViewById(R.id.NewCarsImage);
            mText = itemView.findViewById(R.id.newCarsText);
        }
    }
}
