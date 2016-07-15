package br.alexandrenavarro.forecast.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import java.util.Locale;

import br.alexandrenavarro.forecast.R;
import br.alexandrenavarro.forecast.model.CurrentCondition;

/**
 * Created by alexandrenavarro on 7/15/16.
 */
public class Util {

    public static String getTemperatureBasedOnCurrentLocale(CurrentCondition currentCondition){
        if(Locale.US.equals(Locale.getDefault())){
            return currentCondition.getTempFahrenheit() + " \u2109";
        }

        return currentCondition.getTempCelsius() + " \u2103";
    }

    public static int findColorByWeatherCode(Context context, int weatherCode){
        switch (weatherCode){
            case 116:
                return ContextCompat.getColor(context, R.color.divider);
            case 113:
                return ContextCompat.getColor(context,android.R.color.holo_orange_light);
            default:
                return ContextCompat.getColor(context, R.color.divider);
        }

    }
}
