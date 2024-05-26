package util;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ColorMap {
    static Map<Integer, Color> colorMap = new HashMap<>();

    //todo: complete the method to add other color
    public static void InitialColorMap() {
        colorMap.put(2, new Color(238, 228, 208));
        colorMap.put(4, new Color(208, 159, 94));
        colorMap.put(8, new Color(242, 177, 121));
        colorMap.put(16, new Color(245, 149, 99));
        colorMap.put(32, new Color(246, 124, 95));
        colorMap.put(64, new Color(246, 94, 59));
        colorMap.put(128, new Color(117, 167, 241));
        colorMap.put(256, new Color(69, 133, 242));
        colorMap.put(512, new Color(237, 207, 114));
        colorMap.put(1024, new Color(237, 197, 63));
        colorMap.put(2048, new Color(247, 202, 24));
        colorMap.put(4096, new Color(24, 210, 247));
    }

    public static Color getColor(int i) {
        return colorMap.getOrDefault(i, new Color(14, 17, 17));
    }
}
