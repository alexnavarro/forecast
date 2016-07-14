package br.alexandrenavarro.forecast.model;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.List;

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
    @SerializedName("hourly")
    private List<WeatherHourly> weatherHourlies;

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

    private class WeatherHourly{

        protected List<WeatherResources> weatherIconUrl;
        @SerializedName("weatherDesc")
        protected List<WeatherResources> weatherDescription;

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
}
