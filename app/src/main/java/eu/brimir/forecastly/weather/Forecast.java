package eu.brimir.forecastly.weather;

import eu.brimir.forecastly.R;

/**
 * Created by Patrik on 2015-07-10.
 */
public class Forecast {

    private Current mCurrent;
    private Hourly[] mHourlyForecast;
    private Daily[] mDailyForecast;
    private TimeMachine mTimeMachine;

    public TimeMachine getTimeMachine() {
        return mTimeMachine;
    }

    public void setTimeMachine(TimeMachine timeMachine) {
        mTimeMachine = timeMachine;
    }

    public Current getCurrent() {
        return mCurrent;
    }

    public void setCurrent(Current current) {
        mCurrent = current;
    }

    public Hourly[] getHourlyForecast() {
        return mHourlyForecast;
    }

    public void setHourlyForecast(Hourly[] hourlyForecast) {
        mHourlyForecast = hourlyForecast;
    }

    public Daily[] getDailyForecast() {
        return mDailyForecast;
    }

    public void setDailyForecast(Daily[] dailyForecast) {
        mDailyForecast = dailyForecast;
    }
    
    public static int getIconId(String iconString){
        int iconId = R.drawable.clear_day;
        switch (iconString) {
            case "clear-day":
                iconId = R.drawable.clear_day;
                break;
            case "clear-night":
                iconId = R.drawable.clear_night;
                break;
            case "rain":
                iconId = R.drawable.rain;
                break;
            case "snow":
                iconId = R.drawable.snow;
                break;
            case "sleet":
                iconId = R.drawable.sleet;
                break;
            case "wind":
                iconId = R.drawable.wind;
                break;
            case "fog":
                iconId = R.drawable.fog;
                break;
            case "cloudy":
                iconId = R.drawable.cloudy;
                break;
            case "partly-cloudy-day":
                iconId = R.drawable.partly_cloudy;
                break;
            case "partly-cloudy-night":
                iconId = R.drawable.cloudy_night;
                break;
        }
        return iconId;
    }
}
