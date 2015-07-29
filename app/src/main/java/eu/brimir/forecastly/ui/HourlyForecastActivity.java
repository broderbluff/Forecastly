package eu.brimir.forecastly.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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

        final Dialog dialog = new Dialog(this,
                android.R.style.Theme_Translucent_NoTitleBar);
        Window window = dialog.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);

        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog);




        TextView title = (TextView)dialog.findViewById(R.id.pickedDayTextView);
        TextView message = (TextView)dialog.findViewById(R.id.contentAlertDIalogTextView);
        ImageView icon = (ImageView)dialog.findViewById(R.id.iconImageViewAlert);
        Button okButton = (Button)dialog.findViewById(R.id.alertDialogButton);
        LinearLayout icon3 = (LinearLayout)dialog.findViewById(R.id.linearlayout132);
        LinearLayout icon2 = (LinearLayout)dialog.findViewById(R.id.linearLayout5);

        icon3.setVisibility(View.GONE);
        icon2.setVisibility(View.GONE);

        title.setText(pickedHour);
        message.setText(getString(R.string.the_temp_will_text) + highTemp + getString(R.string.degrees_Text) + "\n" + "\n" + getString(R.string.and_it_will) + conditions);
        icon.setImageResource(iconId);

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










