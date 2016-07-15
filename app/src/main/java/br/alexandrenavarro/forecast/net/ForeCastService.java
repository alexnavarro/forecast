package br.alexandrenavarro.forecast.net;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by alexandrenavarro on 7/13/16.
 */
public interface ForecastService {

    @GET("search.ashx?format=json&key=f3a4e50779964291867202620161207")
    Call<SearchCityResponse> searchByCity(@Query("query") String query,
                                          @Query("num_of_results") int numberOfResults);

    @GET("weather.ashx?format=json&key=f3a4e50779964291867202620161207")
    Call<ForecastResponse> foreCast(@Query("q") String latitudeAndLongitude,
                                    @Query("num_of_days") int numberOfDays, @Query("tp") int timeIntervals);
}
