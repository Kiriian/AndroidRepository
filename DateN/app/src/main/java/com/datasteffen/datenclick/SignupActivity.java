package com.datasteffen.datenclick;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class SignupActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button btn = (Button)findViewById(R.id.createuserbutton);

        final EditText email = (EditText)findViewById(R.id.email);
        final EditText password = (EditText)findViewById(R.id.password);
        final EditText name = (EditText)findViewById(R.id.name);
        final EditText age = (EditText)findViewById(R.id.age);
        final RadioButton gendermale = (RadioButton) findViewById(R.id.radioboxgendermale);
        final CheckBox searchmale = (CheckBox) findViewById(R.id.checkboxsearchmale);
        final CheckBox searchfemale = (CheckBox) findViewById(R.id.checkboxsearchfemale);
        final EditText searchfromage = (EditText)findViewById(R.id.searchfromage);
        final EditText searchtoage = (EditText)findViewById(R.id.searchtoage);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Profile pf = new Profile();
                pf.setPassword(password.getText().toString());pf.setEmail(email.getText().toString());
                pf.setName(name.getText().toString());
                pf.setAge(Integer.parseInt(age.getText().toString()));
                String gender = gendermale.isChecked() ? "male": "female";
                pf.setGender(gender);
                pf.setSearchfemale(searchfemale.isChecked());
                pf.setSearchmale(searchmale.isChecked());
                pf.setSearchfromage(Integer.parseInt(searchfromage.getText().toString()));
                pf.setSearchtoage(Integer.parseInt(searchtoage.getText().toString()));

                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                intent.putExtra("profilefromsignup",pf);
                startActivity(intent);

                //Toast.makeText(getApplicationContext(),pf.getName(),Toast.LENGTH_LONG).show();
                try {
                    pf = new AsyncTaskSendUserToDb().execute(pf).get();
                    //to do, save pf in sqllite
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public class AsyncTaskSendUserToDb extends AsyncTask<Profile,Void,Profile>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Profile doInBackground(Profile... params) {
            HttpURLConnection urlConnection = null;
            try {
                Profile pf = params[0];
                URL url = new URL("http://android2-smcphbusiness.rhcloud.com/users/adduser");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");

                OutputStream os = urlConnection.getOutputStream();
                OutputStreamWriter wr = new OutputStreamWriter(os);
                String json = pf.toJson();
                wr.write(json);
                wr.flush();
                wr.close();
                os.close();

                InputStream ins = urlConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(ins);
                BufferedReader in = new BufferedReader(isr);

                String inputLine;
                String result= "";

                while( (inputLine = in.readLine()) != null )
                    result += inputLine;

                in.close();


                //      InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                return pf;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                urlConnection.disconnect();
            }return null;
        }
    }

}

