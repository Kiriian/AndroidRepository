package com.datasteffen.datenclick;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {

    String mEmail;
    String mPassword;
    private EditText mEmailView;
    private EditText mPasswordView;
    Boolean b = false;
    Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        Button signup = (Button) findViewById(R.id.signupbutton);
        assert signup != null;
        signup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(i);
            }
        });

        Button loginbutton = (Button) findViewById(R.id.email_sign_in_button);

        assert loginbutton != null;
        loginbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                mEmailView = (EditText) findViewById(R.id.email);
                mPasswordView =(EditText) findViewById(R.id.password);

                // skal sende signupactivity intent extra

                try {
                    profile = new UserLoginTask().execute().get();
                    if(profile !=null){
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        i.putExtra("profiletoimage",profile);
                        startActivity(i);
                    }else {
                        Toast.makeText(getApplicationContext(),"Please try login again",Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public class UserLoginTask extends AsyncTask<Profile, Void, Profile> {

        URL url;
        HttpURLConnection urlConnection;

        @Override
        protected void onPreExecute() {

            mEmail = mEmailView.getText().toString();
            mPassword = mPasswordView.getText().toString();

        }

        @Override
        protected Profile doInBackground(Profile... params) {

            try {
                String emailtourl = mEmail;
                String passwordtourl = mPassword;
                url = new URL("http://android2-smcphbusiness.rhcloud.com/users/getuserfromlogin"+"/"+emailtourl+"/"+passwordtourl);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                Log.d("det", in.toString());
                Profile pf =  readStream(in);


                return pf;


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }finally {
                urlConnection.disconnect();
            }

            return null;
        }
    }
    private Profile readStream(InputStream is) throws JSONException {

        Profile profile = null;

        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }

            JSONObject jsonobject1 = new JSONObject(bo.toString());

            String id = jsonobject1.optString("_id");
            String name = jsonobject1.optString("name");
            int age = Integer.parseInt(jsonobject1.optString("age"));
            String gender = jsonobject1.optString("gender");
            Boolean searchmale = Boolean.parseBoolean(jsonobject1.optString("searchmale"));
            Boolean searchfemale = Boolean.parseBoolean(jsonobject1.optString("searchfemale"));
            int searchfromage = Integer.parseInt(jsonobject1.optString("searchfromage"));
            int searchtoage = Integer.parseInt(jsonobject1.optString("searchtoage"));

            profile = new Profile(id,name,age,gender,searchmale,searchfemale,searchfromage,searchtoage);

            return profile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}