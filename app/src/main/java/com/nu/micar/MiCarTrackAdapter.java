package com.nu.micar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Waqar Malik on 27-Apr-17.
 */


public class MiCarTrackAdapter extends RecyclerView.Adapter<MiCarTrackAdapter.ViewHolder> {

    List<RegisterCarModel> registerCarModelList;
    Context context;

    MiCarTrackAdapter(Context context, List<RegisterCarModel> registerCarModel) {
        this.registerCarModelList = new ArrayList<RegisterCarModel>();
        this.context = context;
        this.registerCarModelList = registerCarModel;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.track_frag_row_layout, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.tv_car_reg_no.setText(registerCarModelList.get(position).getRegistration_no());
        holder.tv_car_manufacturer.setText(registerCarModelList.get(position).getManufacturer());
        holder.tv_car_model.setText(registerCarModelList.get(position).getModel());



       /* if(!holder.mySwitch.isChecked()) {
            holder.mySwitch.setOnClickListener(new View.OnClickListener()

                                               {
                                                   public void onClick(View v) {

                                                       Intent intent = new Intent(context, MiCarTrackingonMap.class);
                                                       Bundle extras = new Bundle();
                                                       extras.putString("deviceid", registerCarModelList.get(position).getDevice_id());
                                                       extras.putString("regno", registerCarModelList.get(position).getRegistration_no());
                                                       intent.putExtras(extras);
                                                       context.startActivity(intent);
                                                   }
                                               }
            );
        }*/

        holder.mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    if(!registerCarModelList.get(position).getDevice_id().equals("")) {
                        Intent intent = new Intent(context, MiCarTrackingonMap.class);
                        Bundle extras = new Bundle();
                        extras.putString("deviceid", registerCarModelList.get(position).getDevice_id());
                        extras.putString("regno", registerCarModelList.get(position).getRegistration_no());
                        intent.putExtras(extras);
                        context.startActivity(intent);
                    }
                    else{
                        Toast.makeText(context , "No device id associated with this car", Toast.LENGTH_LONG).show();
                        holder.mySwitch.setChecked(false);
                    }
                }else{

                }

            }
        });



    }

    @Override
    public int getItemCount() {
        return registerCarModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //   public TextView tv_manifestno, tv_truckno, tv_customername;

        public TextView tv_car_reg_no, tv_car_manufacturer, tv_car_model;
        private Switch mySwitch;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tv_car_reg_no = (TextView) itemLayoutView.findViewById(R.id.tv_car_reg_no);
            tv_car_manufacturer = (TextView) itemLayoutView.findViewById(R.id.tv_car_manufacturer);
            tv_car_model = (TextView) itemLayoutView.findViewById(R.id.tv_car_model);
            mySwitch = (Switch) itemLayoutView.findViewById(R.id.myswitch);

            itemLayoutView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            /*Intent intent = new Intent(context, MiCarTrackingonMap.class);
            Bundle extras = new Bundle();

            extras.putString("regno", registerCarModelList.get(getAdapterPosition()).getRegistration_no());
            extras.putString("model", registerCarModelList.get(getAdapterPosition()).getModel());
            extras.putString("modelyear", registerCarModelList.get(getAdapterPosition()).getModel_year());
            extras.putString("manu", registerCarModelList.get(getAdapterPosition()).getManufacturer());
            extras.putString("color", registerCarModelList.get(getAdapterPosition()).getColor());
            extras.putString("regcity", registerCarModelList.get(getAdapterPosition()).getRegistration_city());
            extras.putString("enginecapacity", registerCarModelList.get(getAdapterPosition()).getEngine_capacity());
            extras.putString("enginetype", registerCarModelList.get(getAdapterPosition()).getDevice_id());
            extras.putString("deviceid", registerCarModelList.get(getAdapterPosition()).getDevice_id());

            intent.putExtras(extras);
            context.startActivity(intent);
            ((Activity) context).finish();*/

            //  Toast.makeText(RecyclerAdapter.context, "you have clicked Row " + getAdapterPosition(), Toast.LENGTH_LONG).show();
        }
    }
}

