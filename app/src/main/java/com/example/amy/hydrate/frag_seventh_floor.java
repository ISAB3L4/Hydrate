package com.example.amy.hydrate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class frag_seventh_floor extends Fragment
{
    //Declaring buttons for use, three per floor: men's, women's, fountain
    Button floor_7_1;
    Button floor_7_2;
    Button floor_7_3;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //What the fragment will look like, from its XML file
        View InputFragmentView = inflater.inflate(R.layout.fragfloor7, container, false);
        //finds where the buttons are located
        floor_7_1 = (Button) InputFragmentView.findViewById(R.id.f_7_1);
        floor_7_2 = (Button) InputFragmentView.findViewById(R.id.f_7_2);
        floor_7_3 = (Button) InputFragmentView.findViewById(R.id.f_7_3);

        //What the button will do when clicked
        floor_7_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //specifies new activity, must call parent activity to be able to switch from fragment
                Intent resultActivity = new Intent(getActivity(), rating.class);
                //Sets a global string so that the Dropbox API knows what button on what floor was clicked
                resultActivity.putExtra(frag_basement_floor.bathroom_text,"7_1");
                //launch the new activity
                getActivity().startActivity(resultActivity);

            }
        });

        //REPEATED FOR OTHER BUTTONS
        floor_7_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultActivity = new Intent(getActivity(), rating.class);
                resultActivity.putExtra(frag_basement_floor.bathroom_text,"7_2");
                getActivity().startActivity(resultActivity);

            }
        });


        floor_7_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultActivity = new Intent(getActivity(), rating.class);
                resultActivity.putExtra(frag_basement_floor.bathroom_text,"7_3");
                getActivity().startActivity(resultActivity);

            }
        });


        //displays what the fragment looks like
        return InputFragmentView;
    }
}