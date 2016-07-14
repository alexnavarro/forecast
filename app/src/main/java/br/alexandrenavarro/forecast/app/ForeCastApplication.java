package br.alexandrenavarro.forecast.app;

import android.app.Application;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import br.alexandrenavarro.forecast.gson_adapter.DateTypeAdapter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alexandrenavarro on 7/13/16.
 */
public class ForeCastApplication extends Application {

    private Retrofit defaultRetrofit;
    private static final String BASE_URL = "http://api.worldweatheronline.com/premium/v1";

    public Retrofit getRetrofitClient() {
        if (defaultRetrofit == null) {
            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .registerTypeAdapter(Date.class, new DateTypeAdapter())
                    .create();
            defaultRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        }

        return defaultRetrofit;
    }
}
