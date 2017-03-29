package com.kktheavi.project.lucknowmetro;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kktheavi.project.lucknowmetro.R;

import java.util.ArrayList;

/**
 * Created by kktheavi on 10/3/2016.
 */

public class ResultRoutes extends Activity {

    String stations[];
    Activity activity;
    ListView station_list;
    ResultRoutesAdapter adapter;
    String starting, destination;
    ArrayList<String> scripts;
    int a, b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_result);
        activity = this;
        scripts = new ArrayList<>();
        station_list = (ListView) findViewById(R.id.result_routes);


        stations = new String[]{"Chaudhary Charan Singh International Airport",
                " Amausi",
                "Transport Nagar",
                " Krishna Nagar",
                "Singaar Nagar",
                "Alambagh",
                "Alambagh", " ISBT",
                "Mawaiya",
                "Durgapuri",
                "Lucknow Charbagh Railway Station",
                "Hussain Ganj",
                "Sachivalaya",
                "Hazratganj",
                "KD Singh Babu Stadium",
                "Lucknow University",
                "IT Chauraha",
                "Badshahnagar",
                "Lekhraj Market",
                "Ramsagar Mishra Nagar",
                "Indira Nagar",
                "Munshi Pulia",
                "Gautam Buddha Marg",
                "Aminabad",
                "Pandeyganj",
                "Lucknow City Railway Station",
                "Medical College Chauraha",
                "Nawazganj",
                "Thakurganj",
                "Balaganj",
                "Sarfarazganj",
                "Musabagh",
                "Vasant Kunj"};

        if (getIntent().hasExtra("Starting Station"))
            starting = getIntent().getStringExtra("Starting Station");
        if (getIntent().hasExtra("Destination Station"))
            destination = getIntent().getStringExtra("Destination Station");


        for (int i = 0; i < stations.length; i++) {
            if (starting.equalsIgnoreCase(stations[i])) {
                a = i;
            }
            if (destination.equalsIgnoreCase(stations[i])) {
                b = i;
            }
        }
        if (a > b) {
            for (int i = b; i < a; i++) {
                scripts.add(stations[i]);
            }
        } else if (b > a) {
            for (int i = a; i < b; i++) {
                scripts.add(stations[i]);
            }
        }

        System.out.println("=====" + scripts);
        adapter = new ResultRoutesAdapter(scripts);
        station_list.setAdapter(adapter);


    }

    public class ResultRoutesAdapter extends BaseAdapter {


        ArrayList<String> image_connection;
        LayoutInflater inflater;


        public ResultRoutesAdapter(ArrayList<String> scripts) {

            this.image_connection = scripts;
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return image_connection.size();
        }

        @Override
        public Object getItem(int position) {
            return image_connection.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        private class Holder {
            TextView station_name;
        }


        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {

            final Holder holder;
            if (view == null) {
                view = inflater.inflate(R.layout.single_list_station, viewGroup, false);
                holder = new Holder();
                holder.station_name = (TextView) view.findViewById(R.id.station_name);
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }
            holder.station_name.setText(image_connection.get(i));
            return view;
        }


    }
}
