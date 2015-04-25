package com.example.amy.hydrate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class frag_eighth_floor extends Fragment
{

    Button floor_8_1;
    Button floor_8_2;
    Button floor_8_3;


    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View InputFragmentView = inflater.inflate(R.layout.fragfloor8, container, false);
        floor_8_1 = (Button) InputFragmentView.findViewById(R.id.f_8_1);
        floor_8_2 = (Button) InputFragmentView.findViewById(R.id.f_8_2);
        floor_8_3 = (Button) InputFragmentView.findViewById(R.id.f_8_3);

        floor_8_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultActivity = new Intent(getActivity(), rating.class);
                getActivity().startActivity(resultActivity);

            }
        });

        floor_8_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultActivity = new Intent(getActivity(), rating.class);
                getActivity().startActivity(resultActivity);

            }
        });

        floor_8_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultActivity = new Intent(getActivity(), rating.class);
                getActivity().startActivity(resultActivity);

            }
        });

        return InputFragmentView;
    }
}