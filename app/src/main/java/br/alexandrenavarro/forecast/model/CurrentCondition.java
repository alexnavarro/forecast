package br.alexandrenavarro.forecast.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alexandrenavarro on 7/13/16.
 */
public class CurrentCondition {

    @SerializedName("temp_C")
    private String tempCelsius;
    @SerializedName("temp_F")
    private String tempFahrenheit;

    public String getTempCelsius() {
        return tempCelsius;
    }

    public String getTempFahrenheit() {
        return tempFahrenheit;
    }
}
