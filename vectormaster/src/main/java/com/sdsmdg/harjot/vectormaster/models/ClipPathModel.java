package com.sdsmdg.harjot.vectormaster.models;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

public class ClipPathModel extends Model {
    private String pathData;

    private Path path;
    private Path transformedPath;
    private Matrix lastTransformation;

    private Paint clipPaint;

    public ClipPathModel() {
        path = new Path();
        clipPaint = new Paint();
        clipPaint.setAntiAlias(true);
        clipPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    @Override
    public void init() {
       preparePath(new Matrix());
    }

    @Override
    public void prepare(Canvas canvas, Matrix parentTransformation, float strokeRatio) {
        canvas.clipPath(preparePath(parentTransformation));
    }

    @Override
    public void draw(Canvas canvas, Matrix parentTransformation, float strokeRatio) {
        //clip path does no drawing
    }

    private Path preparePath(Matrix transformation) {
        //try caching as much as possible to do path calculation only if necessary
        if (path == null) {
            path = com.sdsmdg.harjot.vectormaster.utilities.legacyparser.PathParser.createPathFromPathData(pathData);
            transformedPath = null;
        }
        if (transformedPath == null || lastTransformation == null || !lastTransformation.equals(transformation)) {
            transformedPath = transform(path, transformation);
            lastTransformation = transformation;
        }
        return transformedPath;
    }


    public Paint getClipPaint() {
        return clipPaint;
    }

    public void setClipPaint(Paint clipPaint) {
        this.clipPaint = clipPaint;
    }

    public String getPathData() {
        return pathData;
    }

    public void setPathData(String pathData) {
        this.pathData = pathData;
        path = null;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }



}
