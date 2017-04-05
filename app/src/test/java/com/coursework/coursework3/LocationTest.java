package com.coursework.coursework3;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by user on 4/4/2017.
 */
public class LocationTest {
    @Test
    public void getDistance() throws Exception {
        Location t = new Location(0.0, 0.0, "point 1", 1);
        Location f = new Location(0.0, 0.0, "point 2", 2);
        double d = f.getDistance(t);
        assertEquals(0.0, d, d - 0.0);
    }

}