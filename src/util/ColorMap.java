package util;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ColorMap {
    static Map<Integer, Color> colorMap = new HashMap<>();

    //todo: complete the method to add other color
    public static void InitialColorMap() {
        colorMap.put(1, Color.RED);
        colorMap.put(2, Color.blue);
        colorMap.put(4, Color.orange);
        colorMap.put(8, Color.black);
        colorMap.put(16, Color.pink);
        colorMap.put(32, Color.red);
        colorMap.put(64, Color.pink);
        colorMap.put(128, Color.red);
        colorMap.put(256, Color.blue);
        colorMap.put(512, Color.blue);
        colorMap.put(1024, Color.blue);
        colorMap.put(2048, Color.blue);

    }

    public static Color getColor(int i) {
        return colorMap.getOrDefault(i, Color.black);
    }
}
