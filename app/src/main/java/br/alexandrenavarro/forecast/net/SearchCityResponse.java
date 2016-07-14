package br.alexandrenavarro.forecast.net;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import br.alexandrenavarro.forecast.model.City;

/**
 * Created by alexandrenavarro on 7/13/16.
 */
public class SearchCityResponse {

    @SerializedName("search_api")
    private SearchAPI searchAPI;

    private class SearchAPI{
        protected List<City> result;
    }

    public List<City> getResult() {
        return searchAPI != null && searchAPI.result != null ? searchAPI.result : new ArrayList<City>();
    }
}
