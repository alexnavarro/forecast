package br.alexandrenavarro.forecast.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;

import java.util.List;

import br.alexandrenavarro.forecast.SystemPreferences;
import br.alexandrenavarro.forecast.app.ForecastApplication;
import br.alexandrenavarro.forecast.model.City;

/**
 * Created by alexandrenavarro on 7/15/16.
 */
public class FirsRunJob extends Job {

    public FirsRunJob() {
        super(new Params(Integer.MAX_VALUE).groupBy("first-run"));
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        SystemPreferences.getInstance().setFirstRun();

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

        List<City> cities = City.listAll(City.class);
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
