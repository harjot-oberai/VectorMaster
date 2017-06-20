package com.sdsmdg.harjot.vectormaster.utilities;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class Utils {

    public static int getColorFromString(String value) {
        int color;

        if (value.length() == 4) {
            color = Color.parseColor("#" + value.charAt(1) + value.charAt(1) + value.charAt(2) + value.charAt(2) + value.charAt(3) + value.charAt(3));
        } else {
            color = Color.parseColor(value);
        }

        return color;
    }

    public static Path.FillType getFillTypeFromString(String value) {
        Path.FillType fillType = Path.FillType.WINDING;
        if (value.equals("evenOdd")) {
            fillType = Path.FillType.EVEN_ODD;
        }
        return fillType;
    }

    public static Paint.Cap getLineCapFromString(String value) {
        switch (value) {
            case "butt":
                return Paint.Cap.BUTT;
            case "round":
                return Paint.Cap.ROUND;
            case "square":
                return Paint.Cap.SQUARE;
            default:
                return Paint.Cap.BUTT;
        }
    }

    public static Paint.Join getLineJoinFromString(String value) {
        switch (value) {
            case "bevel":
                return Paint.Join.BEVEL;
            case "miter":
                return Paint.Join.MITER;
            case "round":
                return Paint.Join.ROUND;
            default:
                return Paint.Join.MITER;
        }
    }

    public static int getAlphaFromFloat(float value) {
        int newValue = (int) (255 * value);
        return Math.min(255, newValue);
    }

}
