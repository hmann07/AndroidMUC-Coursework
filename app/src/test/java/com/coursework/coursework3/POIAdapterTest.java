package com.coursework.coursework3;

import android.content.Context;
import android.test.mock.MockContext;

import org.junit.Test;

import java.util.ArrayList;

import static com.coursework.coursework3.R.string.test;
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
        Location t = new Location(51.5264420, -0.1334590, "point 1", 1);
        mAdapter.add(t);
        assertEquals(1,mAdapter.getCount(),1-mAdapter.getCount());
    }
}