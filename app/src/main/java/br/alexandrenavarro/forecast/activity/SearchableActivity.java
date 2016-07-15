package br.alexandrenavarro.forecast.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import br.alexandrenavarro.forecast.R;
import br.alexandrenavarro.forecast.adapter.CityAutoCompleteAdapter;
import br.alexandrenavarro.forecast.model.City;
import br.alexandrenavarro.forecast.view.DelayAutoCompleteTextView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexandrenavarro on 7/14/16.
 */

public class SearchableActivity extends AppCompatActivity{

    @BindView(R.id.auto_complete) DelayAutoCompleteTextView autoCompleteTextView;
    @BindView(R.id.pb_loading_indicator) ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        ButterKnife.bind(this);

        autoCompleteTextView.setAdapter(new CityAutoCompleteAdapter(getApplicationContext()));
        autoCompleteTextView.setThreshold(3);
        autoCompleteTextView.setLoadingIndicator(progressBar);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                City city = (City) adapterView.getItemAtPosition(position);
                Intent intent = new Intent();
                intent.putExtra(MainActivity.EXTRA_CITY, city);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


    }
}