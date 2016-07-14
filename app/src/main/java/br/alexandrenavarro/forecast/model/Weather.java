package br.alexandrenavarro.forecast.model;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;

/**
 * Created by alexandrenavarro on 7/13/16.
 */
public class Weather {

    private Calendar date;
    @SerializedName("mintempC")
    private String minTempCelsius;
    @SerializedName("mintempF")
    private String minTempFahrenheit;
    @SerializedName("maxtempC")
    private String maxTempCelsius;
    @SerializedName("maxtempF")
    private String maxTempFahrenheit;

    public Calendar getDate() {
        return date;
    }

    public String getMinTempCelsius() {
        return minTempCelsius;
    }

    public String getMinTempFahrenheit() {
        return minTempFahrenheit;
    }

    public String getMaxTempCelsius() {
        return maxTempCelsius;
    }

    public String getMaxTempFahrenheit() {
        return maxTempFahrenheit;
    }
}
