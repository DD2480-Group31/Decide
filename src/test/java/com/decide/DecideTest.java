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
}