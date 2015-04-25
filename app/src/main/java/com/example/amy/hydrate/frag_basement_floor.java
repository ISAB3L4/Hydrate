package com.example.amy.hydrate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class frag_basement_floor extends Fragment
{

    Button floor_b_1;
    Button floor_b_2;
    Button floor_b_3;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View InputFragmentView = inflater.inflate(R.layout.floorfragb, container, false);
        floor_b_1 = (Button) InputFragmentView.findViewById(R.id.f_b_1);
        floor_b_2 = (Button) InputFragmentView.findViewById(R.id.f_b_2);
        floor_b_3 = (Button) InputFragmentView.findViewById(R.id.f_b_3);
        //dfdkfjdlkfj
        floor_b_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultActivity = new Intent(getActivity(), rating.class);
                getActivity().startActivity(resultActivity);

            }
        });

        floor_b_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultActivity = new Intent(getActivity(), rating.class);
                getActivity().startActivity(resultActivity);

            }
        });

        floor_b_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultActivity = new Intent(getActivity(), rating.class);
                getActivity().startActivity(resultActivity);

            }
        });

        return InputFragmentView;
    }
}