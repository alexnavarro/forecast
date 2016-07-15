package br.alexandrenavarro.forecast.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;

import br.alexandrenavarro.forecast.app.ForecastApplication;
import br.alexandrenavarro.forecast.event.UpdateForecastEvent;
import br.alexandrenavarro.forecast.model.City;
import br.alexandrenavarro.forecast.net.ForecastResponse;
import br.alexandrenavarro.forecast.net.ForecastService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alexandrenavarro on 7/14/16.
 */
public class ForecastJob extends Job {

    private City city;

    public ForecastJob(City city) {
        super(new Params(Integer.MAX_VALUE).groupBy("forecast"));
        this.city = city;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        ForecastApplication.getInstance().getRetrofitClient().
                create(ForecastService.class).foreCast((city.getLatitude() + "," + city.getLongitude()), 5, 24).
                enqueue(new Callback<ForecastResponse>() {
                    @Override
                    public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
                        if(response != null && response.body() != null)
                        ForecastApplication.getInstance().getBus().
                                post(new UpdateForecastEvent(city, response.body().getForecast()));
                    }

                    @Override
                    public void onFailure(Call<ForecastResponse> call, Throwable t) {
                        Log.d(ForecastJob.class.getSimpleName(), t.getMessage());
                    }
                });
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {

    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return null;
    }
}