package com.coursework.coursework3;

import android.content.Context;
import android.test.mock.MockContext;

import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * Created by user on 4/9/2017.
 */
public class POIAdapterTest {

    @Test
    public void arrayChange() throws Exception {
        Context c = new MockContext();
        ArrayList mNearby = new ArrayList<Location>();
        POIAdapter mAdapter = new POIAdapter(c, mNearby);
        Location t = new Location(51.5264420, -0.1334590, "point 1", 1, 0.0f);
        mAdapter.add(t);
        assertEquals(1,mAdapter.getCount(),1-mAdapter.getCount());
    }
    @Test

    public void clearArray() throws Exception {
        Context c = new MockContext();
        ArrayList mNearby = new ArrayList<Location>();
        POIAdapter mAdapter = new POIAdapter(c, mNearby);
        Location t = new Location(51.5264420, -0.1334590, "point 1", 1, 0.0f);
        mAdapter.add(t);
        mAdapter.clear();
        assertEquals(0,mAdapter.getCount(),0-mAdapter.getCount());
    }
}