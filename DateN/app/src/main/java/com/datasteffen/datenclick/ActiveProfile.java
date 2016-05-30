package com.datasteffen.datenclick;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by steffen on 23-05-2016.
 */
public class ActiveProfile implements Serializable {

    private String _id;
    private float lat;
    private float lon;
    private byte[] imgbytes;
    private Date date;

    public ActiveProfile() {
    }

    public ActiveProfile(float lat, float lon, byte[] imgbytes, Date date) {
        this.lat = lat;
        this.lon = lon;
        this.imgbytes = imgbytes;
        this.date = date;
    }

    public ActiveProfile(String _id, float lat, float lon, byte[] imgbytes, Date date) {
        this._id = _id;
        this.lat = lat;
        this.lon = lon;
        this.imgbytes = imgbytes;
        this.date = date;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
