package br.alexandrenavarro.forecast.model;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by alexandrenavarro on 7/13/16.
 */
public class Weather implements Serializable{

    private String date;
    @SerializedName("mintempC")
    private String minTempCelsius;
    @SerializedName("mintempF")
    private String minTempFahrenheit;
    @SerializedName("maxtempC")
    private String maxTempCelsius;
    @SerializedName("maxtempF")
    private String maxTempFahrenheit;
    @SerializedName("hourly")
    private List<WeatherHourly> weatherHourlies;

    public Date getDate() {
        if(TextUtils.isEmpty(date)){
            return null;
        }

        Date returnDate = null;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            returnDate = dateFormat.parse(date);
        } catch (ParseException e) {
            Log.d(Weather.class.getSimpleName(), e.getMessage());
        }
        return returnDate;
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

    private class WeatherHourly implements Serializable{

        protected List<WeatherResources> weatherIconUrl;
        @SerializedName("weatherDesc")
        protected List<WeatherResources> weatherDescription;
        protected int weatherCode;

    }

    public String getWeatherIconUrl(){
        if(weatherHourlies == null || weatherHourlies.size() < 1 ||
                weatherHourlies.get(0).weatherIconUrl == null || weatherHourlies.get(0).weatherIconUrl.size() < 1){
            return null;
        }

        return weatherHourlies.get(0).weatherIconUrl.get(0).getValue();

    }

    public String getWeatherDescription(){
        if(weatherHourlies == null || weatherHourlies.size() < 1 ||
                weatherHourlies.get(0).weatherDescription == null || weatherHourlies.get(0).weatherDescription.size() < 1){
            return null;
        }

        return weatherHourlies.get(0).weatherDescription.get(0).getValue();
    }

    public int getWeatherCode(){
        if(weatherHourlies == null || weatherHourlies.size() < 1 ){
            return 0;
        }
        return weatherHourlies.get(0).weatherCode;
    }
}