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

  /**
   * The rendering process always calls calculate(), perform(), draw() so that the models recalculate dynamically if needed.
   * The methods should cache as much as possible and avoid allocation of new objects.
   * @param parentTransformation
   * @param transformationChanged
   * @param strokeRatio
   */
  public abstract void calculate(Matrix parentTransformation, Boolean transformationChanged, float strokeRatio);
  public abstract void prepare(Canvas canvas);
  public abstract void draw(Canvas canvas);

  /**
   * adds the UNTRANSFORMED path (if the model has one) to collectedPath
   * @return
   */
  public void collectFullPath(Path collectedPath) {
    //by default nothing to collect
  }

  protected Matrix initializeMatrix(Matrix matrix, Matrix initialValue) {
    if (matrix == null) {
      matrix = new Matrix(); //try to create new matrix objects as less as possible
      if (initialValue != null) {
        matrix.set(initialValue);
      }
    } else {
      if (initialValue != null) {
        matrix.set(initialValue);
      } else {
        matrix.reset();
      }
    }
    return matrix;
  }

}
