package com.coursework.coursework3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 3/26/2017.
 * Information on Creating custom adapters from https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
 */

public class POIAdapter extends ArrayAdapter<Location> {
    public POIAdapter(Context context, ArrayList<Location> loc) {
        super(context, 0, loc);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Location loc = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item, parent, false);
        }
        // get all the views in the layout
        TextView lat = (TextView) convertView.findViewById(R.id.lat_item);
        TextView lng = (TextView) convertView.findViewById(R.id.lng_item);
        TextView d = (TextView) convertView.findViewById(R.id.loc_descr);
        ImageView img = (ImageView) convertView.findViewById(R.id.loc_img);

        // define content of the views
        lat.setText("" + loc.getCurrentLocation().get("lat"));
        lng.setText("" + loc.getCurrentLocation().get("lng"));
        d.setText(loc.getDescription());
        img.setImageResource(loc.getImageResourceId());

        // Return the completed view to render on screen
        return convertView;
    }
}
