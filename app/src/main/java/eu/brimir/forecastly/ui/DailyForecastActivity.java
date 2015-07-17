package eu.brimir.forecastly.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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


        String message = String.format(getString(R.string.on_text)+ dayOfTheWeek + getString(R.string.will_the_temp) + highTemp +getString(R.string.degrees_Text)+ "\n"  +"\n" +getString(R.string.and_it_will)+ conditions);
        for (int i=0; i < 2; i++)
        {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }



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
