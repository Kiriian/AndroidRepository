package com.datasteffen.testandroidgps;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by steffen on 20-05-2016.
 */
public class Dater implements Serializable{

    private String name;
    private float lat;
    private float lon;

    public Dater() {
    }

    public Dater(String name, float lat, float lon) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Dater{" +
                "name='" + name + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }

}
