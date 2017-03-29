package com.kktheavi.project.lucknowmetro;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kktheavi.project.lucknowmetro.R;

/**
 * Created by kktheavi on 10/2/2016.
 */

public class SingleStaionInfo extends Activity implements View.OnClickListener {


    String station, station_start, station_end;
    TextView station_tv, station_start_tv, station_end_tv;
    ImageView setting, back;
    LinearLayout train_timimg, platform, contact,  gates_and_dir, tourist_place, parking, lift;
    AlertDialog.Builder alertDialogBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_station_info);
        station_tv = (TextView) findViewById(R.id.station_name);
        station_start_tv = (TextView) findViewById(R.id.station_start);
        station_end_tv = (TextView) findViewById(R.id.station_end);
        setting = (ImageView) findViewById(R.id.open_menu);
        back = (ImageView) findViewById(R.id.back);
        setting.setVisibility(View.INVISIBLE);

        alertDialogBuilder = new AlertDialog.Builder(this);

        train_timimg=(LinearLayout)findViewById(R.id.train_timings);
        platform=(LinearLayout)findViewById(R.id.platforms);
        contact=(LinearLayout)findViewById(R.id.contact);
        gates_and_dir=(LinearLayout)findViewById(R.id.gates);
        tourist_place=(LinearLayout)findViewById(R.id.tourist_spots);
        parking=(LinearLayout)findViewById(R.id.parking);
        lift=(LinearLayout)findViewById(R.id.lift_and_escalators);

        if (getIntent().hasExtra("station"))
            station = getIntent().getStringExtra("station");
        if (getIntent().hasExtra("station_start"))
            station_start = getIntent().getStringExtra("station_start");
        if (getIntent().hasExtra("station_end"))
            station_end = getIntent().getStringExtra("station_end");


        station_tv.setText(station);
        station_start_tv.setText(station_start);
        station_end_tv.setText(station_end);

        back.setOnClickListener(this);

        train_timimg.setOnClickListener(this);
        platform.setOnClickListener(this);
        contact.setOnClickListener(this);
        gates_and_dir.setOnClickListener(this);
        tourist_place.setOnClickListener(this);
        parking.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.train_timings:
                openDia("Train Timimg","5:30 am to 11:30pm");
                break;
            case R.id.platforms:
                openDia("Plateform","2 plateform");
                break;
            case R.id.contact:
                openDia("Contact ","+918115969626");
                break;
            case R.id.gates:
                openDia("Directions and Gates ","2 Ways in east and north directions");
                break;
            case R.id.tourist_spots:
                openDia("Tourist ","dafd");
                break;
            case R.id.parking:
                openDia("Parking","Available");
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

/*
* open dialog on click of list item
* */
    private void openDia(String title, String message) {


        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}



