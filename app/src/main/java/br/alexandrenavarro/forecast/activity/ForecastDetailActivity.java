package br.alexandrenavarro.forecast.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import br.alexandrenavarro.forecast.R;
import br.alexandrenavarro.forecast.adapter.ForecastDetailAdapter;
import br.alexandrenavarro.forecast.app.ForecastApplication;
import br.alexandrenavarro.forecast.event.UpdateForecastEvent;
import br.alexandrenavarro.forecast.job.ForecastJob;
import br.alexandrenavarro.forecast.model.City;
import br.alexandrenavarro.forecast.model.CurrentCondition;
import br.alexandrenavarro.forecast.model.Forecast;
import br.alexandrenavarro.forecast.utils.Util;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexandrenavarro on 7/15/16.
 */
public class ForecastDetailActivity extends AppCompatActivity{

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.imv_weather) ImageView imageView;
    @BindView(R.id.txt_city) TextView txtCity;
    @BindView(R.id.txt_description) TextView txtDescription;
    @BindView(R.id.txt_temperature) TextView txtTemperature;
    @BindView(R.id.main_container) View mainContainer;
    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout mSwipeRefreshLayout;

    City mCity;
    private ForecastDetailAdapter mAdapter;
    private boolean isLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_detail);
        ButterKnife.bind(this);
        setUpToolBar();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAdapter = new ForecastDetailAdapter();
        recyclerView.setAdapter(mAdapter);

        if(getIntent() != null && getIntent().hasExtra(MainActivity.EXTRA_CITY)){
            mCity = (City) getIntent().getSerializableExtra(MainActivity.EXTRA_CITY);
            txtCity.setText(mCity.getName());
            if(mCity.getForecast() != null){
                bind(mCity.getForecast());
            }else {
                update();
            }
        }
    }

    private void bind(Forecast forecast) {
        if(forecast.getCurrentConditions() != null && forecast.getCurrentConditions() != null) {
            CurrentCondition currentCondition = forecast.getCurrentConditions().get(0);
            Picasso.with(getApplicationContext()).load(currentCondition.getWeatherIconUrl()).
                    centerCrop().fit().
            into(imageView);
            txtDescription.setText(currentCondition.getWeatherDescription());
            txtTemperature.setText(Util.getTemperatureBasedOnCurrentLocale(currentCondition));
            mainContainer.setBackgroundColor(Util.findColorByWeatherCode(getApplicationContext(), currentCondition.getWeatherCode()));
        }

        if(forecast.getWeathers() != null){
            mAdapter.addAll(forecast.getWeathers());
        }

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

    private void update(){
        if(mCity != null)
            isLoading = true;
            ForecastApplication.getInstance().getJobManager().addJobInBackground(new ForecastJob(mCity));
    }

    @Subscribe
    public void onForecastSuccess(UpdateForecastEvent event){
        mCity.setForecast(event.getForecast());
        bind(event.getForecast());
        isLoading = false;
        mSwipeRefreshLayout.setRefreshing(false);
    }


    private void setUpToolBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return false;
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
}