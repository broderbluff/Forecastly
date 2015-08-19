package eu.brimir.forecastly.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
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
import eu.brimir.forecastly.adapters.PlacesAutoCompleteAdapter;
import eu.brimir.forecastly.utils.Constants;
import eu.brimir.forecastly.utils.GetForcastURL;
import eu.brimir.forecastly.utils.SetBackgroundImage;
import eu.brimir.forecastly.weather.Current;
import eu.brimir.forecastly.weather.Daily;
import eu.brimir.forecastly.weather.Forecast;
import eu.brimir.forecastly.weather.Hourly;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private static final String TAG = MainActivity.class.getSimpleName();

    private Forecast mForecast;
    private String provider;
    private String description;
    private LocationRequest mLocationRequest;
    private double currentLongitude;
    private double currentLatitude;
    private double latitude;
    private double longitude;
    private double pickedLatitude;
    private double pickedLongitude;
    private String getLocality;
    private boolean stormAlert = false;
    private List<Address> addresses;
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
    private AnimationDrawable alertAnimation;
    private SharedPreferences savedLocation;
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

    @Bind(R.id.locationLabel)
    TextView mLocationLabel;


    @Bind(R.id.timemachine)
    ImageView mTimeMachine;
    @Bind(R.id.layoutBackground)
    ImageView mImageviewLayout;

    @Bind(R.id.alertImageView)
    ImageView mAlertImage;
    // private SwipeRefreshLayout swipeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        savedLocation = this.getSharedPreferences(Constants.KEY_SHARED_PREF, 0);
        savedLocation.edit().clear().commit();


        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);


        if (!enabled) {

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

                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    });

            android.app.AlertDialog dialog = builder.create();


            dialog.show();


        }
        mAlertImage.setVisibility(View.INVISIBLE);

        mProgressBar.setVisibility(View.INVISIBLE);


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

                stormAlert = false;
                mAlertImage.setVisibility(View.INVISIBLE);


                savedLocation = MainActivity.this.getSharedPreferences(Constants.KEY_SHARED_PREF, 0);
                savedLocation.edit().clear().commit();

                updateDisplay();
                getAddress(currentLatitude, currentLongitude);
                getForecast(currentLatitude, currentLongitude);


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

            intent.putExtra("latitude", currentLatitude);
            intent.putExtra("longitude", currentLongitude);
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
        GetForcastURL getForcastURL = new GetForcastURL();

        String forecastUrl = getForcastURL.getURL(latitude, longitude);
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

    @SuppressWarnings("deprecation")
    private void updateDisplay() {
        Current current = mForecast.getCurrent();


        final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
        animation.setDuration(500); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in


        if (stormAlert) {
            mAlertImage.setVisibility(View.VISIBLE);
            mAlertImage.startAnimation(animation);


            mAlertImage.setBackgroundResource(R.drawable.alertanimation);
            alertAnimation = (AnimationDrawable) mAlertImage.getBackground();
            alertAnimation.start();
        } else if (stormAlert == false) {


            mAlertImage.setAnimation(null);

            mAlertImage.setVisibility(View.INVISIBLE);


        }


        if (currentLatitude == 0.0 && currentLongitude == 0.0) {
            mTemperatureLabel.setText("--");
        }
        mTemperatureLabel.setText(current.getTemperature() + "");
        mTimeLabel.setText(current.getFormattedTime());
        mHumidityValue.setText(current.getHumidity() + "%");
        mPrecipValue.setText(current.getPrecipChance() + "%");
        mSummaryLabel.setText(current.getSummary());
        SetBackgroundImage bgImage = new SetBackgroundImage();
        Drawable bg = bgImage.getBackgroundImage(current.getSummary(), current.getIcon());

        mImageviewLayout.setImageDrawable(bg);

        @SuppressWarnings("deprecation")
        Drawable drawable = getResources().getDrawable(current.getIconId());
        mIconImageView.setImageDrawable(drawable);


        if (locale.equals("en_US"))

        {
            if (current.getTemperature() >= 77 && currentLatitude != 0.0 && currentLongitude != 0.0) {
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(findViewById(R.id.temperatureLabel));
            } else if (current.getTemperature() <= 32) {
                YoYo.with(Techniques.Shake)
                        .duration(500)
                        .playOn(findViewById(R.id.temperatureLabel));
            }
        } else

        {
            String label = mTemperatureLabel.getText().toString();
            if (current.getTemperature() >= 25 && currentLatitude != 0.0 && currentLongitude != 0.0) {
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
            day.setPrecipIntensityMax(jsonDay.getDouble("precipIntensityMax"));
            day.setPrecipProbability(jsonDay.getDouble("precipProbability"));
            if (jsonDay.has("precipType")) {
                day.setPrecipType(jsonDay.getString("precipType"));
            }


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
        if (currently.has("nearestStormDistance")) {
            current.setNearestStormDistance(currently.getDouble("nearestStormDistance"));
            current.setNearestStormBearing(currently.getDouble("nearestStormBearing"));

            stormAlert = true;
        } else {
            stormAlert = false;


        }


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


    @Override
    protected void onResume() {
        super.onResume();

        mGoogleApiClient.connect();


    }


    @Override
    protected void onStop() {
        super.onStop();
        savedLocation = this.getSharedPreferences(Constants.KEY_SHARED_PREF, 0);
        savedLocation.edit().clear().commit();
    }

    @Override
    protected void onPause() {
        super.onPause();


        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }


    @OnClick(R.id.sevenDaysButton)
    public void startDailyActivity(View view) {
        Intent intent = new Intent(this, DailyForecastActivity.class);
        intent.putExtra(Constants.DAILY_FORECAST, mForecast.getDailyForecast());
        intent.putExtra("location", locationForDaily);
        startActivity(intent);
    }

    @OnClick(R.id.hourlyButton)
    public void startHourlyActivity(View view) {
        Intent intent = new Intent(this, HourlyForecastActivity.class);
        intent.putExtra(Constants.HOURLY_FORECAST, mForecast.getHourlyForecast());
        intent.putExtra("location", locationForDaily);
        startActivity(intent);
    }

    @OnClick(R.id.alertImageView)
    public void openAlertActivity(View view) {
        Current current = mForecast.getCurrent();

        float toDegrees = (float) current.getNearestStormBearing();
        int distanceStorm = (int) Math.round(current.getNearestStormDistance());


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        @SuppressLint("InflateParams") View layout = inflater.inflate(R.layout.dialog_alert, null);

        builder.setView(layout);


        ImageView arrow = (ImageView) layout.findViewById(R.id.alertDialogArrow);
        TextView distance = (TextView) layout.findViewById(R.id.distanceToStormTextView);

        if (localeUS.equals("en_US")) {
            distance.setText(distanceStorm + " miles away");
        } else {
            distance.setText(distanceStorm + getString(R.string.alert_dialog_km_away));
        }


        Animation ani = new RotateAnimation(
                0, /* from degree*/
                toDegrees, /* to degree */
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ani.setDuration(1000);
        ani.setFillAfter(true);
        arrow.startAnimation(ani);

        Button okButton = (Button) layout.findViewById(R.id.alertDialogButton);


        final AlertDialog dialog = builder.create();
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();


    }


    @OnClick(R.id.precipLayout)
    public void openPrecipDialog(View view) {
        Current current = mForecast.getCurrent();
        if (current.getPrecipChance() != 0) {
            final Dialog dialog = new Dialog(this,
                    R.style.Theme_D1NoTitleDim);
            Window window = dialog.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(true);

            dialog.setCanceledOnTouchOutside(true);
            dialog.setContentView(R.layout.dialog);


            TextView title = (TextView) dialog.findViewById(R.id.pickedDayTextView);

            ImageView precipIcon = (ImageView) dialog.findViewById(R.id.precipIntensityMaxIcon);
            Button okButton = (Button) dialog.findViewById(R.id.alertDialogButton);
            LinearLayout icon3 = (LinearLayout) dialog.findViewById(R.id.linearlayout132);
            TextView precipIntenstity = (TextView) dialog.findViewById(R.id.precipIntensityMaxTextView);

            icon3.setVisibility(View.GONE);

            if (current.getPrecipType().equals("snow")) {
                precipIcon.setImageDrawable(getResources().getDrawable(R.drawable.snowflaje));
            } else if (current.getPrecipType().equals("rain")) {
                precipIcon.setImageDrawable(getResources().getDrawable(R.drawable.water));
            }
            title.setText(R.string.precip_dialog_title);
            DecimalFormat form = new DecimalFormat("0.00");
            if (localeUS.equals("en_US")) {
                precipIntenstity.setText(form.format(current.getPrecipIntensity()) + " inches per hour");
            } else {
                precipIntenstity.setText(form.format(current.getPrecipIntensity()) + getString(R.string.precip_dialog_message2));
            }


            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


            dialog.show();

        }

    }


    @OnClick(R.id.locationLabel)
    public void openPickLocationDialog(View view) {


        final Dialog dialog = new Dialog(this,
                R.style.Theme_D1NoTitleDim2);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.TOP;

        wlp.y = 50;
        window.setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);

        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_pick_place);
        dialog.getWindow().setSoftInputMode(wlp.SOFT_INPUT_STATE_VISIBLE);

        final AutoCompleteTextView autocompleteView = (AutoCompleteTextView) dialog.findViewById(R.id.autocomplete);
        autocompleteView.setAdapter(new PlacesAutoCompleteAdapter(MainActivity.this, R.layout.list_item));
        autocompleteView.requestFocus();

        autocompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get data associated with the specified position
                // in the list (AdapterView)

                description = (String) parent.getItemAtPosition(position);
                addressToCoordinates();
                description = null;
                dialog.dismiss();
            }
        });


        Button okButton = (Button) dialog.findViewById(R.id.alertDialogButton);
        Button closeButton = (Button) dialog.findViewById(R.id.alertDialogCloseButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                description = null;

            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (description == null) {
                    Toast.makeText(MainActivity.this, R.string.pick_location_toast, Toast.LENGTH_LONG).show();
                } else {
                    addressToCoordinates();
                    description = null;
                    dialog.dismiss();
                }

            }
        });


        dialog.show();

    }


    public void addressToCoordinates() {
        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocationName(description, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses.size() > 0) {

            pickedLatitude = addresses.get(0).getLatitude();

            pickedLongitude = addresses.get(0).getLongitude();
        }
        getAddress(pickedLatitude, pickedLongitude);
        getForecast(pickedLatitude, pickedLongitude);
        SharedPreferences pref = MainActivity.this.getSharedPreferences(Constants.KEY_SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(Constants.KEY_LATITUDE, (float) pickedLatitude);
        editor.putFloat(Constants.KEY_LONGITUDE, (float) pickedLongitude);


        editor.apply();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "Location services connected.");
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            handleNewLocation(location);

        }
        float fLat = savedLocation.getFloat(Constants.KEY_LATITUDE, 0);
        float fLong = savedLocation.getFloat(Constants.KEY_LONGITUDE, 0);
        if (fLat == 0 && fLong == 0) {


            getAddress(currentLatitude, currentLongitude);
            getForecast(currentLatitude, currentLongitude);
        } else {
            pickedLatitude = fLat;
            pickedLongitude = fLong;
            getAddress(pickedLatitude, pickedLongitude);
            getForecast(pickedLatitude, pickedLongitude);
        }


    }


    private void handleNewLocation(Location location) {

        currentLatitude = location.getLatitude();

        currentLongitude = location.getLongitude();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);

    }


    private class GetAddressAsynctask extends AsyncTask<Object, Void, Void> {

        @Override
        protected Void doInBackground(Object... location) {

            double latitude = (Double) location[0];
            double longitude = (Double) location[1];
            Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());

            addresses = null;
            try {
                addresses = gcd.getFromLocation(latitude,
                        longitude, 1);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            if (addresses != null && addresses.size() > 0) {

                if ((addresses.get(0).getLocality() != null)) {

                    getLocality = addresses.get(0).getLocality();
                    locationForDaily = getLocality;
                } else {
                    getLocality = addresses.get(0).getAddressLine(0);
                    locationForDaily = getLocality;
                }


            }

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mLocationLabel.setText(getLocality);


        }

    }


}

