package eu.brimir.forecastly.weather;

import android.annotation.SuppressLint;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by Patrik on 2015-07-09.
 */
public class TimeMachine {
    private String mIcon;
    private long mTime;
    private double mTemperature;
    private double mHumidity;
    private double mPrecipChance;
    private String mPrecipType;
    private String mSummary;
    private String mTimeZone;

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


    public String getFormattedDay() {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatterDay = new SimpleDateFormat("d" + " LLLL");

        formatterDay.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        Date dateTime = new Date(getTime() * 1000);

        return formatterDay.format(dateTime);
    }
    public String getFormattedYear() {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatterYear = new SimpleDateFormat("yyyy");

        formatterYear.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        Date dateTime = new Date(getTime() * 1000);

        return formatterYear.format(dateTime);
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

                return "Risk för regn";
            case "snow":
                return "Risk för snöfall";
            case "sleet":
                return "Risk för snöblandat regn";
            case "hail":
                return "Risk för hagel";
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
