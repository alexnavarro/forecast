package br.alexandrenavarro.forecast.app;

import android.os.Handler;
import android.util.Log;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;
import com.birbit.android.jobqueue.log.CustomLogger;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orm.SugarApp;
import com.squareup.otto.Bus;

import java.util.Date;

import br.alexandrenavarro.forecast.SystemPreferences;
import br.alexandrenavarro.forecast.gson_adapter.DateTypeAdapter;
import br.alexandrenavarro.forecast.model.City;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alexandrenavarro on 7/13/16.
 */
public class ForeCastApplication extends SugarApp {

    private Bus bus;
    private static ForeCastApplication instance;
    private Retrofit defaultRetrofit;
    private static final String BASE_URL = "http://api.worldweatheronline.com/premium/v1";
    private JobManager jobManager;

    public ForeCastApplication() {
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getJobManager();
        SystemPreferences.init(this);

        if(SystemPreferences.getInstance().isFirstRun()){
            SystemPreferences.getInstance().setFirstRun();
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    City dublinCity = new City();
                    dublinCity.setName("Dublin");
                    dublinCity.setCountry("Ireland");
                    dublinCity.setRegion("Dublin");
                    dublinCity.setLatitude(53.333);
                    dublinCity.setLongitude(-6.249);
                    dublinCity.save();

                    City londonCity = new City();
                    londonCity.setName("London");
                    londonCity.setCountry("United Kingdom");
                    londonCity.setRegion("City of London, Greater London");
                    londonCity.setLatitude(51.517);
                    londonCity.setLongitude(-0.106);
                    londonCity.save();

                    City newYorkCity = new City();
                    newYorkCity.setName("New York");
                    newYorkCity.setCountry("United States of America");
                    newYorkCity.setRegion("New York");
                    newYorkCity.setLatitude(40.714);
                    newYorkCity.setLongitude(-74.006);
                    newYorkCity.save();

                    City barcelonaCity = new City();
                    barcelonaCity.setName("Barcelona");
                    barcelonaCity.setCountry("Spain");
                    barcelonaCity.setRegion("Catalonia");
                    barcelonaCity.setLatitude(41.383);
                    barcelonaCity.setLongitude(2.183);
                    barcelonaCity.save();
                }
            });
        }
    }

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

    private void configureJobManager() {
        Configuration.Builder builder = new Configuration.Builder(this)
                .customLogger(new CustomLogger() {
                    private static final String TAG = "JOBS";
                    @Override
                    public boolean isDebugEnabled() {
                        return true;
                    }

                    @Override
                    public void d(String text, Object... args) {
                        Log.d(TAG, String.format(text, args));
                    }

                    @Override
                    public void e(Throwable t, String text, Object... args) {
                        Log.e(TAG, String.format(text, args), t);
                    }

                    @Override
                    public void e(String text, Object... args) {
                        Log.e(TAG, String.format(text, args));
                    }

                    @Override
                    public void v(String text, Object... args) {

                    }
                })
                .minConsumerCount(1)//always keep at least one consumer alive
                .maxConsumerCount(3)//up to 3 consumers at a time
                .loadFactor(3)//3 jobs per consumer
                .consumerKeepAlive(120);//wait 2 minute
        jobManager = new JobManager(builder.build());
    }

    public Bus getBus() {
        if (this.bus == null) {
            this.bus = new Bus();
        }

        return this.bus;
    }

    public static ForeCastApplication getInstance() {
        return instance;
    }

    public synchronized JobManager getJobManager() {
        if (jobManager == null) {
            configureJobManager();
        }
        return jobManager;
    }
}