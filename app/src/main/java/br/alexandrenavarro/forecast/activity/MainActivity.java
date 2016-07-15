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

import br.alexandrenavarro.forecast.R;
import br.alexandrenavarro.forecast.SystemPreferences;
import br.alexandrenavarro.forecast.adapter.CityAdapter;
import br.alexandrenavarro.forecast.app.ForecastApplication;
import br.alexandrenavarro.forecast.event.UpdateForecastEvent;
import br.alexandrenavarro.forecast.job.FirsRunJob;
import br.alexandrenavarro.forecast.job.ForecastJob;
import br.alexandrenavarro.forecast.job.LoadAllCitiesJob;
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
            ForecastApplication.getInstance().getJobManager().addJobInBackground(new FirsRunJob());
        }else {
            update();
        }
    }

    private void update() {
        isLoading = true;
        ForecastApplication.getInstance().getJobManager().addJobInBackground(new LoadAllCitiesJob());
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
        isLoading = false;
        mSwipeRefreshLayout.setRefreshing(false);
        for (City city: cities) {
            ForecastApplication.getInstance().getJobManager().addJobInBackground(new ForecastJob(city));
        }
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