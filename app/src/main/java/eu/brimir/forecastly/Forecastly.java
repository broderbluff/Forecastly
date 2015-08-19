package eu.brimir.forecastly;

import android.app.Application;
import android.content.Context;

/**
 * Created by brode on 2015-08-19.
 */
public class Forecastly  extends Application{

    private static Context context;

    public void onCreate(){
        super.onCreate();
        Forecastly.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return Forecastly.context;
    }
}



