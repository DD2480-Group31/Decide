package com.decide;

import org.junit.Test;
import static org.junit.Assert.*;

public class PointTest {
    Point a = new Point(1, 5);
    Point b = new Point(-12.314, 15.2);

    final double DELTA = 0.000001;

    @Test
    public void addition() {
        Point ab = a.add(b);
        assertEquals(ab.x, a.x + b.x, DELTA);
        assertEquals(ab.y, a.y + b.y, DELTA);

        Point ba = b.add(a);
        assertEquals(ba.x, a.x + b.x, DELTA);
        assertEquals(ba.y, a.y + b.y, DELTA);
    }

    @Test
    public void subtraction() {
        Point ab = a.sub(b);
        assertEquals(ab.x, a.x - b.x, DELTA);
        assertEquals(ab.y, a.y - b.y, DELTA);

        Point ba = b.sub(a);
        assertEquals(ba.x, b.x - a.x, DELTA);
        assertEquals(ba.y, b.y - a.y, DELTA);
    }

    @Test
    public void distance() {
        assertEquals(a.dist(b), Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2)), DELTA);
    }

}
