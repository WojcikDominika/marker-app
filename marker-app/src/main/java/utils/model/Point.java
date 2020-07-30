package utils.model;

public class Point {

    private final double x;
    private final double y;

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

    public Point subtract(Point point){
        return new Point(x-point.x, y-point.y);
    }

    public Point add(Point point){
        return new Point(x+point.x, y+point.y);
    }
}
