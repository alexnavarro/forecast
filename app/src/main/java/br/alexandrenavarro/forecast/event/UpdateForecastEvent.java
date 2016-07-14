package br.alexandrenavarro.forecast.event;

import br.alexandrenavarro.forecast.model.City;
import br.alexandrenavarro.forecast.model.Forecast;

/**
 * Created by alexandrenavarro on 7/14/16.
 */
public class UpdateForecastEvent {

    private City city;
    private Forecast forecast;

    public UpdateForecastEvent(City city, Forecast forecast){
        this.city = city;
        this.forecast = forecast;
    }

    public City getCity() {
        return city;
    }

    public Forecast getForecast() {
        return forecast;
    }
}
