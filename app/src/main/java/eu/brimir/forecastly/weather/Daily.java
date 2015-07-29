package eu.brimir.forecastly.weather;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;


public class Daily implements Parcelable{

    private long mTime;
    private String mSummary;
    private double mTemperatureMax;
    private String mIcon;
    private double mWindSpeed;
    private double mWindBearing;
    private String mLocation;
    private double mStoreBearing;
    private double mPrecipProbability;
    private double mPrecipIntensityMax;
    private String mPrecipType;

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

    private String locale = Locale.getDefault().getLanguage();
    public int getPrecipProbability() {

        double precipPercentage = mPrecipProbability * 100;
        return (int) Math.round(precipPercentage);

    }

    public void setPrecipProbability(double precipProbability) {
        mPrecipProbability = precipProbability;
    }

    public double getPrecipIntensityMax() {


        return  mPrecipIntensityMax;
    }

    public void setPrecipIntensityMax(double precipIntensityMax) {
        mPrecipIntensityMax = precipIntensityMax;
    }



    public double getStoreBearing() {
        return mStoreBearing;
    }

    public void setStoreBearing(double storeBearing) {
        mStoreBearing = storeBearing;
    }

    public double getWindBearing() {
        return mWindBearing;
    }

    public void setWindBearing(double windBearing) {
        mWindBearing = windBearing;
    }

    public int getWindSpeed() {


        return (int) Math.round(mWindSpeed);
    }

    public void setWindSpeed(double windSpeed) {
        mWindSpeed = windSpeed;
    }

    private String mTimezone;


    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public int getTemperatureMax() {


        return (int) Math.round(mTemperatureMax);

    }

    public void setTemperatureMax(double temperatureMax) {
        mTemperatureMax = temperatureMax;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getTimezone() {
        return mTimezone;
    }

    public void setTimezone(String timezone) {
        mTimezone = timezone;
    }
public int getIconId(){
    return Forecast.getIconId(mIcon);
}

    public String getDayOfTheWeek(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("cccc");
        formatter.setTimeZone(TimeZone.getTimeZone(mTimezone));

        Date dateTime = new Date(mTime *1000);
                return formatter.format(dateTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mTime);
        dest.writeString(mSummary);
        dest.writeDouble(mTemperatureMax);
        dest.writeString(mIcon);
        dest.writeString(mTimezone);
        dest.writeDouble(mWindSpeed);
        dest.writeDouble(mWindBearing);
        dest.writeString(mLocation);
        dest.writeDouble(mPrecipIntensityMax);
        dest.writeDouble(mPrecipProbability);
        dest.writeString(mPrecipType);

    }

    private Daily(Parcel in){
        mTime = in.readLong();
        mSummary = in.readString();
        mTemperatureMax = in.readDouble();
        mIcon = in.readString();
        mTimezone = in.readString();
        mWindSpeed = in.readDouble();
        mWindBearing = in.readDouble();
        mLocation = in.readString();
        mPrecipIntensityMax = in.readDouble();
        mPrecipProbability = in.readDouble();
        mPrecipType = in.readString();
    }
    public Daily(){

    }
    public static final Creator<Daily> CREATOR = new Creator<Daily>() {
        @Override
        public Daily createFromParcel(Parcel source) {
            return new Daily(source);
        }

        @Override
        public Daily[] newArray(int size) {
            return new Daily[size];
        }
    };
}
