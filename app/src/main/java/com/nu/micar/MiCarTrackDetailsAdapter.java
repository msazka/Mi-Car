package com.nu.micar;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashfaqn on 3/29/2017.
 */

public class MiCarTrackDetailsAdapter extends RecyclerView.Adapter {

    public static final int VIEW_TYPE_EXPANDABLE_LIST_HEADER=2;
    public static final int VIEW_TYPE_EXPANDABLE_LIST_CHILD=3;

    private List<Item> data;
    private MiCarMainActivity activity;
    private LayoutInflater layoutInflater;
    private MiCarTrackDetailsListener listener;

    public MiCarTrackDetailsAdapter(MiCarMainActivity activity, MiCarTrackDetailsListener listener) {
        this.activity = activity;
        this.listener = listener;
        layoutInflater = activity.getLayoutInflater();
        data = new ArrayList<>();
    }

    public List<Item> getData(){
        return data;
    }

    @Override
    public int getItemViewType(int position){
        if(position==2)
        {
            return VIEW_TYPE_EXPANDABLE_LIST_HEADER;
        }
        if (position==3)
        {
            return VIEW_TYPE_EXPANDABLE_LIST_CHILD;
        }

        throw new IllegalArgumentException(
                "we are being asked for an item type for position "+ position +", though we have no such item"
        );
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View expandableHeaderView = layoutInflater.inflate(R.layout.activity_micar_track_expandable_header,parent,false);
        View miCarTrackDetailsView = layoutInflater.inflate(R.layout.activity_micar_track_details,parent,false);

        switch(viewType){
            case VIEW_TYPE_EXPANDABLE_LIST_CHILD:
                final MiCarTrackDetailsHolder holder = new MiCarTrackDetailsHolder(miCarTrackDetailsView);
                holder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        MiCarTrackDetails miCarTrackDetails = (MiCarTrackDetails) holder.itemView.getTag();
                        listener.OnMiCarTrackDetailsClicked(miCarTrackDetails);

                    }
                });
                return holder;

            case VIEW_TYPE_EXPANDABLE_LIST_HEADER:
                return new MiCarTrackDetailsExpandableHeaderHolder(expandableHeaderView);
        }

        throw new IllegalArgumentException("ViewType " + viewType + " is not supported");


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MiCarTrackDetailsExpandableHeaderHolder){
            position--;

            final Item item = data.get(position);
            final MiCarTrackDetailsExpandableHeaderHolder itemController = (MiCarTrackDetailsExpandableHeaderHolder) holder;

        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public interface MiCarTrackDetailsListener{
        void OnMiCarTrackDetailsClicked(MiCarTrackDetails miCarTrackDetails);
    }
}
