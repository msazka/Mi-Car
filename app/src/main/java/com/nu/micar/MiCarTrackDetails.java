package com.nu.micar;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ashfaqn on 3/29/2017.
 */

//This class will hold details about tracking details
public class MiCarTrackDetails implements Parcelable {

    private int carId;
    private String carName;
    private String trackDate;
    private String trackTime;
    private String carLocation;
    private double carLatitude;
    private double carLongitude;

    public MiCarTrackDetails(int carId, String carName, String trackDate, String trackTime, String carLocation, double carLatitude, double carLongitude) {
        this.carId = carId;
        this.carName = carName;
        this.trackDate = trackDate;
        this.trackTime = trackTime;
        this.carLocation = carLocation;
        this.carLatitude = carLatitude;
        this.carLongitude = carLongitude;
    }

    public int getCarId() {
        return carId;
    }

    public String getCarName() {
        return carName;
    }

    public String getTrackDate() {
        return trackDate;
    }

    public String getTrackTime() {
        return trackTime;
    }

    public String getCarLocation() {
        return carLocation;
    }

    public double getCarLatitude() {
        return carLatitude;
    }

    public double getCarLongitude() {
        return carLongitude;
    }

    protected MiCarTrackDetails(Parcel in) {
        carId = in.readInt();
        carName = in.readString();
        trackDate = in.readString();
        trackTime = in.readString();
        carLocation = in.readString();
        carLatitude = in.readDouble();
        carLongitude = in.readDouble();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(carId);
        dest.writeString(carName);
        dest.writeString(trackDate);
        dest.writeString(trackTime);
        dest.writeString(carLocation);
        dest.writeDouble(carLatitude);
        dest.writeDouble(carLongitude);
    }

    public static final Creator<MiCarTrackDetails> CREATOR = new Creator<MiCarTrackDetails>() {
        @Override
        public MiCarTrackDetails createFromParcel(Parcel in) {
            return new MiCarTrackDetails(in);
        }

        @Override
        public MiCarTrackDetails[] newArray(int size) {
            return new MiCarTrackDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


}
