package com.example.amy.hydrate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

//extends for Activity, uses OnClickListener for enter button
public class loading_page extends Activity implements OnClickListener {

    //Creating Button to enter application
    private Button first_button;

    //displays layout in activity_loading_page.xml, sets what function the button will call
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //The super keyword is used to refer to the parent class in java
        super.onCreate(savedInstanceState);

        //How the activity actually looks is inside main.xml, inside the layout folder
        setContentView(R.layout.activity_loading_page);

        //The buttons have parameters corresponding to the IDs in Main.xml
        first_button = (Button) findViewById(R.id.enter_button);

        first_button.setOnClickListener(this);

    }

    //function the button will call when clicked
    @Override
    public void onClick(View v) {
        launchResultActivity();
    }

    //Launches a new activity when button is clicked,  must call an intent
    private void launchResultActivity() {

        Intent resultActivity = new Intent(loading_page.this, floor_swipe.class);

        //Launches the new activity
        startActivity(resultActivity);
    }

    //To resume the app when going to Dropbox API
    @Override
    protected void onPostResume() {
        super.onPostResume();
    }
}

