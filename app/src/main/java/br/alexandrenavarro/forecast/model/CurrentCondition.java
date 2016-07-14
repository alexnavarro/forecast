package br.alexandrenavarro.forecast.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alexandrenavarro on 7/13/16.
 */
public class CurrentCondition {

    @SerializedName("temp_C")
    private String tempCelsius;
    @SerializedName("temp_F")
    private String tempFahrenheit;
    private String humidity;
    private List<WeatherResources> weatherIconUrl;
    @SerializedName("weatherDesc")
    private List<WeatherResources> weatherDescription;
    private int weatherCode;

    public String getTempCelsius() {
        return tempCelsius;
    }

    public String getTempFahrenheit() {
        return tempFahrenheit;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getWeatherDescription() {
        return weatherDescription != null && weatherDescription.size() > 0 ? weatherDescription.get(0).getValue() : "";
    }

    public String getWeatherIconUrl() {
        return weatherIconUrl != null && weatherIconUrl.size() > 0 ? weatherIconUrl.get(0).getValue() : null ;
    }

    public int getWeatherCode() {
        return weatherCode;
    }
}
