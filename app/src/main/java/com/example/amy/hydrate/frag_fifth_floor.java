package com.example.amy.hydrate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static com.example.amy.hydrate.R.id.bathroom_num;


public class frag_fifth_floor extends Fragment
{

    Button floor_5_1;
    Button floor_5_2;
    Button floor_5_3;
    TextView bathroom_text;


    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View InputFragmentView = inflater.inflate(R.layout.fragfloor5, container, false);
        floor_5_1 = (Button) InputFragmentView.findViewById(R.id.f_5_1);
        floor_5_2 = (Button) InputFragmentView.findViewById(R.id.f_5_2);
        floor_5_3 = (Button) InputFragmentView.findViewById(R.id.f_5_3);
        bathroom_text=(TextView) InputFragmentView.findViewById(bathroom_num);

        floor_5_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultActivity = new Intent(getActivity(), rating.class);
                getActivity().startActivity(resultActivity);
                bathroom_text.setText("5_1");

            }
        });

        floor_5_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultActivity = new Intent(getActivity(), rating.class);
                getActivity().startActivity(resultActivity);
                bathroom_text.setText("5_2");

            }
        });

        floor_5_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultActivity = new Intent(getActivity(), rating.class);
                getActivity().startActivity(resultActivity);
                bathroom_text.setText("5_3");

            }
        });


        return InputFragmentView;
    }
}