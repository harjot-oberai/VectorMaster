package com.sdsmdg.harjot.vectormaster.models;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;

public abstract class Model {

  private String name;
  private ParentModel parent;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ParentModel getParent() {
    return parent;
  }

  public void setParent(ParentModel parent) {
    this.parent = parent;
  }

  public abstract void calculateStatic();

  public abstract void scalePaths(Matrix originalScaleMatrix, Matrix concatTransformMatrix);

  public abstract void scaleStrokeWidth(float ratio);

  public abstract void prepare(Canvas canvas, float offsetX, float offsetY, float scaleX, float scaleY);

  public abstract void draw(Canvas canvas, float offsetX, float offsetY, float scaleX, float scaleY);

  public void draw(Canvas canvas) {
    draw(canvas, 0.0f, 0.0f, 1.0f, 1.0f);
  }

  public void prepare(Canvas canvas) {
    prepare(canvas, 0.0f, 0.0f, 1.0f, 1.0f);
  }

  public Path getScaledAndOffsetPath(Path srcPath, float offsetX, float offsetY, float scaleX, float scaleY) {
    if (offsetX == 0 && offsetY == 0 && scaleX == 1 && scaleY == 1) {
      return srcPath;
    } else {
      Path newPath = new Path(srcPath);
      newPath.offset(offsetX, offsetY);
      newPath.transform(getScaleMatrix(newPath, scaleX, scaleY));
      return newPath;
    }
  }

  public Matrix getScaleMatrix(Path srcPath, float scaleX, float scaleY) {
    Matrix scaleMatrix = new Matrix();
    RectF rectF = new RectF();
    srcPath.computeBounds(rectF, true);
    scaleMatrix.setScale(scaleX, scaleY, rectF.left, rectF.top);
    return scaleMatrix;
  }




}
