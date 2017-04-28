package com.nu.micar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Waqar Malik on 26-Apr-17.
 */


public class MiCarCarsAdapter extends RecyclerView.Adapter<MiCarCarsAdapter.ViewHolder> {

     List<RegisterCarModel> registerCarModelList;
     Context context;

    MiCarCarsAdapter(Context context, List<RegisterCarModel> registerCarModel) {
        this.registerCarModelList = new ArrayList<RegisterCarModel>();
        this.context = context;
        this.registerCarModelList = registerCarModel;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.cars_frag_row_layout, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        holder.tv_car_reg_no.setText(registerCarModelList.get(position).getRegistration_no());
        holder.tv_car_manufacturer.setText(registerCarModelList.get(position).getManufacturer());
        holder.tv_car_model.setText(registerCarModelList.get(position).getModel());

    }

    @Override
    public int getItemCount() {
        return registerCarModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //   public TextView tv_manifestno, tv_truckno, tv_customername;

        public TextView tv_car_reg_no, tv_car_manufacturer, tv_car_model;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tv_car_reg_no = (TextView) itemLayoutView.findViewById(R.id.tv_car_reg_no);
            tv_car_manufacturer = (TextView) itemLayoutView.findViewById(R.id.tv_car_manufacturer);
            tv_car_model = (TextView) itemLayoutView.findViewById(R.id.tv_car_model);

            itemLayoutView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
           /*Intent intent = new Intent(context, Manifest_SingleTruck.class);
            Bundle extras = new Bundle();
            extras.putInt("position", getAdapterPosition());
            extras.putString("manifestno", truckActivitiesDataModel.get(getAdapterPosition()).getManifestNo());
            extras.putString("truckno", truckActivitiesDataModel.get(getAdapterPosition()).getTruckNo());
            extras.putString("customername", truckActivitiesDataModel.get(getAdapterPosition()).getCustomerName());
            extras.putString("currentcity", truckActivitiesDataModel.get(getAdapterPosition()).getCurrentCity());
            extras.putString("fromloc", truckActivitiesDataModel.get(getAdapterPosition()).getFromLoc());
            extras.putString("toloc", truckActivitiesDataModel.get(getAdapterPosition()).getToLoc());
            extras.putString("status", truckActivitiesDataModel.get(getAdapterPosition()).getStatusNow());
            extras.putDouble("speed", truckActivitiesDataModel.get(getAdapterPosition()).getSpeed());
            extras.putString("departuretime", truckActivitiesDataModel.get(getAdapterPosition()).getDepartureTime());
            intent.putExtras(extras);
            context.startActivity(intent);*/
            //((Activity) context).finish();

            Intent intent = new Intent(context, CarUpdateActivity.class);
            Bundle extras = new Bundle();

            extras.putString("carid", registerCarModelList.get(getAdapterPosition()).getId());
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
            ((Activity) context).finish();

            //  Toast.makeText(RecyclerAdapter.context, "you have clicked Row " + getAdapterPosition(), Toast.LENGTH_LONG).show();
        }
    }
}

