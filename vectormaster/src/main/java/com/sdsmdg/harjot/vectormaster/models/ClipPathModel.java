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
  private Path transformedPath = new Path();
  private Matrix lastParentTransformation;

  private Paint clipPaint;

  public ClipPathModel() {
    path = new Path();
    clipPaint = new Paint();
    clipPaint.setAntiAlias(true);
    clipPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
  }

  @Override
  public void calculate(Matrix parentTransformation, Boolean transformationChanged, float strokeRatio) {
    if (transformationChanged || lastParentTransformation == null) {
      lastParentTransformation = parentTransformation;
      calculateTransformedPath();
    }
  }

  @Override
  public void prepare(Canvas canvas) {
    canvas.clipPath(transformedPath);
  }

  @Override
  public void draw(Canvas canvas) {
    //clip path does no drawing
  }

  private void calculateTransformedPath() {
    if (path == null || lastParentTransformation == null) {
      return;
    }
    transformedPath.rewind();
    path.transform(lastParentTransformation, transformedPath);
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
    Path path = com.sdsmdg.harjot.vectormaster.utilities.legacyparser.PathParser.createPathFromPathData(pathData);
    setPath(path);
  }

  public Path getPath() {
    return path;
  }

  public void setPath(Path path) {
    this.path = path;
    calculateTransformedPath();
  }

}
