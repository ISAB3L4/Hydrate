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

public class frag_first_floor extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.floorfrag1, container, false);
    }

    public class loading_page extends Activity implements OnClickListener {
        private Button floor_1_1;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            //The super keyword is used to refer to the parent class in java
            super.onCreate(savedInstanceState);

            //How the activity actually looks is inside main.xml, inside the layout folder
            setContentView(R.layout.floorfrag1);

            //The buttons have parameters corresponding to the IDs in Main.xml
            floor_1_1 = (Button) findViewById(R.id.f_1_1);

            floor_1_1.setOnClickListener(this);

        }

        @Override

        public void onClick(View v) {
            launchResultActivity();
        }

        private void launchResultActivity() {

            Intent resultActivity = new Intent(getActivity(), rating.class);

            //Launches the new activity
            getActivity().startActivity(resultActivity);
        }
    }

}
