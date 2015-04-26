package com.example.amy.hydrate;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AppKeyPair;

public class rating extends Activity {

    private String bathroom_num="0_0";
    final static private String APP_KEY = "cxepcok6btcf0vh";
    final static private String APP_SECRET = "vh398xojy7flrbk";
    private DropboxAPI<AndroidAuthSession> mDBApi;

    private RatingBar ratingBar;
    private Button btnSubmit;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        addListenerOnRatingBar();
        addListenerOnButton();
    }

    private void addListenerOnButton() {
        btnSubmit=(Button) findViewById(R.id.submit_rating);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                myClickHandler();
            }
        });
    }
    private void addListenerOnRatingBar() {
        ratingBar=(RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b)
            {
                //Do nothing until the user clicks submit
            }
        });
    }
    // When user clicks button, calls AsyncTask.
    // Before attempting to fetch the URL, makes sure that there is a network connection.
    public void myClickHandler() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
        {
            AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
            AndroidAuthSession session = new AndroidAuthSession(appKeys);
            mDBApi = new DropboxAPI<AndroidAuthSession>(session);
            mDBApi.getSession().startOAuth2Authentication(rating.this);

        } else {
            btnSubmit.setText("No network connection available.");
        }
    }

    protected void onPause()
    {
        super.onPause();
    }

     protected void onResume()
     {
     super.onResume();
     if (mDBApi!=null)
     {
         if(mDBApi.getSession().authenticationSuccessful())
         {
             btnSubmit.setText("It works");
         }
     }
     if (mDBApi!=null && mDBApi.getSession().authenticationSuccessful()) {
     try {
     // Required to complete auth, sets the access token on the session
     mDBApi.getSession().finishAuthentication();
     String accessToken = mDBApi.getSession().getOAuth2AccessToken();
     new DB_Download().execute(bathroom_num);
     btnSubmit.setText("Sending data...");
     } catch (IllegalStateException e) {
     Log.i("DbAuthLog", "Error authenticating", e);
     }
     }
     }

    private class DB_Download extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... bathroom) {
            String title = "/".concat(String.valueOf(bathroom)).concat(".txt");
            File file = new File(title);
            String aString = null;
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(file);
                DropboxAPI.DropboxFileInfo info = mDBApi.getFile(title, null, outputStream, null);
                Log.i("DbExampleLog", "The file's rev is: " + info.getMetadata().rev);
            } catch (DropboxException e) {
                btnSubmit.setText("File download unsuccessful.");
            } catch (FileNotFoundException e) {
                btnSubmit.setText("File not found.");
            }
            //Convert OutputStream to String
            try {
                aString = Convert_to_String(outputStream);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Add_Calculate_New_Rating(aString);
            return aString;
        }
        protected String Convert_to_String(FileOutputStream outputStream) throws UnsupportedEncodingException {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            String aString = new String(os.toByteArray(),"UTF-8");
            return aString;
        }
    }

    private String Add_Calculate_New_Rating(String myRating) {
        //Basically, we will add the new rating to the string
        //then convert all of the ratings to integers
        //then average them for the new rating
        myRating.concat(Integer.toString(ratingBar.getNumStars()));
        int result=0;
        for (int i = 0; i < myRating.length(); i++) {
            try {
                result+= (int)(myRating.charAt(i))-48; //ASCII value of 0 is 48
            } catch (NumberFormatException nfe) {};
        }
        ratingBar.setNumStars(result/myRating.length());
        //Call the upload function
        new DB_Upload().execute(bathroom_num);
        return myRating;
    }
    private class DB_Upload extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... bathroom) {
            String title = "/".concat(String.valueOf(bathroom)).concat(".txt");
            File file = new File(title);
            String aString = null;
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(file);
                DropboxAPI.Entry response = mDBApi.putFile("/magnum-opus.txt", inputStream,
                        file.length(), null, null);
                Log.i("DbExampleLog", "The uploaded file's rev is: " + response.rev);
            } catch (DropboxException e) {
                //btnSubmit.setText("File download unsuccessful.");
            } catch (FileNotFoundException e) {
                //btnSubmit.setText("File not found.");
            }
            return "SUCCESS!";
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rating, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}