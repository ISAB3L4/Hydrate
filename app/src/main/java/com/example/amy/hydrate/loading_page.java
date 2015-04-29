package com.example.amy.hydrate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AppKeyPair;


//extends for Activity, uses OnClickListener for enter button
public class loading_page extends Activity implements OnClickListener {

    //Creating Button to enter application
    private Button first_button;
    Intent resultActivity;
    final static private String APP_KEY = "cxepcok6btcf0vh";
    final static private String APP_SECRET = "vh398xojy7flrbk";
    private AppKeyPair appKeys;
    private AndroidAuthSession session;
    public static NetworkInfo networkInfo;
    public static DropboxAPI<AndroidAuthSession> mDBApi;
    private String accessToken;

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

        resultActivity = new Intent(loading_page.this, floor_swipe.class);
        myClickHandler();

    }
    public void myClickHandler() {
        //This batch of code is directly from Android Studio's tutorial. It's just checking to make
        //sure that there is a network connection
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //If there is a network connection
            appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
            SharedPreferences prefs = getSharedPreferences("DROPBOX_PREFS", 0);
            String token = prefs.getString(APP_KEY, null);

            //This part of the code is supposed to create a new session with existing keys,
            //if they exist
            if (token != null) {
                session = new AndroidAuthSession(appKeys, token);
                mDBApi = new DropboxAPI<>(session);
            } else {
                session = new AndroidAuthSession(appKeys);
                mDBApi = new DropboxAPI<>(session);
            }
            //mDBApi = new DropboxAPI<AndroidAuthSession>(session);
            mDBApi.getSession().startOAuth2Authentication(loading_page.this);
        } else {
            first_button.setText("No network connection available.");
        }
    }
    @Override
    protected void onPause()
    {
        super.onPause();
    }

    //To resume the app when going to Dropbox API
    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (mDBApi!=null && mDBApi.getSession().authenticationSuccessful()) {
            try {
                // Required to complete auth, sets the access token on the session
                mDBApi.getSession().finishAuthentication();
                accessToken = mDBApi.getSession().getOAuth2AccessToken();
                // Store it locally in our app for later use
                SharedPreferences prefs = getSharedPreferences("DROPBOX_PREFS", 0);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(APP_KEY, accessToken);
                editor.apply();
            } catch (IllegalStateException e) {
                Log.i("DbAuthLog", "Error authenticating", e);
            }
        }
        //Launches the new activity
        if(resultActivity!=null)
            startActivity(resultActivity);
    }
}

