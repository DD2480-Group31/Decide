package com.decide;

import java.lang.Math;

public class Point {
    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point add(Point rhs) {
        return new Point(this.x + rhs.x, this.y + rhs.y);
    }

    public Point sub(Point rhs) {
        return new Point(this.x - rhs.x, this.y - rhs.y);
    }

    public double dist(Point other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    public Point mul(double rhs) {
        return new Point(this.x * rhs, this.y * rhs);
    }

    public Point div(double rhs) {
        return new Point(this.x / rhs, this.y / rhs);
    }
}
