package eu.brimir.forecastly.weather;

/**
 * Created by Patrik on 2015-07-20.
 */
public class Alert {

    private String mTitle;
    private long mTimeExpires;
    private String mDescription;

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        mUri = uri;
    }

    private String mUri;
    private String mTimeZone;

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public long getTimeExpires() {
        return mTimeExpires;
    }

    public void setTimeExpires(long timeExpires) {
        mTimeExpires = timeExpires;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }


}
