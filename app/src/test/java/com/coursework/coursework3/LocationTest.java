package com.coursework.coursework3;

import org.junit.Test;

import java.text.DecimalFormat;

import static org.junit.Assert.*;

/**
 * Created by user on 4/4/2017.
 */
public class LocationTest {
    @Test
    public void getZeroDistance() throws Exception {
        Location t = new Location(0.0, 0.0, "point 1", 1);
        Location f = new Location(0.0, 0.0, "point 2", 2);
        double d = f.getDistance(t);
        assertEquals(0.0, d, d - 0.0);
    }

    @Test
    public void getNonZeroDistance() throws Exception {
        Location t = new Location(51.5264420, -0.1334590, "point 1", 1);
        Location f = new Location(51.5272230, -0.1310450, "point 2", 2);
        double d = f.getDistance(t);
        DecimalFormat df = new DecimalFormat("#.##");

        assertEquals("188.23", df.format(d));
    }

}