package br.alexandrenavarro.forecast;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


/**
 * Created by alexandrenavarro on 7/14/16.
 */
public class SystemPreferences {

    private static SystemPreferences mInstance;
    private SharedPreferences mPreferences;

    public static void init(Context context){
        mInstance = new SystemPreferences(context.getApplicationContext());
    }

    public static SystemPreferences getInstance() {
        if (mInstance == null) {
            throw new NullPointerException("You must initialize on Application class using init()");
        }

        return mInstance;
    }

    private SystemPreferences(Context context){
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setFirstRun(){
        if(isFirstRun()) {
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putBoolean("first_run", false);
            editor.commit();
        }
    }

    public boolean isFirstRun() {
        return mPreferences.getBoolean("first_run", true);
    }
}
