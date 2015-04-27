package com.example.amy.hydrate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import static com.example.amy.hydrate.R.id.bathroom_num;

public class frag_fourth_floor extends Fragment
{

    Button floor_4_1;
    Button floor_4_2;
    Button floor_4_3;
    TextView bathroom_text;


    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View InputFragmentView = inflater.inflate(R.layout.fragfloor4, container, false);
        floor_4_1 = (Button) InputFragmentView.findViewById(R.id.f_4_1);
        bathroom_text=(TextView) InputFragmentView.findViewById(bathroom_num);
        floor_4_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultActivity = new Intent(getActivity(), rating.class);
                getActivity().startActivity(resultActivity);
                bathroom_text.setText("4_1");

            }
        });


        floor_4_2 = (Button) InputFragmentView.findViewById(R.id.f_4_2);

        floor_4_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultActivity = new Intent(getActivity(), rating.class);
                getActivity().startActivity(resultActivity);
                bathroom_text.setText("4_2");

            }
        });


        floor_4_3 = (Button) InputFragmentView.findViewById(R.id.f_4_3);

        floor_4_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultActivity = new Intent(getActivity(), rating.class);
                getActivity().startActivity(resultActivity);
                bathroom_text.setText("4_3");

            }
        });




        return InputFragmentView;
    }
}