package eu.brimir.forecastly.weather;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by Patrik on 2015-07-10.
 */
public class Daily implements Parcelable{

    private long mTime;
    private String mSummary;
    private double mTemperatureMax;
    private String mIcon;
    private double mWindSpeed;
    private double mWindBearing;
    private String mLocation;
    private double mStoreBearing;

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
        SimpleDateFormat formatter = new SimpleDateFormat("cccc");
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
