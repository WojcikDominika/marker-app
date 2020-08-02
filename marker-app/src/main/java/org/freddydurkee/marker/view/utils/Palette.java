package org.freddydurkee.marker.view.utils;

import javafx.scene.paint.Color;

import java.util.Random;

public class Palette {

    private static Random random = new Random();

    private Palette() {
    }

    public static Color randomColor() {
        final float hue = random.nextFloat() * 360;
        final float saturation = 0.9f;
        final float luminance = 1.0f;
        return Color.hsb(hue, saturation, luminance);
    }
}
