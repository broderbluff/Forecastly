package eu.brimir.forecastly.weather;

import android.content.Context;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import eu.brimir.forecastly.R;

/**
 * Created by Patrik on 2015-07-09.
 */
public class Current {
    private String mIcon;
    private long mTime;
    private double mTemperature;
    private double mHumidity;
    private double mPrecipChance;
    private String mPrecipType;
    private String mSummary;
    private String mTimeZone;
    private Context mContext;
    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public int getIconId() {
        //clear-day, clear-night, rain, snow, sleet, wind, fog, cloudy, partly-cloudy-day, or partly-cloudy-night
      return Forecast.getIconId(mIcon);
    }

    private long getTime() {
        return mTime;
    }


    public String getFormattedTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        SimpleDateFormat formatterDay = new SimpleDateFormat("ccc" + " d/MM");
        SimpleDateFormat formatterWeek = new SimpleDateFormat("w");
        formatter.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        Date dateTime = new Date(getTime() * 1000);
        String timeString = formatter.format(dateTime);
        String dayString = formatterDay.format(dateTime);
        String weekString = formatterWeek.format(dateTime);
        String locale = Locale.getDefault().getLanguage().toString();
        if (locale.equals("sv")){
            return timeString + ", "+dayString + ". " + " Vecka " + " " + weekString ;
        }else{

        }return timeString + ", "+dayString;

    }


    public void setTime(long time) {
        mTime = time;
    }

    public int getTemperature() {


        return (int) Math.round(mTemperature);
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public int getHumidity() {


        double humidityPercentage = mHumidity * 100;


        return (int) Math.round(humidityPercentage);

    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }

    public int getPrecipChance() {
        double precipPercentage = mPrecipChance * 100;
        return (int) Math.round(precipPercentage);
    }

    public void setPrecipChance(double precipChance) {
        mPrecipChance = precipChance;
    }


    public String getPrecipType() {

        switch (mPrecipType) {
            case "rain":

                return mContext.getString(R.string.rain);
            case "snow":
                return mContext.getString(R.string.snow);
            case "sleet":
                return mContext.getString(R.string.sleet);
            case "hail":
                return mContext.getString(R.string.hail);
        }
        return mPrecipType;
    }

    public void setPrecipType(String precipType) {
        mPrecipType = precipType;
    }

    public String getSummary() {



        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }


}
