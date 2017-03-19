package com.coursework.coursework3;

import java.util.HashMap;
import java.util.Map;

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


}
