package me.itzme1on.alcocraftplus.neoforge.core.utils;

public class ColorUtil {
    public static int getColorFromRGB(int red, int green, int blue) {
        return (red & 0xFF) << 16 | (green & 0xFF) << 8 | (blue & 0xFF);
    }

    public static int getRGBFromHex(String hex) {
        String stringToProcess;
        if (hex.startsWith("#")) {
            stringToProcess = hex.substring(1);

        } else {
            stringToProcess = hex;
        }

        if (stringToProcess.length() != 6) {
            throw new IllegalArgumentException("Incorrect HEX color format. There must be a format #RRGGBB");
        }

        try {
            int rgb = Integer.parseInt(stringToProcess, 16);
            int red = (rgb >> 16) & 0xFF;
            int green = (rgb >> 8) & 0xFF;
            int blue = rgb & 0xFF;

            return getColorFromRGB(red, green, blue);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error parsing HEX colors:", e);
        }
    }
}
