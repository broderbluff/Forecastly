package eu.brimir.forecastly.ui;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import eu.brimir.forecastly.adapters.DayAdapter;
import eu.brimir.forecastly.utils.Constants;
import eu.brimir.forecastly.weather.Daily;

public class DailyForecastActivity extends ListActivity {

    private Daily[] mDays;


    @Bind(R.id.thisWeekLabel) TextView mWeekLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);
        ButterKnife.bind(this);
        Intent intent = getIntent();


        Parcelable[] parcelables = intent.getParcelableArrayExtra(Constants.DAILY_FORECAST);

        mDays = Arrays.copyOf(parcelables, parcelables.length, Daily[].class);
        DayAdapter adapter = new DayAdapter(this, mDays);
        setListAdapter(adapter);





    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String dayOfTheWeek = mDays[position].getDayOfTheWeek();
        String conditions = mDays[position].getSummary();
        String highTemp = mDays[position].getTemperatureMax() + "";
        String precipProbability = mDays[position].getPrecipProbability() + "";
        String precipType = "";
        String precipIntensityMax = String.format("%.2f", mDays[position].getPrecipIntensityMax());
        if ( mDays[position].getPrecipProbability() != 0){

            precipType = mDays[position].getPrecipType();
        }

        int iconId = mDays[position].getIconId();

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







        TextView title = (TextView)dialog.findViewById(R.id.pickedDayTextView);
        TextView message = (TextView)dialog.findViewById(R.id.contentAlertDIalogTextView);
        ImageView icon = (ImageView)dialog.findViewById(R.id.iconImageViewAlert);
        Button okButton = (Button)dialog.findViewById(R.id.alertDialogButton);
        TextView precip = (TextView)dialog.findViewById(R.id.precipChanceDialogTextView);
        TextView precipIntenstity = (TextView)dialog.findViewById(R.id.precipIntensityMaxTextView);
        title.setText(dayOfTheWeek);

        if(precipType.isEmpty()){
            message.setText(getString(R.string.will_the_temp) + highTemp + getString(R.string.degrees_Text) + "\n" + "\n" + getString(R.string.and_it_will)
                    + conditions);
            LinearLayout icon3 = (LinearLayout)dialog.findViewById(R.id.linearlayout132);
            LinearLayout icon2 = (LinearLayout)dialog.findViewById(R.id.linearLayout5);

            icon3.setVisibility(View.GONE);
            icon2.setVisibility(View.GONE);

        }else{


            message.setText(getString(R.string.will_the_temp) + highTemp + getString(R.string.degrees_Text) + "\n" + "\n" + getString(R.string.and_it_will)
                    + conditions+ "\n" );

            precip.setText(precipProbability + "%" + " " + precipType);
            precipIntenstity.setText(precipIntensityMax + getString(R.string.daily_mmanhour));


        }



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

    @OnClick(R.id.dailyOkButton)
    public void finishDailyActivity(View view) {
       finish();
    }
    @OnClick(R.id.thisWeekLabel)
    public void finishDailyActivitytwo(View view) {
        finish();
    }
}
