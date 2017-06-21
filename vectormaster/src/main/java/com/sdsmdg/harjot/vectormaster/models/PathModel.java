package com.sdsmdg.harjot.vectormaster.models;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.sdsmdg.harjot.vectormaster.DefaultValues;
import com.sdsmdg.harjot.vectormaster.VectorMasterView;
import com.sdsmdg.harjot.vectormaster.utilities.Utils;
import com.sdsmdg.harjot.vectormaster.utilities.parser.PathParser;

public class PathModel {

    private String name;

    private float fillAlpha;
    private int fillColor;

    private Path.FillType fillType;

    private String pathData;

    private float strokeAlpha;
    private int strokeColor;
    private Paint.Cap strokeLineCap;
    private Paint.Join strokeLineJoin;
    private float strokeMiterLimit;
    private float strokeWidth;

    private float strokeRatio;

    private boolean isFillAndStroke = false;

    // Support for trim-paths is not available

    private Path path;
    private Paint pathPaint;

    public PathModel() {
        fillAlpha = DefaultValues.PATH_FILL_ALPHA;
        fillColor = DefaultValues.PATH_FILL_COLOR;
        fillType = DefaultValues.PATH_FILL_TYPE;
        strokeAlpha = DefaultValues.PATH_STROKE_ALPHA;
        strokeColor = DefaultValues.PATH_STROKE_COLOR;
        strokeLineCap = DefaultValues.PATH_STROKE_LINE_CAP;
        strokeLineJoin = DefaultValues.PATH_STROKE_LINE_JOIN;
        strokeMiterLimit = DefaultValues.PATH_STROKE_MITER_LIMIT;
        strokeWidth = DefaultValues.PATH_STROKE_WIDTH;
        strokeRatio = DefaultValues.PATH_STROKE_RATIO;

        pathPaint = new Paint();
        pathPaint.setAntiAlias(true);
        updatePaint();
    }

    public void buildPath() {
        if (VectorMasterView.useAndroidParser) {
            path = com.sdsmdg.harjot.vectormaster.utilities.androidparser.PathParser.createPathFromPathData(pathData);
        } else {
            path = PathParser.doPath(pathData);
        }
        if (path != null)
            path.setFillType(fillType);
    }

    public void updatePaint() {
        pathPaint.setStrokeWidth(strokeWidth * strokeRatio);

        if (fillColor != Color.TRANSPARENT && strokeColor != Color.TRANSPARENT) {
            isFillAndStroke = true;
        } else if (fillColor != Color.TRANSPARENT) {
            pathPaint.setColor(fillColor);
            pathPaint.setAlpha(Utils.getAlphaFromFloat(fillAlpha));
            pathPaint.setStyle(Paint.Style.FILL);
            isFillAndStroke = false;
        } else if (strokeColor != Color.TRANSPARENT) {
            pathPaint.setColor(strokeColor);
            pathPaint.setAlpha(Utils.getAlphaFromFloat(strokeAlpha));
            pathPaint.setStyle(Paint.Style.STROKE);
            isFillAndStroke = false;
        }

        pathPaint.setStrokeCap(strokeLineCap);
        pathPaint.setStrokeJoin(strokeLineJoin);
        pathPaint.setStrokeMiter(strokeMiterLimit);
    }

    public void makeStrokePaint() {
        pathPaint.setColor(strokeColor);
        pathPaint.setAlpha(Utils.getAlphaFromFloat(strokeAlpha));
        pathPaint.setStyle(Paint.Style.STROKE);
    }

    public void makeFillPaint() {
        pathPaint.setColor(fillColor);
        pathPaint.setAlpha(Utils.getAlphaFromFloat(fillAlpha));
        pathPaint.setStyle(Paint.Style.FILL);
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Paint getPathPaint() {
        return pathPaint;
    }

    public void setPathPaint(Paint pathPaint) {
        this.pathPaint = pathPaint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getFillAlpha() {
        return fillAlpha;
    }

    public void setFillAlpha(float fillAlpha) {
        this.fillAlpha = fillAlpha;
        updatePaint();
    }

    public int getFillColor() {
        return fillColor;
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
        updatePaint();
    }

    public Path.FillType getFillType() {
        return fillType;
    }

    public void setFillType(Path.FillType fillType) {
        this.fillType = fillType;
        if (path != null)
            path.setFillType(fillType);
    }

    public String getPathData() {
        return pathData;
    }

    public void setPathData(String pathData) {
        this.pathData = pathData;
    }

    public float getStrokeAlpha() {
        return strokeAlpha;
    }

    public void setStrokeAlpha(float strokeAlpha) {
        this.strokeAlpha = strokeAlpha;
        updatePaint();
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        updatePaint();
    }

    public Paint.Cap getStrokeLineCap() {
        return strokeLineCap;
    }

    public void setStrokeLineCap(Paint.Cap strokeLineCap) {
        this.strokeLineCap = strokeLineCap;
        updatePaint();
    }

    public Paint.Join getStrokeLineJoin() {
        return strokeLineJoin;
    }

    public void setStrokeLineJoin(Paint.Join strokeLineJoin) {
        this.strokeLineJoin = strokeLineJoin;
        updatePaint();
    }

    public float getStrokeMiterLimit() {
        return strokeMiterLimit;
    }

    public void setStrokeMiterLimit(float strokeMiterLimit) {
        this.strokeMiterLimit = strokeMiterLimit;
        updatePaint();
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        updatePaint();
    }

    public float getStrokeRatio() {
        return strokeRatio;
    }

    public void setStrokeRatio(float strokeRatio) {
        this.strokeRatio = strokeRatio;
        updatePaint();
    }

    public boolean isFillAndStroke() {
        return isFillAndStroke;
    }
}
