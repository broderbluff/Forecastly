package eu.brimir.forecastly.utils;


import java.util.Locale;

/**
 * Created by brode on 2015-08-19.
 */
public class GetForcastURL {


    private String locale = Locale.getDefault().getLanguage();
    private String localeUS = Locale.getDefault().toString();

    private String apiKey = "6d73ebc175b9afd40c6e48e5700ca316";


    protected String forecastUrl;

    public String getURL(double latitude, double longitude) {

        if (locale.equals("sv")) {

            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude + "?lang=sv&units=si&exclude=minutely,flags";
        } else if (locale.equals("ar")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude + "?lang=ar&units=auto&exclude=minutely,flags";
        } else if (locale.equals("bs")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude + "?lang=bs&units=auto&exclude=minutely,flags";
        } else if (locale.equals("de")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude + "?lang=de&units=auto&exclude=minutely,flags";
        } else if (locale.equals("es")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude + "?lang=es&units=auto&exclude=minutely,flags";
        } else if (locale.equals("fr")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude + "?lang=fr&units=auto&exclude=minutely,flags";
        } else if (locale.equals("it")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude + "?lang=it&units=auto&exclude=minutely,flags";
        } else if (locale.equals("nl")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude + "?lang=nl&units=auto&exclude=minutely,flags";
        } else if (locale.equals("pl")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude + "?lang=pl&units=auto&exclude=minutely,flags";
        } else if (locale.equals("pt")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude + "?lang=pt&units=auto&exclude=minutely,flags";
        } else if (locale.equals("ru")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude + "?lang=ru&units=auto&exclude=minutely,flags";
        } else if (locale.equals("sk")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude + "?lang=sk&units=auto&exclude=minutely,flags";
        } else if (locale.equals("tet")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude + "?lang=tet&units=auto&exclude=minutely,flags";
        } else if (locale.equals("tr")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude + "?lang=nl&units=auto&exclude=minutely,flags";
        } else if (locale.equals("zh")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude + "?lang=zh&units=auto&exclude=minutely,flags";
        } else if (localeUS.equals("en_US")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude + "?exclude=minutely,flags";
        } else if (localeUS.equals("en_GB")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude + "?units=uk2&exclude=minutely,flags";
        } else {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude + "?units=auto&exclude=minutely,flags";
        }
        return forecastUrl;
    }


}
