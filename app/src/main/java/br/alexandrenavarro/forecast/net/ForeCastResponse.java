package br.alexandrenavarro.forecast.net;

import com.google.gson.annotations.SerializedName;

import br.alexandrenavarro.forecast.model.Forecast;

/**
 * Created by alexandrenavarro on 7/13/16.
 */
public class ForecastResponse {

    @SerializedName("data")
    private Forecast forecast;

    public Forecast getForecast() {
        return forecast;
    }
}
