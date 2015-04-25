package com.example.amy.hydrate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class frag_third_floor extends Fragment
{

    Button floor_3_1;
    Button floor_3_2;
    Button floor_3_3;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View InputFragmentView = inflater.inflate(R.layout.fragfloor3, container, false);
        floor_3_1 = (Button) InputFragmentView.findViewById(R.id.f_3_1);

        floor_3_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultActivity = new Intent(getActivity(), rating.class);
                getActivity().startActivity(resultActivity);

            }
        });

        floor_3_2 = (Button) InputFragmentView.findViewById(R.id.f_3_2);

        floor_3_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultActivity = new Intent(getActivity(), rating.class);
                getActivity().startActivity(resultActivity);

            }
        });

        floor_3_3 = (Button) InputFragmentView.findViewById(R.id.f_3_3);

        floor_3_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultActivity = new Intent(getActivity(), rating.class);
                getActivity().startActivity(resultActivity);

            }
        });


        return InputFragmentView;
    }
}