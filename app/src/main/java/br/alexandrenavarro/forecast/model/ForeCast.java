package br.alexandrenavarro.forecast.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandrenavarro on 7/13/16.
 */
public class Forecast implements Serializable{

    @SerializedName("current_condition")
    private List<CurrentCondition> currentConditions;
    @SerializedName("weather")
    private List<Weather> weathers;

    public List<Weather> getWeathers() {
        return weathers != null ? weathers : new ArrayList<Weather>();
    }

    public List<CurrentCondition> getCurrentConditions() {
        return currentConditions != null ? currentConditions : new ArrayList<CurrentCondition>();
    }
}
