package eu.brimir.forecastly.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import eu.brimir.forecastly.R;
import eu.brimir.forecastly.weather.Hourly;

/**
 * Created by Patrik on 2015-07-15.
 */
public class HourlyAdapter extends BaseAdapter {
    private Context mContext;
    private final Hourly[] mHours;


    public HourlyAdapter(Context context, Hourly[] hours){
        mContext = context;
        mHours = hours;
    }

    @Override
    public int getCount() {
        return mHours.length;
    }

    @Override
    public Object getItem(int position) {
        return mHours[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.hourly_list_item, null);
            holder = new ViewHolder();
            holder.iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
            holder.temperatureLabel = (TextView) convertView.findViewById(R.id.temperatureLabel);
            holder.timeLabel = (TextView) convertView.findViewById(R.id.timeLabel);
            holder.summaryLabel = (TextView) convertView.findViewById(R.id.summaryLabel);



            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Hourly hour = mHours[position];
        holder.iconImageView.setImageResource(hour.getIconId());
        holder.temperatureLabel.setText(hour.getTemperature() +"");
        holder.summaryLabel.setText(hour.getSummary());
        holder.timeLabel.setText(hour.getHour());



        return convertView;
    }



    private static class ViewHolder {
        ImageView iconImageView;
        TextView temperatureLabel;
        TextView timeLabel;
        TextView summaryLabel;


    }
}
