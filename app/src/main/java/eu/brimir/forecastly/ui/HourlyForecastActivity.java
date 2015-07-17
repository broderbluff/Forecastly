package eu.brimir.forecastly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.brimir.forecastly.R;
import eu.brimir.forecastly.adapters.HourlyAdapter;
import eu.brimir.forecastly.weather.Hourly;

public class HourlyForecastActivity extends AppCompatActivity {

    @Bind(R.id.recyclerView)
      RecyclerView mRecyclerView;
    @Bind(R.id.locationLabelHourly)
    TextView mLocationLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_forecast);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.HOURLY_FORECAST);
        Hourly[] hours = Arrays.copyOf(parcelables, parcelables.length, Hourly[].class);
        String locationForDaily = intent.getExtras().getString("location");
        TextView tv = (TextView) findViewById(R.id.locationLabelHourly);
        tv.setText(locationForDaily);

        HourlyAdapter adapter = new HourlyAdapter(this, hours);
        mRecyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

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










