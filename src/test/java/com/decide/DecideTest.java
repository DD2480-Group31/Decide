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
    //Test the boundaries of LIC10
    public void LIC10TestBounds() {
        boolean res = DEFAULT.LIC10(4, null, null, 3, 3, 2);
        assertFalse("LIC10 should return false when number of points is less than 5", res);
        res = DEFAULT.LIC10(5, null, null, 0, 3, 2);
        assertFalse("LIC10 should return false when e_pts is less than 5", res);
        res = DEFAULT.LIC10(5, null, null, 2, 0, 2);
        assertFalse("LIC10 should return false when f_pts is less than 5", res);
    }

    @Test
    public void LIC10TestPositive() {
        int e_pts = 1;
        int f_pts = 2;
        double[] x = {2.4, 7, 5.4, 5.2, 2.6, 4.5, 8.8};
        double[] y = {3.9, 3, 2, 5.9, 1.9, 0.4, 1.6};
        // Points #1, #3, and #6 form a triangle with area ~3.26
        double area = 3.25;
        boolean res = DEFAULT.LIC10(x.length, x, y, e_pts, f_pts, area);
        assertTrue(res);
    }

    @Test
    public void LIC10TestNegative() {
        int e_pts = 1;
        int f_pts = 2;
        double[] x = {2.4, 7, 5.4, 5.2, 2.6, 4.5, 8.8};
        double[] y = {3.9, 3, 2, 5.9, 1.9, 0.4, 1.6};
        // Points #1, #3, and #6 form a triangle with area ~3.26
        double area = 3.27;
        boolean res = DEFAULT.LIC10(x.length, x, y, e_pts, f_pts, area);
        assertFalse(res);
    }

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
    public void LIC1TestPositive() {
        double[] x = {7.2, 12.8, 5.6, 15.3, 8.9};
        double[] y = {6.2, 12.5, 12, 1.4, 15.5};
        // The points (5.6, 12), (15.3, 1.4), (8.9, 15.5) cannot be contained in a circle with radius 7.6 (Only two of the points).
        double r = 7.6;
        boolean res = DEFAULT.LIC1(x.length, x, y, r);
        assertTrue(res);
    }

    @Test
    public void LIC1TestNegative() {
        double[] x = {7.2, 12.8, 5.6, 15.3, 8.9};
        double[] y = {6.2, 12.5, 12, 1.4, 15.5};
        // The points (5.6, 12), (15.3, 1.4), (8.9, 15.5) should be able to be contained in a circle with radius 7.8.
        double r = 7.8;
        boolean res = DEFAULT.LIC1(x.length, x, y, r);
        assertFalse(res);
    }
}
