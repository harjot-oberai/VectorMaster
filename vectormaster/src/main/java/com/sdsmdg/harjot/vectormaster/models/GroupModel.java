package com.sdsmdg.harjot.vectormaster.models;

import android.graphics.Matrix;
import com.sdsmdg.harjot.vectormaster.DefaultValues;

public class GroupModel extends ParentModel {

  private float rotation;
  private float pivotX, pivotY;
  private float scaleX, scaleY;
  private float translateX, translateY;
  private Matrix ownTransformation;
  private boolean ownTransformationChanged = true;
  private Matrix lastTransformation;
  private Matrix lastTransformationWorkMatrix; //reuses this one matrix object if lastTransformation is created by concatination

  public GroupModel() {
    rotation = DefaultValues.GROUP_ROTATION;
    pivotX = DefaultValues.GROUP_PIVOT_X;
    pivotY = DefaultValues.GROUP_PIVOT_Y;
    scaleX = DefaultValues.GROUP_SCALE_X;
    scaleY = DefaultValues.GROUP_SCALE_Y;
    translateX = DefaultValues.GROUP_TRANSLATE_X;
    translateY = DefaultValues.GROUP_TRANSLATE_Y;
  }

  @Override
  public void calculate(Matrix parentTransformation, Boolean transformationChanged, float strokeRatio) {
    if (ownTransformationChanged || transformationChanged) {
      if (ownTransformation == null) {
        lastTransformation = parentTransformation; //no own transformation necessary, so passthrough
      } else {
        if (lastTransformationWorkMatrix == null) {
          lastTransformationWorkMatrix = new Matrix(); //we create the work matrix only if this group needs one. After that it will be reused.
        }
        lastTransformationWorkMatrix.setConcat(parentTransformation, ownTransformation); //own transformation necessary, so concat with parent transformation
        lastTransformation = lastTransformationWorkMatrix;
      }
      ownTransformationChanged = false;
      super.calculate(lastTransformation, true, strokeRatio);
    } else {
      super.calculate(lastTransformation, false, strokeRatio);
    }
  }

  protected void createOwnTransformation() {
    //check if we need an own transformation matrix. if there is nothing to transform, do not build a matrix
    if (scaleX != 1.0 || scaleY != 1.0 || rotation != 0 || translateX != 0 || translateY != 0) {
      if (ownTransformation == null) {
        ownTransformation = new Matrix(); //try to create new matrix objects as less as possible
      } else {
        ownTransformation.reset();
      }
      ownTransformation.postScale(scaleX, scaleY, pivotX, pivotY);
      ownTransformation.postRotate(rotation, pivotX, pivotY);
      ownTransformation.postTranslate(translateX, translateY);
    } else {
      ownTransformation = null;
    }

    ownTransformationChanged = true;
  }

  private void markAsDirty() {
    createOwnTransformation();
  }

  public float getRotation() {
    return rotation;
  }

  public void setRotation(float rotation) {
    this.rotation = rotation;
    markAsDirty();
  }

  public float getPivotX() {
    return pivotX;
  }

  public void setPivotX(float pivotX) {
    this.pivotX = pivotX;
    markAsDirty();
  }

  public float getPivotY() {
    return pivotY;
  }

  public void setPivotY(float pivotY) {
    this.pivotY = pivotY;
    markAsDirty();
  }

  public float getScaleX() {
    return scaleX;
  }

  public void setScaleX(float scaleX) {
    this.scaleX = scaleX;
    markAsDirty();
  }

  public float getScaleY() {
    return scaleY;
  }

  public void setScaleY(float scaleY) {
    this.scaleY = scaleY;
    markAsDirty();
  }

  public float getTranslateX() {
    return translateX;
  }

  public void setTranslateX(float translateX) {
    this.translateX = translateX;
    markAsDirty();
  }

  public float getTranslateY() {
    return translateY;
  }

  public void setTranslateY(float translateY) {
    this.translateY = translateY;
    markAsDirty();
  }
}
