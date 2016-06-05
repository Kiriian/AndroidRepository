package com.datasteffen.datenclick;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by steffen on 03-06-2016.
 */

public class AsyncGetActiveProfile extends AsyncTask<List<ActiveProfile>,Void,List<ActiveProfile>>{
    URL url;
    HttpURLConnection urlConnection;

    @Override
    protected List<ActiveProfile> doInBackground(List<ActiveProfile>... params) {
        try {
            url = new URL("http://android2-smcphbusiness.rhcloud.com/users/getall");

            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            List<ActiveProfile> activeprofiles = readStream(in);

            return activeprofiles;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }finally {
            urlConnection.disconnect();
        }return null;
    }

    @Override
    protected void onPostExecute(List<ActiveProfile> s) {
    }

    private List<ActiveProfile> readStream(InputStream is) throws JSONException {

        ActiveProfile activeProfile = null;
        List<ActiveProfile> activeprofiles = new ArrayList<>();
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }


            JSONArray jarray = new JSONArray(bo.toString());
            for(int j = 0; j < jarray.length();j++){
                JSONObject jsonobject = jarray.getJSONObject(j);
                String id = jsonobject.optString("_id").toString();
                String name = jsonobject.optString("name").toString();
                String email = jsonobject.optString("email").toString();

                JSONObject jso =jsonobject.optJSONObject("activeprofile");


                float lat = Float.parseFloat(jso.optString("lat"));
                float lon = Float.parseFloat(jso.optString("lon"));
                byte[] image = jso.optString("image").getBytes();



                //     float lon = Float.parseFloat(jsonobject.optString("lon"));
                //    float lat = Float.parseFloat(jsonobject.optString("lat").toString());;
                //       double lon = jsonobject.optDouble("lon");

                activeProfile = new ActiveProfile(id,name,email,lat,lon,image);
                activeprofiles.add(activeProfile);

            }

            return activeprofiles;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}