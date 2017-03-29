package com.nu.micar;

import android.support.annotation.BinderThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ashfaqn on 3/29/2017.
 */

public class MiCarTrackDetailsExpandableHeaderHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.list_expandable_header_buttonToogle)
    ImageView buttonToggle;

    @BindView(R.id.list_expandable_header_layout)
    View backgroundView;

    @BindView(R.id.list_expandable_header_headerName)
    TextView headerName;

    public Item refferalItem;


    public MiCarTrackDetailsExpandableHeaderHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
