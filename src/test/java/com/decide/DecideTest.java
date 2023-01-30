package com.decide;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.beans.Transient;
import java.util.stream.Stream;

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
    public void LIC0TestPositive() {
        double[] x = {2.5, 3.3, 6.6, 5.5, 5.1};
        double[] y = {1.4, 4.4, 2.7, 1.2, 5};
        // The most far apart consecutive points are (5.5, 1.2) and (5.1, 5) with a distance of ~3.82
        double d = 3.82;
        boolean res = DEFAULT.LIC0(5, x, y, d);
    }
    
    @Test
    public void LIC0TestNegative() {
        double[] x = {2.5, 3.3, 6.6, 5.5, 5.1};
        double[] y = {1.4, 4.4, 2.7, 1.2, 5};
        // The most far apart consecutive points are (5.5, 1.2) and (5.1, 5) with a distance of ~3.82
        double d = 3.83;
        boolean res = DEFAULT.LIC0(5, x, y, d);
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
        double r1 = 2;
        //Should find three points in a circle of radius 5
        //a_pts = 1, b_pts = 1 --> startP * midP * endP --> 5 total points
        boolean res = DEFAULT.LIC8(x.length, x, y, 1, 1, r1);

        assertTrue("Should not find three points in a circle of radius 2 with 5 points", res);

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

    @Test
    public void LIC4_testTrue(){
        double[] X = {0, -2, 3, -3, 3, -2};
        double[] Y = {0, 1, 2, -2, -3, 7};    
        boolean res = DEFAULT.LIC4(X.length, X, Y, 3, 2);
        assertTrue("One in each quadrant should return true", res);
    }

    @Test
    public void LIC4_testFalse(){
        double[] X = {0, -1, 2, 1, 2, 9};
        double[] Y = {0, 0, 1, 1, 3, 7};    
        
        boolean res = DEFAULT.LIC4(X.length, X, Y, 3, 2);
        assertFalse("There are never more than 2 quads per 3 consecutive elements", res);
    }

    @Test
    public void LIC4_testInputBounds(){
        double[] X = {0, 0, 2, 1, 2, 9};
        double[] Y = {0, 0, 1, 1, 3, 7};    
        
        boolean res = DEFAULT.LIC4(X.length, X, Y, 2, 2);
        assertFalse("q_pts can not be <= number of quadrants", res);

        res = DEFAULT.LIC4(X.length, X, Y, 2, 0);
        assertFalse("Quads < 1 should return false", res);

        res = DEFAULT.LIC4(X.length, X, Y, 7, 2);
        assertFalse("q_pts > NumPoints should return false", res);
    }

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
    public void LIC12TestTwoPointPairs(){
        // k_pst = 2, separated by two pts

        //  1      -      -     1       -       Find the pair with dist > (length1 = 1)
        //  -      2      -     -       2       Find the pair with dist > (length2 = 1)
        //(1,1) (2, 1) (3, 1) (4,1), (5, 1)     Points
        double[] x = {1, 2, 3, 4, 5};
        double[] y = {1, 1, 1, 1, 1};

        boolean res = DEFAULT.LIC12(x.length, x, y, 2, 1, 1);

        assertTrue("Should find two pairs with 2 points between with a distance greater than 1.", res);

    }

    @Test
    public void LIC12TestNegativeTwoPointPairs(){
        // k_pst = 2, separated by two pts

        //  1      -      -     1       -       Find the pair with dist > (length1 = 1)
        //  -      2      -     -       2       Not find the pair with dist > (length2 = 3)
        //(1,1) (2, 1) (3, 1) (4,1), (5, 1)     Points
        double[] x = {1, 2, 3, 4, 5};
        double[] y = {1, 1, 1, 1, 1};

        boolean res = DEFAULT.LIC12(x.length, x, y, 2, 1, 3);

        assertFalse("Should not find two pairs with the second pair having a dist > 3.", res);

    }

    @Test
    public void LIC11_testInputBounds(){
        double[] X = {0, -2, 3, -3, 3, -2};
        double[] Y = {0, 1, 2, -2, -3, 7};    
        
        boolean res = DEFAULT.LIC11(X.length, X, Y, 0);
        assertFalse("g_pts < 1 should return false", res);
    }

    @Test
    public void LIC11_testTrue(){
        double[] X = {0, -2, 3, -3, 3, -2};
        double[] Y = {0, 1, 2, -2, -3, 7};    
        
        boolean res = DEFAULT.LIC11(X.length, X, Y, 2);
        assertTrue("At least X[3](with val -3) and X[0](with val 0) should return true", res);
    }

    @Test
    public void LIC11_testFalse(){
        double[] X = {1, 2, 3, 3, 4, 6};
        double[] Y = {0, 1, 2, -2, -3, 7};    
        
        boolean res = DEFAULT.LIC11(X.length, X, Y, 2);
        assertFalse("Condition is never met, should return false", res);
    }

    @Test
    public void LIC2_testBounds(){
        double[] X = {0, 1, 1, 1, 1, 3};
        double[] Y = {0, 0, 0, 1, 1, 3};    
        
        boolean res = DEFAULT.LIC2(X.length, X, Y, Math.PI/2);
        assertFalse("X[1]Y[1] is equal to X[2]Y[2] and X[3]Y[3] is equal to X[4]Y[4] which should give false", res);

        res = DEFAULT.LIC2(X.length, X, Y, -1);
        assertFalse("Epsilon < 0 which should give false", res);

        res = DEFAULT.LIC2(X.length, X, Y, Math.PI * 1.1);
        assertFalse("Epsilon >= PI which should give false", res);
    }

    @Test
    public void LIC2_testTrue(){
        double[] X = {1, 1, 3, 3, 4, 6};
        double[] Y = {1, 1, 1, -2, -3, 7};    
        
        boolean res = DEFAULT.LIC2(X.length, X, Y, Math.PI/2);
        assertTrue("Should be true", res);

        double[] X1 = {1.9, 1.9, 1.95, 0.0};
        double[] Y1 = {4.0, 2.9, 4.0, 0.0};    
        
        res = DEFAULT.LIC2(X.length, X1, Y1, Math.PI - Math.PI/36);
        assertTrue("Should be True", res);
    }

    @Test
    public void LIC2_testFalse(){
        double[] X = {1.9, 1.9, 2.0};
        double[] Y = {4.0, 3.0, 4.0};    
        
        boolean res = DEFAULT.LIC2(X.length, X, Y, Math.PI - Math.PI/36);
        assertFalse("Should be false", res);
    }
}

