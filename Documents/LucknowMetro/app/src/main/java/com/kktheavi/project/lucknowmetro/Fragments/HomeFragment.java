package com.kktheavi.project.lucknowmetro.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kktheavi.project.lucknowmetro.R;
import com.kktheavi.project.lucknowmetro.RoutesBetweenStation;

/**
 * Created by kktheavi on 10/2/2016.
 */

public class HomeFragment extends android.app.Fragment implements View.OnClickListener {

    LinearLayout route, info, near;
    ImageView main_img;
    FragmentManager fm;
    Fragment fr;
    FragmentTransaction fragmentTransaction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        route = (LinearLayout) view.findViewById(R.id.routeBstation);
        info = (LinearLayout) view.findViewById(R.id.station_info);
        near = (LinearLayout) view.findViewById(R.id.near_station);

        main_img = (ImageView) view.findViewById(R.id.main_image);

        route.setOnClickListener(this);
        info.setOnClickListener(this);
        near.setOnClickListener(this);
        main_img.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.routeBstation:
               /* fr = new RoutesBetweenStationFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fm.beginTransaction().add(R.id.getFragment, fr).addToBackStack("my_fragment")
                        .commit();*/
               Intent intent=new Intent(getActivity(),RoutesBetweenStation.class);
                startActivity(intent);

                break;
            case R.id.station_info:
                fr = new StationListFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fm.beginTransaction().add(R.id.getFragment, fr).addToBackStack("my_fragment")
                        .commit();
                break;
            case R.id.near_station:
                fr = new NearStation();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fm.beginTransaction().add(R.id.getFragment, fr).addToBackStack("my_fragment").commit();
                break;
            case R.id.main_image:
                break;
        }
    }
}