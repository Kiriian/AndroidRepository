package com.datasteffen.datenclick;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by steffen on 23-05-2016.
 */
public class ActiveProfile implements Serializable {

    private String email;
    private double lat;
    private double lon;
    private byte[] imgbytes;


    public ActiveProfile() {
    }

    public ActiveProfile(double lat, double lon, byte[] imgbytes) {
        this.lat = lat;
        this.lon = lon;
        this.imgbytes = imgbytes;

    }

    public ActiveProfile(String email, double lat, double lon, byte[] imgbytes) {
        this.email = email;
        this.lat = lat;
        this.lon = lon;
        this.imgbytes = imgbytes;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public byte[] getImgbytes() {
        return imgbytes;
    }

    public void setImgbytes(byte[] imgbytes) {
        this.imgbytes = imgbytes;
    }


    public String toJson(){
        JSONObject job = new JSONObject();

        try {

            job.put("email",getEmail());
            job.put("lat",getLat());
            job.put("lon",getLon());
           // job.put("image",getImgbytes());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return job.toString();
    }
}
