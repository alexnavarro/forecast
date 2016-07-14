package br.alexandrenavarro.forecast.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.alexandrenavarro.forecast.R;
import br.alexandrenavarro.forecast.model.City;
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

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.txtCity.setText(cities.get(position).getName());
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_city) TextView txtCity;
        public final View root;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            root = v;
        }
    }
}
