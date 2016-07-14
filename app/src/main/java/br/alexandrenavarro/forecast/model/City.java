package br.alexandrenavarro.forecast.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alexandrenavarro on 7/13/16.
 */
public class City {

    @SerializedName("areaName")
    private String name;
    private String country;
    private String region;
    private long latitude;
    private long longitude;

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public long getLatitude() {
        return latitude;
    }

    public long getLongitude() {
        return longitude;
    }
}
