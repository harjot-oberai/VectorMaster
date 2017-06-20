package com.sdsmdg.harjot.vectormaster;


import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class DefaultValues {

    public static String[] PATH_ATTRIBUTES = {"name",
            "fillAlpha",
            "fillColor",
            "fillType",
            "pathData",
            "strokeAlpha",
            "strokeColor",
            "strokeLineCap",
            "strokeLineJoin",
            "strokeMiterLimit",
            "strokeWidth"};

    public final static int PATH_FILL_COLOR = Color.TRANSPARENT;
    public final static int PATH_STROKE_COLOR = Color.TRANSPARENT;

    public final static float PATH_STROKE_WIDTH = 0.0f;
    public final static float PATH_STROKE_ALPHA = 1.0f;
    public final static float PATH_FILL_ALPHA = 1.0f;

    public final static Paint.Cap PATH_STROKE_LINE_CAP = Paint.Cap.BUTT;
    public final static Paint.Join PATH_STROKE_LINE_JOIN = Paint.Join.MITER;

    public final static float PATH_STROKE_MITER_LIMIT = 4.0f;

    // WINDING fill type is equivalent to NON_ZERO
    public final static Path.FillType PATH_FILL_TYPE = Path.FillType.WINDING;

    public final static float STROKE_MULTIPLIER = 10;
    public final static float STROKE_SCALE_RATIO_MULTIPLIER = 0.1f;

    public final static float VECTOR_VIEWPORT_WIDTH = 0.0f;
    public final static float VECTOR_VIEWPORT_HEIGHT = 0.0f;

    public final static float VECTOR_WIDTH = 0.0f;
    public final static float VECTOR_HEIGHT = 0.0f;

    public final static float VECTOR_ALPHA = 1.0f;

}
