package org.freddydurkee.marker.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;

import static utils.view.Palette.randomColor;

public class Marker {
    private final Color color;
    private DoubleProperty x;
    private DoubleProperty y;

    public Marker(double x, double y){
        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
        color = randomColor();
    }

    public double getR() {
        return 5;

    }

    public Color getColor() {
        return color;
    }

    public double getX() {
        return x.get();
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public void setX(double x) {
        this.x.set(x);
    }

    public double getY() {
        return y.get();
    }

    public DoubleProperty yProperty() {
        return y;
    }

    public void setY(double y) {
        this.y.set(y);
    }
}
