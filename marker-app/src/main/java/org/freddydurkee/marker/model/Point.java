package org.freddydurkee.marker.model;

public class Point {

    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Point shift(double xShift, double yShift) {
        return new Point(x + xShift, y + yShift);
    }
}
