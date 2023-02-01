package com.decide;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import com.decide.Decide.CONNECTORS;


public class DecideTest {

    Decide DEFAULT = new Decide(
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
         0, 0, 0, 0, 0, 0, 0, 0,
        null, null, new double[0], new double[0]
        );

    //Example: Typical test syntax
    @Test
    public void verifyNoExceptionsThrown(){
        //Just calls the main method with an epty argument
        Decide.main(new String[] {});
    }


    // Tests for the `decide` function
    
    /**
    * Tests that the decide constructor does not allow the
    * x- and y-value vectors to be of unequal length.
     */
    @Test(expected = IllegalArgumentException.class)
    public void DecideVerifyExceptionThrown(){
        boolean[] puv = new boolean[15];
        CONNECTORS[][] lcm = new CONNECTORS[15][15];
        double [] x = {0,0,0,0,0}; //5 x-values
        double [] y = {0,0,0,0};   //4 y-values
        new Decide(
            1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, lcm, puv, x, y);
        
    }

    @Test
    // Test that the `DECIDE` function returns true for a correct input.
    public void DecideTestPositive() {
        boolean[] puv = new boolean[15];
        Arrays.fill(puv, true);
        CONNECTORS[][] lcm = new CONNECTORS[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                lcm[i][j] = CONNECTORS.ANDD;
            }
        }
        double[] xs = {1, 2.3, 1.5, -1.5,  0.5,  2.5,  -1};
        double[] ys = {1, 1.2, 2.6,    2, -1.5, -0.5, 2.5};
        /**
         * LIC0: (1, 1) & (2.3, 1.2) has distance ~1.32 > (length1 = 1.3) --> True
         * LIC1: (1, 1), (2.3, 1.2), (1.5, 2.6) cannot be contained by circle with r = 0.8 --> True
         * LIC2: Angle of (1, 1), (2.3, 1.2), (1.5, 2.6) is ~1.2 < PI - PI/4 --> True
         * LIC3: Area of (1, 1), (2.3, 1.2), (1.5, 2.6) is 0.99 > 0.95 --> True
         * LIC4: The points 3, 4, 5 are in 3 different quadrants, 3 > 2 --> True
         * LIC5: The second and third points have decreasing x-values --> True
         * LIC6: The fourth point is farthest away from the line of the first five points with a distance ~2.65 > 2.6 --> True
         * LIC7: The first and fourth points have a distance of ~2.69 > 1.3 --> True
         * LIC8: The first, third, and fifth points cannot be contained in a circle with r = 0.8 --> True
         * LIC9: The first, third, and fifth points produce an angle of ~0.06 < PI - epsilon --> True
         * LIC10: The area of the first, third, and sixth points is ~1.575 > 0.95 --> True
         * LIC11: (1, 1) and (-1.5, 2) are separated by 2 intervening points and -1.5 - 1 = -2.5 < 0 --> True 
         * LIC12: The first and fourth points have a distance of ~2.69 > 1.3. The fourth and seventh points have a distance of ~0.7 < 0.8.
         * LIC13: None of the available set of three points fit in a circle with r = 0.8. The first, third, and fifth points fit in a circle with r = 2.2.
         * LIC14: All produced triangle have an area greater than 0.95. The area of the first, fourth, and sixth points is 1.125 < 1.2
         */
        Decide dec = new Decide(
            1.3, 0.8, Math.PI / 4, 0.95, 
            3, 2, 2.6, 5, 2, 1, 1, 
            1, 1, 1, 2, 2, 0.8, 2.2, 1.2, 
            lcm, puv, xs, ys);

        assertTrue("The variable assignment should return 'true' for all LICs and launch.", dec.DECIDE());
    }

    @Test
    // If we take the same setup as the positive test, but change `length2` such that `LIC12` returns false, `DECIDE` should return false.
    public void DecideTestNegative() {
        boolean[] puv = new boolean[15];
        Arrays.fill(puv, true);
        CONNECTORS[][] lcm = new CONNECTORS[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                lcm[i][j] = CONNECTORS.ANDD;
            }
        }
        double[] xs = {1, 2.3, 1.5, -1.5,  0.5,  2.5,  -1};
        double[] ys = {1, 1.2, 2.6,    2, -1.5, -0.5, 2.5};
        /**
         * LIC12: The first and fourth points have a distance of ~2.69 > 1.3. The fourth and seventh points have a distance of ~0.7 > 0.6.
         */
        Decide dec = new Decide(
            1.3, 0.8, Math.PI / 4, 0.95, 
            3, 2, 2.6, 5, 2, 1, 1, 
            1, 1, 1, 2, 2, 0.6, 2.2, 1.2, 
            lcm, puv, xs, ys);

        assertFalse("The variable assignment should return 'false' for LIC12 and not launch.", dec.DECIDE());
    }
    
    @Test
    // When using the same setup as the negative test, but omits `LIC12` in the `PUV`, `DECIDE` should return true again since we don't use that LIC.
    public void DecidePUVCorrectlyExcludesLIC() {
        boolean[] puv = new boolean[15];
        Arrays.fill(puv, true);
        CONNECTORS[][] lcm = new CONNECTORS[15][15];
        // Initialize the LCM to a diagonal matrix so that each row is only represented by its own LIC.
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (i == j) {
                    lcm[i][j] = CONNECTORS.ANDD;
                } else {
                    lcm[i][j] = CONNECTORS.NOTUSED;
                }
                
            }
        }
        puv[12] = false; // Ignore the results from `LIC12`!!

        double[] xs = {1, 2.3, 1.5, -1.5,  0.5,  2.5,  -1};
        double[] ys = {1, 1.2, 2.6,    2, -1.5, -0.5, 2.5};
        /**
         * LIC12: The first and fourth points have a distance of ~2.69 > 1.3. The fourth and seventh points have a distance of ~0.7 > 0.6.
         */
        Decide dec = new Decide(
            1.3, 0.8, Math.PI / 4, 0.95, 
            3, 2, 2.6, 5, 2, 1, 1, 
            1, 1, 1, 2, 2, 0.6, 2.2, 1.2, 
            lcm, puv, xs, ys);

        assertTrue("`DECIDE` should ignore the result of `LIC12`!", dec.DECIDE());
    }

    @Test
    // When all elements in the `PUV` are set to false, `DECIDE` should always return true. 
    public void DecideTestFalsePUV() {
        boolean[] puv = new boolean[15];
        CONNECTORS[][] lcm = new CONNECTORS[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                lcm[i][j] = CONNECTORS.ANDD;
            }
        }
        Decide dec = new Decide(
            0, 0, 0, 0, 0, 0, 
            0, 0, 0, 0, 0, 0, 0, 0, 
            0, 0, 0, 0, 0, lcm, puv, new double[1], new double[1]);
        assertTrue(dec.DECIDE());
    }

    @Test
    // When all elements in the `LCM` are set to NOTUSED, `DECIDE` should always return true. 
    public void DecideTestFalseLCM() {
        boolean[] puv = new boolean[15];
        Arrays.fill(puv, true);
        CONNECTORS[][] lcm = new CONNECTORS[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                lcm[i][j] = CONNECTORS.NOTUSED;
            }
        }
        Decide dec = new Decide(
            0, 0, 0, 0, 0, 0, 
            0, 0, 0, 0, 0, 0, 0, 0, 
            0, 0, 0, 0, 0, lcm, puv, new double[1], new double[1]);
        assertTrue(dec.DECIDE());
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
    // Test that LIC10 finds a triangle with area greater than area1 when such can be found.
    public void LIC10TestPositive() {
        int e_pts = 1;
        int f_pts = 2;
        double[] x = {2.4, 7, 5.4, 5.2, 2.6, 4.5, 8.8};
        double[] y = {3.9, 3, 2, 5.9, 1.9, 0.4, 1.6};
        // The first, third, and sixth points form a triangle with area ~3.26
        double area = 3.25;
        boolean res = DEFAULT.LIC10(x.length, x, y, e_pts, f_pts, area);
        assertTrue(res);
    }

    @Test
    // Test that LIC10 doesn't find a triangle with area greater than area1 when such cannot be found.
    public void LIC10TestNegative() {
        int e_pts = 1;
        int f_pts = 2;
        double[] x = {2.4, 7, 5.4, 5.2, 2.6, 4.5, 8.8};
        double[] y = {3.9, 3, 2, 5.9, 1.9, 0.4, 1.6};
        // The first, third, and sixth points form a triangle with area ~3.26
        double area = 3.27;
        boolean res = DEFAULT.LIC10(x.length, x, y, e_pts, f_pts, area);
        assertFalse(res);
    }

    @Test
    //Test that LIC6 returns false when called with invalid arguments.
    public void LIC6TestBoundaries() {
        boolean res = DEFAULT.LIC6(10, null, null, 11, 0);
        assertFalse("LIC6 should return false when n_pts > numPoints", res);
        res = DEFAULT.LIC6(10, null, null, 2, 0);
        res &= DEFAULT.LIC6(10, null, null, -2, 0);
        assertFalse("LIC6 should return false when n_pts < 3", res);
        res = DEFAULT.LIC6(10, null, null, 5, -1);
        assertFalse("LIC6 should return false when dist < 0", res);
    }

    @Test
    // Test that LIC6 correctly returns true when the critera is met in the datapoints.
    public void LIC6TestPositive() {
        // The distance from [9.4, 10.3] (point #4) to the line [9.1, 2.1]-[13.9, 4.6] (#2-#6) is ~7.13
        double[] x = {4.8, 9.1, 3.6, 9.4, 10.5, 13.9};
        double[] y = {11.1, 2.1, 4.7, 10.3, 5.3, 4.6};
        double dist = 7.1;
        boolean res = DEFAULT.LIC6(x.length, x, y, 5, dist);
        assertTrue(res);
    }

    @Test
    // Test that LIC6 correctly returns false when the critera is not met in the datapoints.
    public void LIC6TestNegative() {
        // The distance from [9.4, 10.3] (point #4) to the line [9.1, 2.1]-[13.9, 4.6] (#2-#6) is ~7.13
        double[] x = {4.8, 9.1, 3.6, 9.4, 10.5, 13.9};
        double[] y = {11.1, 2.1, 4.7, 10.3, 5.3, 4.6};
        double dist = 7.2;
        boolean res = DEFAULT.LIC6(x.length, x, y, 5, dist);
        assertFalse(res);
    }

    @Test
    // Test that the function correctly determines that three points can be contained in a circle.
    public void containedInCirclePositive() {
        double[][] points = {{1.5, 0.5}, {1, 4}, {3.5, 2.5}}; // These points form a circle with radius ~1.822
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
        double[][] points = {{1.5, 0.5}, {1, 4}, {3.5, 2.5}}; // These points form a circle with radius ~1.822
        double radius = 1.8;
        boolean res = DEFAULT.containedInCircle(points, radius);
        assertFalse(res);
    }

    @Test
    /**
     * Test that LIC13 correctly returns true when there are three points who 
     * cannot be contained in a circle of radius `radius1` and three other points
     * that can be contained in a circle of radius `radius2`.
     */
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
    // Test that LIC13 correctly returns false when the criteras are not met in the datapoints.
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
    /**
     * Requirements: See `LIC0` documentation
     * Contract:
     *      Precondition:   Two consecutive points are more than a distance `length1` apart.
     *      Postcondition:  `LIC0` returns true
     */
    public void LIC0TestPositive() {
        double[] x = {2.5, 3.3, 6.6, 5.5, 5.1};
        double[] y = {1.4, 4.4, 2.7, 1.2, 5};
        // The most far apart consecutive points are (5.5, 1.2) and (5.1, 5) with a distance of ~3.82
        double length1 = 3.82;
        boolean res = DEFAULT.LIC0(5, x, y, length1);
        assertTrue(res);
    }

    @Test
    /**
     * Requirements: See `LIC` documentation
     * Contract:
     *      Precondition:   Two consecutive points are less than (or equal to) a distance `length1` apart.
     *      Postcondition:  `LIC0` returns false
     */
    public void LIC0TestNegative() {
        double[] x = {2.5, 3.3, 6.6, 5.5, 5.1};
        double[] y = {1.4, 4.4, 2.7, 1.2, 5};
        // The most far apart consecutive points are (5.5, 1.2) and (5.1, 5) with a distance of ~3.82
        double length1 = 3.83;
        boolean res = DEFAULT.LIC0(5, x, y, length1);
        assertFalse(res);
    }

    @Test
    /**Tests false boundary values for LIC8 where the number of points
     * can not be less than 5 and a_pts and b_pts can not be lower than 1.
    */
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
    /**
     * Tests that LIC8 does find three points each separated by
     * one point that can all be contained inside a circle of radius 5.
     */
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
    /**
     * Tests that LIC8 does not find three points each separated by
     * one point that can all be contained inside a circle of radius 2.
     */
    public void LIC8TestNotInCircle(){
        double[] x = {1, 2, 3, 4, 5, 6, 7, 8};
        double[] y = {1, 2, 3, 4, 5, 6, 7, 8};
        double r1 = 2;
        //Should find three points in a circle of radius 5
        //a_pts = 1, b_pts = 1 --> startP * midP * endP --> 5 total points
        boolean res = DEFAULT.LIC8(x.length, x, y, 1, 1, r1);

        assertTrue("Should not find three points in a circle of radius 2 with 5 points", res);
    }


    @Test
    /**Tests false boundary values for LIC8 where the number of points
     * can not be less than 5 and a_pts and b_pts can not be lower than 1.
    */
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
    /**
     * Tests LIC9 for an orthogonal angle to be less than PI as it should
     * find the orthogonal angle between (2, 1) (3, 1) and (3, 2) with
     * (3, 1) being the vertex.
     */
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
    /**
     * Tests LIC9 for coinciding points with the vertex where no angle should be found.
     * All points lie on the x-axis and one coincides with the vertex.
     */
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

    @Test
    /**
     * Test that LIC1 correctly returns true when there exists three 
     * consecutive points that cannot be contained in a circle with radius `radius1`.
     */
    public void LIC1TestPositive() {
        double[] x = {7.2, 12.8, 5.6, 15.3, 8.9};
        double[] y = {6.2, 12.5, 12, 1.4, 15.5};
        // The points (5.6, 12), (15.3, 1.4), (8.9, 15.5) cannot be contained in a circle with radius 7.6 (Only two of the points).
        double r = 7.6;
        boolean res = DEFAULT.LIC1(x.length, x, y, r);
        assertTrue(res);
    }

    @Test
    /**
     * Test that LIC1 correctly returns false when there doesn't exists three 
     * consecutive points that cannot be contained in a circle with radius `radius1`.
     */
    public void LIC1TestNegative() {
        double[] x = {7.2, 12.8, 5.6, 15.3, 8.9};
        double[] y = {6.2, 12.5, 12, 1.4, 15.5};
        // The points (5.6, 12), (15.3, 1.4), (8.9, 15.5) should be able to be contained in a circle with radius 7.8.
        double r = 7.8;
        boolean res = DEFAULT.LIC1(x.length, x, y, r);
        assertFalse(res);
    }

    @Test
    public void LIC4_testTrue(){
        /*
        * One in each quadrant should return true
        */
        double[] X = {0, -2, 3, -3, 3, -2};
        double[] Y = {0, 1, 2, -2, -3, 7};
        boolean res = DEFAULT.LIC4(X.length, X, Y, 3, 2);
        assertTrue("One in each quadrant should return true", res);
    }

    @Test
    public void LIC4_testFalse(){
        double[] X = {0, -1, 2, 1, 2, 9};
        double[] Y = {0, 0, 1, 1, 3, 7};
        /*
        * There are never more than 2 quads per 3 consecutive elements
        */
        boolean res = DEFAULT.LIC4(X.length, X, Y, 3, 2);
        assertFalse("There are never more than 2 quads per 3 consecutive elements", res);
    }

    @Test
    public void LIC4_testInputBounds(){
        double[] X = {0, 0, 2, 1, 2, 9};
        double[] Y = {0, 0, 1, 1, 3, 7};
        /*
        * q_pts can not be <= number of quadrants
        */
        boolean res = DEFAULT.LIC4(X.length, X, Y, 2, 2);
        assertFalse("q_pts can not be <= number of quadrants", res);
        /*
        * quads < 1 should return false
        */
        res = DEFAULT.LIC4(X.length, X, Y, 2, 0);
        assertFalse("quads < 1 should return false", res);
        /*
        * q_pts > NumPoints should return false
        */
        res = DEFAULT.LIC4(X.length, X, Y, 7, 2);
        assertFalse("q_pts > NumPoints should return false", res);
    }

    @Test
    public void LIC7TestBoundaries() {
        // Test boundaries for the number of points.
        assertFalse(DEFAULT.LIC7( 2, null, null, 0, 0.0));
        assertFalse(DEFAULT.LIC7( 0, null, null, 0, 0.0));
        assertFalse(DEFAULT.LIC7(-5, null, null, 0, 0.0));
        // Test boundaries for the separation value.
        assertFalse(DEFAULT.LIC7(3, null, null,  0, 0.0));
        assertFalse(DEFAULT.LIC7(3, null, null, -2, 0.0));
        assertFalse(DEFAULT.LIC7(3, null, null,  3, 0.0));
        // Test boundaries for the length value.
        assertFalse(DEFAULT.LIC7(3, null, null, 1, -2.00));
        assertFalse(DEFAULT.LIC7(3, null, null, 1, -1e-5));
    }

    @Test
    public void LIC7TestPositive() {
        // Test positive outcome with positive distance.
        double[] x0 = {1.0, 2.0, 3.0};
        double[] y0 = {1.0, 1.0, 1.0};
        assertTrue(DEFAULT.LIC7(3, x0, y0, 1, 1.9));
        // Test positive outcome with negative distance.
        double[] x1 = {3.0, 2.0, 1.0};
        double[] y1 = {1.0, 1.0, 1.0};
        assertTrue(DEFAULT.LIC7(3, x1, y1, 1, 1.9));
    }

    @Test
    public void LIC7TestNegative() {
        // Test negative outcome with too large length.
        double[] x = {-1.5, -1.5,  1.5, 1.5};
        double[] y = {-1.5,  1.5, -1.5, 1.5};
        assertFalse(DEFAULT.LIC7(4, x, y, 1, 5.0));
    }

    @Test
    public void LIC14TestBoundaries() {
        // Test boundaries for the number of points.
        assertFalse(DEFAULT.LIC14(4, null, null, 0, 0, 0, 0));
        assertFalse(DEFAULT.LIC14(0, null, null, 0, 0, 0, 0));
        // Test boundaries for the separation values.
        assertFalse(DEFAULT.LIC14(5, null, null, 0, 2, 0, 0));
        assertFalse(DEFAULT.LIC14(5, null, null, 2, 0, 0, 0));
        assertFalse(DEFAULT.LIC14(5, null, null, 2, 2, 0, 0));
        // Test boundaries for the area values.
        assertFalse(DEFAULT.LIC14(5, null, null, 1, 1, -1e-5, 0));
        assertFalse(DEFAULT.LIC14(5, null, null, 1, 1, 0, -1e-5));
    }

    @Test
    public void LIC14TestPositive() {
        // Test positive outcome with negative point area.
        double[] x = {2, 2, 0, 0, 1, 0, 0, 3, 1};
        double[] y = {2, 0, 0, 2, 1, 3, 0, 0, 2};
        assertTrue(DEFAULT.LIC14(9, x, y, 2, 1, 4.4, 0.6));
    }

    @Test
    public void LIC14TestNegative() {
        double[] x = {0, 1, 3, 0, 2, 3};
        double[] y = {0, 1, 0, 3, 2, 3};
        // Test negative outcome with too large area1.
        assertFalse(DEFAULT.LIC14(6, x, y, 2, 1, 8.0, 5.0));
        // Test negative outcome with too small area2.
        assertFalse(DEFAULT.LIC14(6, x, y, 2, 1, 4.0, 2.5));
    }


    @Test
    /**
     * Tests LIC12 for false boundary points where the number of points 
     * can not be lower than 3, k_pts has to be positive, and both length1
     * and length2 has to be positive.
     */
    public void LIC12TestFalseBoundaries(){
        double[] x = {7.2, 12.8, 5.6, 15.5, 15.3, 12.1, 19.6, 8.9};
        double[] y = {6.2, 12.5, 12, 6.3, 1.4, 6.4, 13.1, 15.5};

        boolean res = DEFAULT.LIC12(2, x, y, 1, 1, 1);
        assertFalse("Should return false with less than 3 points", res);

        res = DEFAULT.LIC12(x.length, x, y, -1, 1, 1);
        assertFalse("Should return false with k_pts < 0", res);

        res = DEFAULT.LIC12(x.length, x, y, 1, -1, 1);
        assertFalse("Should return false with length1 < 0", res);

        res = DEFAULT.LIC12(x.length, x, y, 2, 1, -1);
        assertFalse("Should return false when length2 < 0", res);
    }


    @Test
    /**
     * Tests LIC12 to find one pair of points that are separated by two points
     * in the array with a distance greater than 1 while also finding a pair of points
     * that are again separated by two points in the array and have a dinstance between
     * them of 1.
     */
    public void LIC12TestTwoPointPairs(){
        // k_pst = 2, separated by two pts

        //Find the pair with dist > (length1 = 1)
        //Find the pair with dist < (length2 = 1) --> ()
        //(1,1), (2, 1), (2, 1), (3, 1) (4,1)     Points
        double[] x = {1, 2, 3, 4, 2};
        double[] y = {1, 1, 1, 1, 1};

        boolean res = DEFAULT.LIC12(x.length, x, y, 2, 1, 1);

        assertTrue("Should find two pairs with 2 points between with a distance greater than 1 and one less than 1.", res);

    }

    @Test
    /**
     * Tests LIC12 to not find two pairs of points when the second one
     * requires a distance of 3 between hte points when being separated by
     * 2 points in the array as there is not enough points for this.
     */
    public void LIC12TestNegativeTwoPointPairs(){
        // k_pst = 2, separated by two pts
        double[] x = {1, 2, 3, 4, 5};
        double[] y = {1, 1, 1, 1, 1};

        boolean res = DEFAULT.LIC12(x.length, x, y, 2, 1, 3);

        assertFalse("Should not find two pairs with the second pair having a dist > 3.", res);

    }

    @Test
    public void LIC5TestFalseBoundaries(){
        //(1,1) (2, 1) (3, 1) (4,1), (5, 1)     Points
        double[] x = {1, 1, 1};
        double[] y = {1, 1, 1};

        boolean res = DEFAULT.LIC5(x.length, x, y);

        assertFalse("Should not find two points with (xj - xi) < 0 with the same point", res);

        double[] x1 = {0, 1, 5};
        double[] y1 = {1, 1, 1};
        res = DEFAULT.LIC5(x1.length, x1, y1);
        assertFalse("Should not find two points with (xj - xi) < 0 with increasing consecutive x-values", res);
    }

    @Test
    public void LIC5TestDecreasingX(){
        double[] x = {3, 2, 1};
        double[] y = {1, 1, 1};

        boolean res = DEFAULT.LIC5(x.length, x, y);

        assertTrue("Should find two points with (xj - xi) < 0 with only decreasing x-values", res);
    }

    @Test
    public void LIC5TestDecreasingY(){
        double[] x = {1, 1, 1};
        double[] y = {3, 2, 1};

        boolean res = DEFAULT.LIC5(x.length, x, y);

        assertFalse("Should not find two points with (xj - xi) < 0 with only decreasing y-values", res);
    }

    @Test
    public void LIC5TestConsecutiveXY(){
        double[] x = {3, 2, 1};
        double[] y = {3, 2, 1};

        boolean res = DEFAULT.LIC5(x.length, x, y);

        assertTrue("Should find two points with (xj - xi) < 0 with both decreasing x- and y-values", res);
    }


    @Test
    public void LIC11_testInputBounds(){
        double[] X = {0, -2, 3, -3, 3, -2};
        double[] Y = {0, 1, 2, -2, -3, 7};
        /*
        * g_pts < 1 should return false
        */
        boolean res = DEFAULT.LIC11(X.length, X, Y, 0);
        assertFalse("g_pts < 1 should return false", res);
    }

    @Test
    public void LIC11_testTrue(){
        double[] X = {0, -2, 3, -3, 3, -2};
        double[] Y = {0, 1, 2, -2, -3, 7};
        /*
        * At least X[3](with val -3) and X[0](with val 0) should return true
        */
        boolean res = DEFAULT.LIC11(X.length, X, Y, 2);
        assertTrue("At least X[3](with val -3) and X[0](with val 0) should return true", res);
    }

    @Test
    public void LIC11_testFalse(){
        double[] X = {1, 2, 3, 3, 4, 6};
        double[] Y = {0, 1, 2, -2, -3, 7};
        /*
        * Condition is never met, should return false
        */
        boolean res = DEFAULT.LIC11(X.length, X, Y, 2);
        assertFalse("Condition is never met, should return false", res);
    }

    @Test
    public void LIC2_testBounds(){
        double[] X = {0, 1, 1, 1, 1, 3};
        double[] Y = {0, 0, 0, 1, 1, 3};
        /*
        * X[1]Y[1] is equal to X[2]Y[2] and X[3]Y[3] is equal to X[4]Y[4] which should give false
        */
        boolean res = DEFAULT.LIC2(X.length, X, Y, Math.PI/2);
        assertFalse("X[1]Y[1] is equal to X[2]Y[2] and X[3]Y[3] is equal to X[4]Y[4] which should give false", res);
        /*
        * Epsilon < 0 which should give false
        */
        res = DEFAULT.LIC2(X.length, X, Y, -1);
        assertFalse("Epsilon < 0 which should give false", res);
        /*
        * Epsilon >= PI which should give false
        */
        res = DEFAULT.LIC2(X.length, X, Y, Math.PI * 1.1);
        assertFalse("Epsilon >= PI which should give false", res);
    }

    @Test
    public void LIC2_testTrue(){
        double[] X = {1, 1, 3, 3, 4, 6};
        double[] Y = {1, 1, 1, -2, -3, 7};
        /*
        * Should be true, simple test
        */
        boolean res = DEFAULT.LIC2(X.length, X, Y, Math.PI/2);
        assertTrue("Should be true", res);
        /*
        * Should be true, points tested with calculator
        */
        double[] X1 = {1.9, 1.9, 1.95, 0.0};
        double[] Y1 = {4.0, 2.9, 4.0, 0.0};

        res = DEFAULT.LIC2(X.length, X1, Y1, Math.PI - Math.PI/36);
        assertTrue("Should be True", res);
    }

    @Test
    public void LIC2_testFalse(){
        double[] X = {1.9, 1.9, 2.0};
        double[] Y = {4.0, 3.0, 4.0};
        /*
        * Should be false, points tested with calculator
        */
        boolean res = DEFAULT.LIC2(X.length, X, Y, Math.PI - Math.PI/36);
        assertFalse("Should be false", res);
    }

    @Test
    /**
     * Test that LIC3 correctly returns false when the area is set to zero.
     * This tests that the area-calculation correctly handles all possible orders of points.
     * If it fails, it's probably missing an absolute value in the formula.
     */
    public void LIC3TestZeroArea() {
        double[] xs = {1, 2, 3};
        double[] ys = {1, 2, 1};
        int[][] orders = {{0, 1, 2}, {1, 2, 0}, {2, 0, 1}, {0, 2, 1}};
        for (int[] order : orders) {
            double[] x = {xs[order[0]], xs[order[1]], xs[order[2]]};
            double[] y = {ys[order[0]], ys[order[1]], ys[order[2]]};
            var res = DEFAULT.LIC3(3, x, y, 0);
            assertTrue("LIC3 should always return true when area1 = 0", res);
        }
    }

    @Test
    /**
     * Test that LIC3 correctly return true when there exists three consecutive 
     * points that form a triangle with area greater than `area1`.
     */
    public void LIC3TestPositive() {
        double[] x = {4, 0.5, 2, 2.3};
        double[] y = {1.5, 3.5, 1, 3.5};
        var res = DEFAULT.LIC3(4, x, y, 2.8);
        assertTrue("The area of (4, 1.5), (0.5, 3.5), (2, 1) is 2.875 which is greater than 2.8", res);
    }

    @Test
    /**
     * Test that LIC3 correctly return true when there doesn't exists three 
     * consecutive points that form a triangle with area greater than `area1`.
     */
    public void LIC3TestNegative() {
        double[] x = {4, 0.5, 2, 2.3};
        double[] y = {1.5, 3.5, 1, 3.5};
        var res = DEFAULT.LIC3(4, x, y, 2.9);
        assertFalse("The area of (4, 1.5), (0.5, 3.5), (2, 1) is 2.875 which is less than 2.9", res);
    }
}
