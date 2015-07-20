package eu.brimir.forecastly.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.brimir.forecastly.R;
import eu.brimir.forecastly.weather.Current;
import eu.brimir.forecastly.weather.Daily;
import eu.brimir.forecastly.weather.Forecast;
import eu.brimir.forecastly.weather.Hourly;

@SuppressWarnings("WeakerAccess")
public class MainActivity extends AppCompatActivity implements LocationListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String DAILY_FORECAST = "DAILY_FORECAST";
    public static final String HOURLY_FORECAST = "HOURLY_FORECAST";
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 500;
    private static final long MIN_TIME_FOR_UPDATE = 1000*60*5;
    private Forecast mForecast;
    private String provider;
    private LocationManager locationManager;
    private double latitude;
    private double longitude;
    private String getLocality;
    private String getAdminArea;

    private String locationForDaily;
    private int year_x;
    private int month_x;
    private int day_x;
    private boolean isDatePicked = false;
    private static final int DIALOG_ID = 0;
    private long mTimeMachineValue;
    private Location location;
    private String locale = Locale.getDefault().getLanguage();
    private String localeUS = Locale.getDefault().toString();
    // private SwipeRefreshLayout mSwipeRefreshLayout;


    @Bind(R.id.timeLabel)
    TextView mTimeLabel;

    @Bind(R.id.temperatureLabel)
    TextView mTemperatureLabel;

    @Bind(R.id.humidityValue)
    TextView mHumidityValue;

    @Bind(R.id.precipValue)
    TextView mPrecipValue;

    @Bind(R.id.summaryLabel)
    TextView mSummaryLabel;

    @Bind(R.id.iconImageView)
    ImageView mIconImageView;

    @Bind(R.id.refreshImageView)
    ImageView mRefreshImageView;

    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;

    @Bind(R.id.yearLabel)
    TextView mLocationLabel;

    @Bind(R.id.precipLabel)
    TextView mPrecipLabel;

    @Bind(R.id.timemachine)
    ImageView mTimeMachine;
    // private SwipeRefreshLayout swipeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mProgressBar.setVisibility(View.INVISIBLE);

        getLocation();
        getForecast(latitude, longitude);
        getAddress(latitude, longitude);
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);

        mTimeMachine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //noinspection deprecation
                showDialog(DIALOG_ID);

            }
        });

        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
                getForecast(latitude, longitude);
                getAddress(latitude, longitude);


            }
        });


    }


    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID)
            return new DatePickerDialog(this, dpickerListener, year_x, month_x, day_x);
        return null;
    }

    private final DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_x = year;
            month_x = monthOfYear;
            day_x = dayOfMonth;
            componentTimeToTimestamp(year_x, month_x, day_x);

            Intent intent = new Intent(MainActivity.this, TimeMachineActivity.class);

            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
            intent.putExtra("timeValue", mTimeMachineValue);
            intent.putExtra("year", year_x);
            intent.putExtra("month", month_x);
            intent.putExtra("day", day_x);
            intent.putExtra("location", locationForDaily);
            startActivity(intent);


        }
    };

    private void getAddress(double latitude, double longitude) {

        GetAddressAsynctask addressAsynctask = new GetAddressAsynctask();
        addressAsynctask.execute(latitude, longitude);


    }

    private void componentTimeToTimestamp(int year, int month, int day) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, 14);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        mTimeMachineValue = (c.getTimeInMillis() / 1000L);
    }

    private void getForecast(double latitude, double longitude) {
        String apiKey = "6d73ebc175b9afd40c6e48e5700ca316";



        String forecastUrl;
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


        if (isNetworkAvailable()) {
            toggleRefresh();
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
                            toggleRefresh();
                        }
                    });
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
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

    private void toggleRefresh() {
        if (mProgressBar.getVisibility() == View.INVISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshImageView.setVisibility(View.INVISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshImageView.setVisibility(View.VISIBLE);
        }
    }

    private void updateDisplay() {
        Current current = mForecast.getCurrent();
        if (latitude == 0.0 && longitude == 0.0) {
            mTemperatureLabel.setText("--");
        }
        mTemperatureLabel.setText(current.getTemperature() + "");
        mTimeLabel.setText(current.getFormattedTime());
        mHumidityValue.setText(current.getHumidity() + "%");
        mPrecipValue.setText(current.getPrecipChance() + "%");

        mSummaryLabel.setText(current.getSummary());
        @SuppressWarnings("deprecation") Drawable drawable = getResources().getDrawable(current.getIconId());
        mIconImageView.setImageDrawable(drawable);
        if (current.getPrecipChance() != 0) {
            mPrecipLabel.setText(current.getPrecipType());
        }



        if (locale.equals("en_US")) {
            if (current.getTemperature() >= 77&& latitude != 0.0 && longitude != 0.0 ) {
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(findViewById(R.id.temperatureLabel));
            } else if (current.getTemperature() <= 32) {
                YoYo.with(Techniques.Shake)
                        .duration(500)
                        .playOn(findViewById(R.id.temperatureLabel));
            }
        } else {
            if (current.getTemperature() >= 25 && mTemperatureLabel.equals("--")) {
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(findViewById(R.id.temperatureLabel));
            } else if (current.getTemperature() <= 0) {
                YoYo.with(Techniques.Shake)
                        .duration(500)
                        .playOn(findViewById(R.id.temperatureLabel));
            }
        }


    }


    private Forecast parseForecastDetails(String jsonData) throws JSONException {
        Forecast forecast = new Forecast();

        forecast.setCurrent(getCurrentDetails(jsonData));
        forecast.setHourlyForecast(getHourlyForecast(jsonData));
        forecast.setDailyForecast(getDailyForecast(jsonData));


        return forecast;
    }

    private Daily[] getDailyForecast(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");

        JSONObject daily = forecast.getJSONObject("daily");
        JSONArray data = daily.getJSONArray("data");

        Daily[] days = new Daily[data.length()];

        for (int i = 0; i < data.length(); i++) {
            JSONObject jsonDay = data.getJSONObject(i);
            Daily day = new Daily();
            day.setSummary(jsonDay.getString("summary"));
            day.setIcon(jsonDay.getString("icon"));
            day.setTemperatureMax(jsonDay.getDouble("temperatureMax"));
            day.setTime(jsonDay.getLong("time"));
            day.setTimezone(timezone);
            day.setWindSpeed(jsonDay.getDouble("windSpeed"));
            day.setWindBearing(jsonDay.getDouble("windBearing"));

            days[i] = day;

        }
        return days;
    }

    private Hourly[] getHourlyForecast(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject hourly = forecast.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");

        Hourly[] hours = new Hourly[data.length()];
        for (int i = 0; i < data.length(); i++) {
            JSONObject jsonHour = data.getJSONObject(i);
            Hourly hour = new Hourly();
            hour.setSummary(jsonHour.getString("summary"));
            hour.setTemperature(jsonHour.getDouble("temperature"));
            hour.setIcon(jsonHour.getString("icon"));
            hour.setTime(jsonHour.getLong("time"));
            hour.setTimezone(timezone);

            hours[i] = hour;

        }
        return hours;
    }

    private Current getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");


        JSONObject currently = forecast.getJSONObject("currently");
        Current current = new Current();
        current.setHumidity(currently.getDouble("humidity"));
        current.setTime(currently.getLong("time"));
        current.setIcon(currently.getString("icon"));
        current.setPrecipChance(currently.getDouble("precipProbability"));
        if (current.getPrecipChance() != 0) {
            current.setPrecipType(currently.getString("precipType"));
            current.setPrecipIntensity(currently.getDouble("precipIntensity"));
        }

        current.setSummary(currently.getString("summary"));
        current.setTemperature(currently.getDouble("temperature"));
        current.setTimeZone(timezone);


        return current;


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

    public void onPoweredByClick(View view) {

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.forecast.io"));
        MainActivity.this.startActivity(browserIntent);

    }

    private void getLocation() {


        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default


        if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) && !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);


            builder.setTitle(R.string.location_dialog_title)
                    .setMessage(R.string.location_dialog_message)
                    .setNegativeButton(R.string.close_app_button_title, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            paramDialogInterface.dismiss();

                        }
                    })
                    .setPositiveButton(R.string.location_dialog_positive_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);

                            startActivity(myIntent);
                        }
                    });

            android.app.AlertDialog dialog = builder.create();


            dialog.show();

        } else {
            GetLocationAsyncTask locationAsyncTask = new GetLocationAsyncTask();
            locationAsyncTask.execute();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        locationManager.requestLocationUpdates(provider, MIN_TIME_FOR_UPDATE, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);



    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        getForecast(latitude, longitude);
        getAddress(latitude, longitude);


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.sevenDaysButton)
    public void startDailyActivity(View view) {
        Intent intent = new Intent(this, DailyForecastActivity.class);
        intent.putExtra(DAILY_FORECAST, mForecast.getDailyForecast());
        intent.putExtra("location", locationForDaily);
        startActivity(intent);
    }

    @OnClick(R.id.hourlyButton)
    public void startHourlyActivity(View view) {
        Intent intent = new Intent(this, HourlyForecastActivity.class);
        intent.putExtra(HOURLY_FORECAST, mForecast.getHourlyForecast());
        intent.putExtra("location", locationForDaily);
        startActivity(intent);
    }


    @OnClick(R.id.precipValue)
    public void openPrecipDialog(View view){
        Current current = mForecast.getCurrent();
        if(!mPrecipValue.equals("0%")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater=MainActivity.this.getLayoutInflater();
            @SuppressLint("InflateParams") View layout=inflater.inflate(R.layout.dialog,null);

            builder.setView(layout);


            TextView title = (TextView)layout.findViewById(R.id.pickedDayTextView);
            TextView message = (TextView)layout.findViewById(R.id.contentAlertDIalogTextView);
            ImageView icon = (ImageView)layout.findViewById(R.id.iconImageViewAlert);
            Button okButton = (Button)layout.findViewById(R.id.alertDialogButton);
            title.setText(R.string.precip_dialog_title);
            DecimalFormat form = new DecimalFormat("0.00");
            if (localeUS.equals("en_US")){
                message.setText( "\n" +getString(R.string.precip_dialog_message1)+ form.format(current.getPrecipIntensity())+ " inches per hour" + "\n");
            }else{
                message.setText( "\n" +getString(R.string.precip_dialog_message1)+  form.format(current.getPrecipIntensity())+ getString(R.string.precip_dialog_message2) + "\n");
            }



            final AlertDialog dialog = builder.create();
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });




            dialog.show();

        }

    }




    private class GetAddressAsynctask extends AsyncTask<Object, Void, Void> {

        @Override
        protected Void doInBackground(Object... location) {

            double latitude = (Double) location[0];
            double longitude = (Double) location[1];
            Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = gcd.getFromLocation(latitude,
                        longitude, 1);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            if (addresses != null && addresses.size() > 0) {


                getLocality = (addresses.get(0).getLocality());

                getAdminArea = (addresses.get(0).getLocality());

            }

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mLocationLabel.setText(getLocality);
            locationForDaily = (getLocality);
            if (getLocality == null) {

                mLocationLabel.setText(getAdminArea);
                locationForDaily = (getAdminArea);


            }

        }
    }
    private class GetLocationAsyncTask extends AsyncTask<Object, Void, Void >{

        @Override
        protected Void doInBackground(Object... params) {

            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(true);
            criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);

            provider = locationManager.getBestProvider(criteria, true);

            location = locationManager.getLastKnownLocation(provider);


            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if (location != null) {
                System.out.println("Provider " + provider + " has been selected.");
                onLocationChanged(location);
            }
        }
    }

}

