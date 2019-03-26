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

  public abstract void calculate(Matrix parentTransformation, Boolean transformationChanged, float strokeRatio);
  public abstract void prepare(Canvas canvas);
  public abstract void draw(Canvas canvas);

  /**
   * adds the UNTRANSFORMED path to collectedPath
   * @return
   */
  public void collectFullPath(Path collectedPath) {
    //by default nothing to collect
  }

}
