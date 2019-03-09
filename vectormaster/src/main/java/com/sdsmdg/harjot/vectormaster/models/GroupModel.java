package com.sdsmdg.harjot.vectormaster.models;

import android.graphics.Canvas;
import android.graphics.Matrix;

import com.sdsmdg.harjot.vectormaster.DefaultValues;

public class GroupModel extends ParentModel {

  private float rotation;
  private float pivotX, pivotY;
  private float scaleX, scaleY;
  private float translateX, translateY;
  private Matrix ownTransformation;
  private boolean ownTransformationChanged = true;
  private Matrix lastParentTransformation;
  private Matrix lastTransformation;

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
  public void draw(Canvas canvas, Matrix parentTransformation, float strokeRatio) {

    //try to keep transformed matrix the same instance as long as the values do not change
    if (ownTransformationChanged || lastParentTransformation != parentTransformation) {
      lastParentTransformation = parentTransformation;
      lastTransformation = new Matrix(parentTransformation);
      lastTransformation.preConcat(ownTransformation);
      ownTransformationChanged = false;
    }

    super.draw(canvas, lastTransformation, strokeRatio);
  }

  protected void createOwnTransformation() {
    Matrix transformation = new Matrix();
    transformation.postScale(scaleX, scaleY, pivotX, pivotY);
    transformation.postRotate(rotation, pivotX, pivotY);
    transformation.postTranslate(translateX, translateY);

    ownTransformation = transformation;
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
