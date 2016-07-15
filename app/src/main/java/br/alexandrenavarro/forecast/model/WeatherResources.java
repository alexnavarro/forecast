package br.alexandrenavarro.forecast.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by alexandrenavarro on 7/14/16.
 */
public class WeatherResources implements Serializable{

    private String value;

    public String getValue() {
        return value;
    }
}
