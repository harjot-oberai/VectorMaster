package com.sdsmdg.harjot.vectormaster.models;

import android.graphics.Color;
import android.graphics.Path;

import com.sdsmdg.harjot.vectormaster.enums.TintMode;

public class VectorModel extends ParentModel {

  private float width, height;

  private float alpha = 1.0f;

  private boolean autoMirrored = false;

  private int tint = Color.TRANSPARENT;
  private TintMode tintMode = TintMode.SCR_IN;

  private float viewportWidth, viewportHeight;

  public VectorModel() {
  }

  /**
   * returns the UNTRANSFORMED paths
   * @return
   */
  public Path getFullpath() {
    Path fullPath = new Path();
    collectFullPath(fullPath);
    return fullPath;
  }

  public float getWidth() {
    return width;
  }

  public void setWidth(float width) {
    this.width = width;
  }

  public float getHeight() {
    return height;
  }

  public void setHeight(float height) {
    this.height = height;
  }

  public float getAlpha() {
    return alpha;
  }

  public void setAlpha(float alpha) {
    this.alpha = alpha;
  }

  public boolean isAutoMirrored() {
    return autoMirrored;
  }

  public void setAutoMirrored(boolean autoMirrored) {
    this.autoMirrored = autoMirrored;
  }

  public int getTint() {
    return tint;
  }

  public void setTint(int tint) {
    this.tint = tint;
  }

  public TintMode getTintMode() {
    return tintMode;
  }

  public void setTintMode(TintMode tintMode) {
    this.tintMode = tintMode;
  }

  public float getViewportWidth() {
    return viewportWidth;
  }

  public void setViewportWidth(float viewportWidth) {
    this.viewportWidth = viewportWidth;
  }

  public float getViewportHeight() {
    return viewportHeight;
  }

  public void setViewportHeight(float viewportHeight) {
    this.viewportHeight = viewportHeight;
  }
}
