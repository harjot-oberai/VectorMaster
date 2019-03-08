package com.sdsmdg.harjot.vectormaster.models;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

public class ClipPathModel extends Model {
    private String pathData;

    private Path originalPath;
    private Path path;

    private Paint clipPaint;

    public ClipPathModel() {
        path = new Path();

        clipPaint = new Paint();
        clipPaint.setAntiAlias(true);
        clipPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    @Override
    public void prepare(Canvas canvas, float offsetX, float offsetY, float scaleX, float scaleY) {
        canvas.clipPath(getScaledAndOffsetPath(path, offsetX, offsetY, scaleX, scaleY));
    }

    @Override
    public void draw(Canvas canvas, float offsetX, float offsetY, float scaleX, float scaleY) {
        //clip path does no drawing
    }

    @Override
    public void calculateStatic() {
        originalPath = com.sdsmdg.harjot.vectormaster.utilities.legacyparser.PathParser.createPathFromPathData(pathData);
        path = new Path(originalPath);
    }

    @Override
    public void scaleStrokeWidth(float ratio) {

    }

    @Override
    public void scalePaths(Matrix originalScaleMatrix, Matrix concatTransformMatrix) {
        path = new Path(originalPath);
        path.transform(concatTransformMatrix);
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
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }



}
