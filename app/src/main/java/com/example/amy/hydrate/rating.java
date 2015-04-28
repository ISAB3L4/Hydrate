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

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AppKeyPair;
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
    private int user_rating;

    //We also need the IDs of both the rating and the submit button
    private RatingBar ratingBar;
    private Button btnSubmit;


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
                mDBApi = new DropboxAPI<AndroidAuthSession>(session);
            } else {
                session = new AndroidAuthSession(appKeys);
                mDBApi = new DropboxAPI<AndroidAuthSession>(session);
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
         editor.commit();

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
                //Not really necessary
                //Log.i("DbExampleLog", "The file's rev is: " + info.getMetadata().rev);

                /**
                FileWriter new_file=new FileWriter(title);
                BufferedWriter bw = new BufferedWriter(new_file);
                bw.write((int)ratingBar.getRating());
                bw.close();*/

                //Read from new file
                FileInputStream file = openFileInput(title);
                byte[] buffer = new byte[((int) file.getChannel().size())];
                file.read(buffer,0, (int) file.getChannel().size());
                file.close();

                //buffer[(int) file.getChannel().size()] = float2ByteArray(ratingBar.getRating())[1];

                aString = new String(buffer);
                final String finalAString1 = aString;
                runOnUiThread(new Runnable() {
                    public void run() {
                        btnSubmit.setText(finalAString1);
                    }
                });
                file.close();

            } catch (DropboxException e) {
                //btnSubmit.setText("File download unsuccessful.");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Then, we need to calculate the new average
            int rating=(int)ratingBar.getRating();
            String[] string_array=aString.split(" ");
            rating+=Integer.parseInt(string_array[0]);
            final int counter=Integer.parseInt(string_array[1])+1;
            final int finalResult = rating;

            //I'm running this part on the UI Thread because you can't touch the viewing components
            //directly from this download thread
            runOnUiThread(new Runnable() {
                public void run() {
                    ratingBar.setRating(finalResult /counter);
                }
            });

            try {
                FileOutputStream fos = openFileOutput(title, Context.MODE_PRIVATE);
                fos.write(Integer.toString(rating).getBytes());
                fos.write(" ".getBytes());
                fos.write(Integer.toString(counter).getBytes());
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

                } catch (DropboxException e) {
                    //btnSubmit.setText("File upload unsuccessful.");
                }
                  catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
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