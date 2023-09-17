package com.example.testcarappwithfragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.testcarappwithfragment.SessionManager.UserSessionManagement;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class AdminDashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawerId);
        NavigationView navigationView = findViewById(R.id.NavigationId);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_panel_fragment_container,
                    new AdminDashboardFragment()).commit();
            navigationView.setCheckedItem(R.id.adminDashboardMenuItem);
        }

        /*Code section for Bottom Navigation View*/
        bottomNav = findViewById(R.id.bottom_navigator);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_panel_fragment_container,
                    new AdminDashboardFragment()).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.adminDashboardMenuItem:
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_panel_fragment_container,
                        new AdminDashboardFragment()).commit();
                break;
            case R.id.carBrandMenuItem:
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_panel_fragment_container,
                        new CarBrandMasterFragment()).commit();
                break;
            case R.id.carMenuItem:
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_panel_fragment_container,
                        new CarMasterFragment()).commit();
                break;
            case R.id.orderMenuItem:
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_panel_fragment_container,
                        new OrderMasterFragment()).commit();
                break;
            case R.id.userMenuItem:
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_panel_fragment_container,
                        new UserMasterFragment()).commit();
                break;
            case R.id.contactMenuItem:
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_panel_fragment_container,
                        new ContactMasterFragment()).commit();
                break;
            case R.id.ServiceandRepairMenuItem:
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_panel_fragment_container,
                        new CarPartsMasterFragment()).commit();
                break;
            case R.id.AdminlogoutMenuItem:
               /* getSupportFragmentManager().beginTransaction().replace(R.id.admin_panel_fragment_container,
                        new LoginAdminFragment()).commit();*/
                /*If we click on the logout button, we redirect to the Main Activity*/
//                UserSessionManagement user = new UserSessionManagement(this);
//                user.saveSession();
                /*The Problem is when we log out, the session get destroyed*/
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
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
                        case R.id.carMenuItem:
                            selectedFragment = new CarMasterFragment();
                            break;
                        case R.id.orderMenuItem:
                            selectedFragment = new OrderMasterFragment();
                            break;
                        case R.id.userMenuItem:
                            selectedFragment = new UserMasterFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.admin_panel_fragment_container,
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