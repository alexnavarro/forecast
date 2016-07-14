package br.alexandrenavarro.forecast.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Table;

import java.io.Serializable;

/**
 * Created by alexandrenavarro on 7/13/16.
 */

@Table(name ="city")
public class City extends SugarRecord {

    @SerializedName("areaName")
    private String name;
    private String country;
    private String region;
    private double latitude;
    private double longitude;

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (Double.compare(city.latitude, latitude) != 0) return false;
        return Double.compare(city.longitude, longitude) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
