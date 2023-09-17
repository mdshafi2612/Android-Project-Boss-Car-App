package com.example.testcarappwithfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeBeforeLoginFragment extends Fragment {


    ImageSlider imageSlider;
    private RecyclerView newCarsRecyclerview, carPartsRecyclerView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home_before_login, container, false);

        /*Code Section for adding Auto ImageSlider*/
        imageSlider = root.findViewById(R.id.image_slider);


        /*For showing the ImageSLider*/
        ArrayList<SlideModel> imagelist = new ArrayList<>();

        /*Adding the images to the SlideModel*/
        /*We can also use the CenterCrop property for the ScaleType
         * and also we can collect image from Internet URL, for
         * that we need to set the INTERNET Connectivity
         * in the menifest file*/
        imagelist.add(new SlideModel(R.drawable.car_image1,"This is car1", ScaleTypes.FIT));
        imagelist.add(new SlideModel(R.drawable.car_image2, "This is car2",ScaleTypes.FIT));
        imagelist.add(new SlideModel(R.drawable.car_image3, "This is car3",ScaleTypes.FIT));

        imageSlider.setImageList(imagelist);

        /*Code section for New Cars RecyclerView*/
        // best RecyclerView

        newCarsRecyclerview = root.findViewById(R.id.newcarsRecyclerViewId);
        newCarsRecyclerview.setHasFixedSize(true);
        newCarsRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity() , LinearLayoutManager.HORIZONTAL , false));

        List<NewCars> newCarList = new ArrayList<>();

        newCarList.add(new NewCars(R.drawable.car_image1 , "Up to 20% off"));
        newCarList.add(new NewCars(R.drawable.car_image2 , "Up to 20% off"));
        newCarList.add(new NewCars(R.drawable.car_image3 , "Up to 20% off"));

        NewCarAdapter newCarAdapter = new NewCarAdapter(newCarList);

        newCarsRecyclerview.setAdapter(newCarAdapter);

        /*Code section for Service And Repair RecyclerView*/

        carPartsRecyclerView = root.findViewById(R.id.carPartsRecyclerViewId);
        carPartsRecyclerView.setHasFixedSize(true);
        carPartsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity() , LinearLayoutManager.HORIZONTAL , false));

        List<ServiceAndRepair> carPartsList = new ArrayList<>();

        carPartsList.add(new ServiceAndRepair(R.drawable.carwheel , "Up to 30% off"));
        carPartsList.add(new ServiceAndRepair(R.drawable.carwheel , "Up to 40% off"));
        carPartsList.add(new ServiceAndRepair(R.drawable.carwheel , "Up to 50% off"));

        ServiceAndRepairAdapter serviceAndRepairAdapter = new ServiceAndRepairAdapter(carPartsList);

        carPartsRecyclerView.setAdapter(serviceAndRepairAdapter);

        return root;
    }
}
