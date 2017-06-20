package com.sdsmdg.harjot.vectormaster.models;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class PathModel {

    String name;

    float fillAlpha;
    int fillColor = Color.TRANSPARENT;

    // WINDING fill type is equivalent to NON_ZERO
    Path.FillType fillType = Path.FillType.WINDING;

    String pathData;

    float strokeAlpha = 1.0f;
    int strokeColor = Color.TRANSPARENT;
    Paint.Cap strokeCap = Paint.Cap.BUTT;
    Paint.Join strokeLineJoin = Paint.Join.MITER;
    float miterLimit = 4;
    float strokeWidth = 1.0f;

    // Support for trim-paths is not available

    Path path;
    Paint pathPaint;

}
