package eu.brimir.forecastly.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.brimir.forecastly.R;
import eu.brimir.forecastly.adapters.HourlyAdapter;
import eu.brimir.forecastly.weather.Hourly;

public class HourlyForecastActivity extends ListActivity {
    private Hourly[] mHours;
    @Bind(R.id.locationLabelHourly)
    TextView mLocationLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_forecast);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String locationForHourly = intent.getExtras().getString("location");
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.HOURLY_FORECAST);

        mHours = Arrays.copyOf(parcelables, parcelables.length, Hourly[].class);
        HourlyAdapter adapter = new HourlyAdapter(this, mHours);
        setListAdapter(adapter);
        TextView tv = (TextView) findViewById(R.id.locationLabelHourly);
        tv.setText(locationForHourly);
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String pickedHour = mHours[position].getHour();
        String conditions = mHours[position].getSummary();
        String highTemp = mHours[position].getTemperature() + "";
        int iconId = mHours[position].getIconId();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater=HourlyForecastActivity.this.getLayoutInflater();
        @SuppressLint("InflateParams") View layout=inflater.inflate(R.layout.dialog,null);

        builder.setView(layout);


        TextView title = (TextView)layout.findViewById(R.id.pickedDayTextView);
        TextView message = (TextView)layout.findViewById(R.id.contentAlertDIalogTextView);
        ImageView icon = (ImageView)layout.findViewById(R.id.iconImageViewAlert);
        Button okButton = (Button)layout.findViewById(R.id.alertDialogButton);
        LinearLayout icon3 = (LinearLayout)layout.findViewById(R.id.linearlayout132);
        LinearLayout icon2 = (LinearLayout)layout.findViewById(R.id.linearLayout5);

        icon3.setVisibility(View.GONE);
        icon2.setVisibility(View.GONE);

        title.setText(pickedHour);
        message.setText(getString(R.string.the_temp_will_text) + highTemp + getString(R.string.degrees_Text) + "\n" + "\n" + getString(R.string.and_it_will) + conditions);
        icon.setImageResource(iconId);
        final AlertDialog dialog = builder.create();
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });




        dialog.show();



      /*  String message = String.format(getString(R.string.on_text)+ dayOfTheWeek + getString(R.string.will_the_temp) + highTemp +getString(R.string.degrees_Text)+ "\n"  +"\n" +getString(R.string.and_it_will)+ conditions);
        for (int i=0; i < 2; i++)
        {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }*/



    }









    @OnClick(R.id.locationLabelHourly)
    public void finishHourlyActivity(View view) {
        finish();
    }
    @OnClick(R.id.thisHourLabel)
    public void finishHourlyActivitytwo(View view) {
        finish();
    }

}










