package com.coursework.coursework3;

import java.util.HashMap;
import java.util.Map;
//import java.util.function.DoubleUnaryOperator;
//import java.math.*;
/**
 * Created by user on 3/18/2017.
 */

public class Location {

    private Map<String,Double> mCurrentLocation;

    public Location(Double lat, Double lng){
        mCurrentLocation = new HashMap<String,Double>();
        mCurrentLocation.put("lat", lat);
        mCurrentLocation.put("lng", lng);
    }

    public Map<String, Double> getCurrentLocation() {
        return mCurrentLocation;
    }

    // get distance to/from another location.
    // Takes a locatio object returns distance
    // calculation based on GCDF. (haversign Formula)
    // formula taken from www.moveable-type.co.uk/scripts/latlong.html
    public Double getDistance(Location to){

        Double lat1 = mCurrentLocation.get("lat") * (Math.PI / 180);
        Double lat2 = to.getCurrentLocation().get("lat")* (Math.PI / 180);
        Double lng1 = mCurrentLocation.get("lng")* (Math.PI / 180);
        Double lng2 = to.getCurrentLocation().get("lng")* (Math.PI / 180);
        Double dLng = lng2 - lng1;
        Double dLat = lat2 - lat1;

        Double a = Math.pow(Math.sin((dLat) / 2), 2)  + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dLng / 2),2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return c * 6371 * 1000;

        // Possible implementation of vicenty formula.
        //return (2 * Math.asin()) * 6378.137  / 1000;
        //return (6378.137 * Math.atan2(Math.sqrt(Math.pow(Math.cos(lat2) * Math.sin(lng2 - lng1),2) + Math.pow(Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(lng2 - lng1),2)) / (Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2)* Math.cos(lng2 - lng1)),0))/1000;
    }

}
