package org.freddydurkee.marker.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.paint.Color;

import static utils.view.Palette.randomColor;

public class Marker {
    private final Color color;
    private IntegerProperty x;
    private IntegerProperty y;

    public Marker(int x, int y) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
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

    public IntegerProperty xProperty() {
        return x;
    }

    public void setX(int x) {
        this.x.set(x);
    }

    public double getY() {
        return y.get();
    }

    public IntegerProperty yProperty() {
        return y;
    }

    public void setY(int y) {
        this.y.set(y);
    }
}
