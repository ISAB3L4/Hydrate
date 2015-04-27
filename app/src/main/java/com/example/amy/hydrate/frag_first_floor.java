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

public class frag_first_floor extends Fragment
    {

        Button floor_1_1;
        Button floor_1_2;
        Button floor_1_3;
        TextView bathroom_text;


        @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            View InputFragmentView = inflater.inflate(R.layout.floorfrag1, container, false);
            floor_1_1 = (Button) InputFragmentView.findViewById(R.id.f_1_1);
            floor_1_2 = (Button) InputFragmentView.findViewById(R.id.f_1_2);
            floor_1_3 = (Button) InputFragmentView.findViewById(R.id.f_1_3);
            bathroom_text=(TextView) InputFragmentView.findViewById(bathroom_num);

            floor_1_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent resultActivity = new Intent(getActivity(), rating.class);
                    getActivity().startActivity(resultActivity);
                    bathroom_text.setText("1_1");

                }
            });


            floor_1_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent resultActivity = new Intent(getActivity(), rating.class);
                    getActivity().startActivity(resultActivity);
                    bathroom_text.setText("1_2");
                }
            });

            floor_1_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent resultActivity = new Intent(getActivity(), rating.class);
                    getActivity().startActivity(resultActivity);
                    bathroom_text.setText("1_3");
                }
            });



            return InputFragmentView;
        }
    }
