package com.example.amy.hydrate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class frag_second_floor extends Fragment
{

    Button floor_2_1;
    Button floor_2_2;
    Button floor_2_3;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View InputFragmentView = inflater.inflate(R.layout.floorfrag2, container, false);
        floor_2_1 = (Button) InputFragmentView.findViewById(R.id.f_2_1);

        floor_2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultActivity = new Intent(getActivity(), rating.class);
                resultActivity.putExtra(frag_basement_floor.bathroom_text,"2_1");
                getActivity().startActivity(resultActivity);

            }
        });


        floor_2_2 = (Button) InputFragmentView.findViewById(R.id.f_2_2);

        floor_2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultActivity = new Intent(getActivity(), rating.class);
                resultActivity.putExtra(frag_basement_floor.bathroom_text,"2_2");
                getActivity().startActivity(resultActivity);

            }
        });

        floor_2_3 = (Button) InputFragmentView.findViewById(R.id.f_2_3);

        floor_2_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultActivity = new Intent(getActivity(), rating.class);
                resultActivity.putExtra(frag_basement_floor.bathroom_text,"2_3");
                getActivity().startActivity(resultActivity);

            }
        });




        return InputFragmentView;
    }
}