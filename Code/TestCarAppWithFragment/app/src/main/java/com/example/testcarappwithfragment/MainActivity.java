package com.example.testcarappwithfragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.testcarappwithfragment.Adapter.NewCarAdapter;
import com.example.testcarappwithfragment.Adapter.ServiceAndRepairAdapter;
import com.example.testcarappwithfragment.Model.Car;
import com.example.testcarappwithfragment.Model.Customer;
import com.example.testcarappwithfragment.Model.NewCars;
import com.example.testcarappwithfragment.Model.ServiceAndRepair;
import com.example.testcarappwithfragment.Model.WishlistCarProperty;
import com.example.testcarappwithfragment.SessionManager.UserSessionManagement;
import com.example.testcarappwithfragment.data.DatabaseHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private BottomNavigationView bottomNav;
    private TextView navigationHeaderTextView;
    DatabaseHandler databaseHandler;
    int userID;
//    private ResetPasswordFragment resetPasswordFragment;
//    private ForgetPasswordFragment forgetPasswordFragment;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawerId);
        NavigationView navigationView = findViewById(R.id.NavigationId);
        View headerLayout = navigationView.getHeaderView(0);
        navigationHeaderTextView = headerLayout.findViewById(R.id.navHeaderTextViewId);
        navigationView.setNavigationItemSelectedListener(this);

        //Show or hide menu items
        /*Here we hide the those menu items which can not be shown before a user logged in
         * We will show this menu item once the user is logged in */
        Menu menu = navigationView.getMenu();
        UserSessionManagement sessionManagement = new UserSessionManagement(MainActivity.this);
        userID = sessionManagement.getSession();
        Log.d("Main Activity", "onCreate: " + userID);
        databaseHandler = new DatabaseHandler(MainActivity.this);


        if (userID != -1) {
            Customer customer = databaseHandler.getCustomerBasedOnId(userID);
            if (customer != null) {
                navigationHeaderTextView.setText("Welcome " + customer.getCustomerName() + " to Boss Car App");
            }

            menu.findItem(R.id.loginMenuItem).setVisible(false);
            menu.findItem(R.id.userProfileMenuItem).setVisible(true);
            menu.findItem(R.id.userCartMenuItem).setVisible(true);
            menu.findItem(R.id.userOrderMenuItem).setVisible(true);
            menu.findItem(R.id.userWishlistMenuItem).setVisible(true);
            menu.findItem(R.id.userLogoutMenuItem).setVisible(true);
        } else {
            menu.findItem(R.id.loginMenuItem).setVisible(true);
            menu.findItem(R.id.userProfileMenuItem).setVisible(false);
            menu.findItem(R.id.userCartMenuItem).setVisible(false);
            menu.findItem(R.id.userOrderMenuItem).setVisible(false);
            menu.findItem(R.id.userWishlistMenuItem).setVisible(false);
            menu.findItem(R.id.userLogoutMenuItem).setVisible(false);
        }


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeBeforeLoginFragment()).commit();
            navigationView.setCheckedItem(R.id.homeMenuItem);
        }

        /*Code section for Bottom Navigation View*/
        bottomNav = findViewById(R.id.bottom_navigator);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeBeforeLoginFragment()).commit();
        }

//        //Receive the data passed from the MainActivity and store it in the bundle variable
//        Bundle bundle = getIntent().getExtras();
//
//
//        if(bundle !=  null){
//            int clickedCarId = bundle.getInt("ClickedCarId");
//            DatabaseHandler db = new DatabaseHandler(this);
//            //Fetch the Car object
//            Car clickedCarObject = db.getCar(clickedCarId);
//            int clickedCarObjectId = clickedCarObject.getId();
//            Log.d("Main", "ID: "+clickedCarObjectId);
////            getIntent().putExtra("ClickedCarObjectId", clickedCarObject.getId());
//            NewCarsDetailsPageFragment fragment = NewCarsDetailsPageFragment.newInstance(clickedCarObjectId, userID);
//
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                    new NewCarsDetailsPageFragment()).commit();
//            NewCarsDetailsPageFragment fragment = new NewCarsDetailsPageFragment();
//            Bundle args = new Bundle();
//            args.putInt("carId", clickedCarObjectId);
//            args.putInt("loggedUser", userID);
//            fragment.setArguments(args);
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                    new NewCarsDetailsPageFragment()).commit();


//
//        }

//        resetPasswordFragment = new ResetPasswordFragment();
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, new ForgetPasswordFragment())
//                .commit();


    }

//    @Override
//    public void onInputUsernameSent(CharSequence input) {
//        /*Here input is the input of the EditText that we sent to
//         * our fragments over this activity*/
//        /*Now we pass the data to the FragmmentB with the help of
//         * updateEditText() method*/
//        resetPasswordFragment.updateEditText(input);
//
//    }

    @Override
    protected void onStart() {
        super.onStart();

        checkSession();
    }

    private void checkSession() {
        //check if user is logged in
        //if user is logged in --> move to mainActivity

        UserSessionManagement sessionManagement = new UserSessionManagement(MainActivity.this);
        int userID = sessionManagement.getSession();

        if (userID != -1) {
            //user id logged in and so move to HomeFragment
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeBeforeLoginFragment()).commit();
        } else {
            Toast.makeText(this, "Session has been destroyed", Toast.LENGTH_SHORT).show();

            //do nothing
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.homeMenuItem:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeBeforeLoginFragment()).commit();
                break;
            case R.id.loginMenuItem:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LoginCustomerFragment()).commit();
                break;
            case R.id.userLogoutMenuItem:
                //this method will remove session and open Login Fragment
                UserSessionManagement sessionManagement = new UserSessionManagement(MainActivity.this);
                sessionManagement.removeSession();
                Toast.makeText(this, "Logout is clicked!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.adminloginMenuItem:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LoginAdminFragment()).commit();
                break;
            case R.id.carMenuItem:
                if (userID != -1) {
                    NewCarsListFragment fragment = NewCarsListFragment.newInstance(userID);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            fragment).commit();
                } else {
                    Toast.makeText(this, "You Must Login!!!", Toast.LENGTH_LONG).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new LoginCustomerFragment()).commit();
                }

                break;
            case R.id.serviceandrepairMenuItem:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new NewCarsPartsListFragment()).commit();
                break;
            case R.id.contactUsMenuItem:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ContactUsFragment()).commit();
                break;
            case R.id.userCartMenuItem:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CartPageForCarFragment()).commit();
                break;
            case R.id.userWishlistMenuItem:
                /*Here we have to run a query to selct the user's session*/
                List<WishlistCarProperty> wishlistCarProperties = databaseHandler.getAllWishlistedCarsProperty(userID);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new WishlistPageFragment()).commit();
                break;
            case R.id.userProfileMenuItem:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new UpdateMyProfileFragment()).commit();
                break;
            case R.id.userOrderMenuItem:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MyOrderListFragment()).commit();
                break;


        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.home:
                            selectedFragment = new HomeBeforeLoginFragment();
                            break;
                        case R.id.NewCars:
                            selectedFragment = new NewCarsListFragment();
                            break;
                        case R.id.CarParts:
                            selectedFragment = new NewCarsPartsListFragment();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}