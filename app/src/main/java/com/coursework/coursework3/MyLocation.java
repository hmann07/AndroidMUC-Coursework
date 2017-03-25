package com.coursework.coursework3;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;

/**
 * Created by user on 3/18/2017.
 */

public class MyLocation {

    private Map<String,Double> mCurrentLocation;

    public MyLocation(Double lat, Double lng){
        mCurrentLocation = new HashMap<String,Double>();
        mCurrentLocation.put("lat", lat);
        mCurrentLocation.put("lng", lng);
    }

    public Map<String, Double> getCurrentLocation() {
        return mCurrentLocation;
    }

    // get distance to/from another location.
    // Takes a locatio object returns distance
    // calculation based on GCDF. (Vincenty Formula)
    public Double getDistance(MyLocation to){

        Double lat1 = mCurrentLocation.get("lat");
        Double lat2 = to.getCurrentLocation().get("lat");
        Double lng1 = mCurrentLocation.get("lng");
        Double lng2 = to.getCurrentLocation().get("lng");

        return Math.atan2(Math.sqrt(Math.pow(Math.cos(lat2) * Math.sin(lng1 - lng2),2) + Math.pow(Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(lng1 - lng2),2)) / Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2)* Math.cos(lng1-lng2),0);
    }

}
