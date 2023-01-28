package com.decide;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.beans.Transient;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;



public class DecideTest{

    Decide DEFAULT = new Decide(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

    //Example: Typical test syntax
    @Test
    public void verifyNoExceptionsThrown(){
        //Just calls the main method with an epty argument
        Decide.main(new String[] {});
    }

    @Test
    //Test that LIC6 returns false when called with invalid arguments.
    public void LIC6TestBoundaries() {
        boolean res = DEFAULT.LIC6(10, 11, null, null, 0);
        assertFalse("LIC6 should return false when n_pts > numPoints", res);
        res = DEFAULT.LIC6(10, 2, null, null, 0);
        res &= DEFAULT.LIC6(10, -2, null, null, 0);
        assertFalse("LIC6 should return false when n_pts < 3", res);
        res = DEFAULT.LIC6(10, 5, null, null, -1);
        assertFalse("LIC6 should return false when dist < 0", res);
    }

    @Test
    public void LIC6TestPositive() {
        // The distance from [9.4, 10.3] (point #4) to the line [9.1, 2.1]-[13.9, 4.6] (#2-#6) is ~7.13
        double[] x = {4.8, 9.1, 3.6, 9.4, 10.5, 13.9};
        double[] y = {11.1, 2.1, 4.7, 10.3, 5.3, 4.6};
        double dist = 7.1;
        boolean res = DEFAULT.LIC6(x.length, 5, x, y, dist);
        assertTrue(res);
    }

    @Test 
    public void LIC6TestNegative() {
        // The distance from [9.4, 10.3] (point #4) to the line [9.1, 2.1]-[13.9, 4.6] (#2-#6) is ~7.13
        double[] x = {4.8, 9.1, 3.6, 9.4, 10.5, 13.9};
        double[] y = {11.1, 2.1, 4.7, 10.3, 5.3, 4.6};
        double dist = 7.2;
        boolean res = DEFAULT.LIC6(x.length, 5, x, y, dist);
        assertFalse(res);
    }

    @Test
    // Test that the function correctly determines that three points can be contained in a circle.
    public void containedInCirclePositive() {
        Decide dec = new Decide(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        double[][] points = {{1.5, 0.5}, {1, 4}, {3.5, 2.5}};
        double radius = 2;
        boolean res = dec.containedInCircle(points, radius);
        assert res;
        radius = 1.83; // This should work as well
        res = dec.containedInCircle(points, radius);
        assert res;
    }

    @Test
    // Test that the function correctly determines that three points cannot be contained in a circle.
    public void containedInCircleNegative() {
        Decide dec = new Decide(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        double[][] points = {{1.5, 0.5}, {1, 4}, {3.5, 2.5}};
        double radius = 1.8;
        boolean res = dec.containedInCircle(points, radius);
        assert !res;
    }
}