package br.alexandrenavarro.forecast.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import br.alexandrenavarro.forecast.R;
import br.alexandrenavarro.forecast.app.ForecastApplication;
import br.alexandrenavarro.forecast.model.Weather;
import br.alexandrenavarro.forecast.utils.Util;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexandrenavarro on 7/15/16.
 */
public class ForecastDetailAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Weather> forecasts = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.forecast_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Weather forecast = forecasts.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;

        if(forecast.getDate() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE");
            viewHolder.txtWeekDay.setText(dateFormat.format(forecast.getDate()));
        }

        viewHolder.txtDescription.setText(forecast.getWeatherDescription());

        if(Locale.US.equals(Locale.getDefault())){
            viewHolder.txtMaxTemp.setText(forecast.getMaxTempFahrenheit() + " \u2109");
            viewHolder.txtMinTemp.setText(forecast.getMinTempFahrenheit() + " \u2109");
        }else {
            viewHolder.txtMaxTemp.setText(forecast.getMaxTempCelsius() + " \u2103");
            viewHolder.txtMinTemp.setText(forecast.getMinTempCelsius() + " \u2103");
        }

        Picasso.with(viewHolder.imvWeather.getContext()).
                load(forecast.getWeatherIconUrl()).
                fit().into(viewHolder.imvWeather);

        viewHolder.root.setBackgroundColor(Util.findColorByWeatherCode(viewHolder.root.getContext(), forecast.getWeatherCode()));
    }

    @Override
    public int getItemCount() {
        return forecasts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_week_day) TextView txtWeekDay;
        @BindView(R.id.txt_min_temperature) TextView txtMinTemp;
        @BindView(R.id.txt_max_temperature) TextView txtMaxTemp;
        @BindView(R.id.txt_description) TextView txtDescription;
        @BindView(R.id.imv_weather) ImageView imvWeather;
        public final View root;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            root = v;
        }
    }

    public void addAll(List<Weather> weathers){
        this.forecasts.clear();
        forecasts.addAll(weathers);
        notifyDataSetChanged();
    }
}
