package com.example.testcarappwithfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.testcarappwithfragment.Adapter.NewCarAdapter;
import com.example.testcarappwithfragment.Adapter.ServiceAndRepairAdapter;
import com.example.testcarappwithfragment.Model.NewCars;
import com.example.testcarappwithfragment.Model.ServiceAndRepair;

import java.util.ArrayList;
import java.util.List;

public class AdminDashboardFragment extends Fragment {



    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;
    private ImageView imageView6;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_admin_dashboard, container, false);


        imageView1 = root.findViewById(R.id.imageView2);
        imageView2 = root.findViewById(R.id.imageView3);
        imageView3 = root.findViewById(R.id.imageView4);
        imageView4 = root.findViewById(R.id.imageView5);
        imageView5 = root.findViewById(R.id.imageView6);
        imageView6 = root.findViewById(R.id.imageView7);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.admin_panel_fragment_container,
                        new CarMasterFragment()).commit();
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.admin_panel_fragment_container,
                        new CarBrandMasterFragment()).commit();
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.admin_panel_fragment_container,
                        new OrderMasterFragment()).commit();
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.admin_panel_fragment_container,
                        new UserMasterFragment()).commit();
            }
        });

        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.admin_panel_fragment_container,
                        new ContactMasterFragment()).commit();
            }
        });

        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.admin_panel_fragment_container,
                        new CarPartsMasterFragment()).commit();
            }
        });


        return root;
    }
}
