package com.datasteffen.datenclick;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by steffen on 23-05-2016.
 */
public class ActiveProfile implements Serializable {

    private String _id;
    private String name;
    private String email;
    private float lat;
    private float lon;
    private byte[] imgbytes;


    public ActiveProfile() {
    }

    public ActiveProfile(String _id, String name, String email, float lat, float lon, byte[] imgbytes) {
        this._id = _id;
        this.name = name;
        this.email = email;
        this.lat = lat;
        this.lon = lon;
        this.imgbytes = imgbytes;
    }

    public ActiveProfile(String email, float lat, float lon, byte[] imgbytes) {
        this.email = email;
        this.lat = lat;
        this.lon = lon;
        this.imgbytes = imgbytes;

    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
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
            job.put("image",getImgbytes());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return job.toString();
    }
}
