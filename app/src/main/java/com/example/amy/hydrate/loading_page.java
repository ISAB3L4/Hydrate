package com.example.amy.hydrate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


//This is the class that will first be run when the app is first opened
//Extends = inherits, implements means it interfaces with another class
public class loading_page extends Activity implements OnClickListener
{

    //Creating button objects, which extend (inherit) the View class
    private Button first_button;


    @Override
	/*An onCreate method/function is what is called when a screen is first showed.
	  In this case, this is the main menu screen.
	*/
    protected void onCreate(Bundle savedInstanceState)
    {
        //The super keyword is used to refer to the parent class in java
        super.onCreate(savedInstanceState);

        //How the activity actually looks is inside main.xml, inside the layout folder
        setContentView(R.layout.activity_loading_page);

        //The buttons have parameters corresponding to the IDs in Main.xml
        first_button = (Button) findViewById(R.id.enter_button);


		/*The buttons now have onClickListeners set, a method/function of the button class
		 * to start a new activity/intent when pressed. In this case, pressing a button
		 * will go to the results page.
		 * */
        first_button.setOnClickListener(this);

    }

    @Override
	/*onClick is what is called when the buttons are pressed and they take in Views as arguments
	 * as buttons are children of the view class, buttons can polymorphically be passed in. The button
	 * that called the onClick is automatically fed in*/
    public void onClick(View v)
    {
		/*If all is good, launch a new activity that passes in the amount entered and tip percentage
		NOTE: Double.parseDouble(string) turns a string into a double. It is used to cast the user's input
		to a double and feed it into the new activity
		*/
        launchResultActivity();
    }

    /* The launchResultActivity method is used to start a new activity from within an onClickListener
     * */
    private void launchResultActivity()
    {
		/*The intent class represents an action is used to "load" activities into a variable so they can be passed in and launched from
		 * the startActivity method. Basic intents take two arguments, the current class(.java) and the class(.java) that the app will move to
		 *  The line below initializes an Intent named resultActivity and passes in (Main.this,Result.class) much like the this-> pointer in C++,
		 *  the this keyword in java is used by classes to reference themselves*/
        Intent resultActivity = new Intent(loading_page.this, floor_swipe.class);

		/*Since this method is private, if we want the Result Activity/class to access it's members (the strings TAG_TIP and TAG_GRAND_TOTAL),
		 *we can "push" members from the Main Acivity/class to Result, much like how a friend function can "pull" private members from objects
		*/

        //Launches the new activity
        startActivity(resultActivity);
    }
}



