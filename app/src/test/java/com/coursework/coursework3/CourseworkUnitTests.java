package com.coursework.coursework3;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CourseworkUnitTests {
    @Test
    public void checkDistance() throws Exception {
        Location t = new Location(0.0,0.0,"point 1", 1);
        Location f = new Location(0.0,0.0,"point 2", 2);
        double d = f.getDistance(t);
        assertEquals(0.0, d);
    }
}