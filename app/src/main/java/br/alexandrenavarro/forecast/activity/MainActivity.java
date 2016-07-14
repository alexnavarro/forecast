package br.alexandrenavarro.forecast.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.squareup.otto.Subscribe;

import java.util.List;

import br.alexandrenavarro.forecast.R;
import br.alexandrenavarro.forecast.adapter.CityAdapter;
import br.alexandrenavarro.forecast.app.ForecastApplication;
import br.alexandrenavarro.forecast.event.UpdateForecastEvent;
import br.alexandrenavarro.forecast.job.ForecastJob;
import br.alexandrenavarro.forecast.model.City;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private CityAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        mAdapter = new CityAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<City> cities = City.listAll(City.class);
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mAdapter.addCities(cities);
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

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
