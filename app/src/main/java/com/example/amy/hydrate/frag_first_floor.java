package com.example.amy.hydrate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.app.Activity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class frag_first_floor extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container == null)
        {
            return null;
        }

        // TODO Auto-generated method stub
        LinearLayout view =(LinearLayout) inflater.inflate(R.layout.floorfrag1, container, false);

        ImageButton floor_1_1 = (ImageButton) view.findViewById(R.id.f_1_1);
        floor_1_1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent resultActivity = new Intent(getActivity(), rating.class);

                //Launches the new activity
                getActivity().startActivity(resultActivity);
            }
        });

        return view;

            //Launches the new activity
            getActivity().startActivity(resultActivity);
        }

    }

}
