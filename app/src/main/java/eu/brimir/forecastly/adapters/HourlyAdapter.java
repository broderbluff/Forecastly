package eu.brimir.forecastly.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import eu.brimir.forecastly.R;
import eu.brimir.forecastly.weather.Hourly;

/**
 * Created by Patrik on 2015-07-15.
 */
public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder> {

    private final Hourly[] mHours;
    private Context mContext;

    public HourlyAdapter(Context context, Hourly[] hours){
        mContext = context;
        mHours = hours;
    }


    @Override
    public HourlyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hourly_list_item, viewGroup, false);

        return new HourlyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(HourlyViewHolder holder, int i) {


        holder.bindHour(mHours[i]);

    }

    @Override
    public int getItemCount() {
        return mHours.length;
    }

    public class HourlyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView mTimeLabel;
        public final TextView mSummaryLabel;
        public final TextView mTemperatureLabel;
        public final ImageView mIconImageView;


      public HourlyViewHolder(View itemView) {
          super(itemView);

          mTimeLabel = (TextView) itemView.findViewById(R.id.timeLabel);
          mSummaryLabel = (TextView) itemView.findViewById(R.id.summaryLabel);
          mTemperatureLabel = (TextView) itemView.findViewById(R.id.temperatureLabel);
          mIconImageView = (ImageView) itemView.findViewById(R.id.iconImageView);
          itemView.setOnClickListener(this);

      }

        public void bindHour(Hourly hour){
mTimeLabel.setText(hour.getHour());
mSummaryLabel.setText(hour.getSummary());
mTemperatureLabel.setText(hour.getTemperature() + "");
mIconImageView.setImageResource(hour.getIconId());
        }

        @Override
        public void onClick(View v) {
            String time = mTimeLabel.getText().toString();
            String temperature =mTemperatureLabel.getText().toString();
            String summary = mSummaryLabel.getText().toString();
            String message = String.format(mContext.getString(R.string.time_text) + time +mContext.getString(R.string.the_temp_will_text) +temperature + mContext.getString(R.string.degrees_Text)+ "\n"  + mContext.getString(R.string.and_it_will) + summary);
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        }
    }
}
