import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.beans.Transient;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;



public class DecideTest{

    //Example: Typical test syntax
    @Test
    public void fooTest(){



    }

    /**
     * Test that the function correctly determines that three points can be contained in a circle.
     */
    @Test
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

    /**
     * Test that the function correctly determines that three points cannot be contained in a circle.
     */
    @Test
    public void containedInCircleNegative() {
        Decide dec = new Decide(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        double[][] points = {{1.5, 0.5}, {1, 4}, {3.5, 2.5}};
        double radius = 1.8;
        boolean res = dec.containedInCircle(points, radius);
        assert !res;
    }
}