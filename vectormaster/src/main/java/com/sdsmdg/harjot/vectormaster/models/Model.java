package com.sdsmdg.harjot.vectormaster.models;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;

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

  public abstract void prepare(Canvas canvas, Matrix parentTransformation, float strokeRatio);
  public abstract void draw(Canvas canvas, Matrix parentTransformation, float strokeRatio);

  protected Path transform(Path path, Matrix transformation) {
    Path resultPath = new Path(path);
    resultPath.transform(transformation);
    return resultPath;
  }

}
