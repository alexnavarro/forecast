package br.alexandrenavarro.forecast.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import br.alexandrenavarro.forecast.R;
import br.alexandrenavarro.forecast.SystemPreferences;
import br.alexandrenavarro.forecast.adapter.CityAdapter;
import br.alexandrenavarro.forecast.app.ForecastApplication;
import br.alexandrenavarro.forecast.event.UpdateForecastEvent;
import br.alexandrenavarro.forecast.job.ForecastJob;
import br.alexandrenavarro.forecast.model.City;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";
    public final static int SEARCHABLE_REQUEST_CODE = 666;
    public final static String EXTRA_CITY = "EXTRA_CITY";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout mSwipeRefreshLayout;

    private CityAdapter mAdapter;
    private boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        mAdapter = new CityAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        firstRun();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isLoading) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    return;
                }

                update();

            }
        });


    }

    private void firstRun() {
        if (SystemPreferences.getInstance().isFirstRun()) {
            SystemPreferences.getInstance().setFirstRun();
            new Thread(new Runnable() {
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

                    final List<City> cities = City.listAll(City.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ForecastApplication.getInstance().getBus().post(cities);
                        }
                    });

                    for (City city: cities) {
                        ForecastApplication.getInstance().getJobManager().addJob(new ForecastJob(city));
                    }
                }
            }).start();
        }else {
            update();
        }
    }

    private void update() {
        isLoading = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<City> cities = City.listAll(City.class);
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mAdapter.addCities(cities);
                        isLoading = false;
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });

                for (City city: cities) {
                    ForecastApplication.getInstance().getJobManager().addJob(new ForecastJob(city));
                }
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ForecastApplication.getInstance().getBus().register(this);
    }

    @Override
    protected void onPause() {
        ForecastApplication.getInstance().getBus().unregister(this);
        super.onPause();
    }

    @Subscribe
    public void onForecastSuccess(UpdateForecastEvent event){
        mAdapter.updateForecast(event.getCity(), event.getForecast());
    }

    @Subscribe
    public void updateAll(ArrayList<City> cities){
        mAdapter.addCities(cities);
    }

    @OnClick(R.id.fab)
    public void onClickFab(View view) {
        Intent intent = new Intent(getApplicationContext(), SearchableActivity.class);
        startActivityForResult(intent, SEARCHABLE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == SEARCHABLE_REQUEST_CODE && resultCode == RESULT_OK){
            if(data != null && data.hasExtra(EXTRA_CITY)){
                City city = (City) data.getSerializableExtra(EXTRA_CITY);
                mAdapter.addCity(city);
            }
        }
    }
}