package br.alexandrenavarro.forecast.app;

import android.util.Log;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;
import com.birbit.android.jobqueue.log.CustomLogger;
import com.orm.SugarApp;
import com.squareup.otto.Bus;

import br.alexandrenavarro.forecast.BuildConfig;
import br.alexandrenavarro.forecast.SystemPreferences;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alexandrenavarro on 7/13/16.
 */
public class ForecastApplication extends SugarApp {

    private Bus bus;
    private static ForecastApplication instance;
    private Retrofit defaultRetrofit;
    private static final String BASE_URL = "http://api.worldweatheronline.com/premium/v1/";
    private JobManager jobManager;

    public ForecastApplication() {
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getJobManager();
        SystemPreferences.init(this);
    }

    public Retrofit getRetrofitClient() {
        if (defaultRetrofit == null) {
            defaultRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
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

    public static ForecastApplication getInstance() {
        return instance;
    }

    public synchronized JobManager getJobManager() {
        if (jobManager == null) {
            configureJobManager();
        }
        return jobManager;
    }

    private OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        return new OkHttpClient.Builder().addInterceptor(logging).build();
    }
}