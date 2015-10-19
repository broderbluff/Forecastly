package eu.brimir.forecastly.utils;

import android.graphics.drawable.Drawable;

import java.util.Calendar;
import java.util.Locale;

import eu.brimir.forecastly.Forecastly;
import eu.brimir.forecastly.R;

/**
 * Created by brode on 2015-08-19.
 */
@SuppressWarnings("ALL")
public class SetBackgroundImage {
    private String locale = Locale.getDefault().getLanguage();
    private String localeUS = Locale.getDefault().toString();
    Drawable bgImage;



    public Drawable getBackgroundImage(String summary, String icon){
        Calendar cal = Calendar.getInstance();
        int monthForBackground = cal.get(Calendar.MONTH) +1;
        if (locale.equals("sv")) {

            if (monthForBackground == 12 && monthForBackground <= 2) {
                if (summary.equals("Regnskurar")) {

                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.rain_showers_photo_bg);




                } else if (summary.equals("Duggregn")) {
                  bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.drizzle_photo_bg);



                } else if (summary.equals("Regn")) {
                   bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.rain_photo_bg);

                } else if (summary.equals("Skyfall")) {
                   bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.heavy_rain_photo_bg);

                } else if (icon.equals("clear-day")) {
                  bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.clear_day_photo_bg);

                } else if (summary.equals("Lätt molnighet") && icon.equals("partly-cloudy-day")) {
                   bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.light_cloudy_photo_day);

                } else if (summary.equals("Molnigt") && icon.equals("partly-cloudy-day")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.partly_cloudy_day_photo_bg);

                } else if (icon.equals("partly-cloudy-night")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.cloudy_night_photo_bg);

                } else if (icon.equals("clear-night")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.clear_night_photo_bg);

                } else if (icon.equals("cloudy")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.cloudy_photo_bg);

                } else if (icon.equals("fog")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.fog_photo_bg);

                } else if (icon.equals("snow")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.snow_photo_bg);

                } else if (icon.equals("wind")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.windy_photo_day);

                }
            } else if (monthForBackground >= 3 && monthForBackground <= 5) {
                if (summary.equals("Regnskurar")) {
                    //noinspection deprecation
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.rain_showers_photo_bg);

                } else if (summary.equals("Duggregn")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.drizzle_photo_bg);

                } else if (summary.equals("Regn")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.rain_photo_bg);

                } else if (summary.equals("Skyfall")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.heavy_rain_photo_bg);

                } else if (icon.equals("clear-day")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.clear_day_photo_bg);

                } else if (summary.equals("Lätt molnighet") && icon.equals("partly-cloudy-day")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.light_cloudy_photo_day);

                } else if (summary.equals("Molnigt") && icon.equals("partly-cloudy-day")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.partly_cloudy_day_photo_bg);

                } else if (icon.equals("partly-cloudy-night")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.cloudy_night_photo_bg);

                } else if (icon.equals("clear-night")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.clear_night_photo_bg);

                } else if (icon.equals("cloudy")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.cloudy_photo_bg);

                } else if (icon.equals("fog")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.fog_photo_bg);

                } else if (icon.equals("snow")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.snow_photo_bg);

                } else if (icon.equals("wind")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.windy_photo_day);

                }
            } else if (monthForBackground >= 6 && monthForBackground <= 8) {
                if (summary.equals("Regnskurar")) {
                    //noinspection deprecation
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.rain_showers_photo_bg);

                } else if (summary.equals("Duggregn")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.drizzle_photo_bg);

                } else if (summary.equals("Regn")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.rain_photo_bg);

                } else if (summary.equals("Skyfall")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.heavy_rain_photo_bg);

                } else if (summary.equals("Duggregn och måttlig vind")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.drizzle_breeze_photo_bg);

                } else if (summary.equals("Måttlig vind och lätt molnighet")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.breeze_light_cloudy_photo_bg);

                }else if (summary.equals("Måttlig vind och molnigt")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.breeze_cloudy_photo_bg);

                }  else if (icon.equals("clear-day")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.clear_day_photo_bg);

                } else if (summary.equals("Lätt molnighet") && icon.equals("partly-cloudy-day")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.light_cloudy_photo_day);

                } else if (summary.equals("Molnigt") && icon.equals("partly-cloudy-day")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.partly_cloudy_day_photo_bg);

                } else if (icon.equals("partly-cloudy-night")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.cloudy_night_photo_bg);

                } else if (icon.equals("clear-night")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.clear_night_photo_bg);

                } else if (icon.equals("cloudy")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.cloudy_photo_bg);

                } else if (icon.equals("fog")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.fog_photo_bg);

                } else if (icon.equals("snow")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.snow_photo_bg);

                } else if (icon.equals("wind")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.windy_photo_day);

                }

            } else if (monthForBackground >= 9 && monthForBackground <= 11) {
                if (summary.equals("Regnskurar")) {
                    //noinspection deprecation
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.rain_showers_photo_bg);

                } else if (summary.equals("Duggregn")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.drizzle_photo_autmn_bg);

                } else if (summary.equals("Regn")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.rain_photo_autumn_bg);

                } else if (summary.equals("Skyfall")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.heavy_rain_photo_bg);

                } else if (icon.equals("clear-day")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.clear_day_photo_autumn_bg);

                } else if (summary.equals("Lätt molnighet") && icon.equals("partly-cloudy-day")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.light_cloudy_photo_day_autumn);

                } else if (summary.equals("Molnigt") && icon.equals("partly-cloudy-day")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.partly_cloudy_day_photo_bg);

                } else if (icon.equals("partly-cloudy-night")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.cloudy_night_photo_bg);

                } else if (icon.equals("clear-night")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.clear_night_photo_bg);

                } else if (icon.equals("cloudy")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.cloudy_photo_bg);

                } else if (icon.equals("fog")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.fog_photo_autumn_bg);

                } else if (icon.equals("snow")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.snow_photo_bg);

                } else if (icon.equals("wind")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.windy_photo_day_autumn);

                }



            }

        } else if (locale.equals("en")) {



            if (monthForBackground >= 9 && monthForBackground <= 11) {
                if (summary.equals("Light Rain")) {
                    //noinspection deprecation
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.rain_showers_photo_bg);

                } else if (summary.equals("Drizzle")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.drizzle_photo_autmn_bg);

                } else if (summary.equals("Rain")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.rain_photo_autumn_bg);

                } else if (summary.equals("Heavy Rain")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.heavy_rain_photo_bg);

                } else if (icon.equals("clear-day")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.clear_day_photo_autumn_bg);

                } else if (summary.equals("Partly Cloudy") && icon.equals("partly-cloudy-day")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.light_cloudy_photo_day_autumn);

                } else if (summary.equals("Mostly Cloudy") && icon.equals("partly-cloudy-day")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.partly_cloudy_day_photo_bg);

                } else if (icon.equals("partly-cloudy-night")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.cloudy_night_photo_bg);

                } else if (icon.equals("clear-night")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.clear_night_photo_bg);

                } else if (icon.equals("cloudy")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.cloudy_photo_autmn_bg);

                } else if (icon.equals("fog")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.fog_photo_autumn_bg);

                } else if (icon.equals("snow")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.snow_photo_bg);

                } else if (icon.equals("wind")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.windy_photo_day_autumn);

                }
            }






            else if (monthForBackground >= 6 && monthForBackground <= 8) {
                if (summary.equals("Light Rain")) {
                    //noinspection deprecation
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.rain_showers_photo_bg);

                } else if (summary.equals("Drizzle")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.drizzle_photo_bg);

                } else if (summary.equals("Rain")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.rain_photo_bg);

                } else if (summary.equals("Heavy Rain")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.heavy_rain_photo_bg);

                } else if (icon.equals("clear-day")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.clear_day_photo_bg);

                } else if (summary.equals("Partly Cloudy") && icon.equals("partly-cloudy-day")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.light_cloudy_photo_day);

                } else if (summary.equals("Mostly Cloudy") && icon.equals("partly-cloudy-day")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.partly_cloudy_day_photo_bg);

                } else if (icon.equals("partly-cloudy-night")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.cloudy_night_photo_bg);

                } else if (icon.equals("clear-night")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.clear_night_photo_bg);

                } else if (icon.equals("cloudy")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.cloudy_photo_bg);

                } else if (icon.equals("fog")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.fog_photo_bg);

                } else if (icon.equals("snow")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.snow_photo_bg);

                } else if (icon.equals("wind")) {
                    bgImage= Forecastly.getAppContext().getResources().getDrawable(R.drawable.windy_photo_day);

                }
            }










        } else {


            if (monthForBackground >= 6 && monthForBackground <= 8) {
                switch (icon) {
                    case "rain":
                        bgImage = Forecastly.getAppContext().getResources().getDrawable(R.drawable.drizzle_photo_bg);


                        break;
                    case "clear-day":
                        bgImage = Forecastly.getAppContext().getResources().getDrawable(R.drawable.clear_day_photo_bg);

                        break;
                    case "partly-cloudy-day":
                        bgImage = Forecastly.getAppContext().getResources().getDrawable(R.drawable.partly_cloudy_day_photo_bg);

                        break;
                    case "partly-cloudy-night":
                        bgImage = Forecastly.getAppContext().getResources().getDrawable(R.drawable.cloudy_night_photo_bg);

                        break;
                    case "clear-night":
                        bgImage = Forecastly.getAppContext().getResources().getDrawable(R.drawable.clear_night_photo_bg);

                        break;
                    case "cloudy":
                        bgImage = Forecastly.getAppContext().getResources().getDrawable(R.drawable.cloudy_photo_bg);

                        break;
                    case "fog":
                        bgImage = Forecastly.getAppContext().getResources().getDrawable(R.drawable.fog_photo_bg);

                        break;
                    case "snow":
                        bgImage = Forecastly.getAppContext().getResources().getDrawable(R.drawable.snow_photo_bg);

                        break;
                    case "wind":
                        bgImage = Forecastly.getAppContext().getResources().getDrawable(R.drawable.windy_photo_day);

                        break;
                }
            }
           else if (monthForBackground >= 9 && monthForBackground <= 11) {
                switch (icon) {
                    case "rain":
                        bgImage = Forecastly.getAppContext().getResources().getDrawable(R.drawable.drizzle_photo_autmn_bg);


                        break;
                    case "clear-day":
                        bgImage = Forecastly.getAppContext().getResources().getDrawable(R.drawable.clear_day_photo_autumn_bg);

                        break;
                    case "partly-cloudy-day":
                        bgImage = Forecastly.getAppContext().getResources().getDrawable(R.drawable.partly_cloudy_day_photo_bg);

                        break;
                    case "partly-cloudy-night":
                        bgImage = Forecastly.getAppContext().getResources().getDrawable(R.drawable.cloudy_night_photo_bg);

                        break;
                    case "clear-night":
                        bgImage = Forecastly.getAppContext().getResources().getDrawable(R.drawable.clear_night_photo_bg);

                        break;
                    case "cloudy":
                        bgImage = Forecastly.getAppContext().getResources().getDrawable(R.drawable.cloudy_photo_autmn_bg);

                        break;
                    case "fog":
                        bgImage = Forecastly.getAppContext().getResources().getDrawable(R.drawable.fog_photo_autumn_bg);

                        break;
                    case "snow":
                        bgImage = Forecastly.getAppContext().getResources().getDrawable(R.drawable.snow_photo_bg);

                        break;
                    case "wind":
                        bgImage = Forecastly.getAppContext().getResources().getDrawable(R.drawable.windy_photo_day_autumn);

                        break;
                }
            }
        }

        return bgImage;

    }

}
