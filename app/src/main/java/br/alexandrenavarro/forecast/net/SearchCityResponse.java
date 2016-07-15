package br.alexandrenavarro.forecast.net;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import br.alexandrenavarro.forecast.model.City;
import br.alexandrenavarro.forecast.model.WeatherResources;

/**
 * Created by alexandrenavarro on 7/13/16.
 */
public class SearchCityResponse {

    @SerializedName("search_api")
    private SearchAPI searchAPI;

    private class SearchAPI {
        protected List<Result> result;
    }

    private class Result {

        private List<WeatherResources> areaName;
        private List<WeatherResources> country;
        private List<WeatherResources> region;

        double latitude;
        double longitude;

        String getName(){
            return areaName!=null && areaName.size() > 0 ? areaName.get(0).getValue() : "";
        }

        String getCountry(){
            return country!=null && country.size() > 0 ? country.get(0).getValue() : "";
        }

        String getRegion(){
            return region!=null && region.size() > 0 ? region.get(0).getValue() : "";
        }

    }

    public List<City> getResult() {
        if(searchAPI != null && searchAPI.result != null){
            List<City> cities = new ArrayList<>();
            for(Result result : searchAPI.result){
                City city = new City();
                city.setLongitude(result.longitude);
                city.setLatitude(result.latitude);
                city.setRegion(result.getRegion());
                city.setName(result.getName());
                city.setCountry(result.getCountry());
                cities.add(city);
            }

            return cities;
        }

        return new ArrayList<>();
    }
}
