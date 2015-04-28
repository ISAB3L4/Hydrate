package com.example.amy.hydrate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;



//This is the class that will first be run when the app is first opened
//Extends = inherits, implements means it interfaces with another class
public class loading_page extends Activity implements OnClickListener {

    //Creating button objects, which extend (inherit) the View class
    private Button first_button;


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

    @Override
	/*onClick is what is called when the buttons are pressed and they take in Views as arguments
	 * as buttons are children of the view class, buttons can polymorphically be passed in. The button
	 * that called the onClick is automatically fed in*/
    public void onClick(View v) {
        launchResultActivity();
    }

    /* The launchResultActivity method is used to start a new activity from within an onClickListener
     * */
    private void launchResultActivity() {

        Intent resultActivity = new Intent(loading_page.this, floor_swipe.class);

        //Launches the new activity
        startActivity(resultActivity);
    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
    }
}

