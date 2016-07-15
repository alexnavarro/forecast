package br.alexandrenavarro.forecast.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.alexandrenavarro.forecast.R;
import br.alexandrenavarro.forecast.app.ForecastApplication;
import br.alexandrenavarro.forecast.job.ForecastJob;
import br.alexandrenavarro.forecast.model.City;
import br.alexandrenavarro.forecast.model.CurrentCondition;
import br.alexandrenavarro.forecast.model.Forecast;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexandrenavarro on 7/14/16.
 */
public class CityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

   private List<City> cities = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_item, parent, false));

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo Abrir Activity de Previsao
            }
        });

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               City city = (City) view.getTag();
                remove(city);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        City city = cities.get(position);
        viewHolder.txtCity.setText(city.getName());
        viewHolder.btnRemove.setTag(city);

        Forecast forecast = city.getForecast();
        if(forecast != null){
            if(forecast.getCurrentConditions() != null && forecast.getCurrentConditions().size() > 0){
                CurrentCondition currentCondition = forecast.getCurrentConditions().get(0);
                if(Locale.US.equals(Locale.getDefault())){
                    viewHolder.txtTemperature.setText(currentCondition.getTempFahrenheit() + " \u2109");
                }else {
                    viewHolder.txtTemperature.setText(currentCondition.getTempCelsius() + " \u2103");
                }

                if(currentCondition.getWeatherIconUrl() != null){
                    Picasso.with(viewHolder.imvWeather.getContext()).
                            load(currentCondition.getWeatherIconUrl()).
                            fit().into(viewHolder.imvWeather);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public void addCities(List<City> cities){
        if(cities != null && cities.size() > 0){
            if(this.cities.size() > 0){
                this.cities.clear();
            }

            this.cities.addAll(cities);
            notifyDataSetChanged();
        }
    }

    public void addCity(final City city){
        int index = cities.indexOf(city);
        if(index == -1){
            cities.add(city);
            notifyItemChanged(index);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    city.save();
                    ForecastApplication.getInstance().getJobManager().addJob(new ForecastJob(city));
                }
            }).start();
        }
    }

    public void remove(final City city){
        int index = cities.indexOf(city);
        if(index != -1){
            cities.remove(city);
            notifyItemRemoved(index);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    city.delete();
                }
            }).start();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_city) TextView txtCity;
        @BindView(R.id.btn_remove) ImageButton btnRemove;
        @BindView(R.id.txt_temperature) TextView txtTemperature;
        @BindView(R.id.imv_weather) ImageView imvWeather;
        public final View root;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            root = v;
        }
    }

    public void updateForecast(City city, Forecast forecast){
        int index = cities.indexOf(city);
        if(index != -1){
            cities.get(index).setForecast(forecast);
            notifyItemChanged(index);
        }
    }
}