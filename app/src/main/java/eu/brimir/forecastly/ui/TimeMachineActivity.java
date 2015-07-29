package eu.brimir.forecastly.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import eu.brimir.forecastly.R;
import eu.brimir.forecastly.weather.Forecast;
import eu.brimir.forecastly.weather.TimeMachine;

@SuppressWarnings({"ALL", "WeakerAccess"})
public class TimeMachineActivity extends AppCompatActivity {

    private static final String TAG = TimeMachineActivity.class.getSimpleName();
    private Forecast mForecast;
    private long mTimeMachineValue;
    private double latitude, longitude;
    private int mYear, mMonth, mDay;
    private String locationLabel;
    private String forecastUrl;
    private String locale;
    @Bind(R.id.timeLabel)
    TextView mTimeLabel;

    @Bind(R.id.temperatureLabel)
    TextView mTemperatureLabel;

    @Bind(R.id.timemachinemainlayout)
    RelativeLayout mRelativeLayout;

    @Bind(R.id.summaryLabel)
    TextView mSummaryLabel;

    @Bind(R.id.iconImageView)
    ImageView mIconImageView;

    @Bind(R.id.yearLabel)
    TextView mYearLabel;

    @Bind(R.id.locationLabelTimeMachine)
    TextView mLocationLabel;
    @Bind(R.id.layoutBackground)
    ImageView mImageviewLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_machine);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        latitude = intent.getExtras().getDouble("latitude");
        longitude = intent.getExtras().getDouble("longitude");
        mTimeMachineValue = intent.getExtras().getLong("timeValue");
        mYear = intent.getExtras().getInt("year");
        mMonth = intent.getExtras().getInt("month");
        mDay = intent.getExtras().getInt("day");
        locationLabel = intent.getExtras().getString("location");
        getForecast(latitude, longitude);
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }

        });


    }


    private void getForecast(double latitude, double longitude) {
        String apiKey = "856246af9921b9926768314103a438d9";


       
        locale = Locale.getDefault().getLanguage().toString();
        String locale2 = Locale.getDefault().toString();
        if (locale.equals("sv")) {

            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude + "," + mTimeMachineValue +"?lang=sv&units=si&exclude=minutely,flags";
        } else if (locale.equals("ar")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude +"," + mTimeMachineValue + "?lang=ar&units=auto&exclude=minutely,flags";
        } else if (locale.equals("bs")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude + "," + mTimeMachineValue +"?lang=bs&units=auto&exclude=minutely,flags";
        } else if (locale.equals("de")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude +"," + mTimeMachineValue + "?lang=de&units=auto&exclude=minutely,flags";
        } else if (locale.equals("es")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude +"," + mTimeMachineValue + "?lang=es&units=auto&exclude=minutely,flags";
        } else if (locale.equals("fr")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude +"," + mTimeMachineValue + "?lang=fr&units=auto&exclude=minutely,flags";
        } else if (locale.equals("it")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude + "," + mTimeMachineValue +"?lang=it&units=auto&exclude=minutely,flags";
        } else if (locale.equals("nl")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude +"," + mTimeMachineValue + "?lang=nl&units=auto&exclude=minutely,flags";
        } else if (locale.equals("pl")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude + "," + mTimeMachineValue +"?lang=pl&units=auto&exclude=minutely,flags";
        } else if (locale.equals("pt")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude +"," + mTimeMachineValue + "?lang=pt&units=auto&exclude=minutely,flags";
        } else if (locale.equals("ru")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude + "," + mTimeMachineValue +"?lang=ru&units=auto&exclude=minutely,flags";
        } else if (locale.equals("sk")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude + "," + mTimeMachineValue +"?lang=sk&units=auto&exclude=minutely,flags";
        } else if (locale.equals("tet")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude +"," + mTimeMachineValue + "?lang=tet&units=auto&exclude=minutely,flags";
        } else if (locale.equals("tr")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude +"," + mTimeMachineValue + "?lang=nl&units=auto&exclude=minutely,flags";
        } else if (locale.equals("zh")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude +"," + mTimeMachineValue + "?lang=zh&units=auto&exclude=minutely,flags";
        } else if (locale2.equals("en_US")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude +"," + mTimeMachineValue + "?exclude=minutely,flags";
        } else if (locale2.equals("en_GB")) {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude +"," + mTimeMachineValue + "?units=uk2&exclude=minutely,flags";
        } else {
            forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude + "," + mTimeMachineValue +"?units=auto&exclude=minutely,flags";
        }


        if (isNetworkAvailable()) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(forecastUrl)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                    try {
                        String jsonData = response.body().string();

                        if (response.isSuccessful()) {
                            mForecast = parseForecastDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });

                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException | JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }

                }
            });
        } else {
            Toast.makeText(this, R.string.no_network_toast, Toast.LENGTH_LONG).show();
        }
    }

    private Forecast parseForecastDetails(String jsonData) throws JSONException {
        Forecast forecast = new Forecast();

        forecast.setTimeMachine(getTimeMachineDetails(jsonData));


        return forecast;
    }


    private TimeMachine getTimeMachineDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");


        JSONObject currently = forecast.getJSONObject("currently");
        TimeMachine timeMachine = new TimeMachine();

        timeMachine.setTime(currently.getLong("time"));
        timeMachine.setIcon(currently.getString("icon"));

        timeMachine.setSummary(currently.getString("summary"));
        timeMachine.setTemperature(currently.getDouble("temperature"));
        timeMachine.setTimeZone(timezone);


        return timeMachine;


    }


    private void updateDisplay() {
        TimeMachine timeMachine = mForecast.getTimeMachine();

        mTemperatureLabel.setText(timeMachine.getTemperature() + "");
        mTimeLabel.setText(timeMachine.getFormattedDay());
        final Calendar cal = Calendar.getInstance();
        int year_x = cal.get(Calendar.YEAR);
        int month_x = cal.get(Calendar.MONTH);
        int day_x = cal.get(Calendar.DAY_OF_MONTH);




        mLocationLabel.setText(locationLabel);
        mSummaryLabel.setText(timeMachine.getSummary());
        mYearLabel.setText(timeMachine.getFormattedYear());
        Drawable drawable = getResources().getDrawable(timeMachine.getIconId());
        mIconImageView.setImageDrawable(drawable);


        if(locale.equals("sv")){
            if (timeMachine.getSummary().equals("Regnskurar")) {
                mImageviewLayout.setImageDrawable(getResources().getDrawable(R.drawable.rain_showers_photo_bg));

            } else if (timeMachine.getSummary().equals("Duggregn")) {
                mImageviewLayout.setImageDrawable(getResources().getDrawable(R.drawable.drizzle_photo_bg));

            } else if (timeMachine.getSummary().equals("Regn")) {
                mImageviewLayout.setImageDrawable(getResources().getDrawable(R.drawable.rain_photo_bg));

            } else if (timeMachine.getSummary().equals("Skyfall")) {
                mImageviewLayout.setImageDrawable(getResources().getDrawable(R.drawable.heavy_rain_photo_bg));

            } else if (timeMachine.getIcon().equals("clear-day")) {
                mImageviewLayout.setImageDrawable(getResources().getDrawable(R.drawable.clear_day_photo_bg));

            } else if (timeMachine.getSummary().equals("Lätt molnighet")) {
                mImageviewLayout.setImageDrawable(getResources().getDrawable(R.drawable.light_cloudy_photo_day));

            } else if (timeMachine.getSummary().equals("Molnigt")) {
                mImageviewLayout.setImageDrawable(getResources().getDrawable(R.drawable.partly_cloudy_day_photo_bg));

            } else if (timeMachine.getIcon().equals("partly-cloudy-night")) {
                mImageviewLayout.setImageDrawable(getResources().getDrawable(R.drawable.cloudy_night_photo_bg));

            } else if (timeMachine.getIcon().equals("clear-night")) {
                mImageviewLayout.setImageDrawable(getResources().getDrawable(R.drawable.clear_night_photo_bg));

            } else if (timeMachine.getIcon().equals("cloudy")) {
                mImageviewLayout.setImageDrawable(getResources().getDrawable(R.drawable.cloudy_photo_bg));

            } else if (timeMachine.getIcon().equals("fog")) {
                mImageviewLayout.setImageDrawable(getResources().getDrawable(R.drawable.fog_photo_bg));

            } else if (timeMachine.getIcon().equals("snow")) {
                mImageviewLayout.setImageDrawable(getResources().getDrawable(R.drawable.snow_photo_bg));

            } else if (timeMachine.getIcon().equals("wind")) {
                mImageviewLayout.setImageDrawable(getResources().getDrawable(R.drawable.windy_photo_day));

            }
        }else{
            if (timeMachine.getIcon().equals("rain")) {
                mImageviewLayout.setImageDrawable(getResources().getDrawable(R.drawable.drizzle_photo_bg));



            } else if (timeMachine.getIcon().equals("clear-day")) {
                mImageviewLayout.setImageDrawable(getResources().getDrawable(R.drawable.clear_day_photo_bg));

            } else if (timeMachine.getIcon().equals("partly-cloudy-day")) {
                mImageviewLayout.setImageDrawable(getResources().getDrawable(R.drawable.partly_cloudy_day_photo_bg));

            }  else if (timeMachine.getIcon().equals("partly-cloudy-night")) {
                mImageviewLayout.setImageDrawable(getResources().getDrawable(R.drawable.cloudy_night_photo_bg));

            } else if (timeMachine.getIcon().equals("clear-night")) {
                mImageviewLayout.setImageDrawable(getResources().getDrawable(R.drawable.clear_night_photo_bg));

            } else if (timeMachine.getIcon().equals("cloudy")) {
                mImageviewLayout.setImageDrawable(getResources().getDrawable(R.drawable.cloudy_photo_bg));

            } else if (timeMachine.getIcon().equals("fog")) {
                mImageviewLayout.setImageDrawable(getResources().getDrawable(R.drawable.fog_photo_bg));

            } else if (timeMachine.getIcon().equals("snow")) {
                mImageviewLayout.setImageDrawable(getResources().getDrawable(R.drawable.snow_photo_bg));

            } else if (timeMachine.getIcon().equals("wind")) {
                mImageviewLayout.setImageDrawable(getResources().getDrawable(R.drawable.windy_photo_day));

            }
        }



        if (latitude == 0.0 && longitude == 0.0) {
            mTemperatureLabel.setText(R.string.no_temp);
        }
        if (timeMachine.getTemperature() >= 25) {
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(findViewById(R.id.temperatureLabel));
        } else if (timeMachine.getTemperature() <= 0) {
            YoYo.with(Techniques.Shake)
                    .duration(500)
                    .playOn(findViewById(R.id.temperatureLabel));
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }

}
