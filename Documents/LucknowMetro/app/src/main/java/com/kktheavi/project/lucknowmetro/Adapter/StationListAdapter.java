package com.kktheavi.project.lucknowmetro.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kktheavi.project.lucknowmetro.SingleStaionInfo;
import com.kktheavi.project.lucknowmetro.R;

import java.util.ArrayList;

/**
 * Created by kktheavi on 10/2/2016.
 */

public class StationListAdapter extends BaseAdapter {

    Activity activity;
    LayoutInflater inflater;
    ArrayList<String> station;


    public StationListAdapter(Activity activity,  ArrayList<String> station) {
        this.activity = activity;
        this.station = station;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return station.size();
    }

    @Override
    public Object getItem(int position) {

        return station.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private class Holder {
        TextView station_name;
        LinearLayout single_list_layout;
    }


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final Holder holder;
        if (view == null) {

            view = inflater.inflate(R.layout.single_list_station, viewGroup, false);
            holder = new Holder();
            holder.station_name = (TextView) view.findViewById(R.id.station_name1);
            holder.single_list_layout = (LinearLayout) view.findViewById(R.id.single_dfssfslist_layout);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        holder.station_name.setText(station.get(i));

        holder.single_list_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(activity, SingleStaionInfo.class);
                in.putExtra("station",station.get(i));
                in.putExtra("station_start","station_start");
                in.putExtra("station_end","station_end");
                activity.startActivity(in);
            }
        });
        return view;
    }

    public void notifyDataSetChanged(ArrayList<String> array) {
        super.notifyDataSetChanged();
        this.station = array;
    }


}

