package com.kktheavi.project.lucknowmetro.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.kktheavi.project.lucknowmetro.R;

/**
 * Created by kktheavi on 10/5/2016.
 */

public class NearStation extends android.app.Fragment implements View.OnClickListener {


    Activity activity;
    SeekBar mSeekbar;
    TextView textInKm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.near_metro_fragment, container, false);
        activity=getActivity();
        textInKm=(TextView)view.findViewById(R.id.textInKm);


        mSeekbar = (SeekBar) view.findViewById(R.id.SeekBar01);

        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                textInKm.setText(Integer.toString(progress)+" km");
            }

            public void onStartTrackingTouch(SeekBar seekBar) {}

            public void onStopTrackingTouch(SeekBar seekBar) {}
        });



       /* radioDistanceGroup=(RadioGroup)view.findViewById(R.id.radioDistance);
        radioDistanceGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.d1) {
                    Toast.makeText(activity, "choice: Silent",
                            Toast.LENGTH_SHORT).show();
                } else if(checkedId == R.id.d5) {
                    Toast.makeText(activity, "choice: Sound",
                            Toast.LENGTH_SHORT).show();
                }else if(checkedId == R.id.d10) {
                    Toast.makeText(activity, "choice: Sound",
                            Toast.LENGTH_SHORT).show();
                }else if(checkedId == R.id.d15) {
                    Toast.makeText(activity, "choice: Sound",
                            Toast.LENGTH_SHORT).show();
                }else if(checkedId == R.id.d20) {
                    Toast.makeText(activity, "choice: Sound",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });*/




/*
        // get selected radio button from radioGroup
        int selectedId = radioDistanceGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioDistanceButton = (RadioButton) view.findViewById(selectedId);

        Toast.makeText(activity,radioDistanceButton.getText(), Toast.LENGTH_SHORT).show();*/


        return view;
    }

    @Override
    public void onClick(View view) {

    }
}
