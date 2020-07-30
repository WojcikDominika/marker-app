package utils;

    import javafx.scene.paint.Color;

    import java.util.Random;

public class Palette {


    public static Color randomColor() {
        Random random = new Random();
        float hue = random.nextFloat();
        float saturation = 0.9f;
        float luminance = 1.0f;
        return Color.hsb(hue, saturation, luminance);
    }
}
