package com.example.amy.hydrate;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AppKeyPair;

import org.w3c.dom.Text;

import static java.lang.Character.getNumericValue;
import static java.lang.Float.floatToIntBits;

public class rating extends Activity {

    String value;
    //The two Dropbox folder related keys necessary to access the data we've stored
    final static private String APP_KEY = "cxepcok6btcf0vh";
    final static private String APP_SECRET = "vh398xojy7flrbk";
    private AppKeyPair appKeys;
    private AndroidAuthSession session;
    private DropboxAPI<AndroidAuthSession> mDBApi;
    private String accessToken;

    //We also need the IDs of most of the activity_rating layouts
    private RatingBar ratingBar;
    private Button btnSubmit;
    private CheckBox wifi;
    private CheckBox poop;
    private CheckBox toilet;
    private TextView wifi_result;
    private TextView poop_result;
    private TextView toilet_result;
    private int wifi_num;
    private int poop_num;
    private int toilet_num;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        addListenerOnRatingBar();
        addListenerOnButton();

        //This value here tells the rating class which bathroom has been clicked from the floor
        //Note that the variable bathroom_text is public, so every .java file that has a button
        //that goes to this rating class modifies that bathroom_text variable differently.
        //This allows us to create different filenames
        value=getIntent().getExtras().getString(frag_basement_floor.bathroom_text);

        //And we need to find the IDs of some other things in the activity_rating layout
        wifi= (CheckBox) findViewById(R.id.has_wifi);
        poop= (CheckBox) findViewById(R.id.is_smelly);
        toilet=(CheckBox) findViewById(R.id.toilet_paper);
        wifi_result=(TextView) findViewById(R.id.wifi_result);
        poop_result=(TextView) findViewById(R.id.smell_result);
        toilet_result=(TextView) findViewById(R.id.paper_result);

        //NOTE: The Dropbox .txt files are in the format:
        /**
            Cumulative Rating (Space) # of Ratings (Space) Wi-Fi (Space) Smelly (Space) Toilet Paper
            This will be useful later
         */
    }

    private void addListenerOnButton() {
        //Find the submit button
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
        //Find the rating
        ratingBar=(RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b)
            {
                //Do nothing until the user clicks submit
            }
        });
    }
    // When user clicks button, calls AsyncTask in DB_Download.
    // Before attempting to fetch the URL, makes sure that there is a network connection.
    //This
    public void myClickHandler() {
        //This batch of code is directly from Andriod Stuido's tutorial. It's just checking to make
        //sure that there is a network connection
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
        {
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
            mDBApi.getSession().startOAuth2Authentication(rating.this);
        } else {
            btnSubmit.setText("No network connection available.");
        }
    }
    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
     protected void onResume()
     {
     super.onResume();
         //This is directly from DropBox's tutorial.
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

         //This line of code creates a new class that extends the AsyncTask class (background task)
         new DB_Download().execute(value);

         //For debugging purposes
         btnSubmit.setText("Done");

     } catch (IllegalStateException e) {
     Log.i("DbAuthLog", "Error authenticating", e);
     }
     }
     }

    private class DB_Download extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params) {
            //Create file name
            String title = (value).concat(".txt");

            //Create the input string that will hold the data from the downloaded file
            String aString = "";

            //Create new file
            try {
                FileOutputStream fos = openFileOutput(title, Context.MODE_PRIVATE);

                //GET FILE FROM DROPBOX HERE
                DropboxAPI.DropboxFileInfo info = mDBApi.getFile(title, null, fos, null);
                fos.close();

                //Not really necessary for our purposes
                //Log.i("DbExampleLog", "The file's rev is: " + info.getMetadata().rev);

                //Read from new file to byte array
                FileInputStream file = openFileInput(title);
                byte[] buffer = new byte[((int) file.getChannel().size())];
                file.read(buffer,0, (int) file.getChannel().size());
                file.close();

                //Assign the byte array to the string we will edit later
                //This worked out nicely, since the String constructor has a byte array
                //parameter for a specific constructor
                aString = new String(buffer);

                //Catch some stuff
            } catch (DropboxException | IOException e) {
                e.printStackTrace();
            }

            //Then, we need to calculate the new average
            //Get the user's rating
            int rating=(int)ratingBar.getRating();

            //Now, we split the String based on the spaces. Remember from before:
            //NOTE: The Dropbox .txt files are in the format:
            /**
             Cumulative Rating (Space) # of Ratings (Space) Wi-Fi (Space) Smelly (Space) Toilet Paper
             This will be useful later
             */
            String[] string_array=aString.split(" ");

            //We know that the cumulative rating is the first entry in the array, so we add it to the existing user's rating
            rating+=Integer.parseInt(string_array[0]);

            //The reason for having these variables declared as final is because we need to call the
            //runonUIThread method to edit the View objects on the screen. Inner classes need final variables
            //We know that the # of rating is in the second entry of the array, so we add it to the existing array
            final int counter=Integer.parseInt(string_array[1])+1;
            final int finalResult = rating;

            //Now that the rating part is done, we move to the tags
            wifi_num=Integer.parseInt(string_array[2]);
            poop_num=Integer.parseInt(string_array[3]);
            toilet_num=Integer.parseInt(string_array[4]);
            //Start with the Wi-Fi
            if (wifi.isChecked())
                wifi_num++;
            //Then the smell
            if (poop.isChecked())
                poop_num++;
            //Then the toilet paper
            if (toilet.isChecked())
                toilet_num++;

            //I'm running this part on the UI Thread because you can't touch the viewing components
            //directly from this download thread
            runOnUiThread(new Runnable() {
                public void run() {
                    //Set the number of stars
                    ratingBar.setRating(finalResult /counter);
                    //Set the tag numbers
                    wifi_result.setText("(".concat(Integer.toString(wifi_num)).concat(")"));
                    poop_result.setText("(".concat(Integer.toString(poop_num)).concat(")"));
                    toilet_result.setText("(".concat(Integer.toString(toilet_num)).concat(")"));
                }
            });

            //And here, we write the new cumulative rating and the number of ratings
            //to the file for upload
            try {
                FileOutputStream fos = openFileOutput(title, Context.MODE_PRIVATE);
                fos.write(Integer.toString(rating).getBytes());
                fos.write(" ".getBytes());
                fos.write(Integer.toString(counter).getBytes());
                fos.write(" ".getBytes());
                fos.write(Integer.toString(wifi_num).getBytes());
                fos.write(" ".getBytes());
                fos.write(Integer.toString(poop_num).getBytes());
                fos.write(" ".getBytes());
                fos.write(Integer.toString(toilet_num).getBytes());
                fos.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            new DB_Upload().execute(value);
            return "Success!";
        }
    }

        private class DB_Upload extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... bathroom) {
                //Create filename
                String title = (value).concat(".txt");
                try {
                    //Open the file one more time, for upload
                    FileInputStream fis1 = openFileInput(title);

                    //PUSH TO DROPBOX
                    DropboxAPI.Entry response = mDBApi.putFileOverwrite(title, fis1, (int) fis1.getChannel().size()
                            , null);
                    //Not necessary
                    //Log.i("DbExampleLog", "The uploaded file's rev is: " + response.rev);

                } catch (DropboxException | IOException e) {
                    e.printStackTrace();
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