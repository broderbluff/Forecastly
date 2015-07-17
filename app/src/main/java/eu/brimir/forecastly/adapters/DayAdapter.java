package eu.brimir.forecastly.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
            holder.windBearingLabel = (TextView) convertView.findViewById(R.id.windBearingLabel);


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
        if(locale.equals("en_US")){
            holder.windSpeedLabel.setText(day.getWindSpeed() + " mph");
        }else if(locale.equals("sv_SE")){
            holder.windSpeedLabel.setText(day.getWindSpeed() + " m/s");
        }else if(locale.equals("en_GB")) {
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
        String bearingText;


        if (locale.equals("sv_SE")){
            if ((360 >= day.getWindBearing() && day.getWindBearing() >= 337.5) || (0 <= day.getWindBearing() && day.getWindBearing() <= 22.5))
                bearingText = "N";
            else if (day.getWindBearing() > 22.5 && day.getWindBearing() < 67.5) bearingText = "NO";
            else if (day.getWindBearing() >= 67.5 && day.getWindBearing() <= 112.5) bearingText = "Ö";
            else if (day.getWindBearing() > 112.5 && day.getWindBearing() < 157.5) bearingText = "SO";
            else if (day.getWindBearing() >= 157.5 && day.getWindBearing() <= 202.5) bearingText = "S";
            else if (day.getWindBearing() > 202.5 && day.getWindBearing() < 247.5) bearingText = "SV";
            else if (day.getWindBearing() >= 247.5 && day.getWindBearing() <= 292.5) bearingText = "V";
            else if (day.getWindBearing() > 292.5 && day.getWindBearing() < 337.5) bearingText = "NV";
            else bearingText = "?";
            holder.windBearingLabel.setText(bearingText);
        }else {


            if ((360 >= day.getWindBearing() && day.getWindBearing() >= 337.5) || (0 <= day.getWindBearing() && day.getWindBearing() <= 22.5))
                bearingText = "N";
            else if (day.getWindBearing() > 22.5 && day.getWindBearing() < 67.5) bearingText = "NE";
            else if (day.getWindBearing() >= 67.5 && day.getWindBearing() <= 112.5)
                bearingText = "E";
            else if (day.getWindBearing() > 112.5 && day.getWindBearing() < 157.5)
                bearingText = "SE";
            else if (day.getWindBearing() >= 157.5 && day.getWindBearing() <= 202.5)
                bearingText = "S";
            else if (day.getWindBearing() > 202.5 && day.getWindBearing() < 247.5)
                bearingText = "SW";
            else if (day.getWindBearing() >= 247.5 && day.getWindBearing() <= 292.5)
                bearingText = "W";
            else if (day.getWindBearing() > 292.5 && day.getWindBearing() < 337.5)
                bearingText = "NW";
            else bearingText = "?";
            holder.windBearingLabel.setText(bearingText);

        }
        return convertView;
    }

    private static class ViewHolder {
        ImageView iconImageView;
        TextView temperatureLabel;
        TextView dayLabel;
        TextView windSpeedLabel;
        TextView windBearingLabel;

    }
}
