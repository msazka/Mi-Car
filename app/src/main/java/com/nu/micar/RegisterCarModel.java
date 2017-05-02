package com.nu.micar;

/**
 * Created by Waqar Malik on 26-Apr-17.
 */


public class RegisterCarModel {
// Response


/*{
    "success": 1,
        "message": "Car Registered Successfully.",
        "data": {
    "profile": {
        "id": "14",
                "device_id": "27002a000c51343334363138",
                "user_id": "4",
                "registration_no": "LZC-15-7411",
                "registration_city": "lahore",
                "model": "audi",
                "model_year": "2015",
                "manufacturer": "corolla",
                "engine_capacity": "1800",
                "engine_type": "vtec",
                "color": "white",
                "date_created": "0000-00-00 00:00:00",
                "date_modified": "2017-04-25 17:23:20"
    }
}*/


      String id,device_id, user_id, registration_no, registration_city, model, model_year, manufacturer, engine_capacity, engine_type, color, date_created, date_modified;


    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRegistration_no() {
        return registration_no;
    }

    public void setRegistration_no(String registration_no) {
        this.registration_no = registration_no;
    }

    public String getRegistration_city() {
        return registration_city;
    }

    public void setRegistration_city(String registration_city) {
        this.registration_city = registration_city;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModel_year() {
        return model_year;
    }

    public void setModel_year(String model_year) {
        this.model_year = model_year;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getEngine_capacity() {
        return engine_capacity;
    }

    public void setEngine_capacity(String engine_capacity) {
        this.engine_capacity = engine_capacity;
    }

    public String getEngine_type() {
        return engine_type;
    }

    public void setEngine_type(String engine_type) {
        this.engine_type = engine_type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getDate_modified() {
        return date_modified;
    }

    public void setDate_modified(String date_modified) {
        this.date_modified = date_modified;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

