package com.nu.micar;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ashfaqn on 3/29/2017.
 */

public class MiCarTrackDetailsHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.list_track_details)
    TextView trackDetails;

    @BindView(R.id.list_track_details_date)
    TextView trackDetailsDate;

    @BindView(R.id.list_track_details_time)
    TextView trackDetailsTime;

    @BindView(R.id.list_track_details_location)
    TextView trackDetailsLocation;



    public MiCarTrackDetailsHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void populate(MiCarTrackDetails miCarTrackDetails)
    {
        itemView.setTag(miCarTrackDetails);
        trackDetails.setText(miCarTrackDetails.getCarName());
        trackDetailsDate.setText(miCarTrackDetails.getTrackDate());
        trackDetailsTime.setText(miCarTrackDetails.getTrackTime());
        trackDetailsLocation.setText(miCarTrackDetails.getCarLocation());
    }
}
