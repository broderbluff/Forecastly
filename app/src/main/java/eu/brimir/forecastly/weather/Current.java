package eu.brimir.forecastly.weather;

import android.annotation.SuppressLint;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

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
    private double mPrecipIntensity;
    private double mNearestStormDistance;
    private double mNearestStormBearing;


    public double getPrecipIntensity() {

        return mPrecipIntensity;
    }

    public void setPrecipIntensity(double precipIntensity) {
        mPrecipIntensity = precipIntensity;
    }

    public double getNearestStormDistance() {
        return mNearestStormDistance;
    }

    public void setNearestStormDistance(double nearestStormDistance) {
        mNearestStormDistance = nearestStormDistance;
    }

    public double getNearestStormBearing() {
        return mNearestStormBearing;
    }

    public void setNearestStormBearing(double nearestStormBearing) {
        mNearestStormBearing = nearestStormBearing;
    }

    private String locale = Locale.getDefault().getLanguage();

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
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatterDay = new SimpleDateFormat("ccc" + " d/MM");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatterWeek = new SimpleDateFormat("w");
        formatter.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        Date dateTime = new Date(getTime() * 1000);
        String timeString = formatter.format(dateTime);
        String dayString = formatterDay.format(dateTime);
        String weekString = formatterWeek.format(dateTime);

        if (locale.equals("sv")) {
            return timeString + ", " + dayString + ". " + " Vecka " + " " + weekString;
        }
        return timeString + ", " + dayString;

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

        switch (locale) {
            case "sv":
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
                break;
            case "ar":
                switch (mPrecipType) {
                    case "rain":

                        return "فرصة للمطر";
                    case "snow":
                        return "فرصة للثلج";
                    case "sleet":
                        return "فرصة من الصقيع";
                    case "hail":
                        return "فرصة من البرد";
                }

                break;
            case "bs":
                switch (mPrecipType) {
                    case "rain":

                        return "Moguća kiša";
                    case "snow":
                        return "Mogući snijeg";
                    case "sleet":
                        return "Moguća susnježica";
                    case "hail":
                        return "Moguća grad";
                }
                break;
            case "de":
                switch (mPrecipType) {
                    case "rain":
                        return "Vereinzelt regen";

                    case "snow":
                        return "Vereinzelt Schnee";

                    case "sleet":
                        return "Vereinzelt Schneeregen";

                    case "hail":
                        return "Vereinzelt Hagel";
                }
                break;
            case "en":
                switch (mPrecipType) {
                    case "rain":
                        return "Chance of rain";

                    case "snow":
                        return "Chance of snow";

                    case "sleet":
                        return "Chance of sleet";

                    case "hail":
                        return "Chance of hail";
                }
                break;
            case "es":
                switch (mPrecipType) {
                    case "rain":
                        return "Probabilidad de lluvia";

                    case "snow":
                        return "Probabilidad de lluvia";

                    case "sleet":
                        return "Posibilidad de aguanieve";

                    case "hail":
                        return "Probabilidad de lluvia";
                }
                break;
            case "fr":
                switch (mPrecipType) {
                    case "rain":
                        return "Risque de pluie";

                    case "snow":
                        return "Risque de neige";

                    case "sleet":
                        return "Risque de verglas";

                    case "hail":
                        return "Risque de grêle";
                }
                break;
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
