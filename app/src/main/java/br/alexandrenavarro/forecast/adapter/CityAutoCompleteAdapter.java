package br.alexandrenavarro.forecast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.alexandrenavarro.forecast.R;
import br.alexandrenavarro.forecast.app.ForecastApplication;
import br.alexandrenavarro.forecast.model.City;
import br.alexandrenavarro.forecast.net.ForecastService;
import br.alexandrenavarro.forecast.net.SearchCityResponse;
import retrofit2.Response;

/**
 * Created by alexandrenavarro on 7/14/16.
 */
public class CityAutoCompleteAdapter extends BaseAdapter implements Filterable{

    private List<City> cities = new ArrayList<>();
    private Context mContext;

   public CityAutoCompleteAdapter(Context context){
       this.mContext = context;
   }

    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public City getItem(int i) {
        return cities.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.simple_dropdown_item_1line, parent, false);
        }
        City city = getItem(position);
        ((TextView) convertView.findViewById(android.R.id.text1)).setText(city.getName() + " - " +
        city.getRegion());
        return convertView;
    }

    @Override
    public Filter getFilter() {
       return new Filter(){

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    try {
                        Response<SearchCityResponse> execute = ForecastApplication.getInstance()
                                .getRetrofitClient().
                                        create(ForecastService.class).
                                        searchByCity(constraint.toString(), 3).execute();
                        if(execute != null && execute.body() != null){
                            List<City> result = execute.body().getResult();
                            if(result != null) {
                                filterResults.values = result;
                                filterResults.count = result.size();
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filterResults != null && filterResults.count > 0) {
                    cities = (List<City>) filterResults.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}