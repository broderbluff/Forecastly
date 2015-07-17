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
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.brimir.forecastly.R;
import eu.brimir.forecastly.adapters.DayAdapter;
import eu.brimir.forecastly.weather.Daily;

public class DailyForecastActivity extends ListActivity {

    private Daily[] mDays;

    @Bind(R.id.locationLabelDaily) TextView mLocationLabel;
    @Bind(R.id.thisWeekLabel) TextView mWeekLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String locationForDaily = intent.getExtras().getString("location");

        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);

        mDays = Arrays.copyOf(parcelables, parcelables.length, Daily[].class);
        DayAdapter adapter = new DayAdapter(this, mDays);
        setListAdapter(adapter);

        TextView tv = (TextView) findViewById(R.id.locationLabelDaily);
        tv.setText(locationForDaily);



    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String dayOfTheWeek = mDays[position].getDayOfTheWeek();
        String conditions = mDays[position].getSummary();
        String highTemp = mDays[position].getTemperatureMax() + "";
        int iconId = mDays[position].getIconId();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater=DailyForecastActivity.this.getLayoutInflater();
        @SuppressLint("InflateParams") View layout=inflater.inflate(R.layout.dialog,null);

        builder.setView(layout);


        TextView title = (TextView)layout.findViewById(R.id.pickedDayTextView);
        TextView message = (TextView)layout.findViewById(R.id.contentAlertDIalogTextView);
        ImageView icon = (ImageView)layout.findViewById(R.id.iconImageViewAlert);
        Button okButton = (Button)layout.findViewById(R.id.alertDialogButton);
        title.setText(dayOfTheWeek);
        message.setText(getString(R.string.will_the_temp) + highTemp + getString(R.string.degrees_Text) + "\n" + "\n" + getString(R.string.and_it_will) + conditions);
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

    @OnClick(R.id.locationLabelDaily)
    public void finishDailyActivity(View view) {
       finish();
    }
    @OnClick(R.id.thisWeekLabel)
    public void finishDailyActivitytwo(View view) {
        finish();
    }
}
