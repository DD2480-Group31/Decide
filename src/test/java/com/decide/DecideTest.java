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
    // Test that the function correctly determines that three points can be contained in a circle.
    public void containedInCirclePositive() {
        double[][] points = {{1.5, 0.5}, {1, 4}, {3.5, 2.5}};
        double radius = 2;
        boolean res = DEFAULT.containedInCircle(points, radius);
        assertTrue(res);
        radius = 1.83; // This should work as well
        res = DEFAULT.containedInCircle(points, radius);
        assertTrue(res);
    }

    @Test
    // Test that the function correctly determines that three points cannot be contained in a circle.
    public void containedInCircleNegative() {
        double[][] points = {{1.5, 0.5}, {1, 4}, {3.5, 2.5}};
        double radius = 1.8;
        boolean res = DEFAULT.containedInCircle(points, radius);
        assertFalse(res);
    }

    @Test
    public void LIC13TestPositive() {
        double[] x = {7.2, 12.8, 5.6, 15.5, 15.3, 12.1, 19.6, 8.9};
        double[] y = {6.2, 12.5, 12, 6.3, 1.4, 6.4, 13.1, 15.5};
        // The points (5.6, 12), (15.3, 1.4), (8.9, 15.5) cannot be contained in a circle with radius 7.6 (Only two of the points).
        // The points (12.8, 12.5), (15.5, 6.3), (19.6, 13.1) can just about be contained in a circle with radius 4.25.
        double r1 = 7.6, r2 = 4.25;
        boolean res = DEFAULT.LIC13(x.length, x, y, 1, 2, r1, r2);
        assertTrue(res);
    }

    @Test
    public void LIC13TestNegative() {
        double[] x = {7.2, 12.8, 5.6, 15.5, 15.3, 12.1, 19.6, 8.9};
        double[] y = {6.2, 12.5, 12, 6.3, 1.4, 6.4, 13.1, 15.5};
        double r1 = 7.9, r2 = 4.25;
        // This should return false since all sets of three points can now be contained in the circle with radius 7.9.
        boolean res = DEFAULT.LIC13(x.length, x, y, 1, 2, r1, r2);
        assertFalse(res);

        r1 = 7.6; r2 = 4.15;
        // This should also return false since no set of points can be contained in the circle with radius 4.15.
        res = DEFAULT.LIC13(x.length, x, y, 1, 2, r1, r2);
        assertFalse(res);
    }

    @Test
    public void LIC8TestFalseBoundaries(){
        double[] x = {7.2, 12.8, 5.6, 15.5, 15.3, 12.1, 19.6, 8.9};
        double[] y = {6.2, 12.5, 12, 6.3, 1.4, 6.4, 13.1, 15.5};
        double r1 = 7.9;
        boolean res = DEFAULT.LIC8(4, x, y, 1, 1, r1);
        assertFalse("Should return false with less than 5 points", res);

        res = DEFAULT.LIC8(x.length, x, y, 0, 1, r1);
        assertFalse("Should return false with a_pts < 1", res);

        res = DEFAULT.LIC8(x.length, x, y, 1, 0, r1);
        assertFalse("Should return false with b_pts < 1", res);
    }

    @Test
    public void LIC8TestInCircle(){
        double[] x = {1, 2, 3, 4, 5, 6, 7, 8};
        double[] y = {1, 2, 3, 4, 5, 6, 7, 8};
        double r1 = 5;
        //Should find three points in a circle of radius 5
        //a_pts = 1, b_pts = 1 --> startP * midP * endP
        boolean res = DEFAULT.LIC8(x.length, x, y, 1, 1, r1);

        assertFalse("Should find three points in a circle of radius 5", res);

    }

    @Test
    public void LIC8TestNotInCircle(){
        double[] x = {1, 2, 3, 4, 5, 6, 7, 8};
        double[] y = {1, 2, 3, 4, 5, 6, 7, 8};
        double r1 = 4;
        //Should find three points in a circle of radius 5
        //a_pts = 1, b_pts = 1 --> startP * midP * endP --> 5 total points
        boolean res = DEFAULT.LIC8(x.length, x, y, 1, 1, r1);

        assertTrue("Should not find three points in a circle of radius 4 with 5 points", res);

    }


    @Test
    public void LIC9TestFalseBoundaries(){
        double[] x = {7.2, 12.8, 5.6, 15.5, 15.3, 12.1, 19.6, 8.9};
        double[] y = {6.2, 12.5, 12, 6.3, 1.4, 6.4, 13.1, 15.5};
        double r1 = 7.9;
        boolean res = DEFAULT.LIC9(4, x, y, 1, 1, r1);
        assertFalse("Should return false with less than 5 points", res);

        res = DEFAULT.LIC9(x.length, x, y, 0, 1, r1);
        assertFalse("Should return false with a_pts < 1", res);

        res = DEFAULT.LIC9(x.length, x, y, 1, 0, r1);
        assertFalse("Should return false with b_pts < 1", res);

        res = DEFAULT.LIC9(x.length, x, y, 2, 2, r1);
        assertFalse("Should return false when c_pts+d_pts <= NumPoints-3", res);
    }


    @Test
    public void LIC9TestOrthogonalAngle(){
        //   
        //             (3, 2)
        //(1,1) (2, 1) (3, 1) (4,1)
        double[] x = {1, 2, 3, 4, 3};
        double[] y = {1, 1, 1, 1, 2};
        double epsilon = 0;

        //Angle between (1,1) and (2,2) from (2,1) should be pi/2 --> return true
        boolean res = DEFAULT.LIC9(x.length, x, y, 1, 1, epsilon);

        assertTrue("Should find three points with an orthogonal angle", res);

    }

    @Test
    public void LIC9TestNoAngle(){          
        //(1,1) (2, 1) (3, 1) (4,1)
        double[] x = {1, 2, 3, 4, 3};
        double[] y = {1, 1, 1, 1, 1};
        double epsilon = 0;

        //Angle between (1,1) and (2,1) from (2,1) 
        //point 3 coincide with vertex(point 2) --> return false
        boolean res = DEFAULT.LIC9(x.length, x, y, 1, 1, epsilon);

        assertFalse("Should not find an angle as the points coincide with the vertex", res);

    }


}