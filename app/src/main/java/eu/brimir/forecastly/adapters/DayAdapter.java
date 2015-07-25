package eu.brimir.forecastly.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Locale;

import eu.brimir.forecastly.R;
import eu.brimir.forecastly.weather.Daily;

/**
 * Created by Patrik on 2015-07-12.
 */
@SuppressWarnings("ALL")
public class DayAdapter extends BaseAdapter {
    private Context mContext;
    private Daily[] mDays;
    private String locale = Locale.getDefault().toString();
    public DayAdapter(Context context, Daily[] days) {
        mContext = context;
        mDays = days;
    }

    @Override
    public int getCount() {
        return mDays.length;
    }

    @Override
    public Object getItem(int position) {
        return mDays[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.daily_list_item, null);
            holder = new ViewHolder();
            holder.iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
            holder.temperatureLabel = (TextView) convertView.findViewById(R.id.temperatureLabel);
            holder.dayLabel = (TextView) convertView.findViewById(R.id.dayNameLabel);
            holder.windSpeedLabel = (TextView) convertView.findViewById(R.id.windSpeedLabel);

            holder.windBearingImageView = (ImageView) convertView.findViewById(R.id.windBearingImageView);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Daily day = mDays[position];
        holder.iconImageView.setImageResource(day.getIconId());
        holder.temperatureLabel.setText(day.getTemperatureMax() + "");

        if (position == 0){
            holder.dayLabel.setText(R.string.today);
        }else{
            holder.dayLabel.setText(day.getDayOfTheWeek());
        }
        if(locale.equals("en_US")||locale.equals("en_GB")){
            holder.windSpeedLabel.setText(day.getWindSpeed() + " mph");
        }else{
            holder.windSpeedLabel.setText(day.getWindSpeed() + " m/s");
        }


        if (day.getTemperatureMax() >= 25) {
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(convertView.findViewById(R.id.temperatureLabel));
        } else if (day.getTemperatureMax() <= 0) {
            YoYo.with(Techniques.Shake)
                    .duration(500)
                    .playOn(convertView.findViewById(R.id.temperatureLabel));
        }
        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(mContext);
        // The SharedPreferences editor - must use commit() to submit changes
        SharedPreferences.Editor editor = preferences.edit();

        // Edit the saved preferences


       float fromDegrees = preferences.getFloat("bearing", 0);




        float toDegrees = (float) day.getWindBearing();


        Animation ani = new RotateAnimation(
               fromDegrees, /* from degree*/
                -toDegrees, /* to degree */
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ani.setDuration(1000);
        ani.setFillAfter(true);


        holder.windBearingImageView.startAnimation(ani);
        if (-fromDegrees != -toDegrees){

            editor.putFloat("bearing",  -toDegrees);
            editor.commit();

        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView iconImageView;
        TextView temperatureLabel;
        TextView dayLabel;
        TextView windSpeedLabel;
        ImageView windBearingImageView;


    }
}
