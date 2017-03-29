package com.kktheavi.project.lucknowmetro;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.kktheavi.project.lucknowmetro.Fragments.AboutUsFragment;
import com.kktheavi.project.lucknowmetro.Fragments.HomeFragment;
import com.kktheavi.project.lucknowmetro.Fragments.NearStation;
import com.kktheavi.project.lucknowmetro.Fragments.StationListFragment;

import im.delight.android.location.SimpleLocation;

/**
 * Created by kktheavi on 10/1/2016.
 */

public class MainActivity extends Activity {

    FragmentManager fm;
    Fragment fr;
    FragmentTransaction fragmentTransaction;
    ImageView back;
    AlertDialog alert;
    private SimpleLocation location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        back = (ImageView) findViewById(R.id.back);
        back.setVisibility(View.INVISIBLE);
//        location = new SimpleLocation(this);
        fr = new HomeFragment();
        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        fm.beginTransaction().add(R.id.getFragment, fr).commit();

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ImageView open_drawer = (ImageView) findViewById(R.id.open_menu);
        open_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.END);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    // Handle the preference  action
                    fr = new HomeFragment();
                    fm = getFragmentManager();
                    fragmentTransaction = fm.beginTransaction();
                    fm.beginTransaction().add(R.id.getFragment, fr).addToBackStack("my_fragment").commit();


                } else if (id == R.id.nav_station_info) {
                    // Handle the About action
                    fr = new StationListFragment();
                    fm = getFragmentManager();
                    fragmentTransaction = fm.beginTransaction();
                    fm.beginTransaction().add(R.id.getFragment, fr).addToBackStack("my_fragment").commit();
                }
                else if (id == R.id.nav_route) {
                    // Handle the About action

                    Intent intent=new Intent(MainActivity.this,RoutesBetweenStation.class);
                    startActivity(intent);

                }


                else if (id == R.id.nav_near) {
                    // Handle the About action
                  /*  fr = new RoutesBetweenStationFragment();
                    fm = getFragmentManager();
                    fragmentTransaction = fm.beginTransaction();
                    fm.beginTransaction().add(R.id.getFragment, fr).addToBackStack("my_fragment").commit();*/
                    fr = new NearStation();
                    fm = getFragmentManager();
                    fragmentTransaction = fm.beginTransaction();
                    fm.beginTransaction().add(R.id.getFragment, fr).addToBackStack("my_fragment").commit();
                }





                else if (id == R.id.nav_about) {


                    // Handle the About action
                    fr = new AboutUsFragment();
                    fm = getFragmentManager();
                    fragmentTransaction = fm.beginTransaction();
                    fm.beginTransaction().add(R.id.getFragment, fr).commit();

                /*    Intent about_intent=new Intent(getApplicationContext(),AboutUsPage.class);
                    startActivity(about_intent);*/
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.END);
                return true;
            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Uncomment the below code to Set the message and title from the strings.xml file
        //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to close this application ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });

        //Creating dialog box
        alert = builder.create();
        //Setting the title manually
        alert.setTitle("Lucknow Metro");


/*
        // if we can't access the location yet
        if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this);
        }

        findViewById(R.id.header).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final double latitude = location.getLatitude();
                final double longitude = location.getLongitude();

                // TODO
            }

        });

// reduce the precision to 10,000m for privacy reasons
        location.setBlurRadius(10000);*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            alert.show();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();

        // make the device update its location
//        location.beginUpdates();


    }

    @Override
    protected void onPause() {
        // stop location updates (saves battery)
//        location.endUpdates();

        super.onPause();
    }
}