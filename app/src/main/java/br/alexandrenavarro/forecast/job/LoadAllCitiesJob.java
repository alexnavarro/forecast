package br.alexandrenavarro.forecast.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;

import java.util.List;

import br.alexandrenavarro.forecast.app.ForecastApplication;
import br.alexandrenavarro.forecast.model.City;

/**
 * Created by alexandrenavarro on 7/15/16.
 */
public class LoadAllCitiesJob  extends Job {

    public LoadAllCitiesJob(){
        super(new Params(Integer.MAX_VALUE).groupBy("first-run"));
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        final List<City> cities = City.listAll(City.class);
        ForecastApplication.getInstance().getBus().post(cities);
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {

    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return null;
    }
}
