package com.sdsmdg.harjot.vectormaster.models;

import android.graphics.Matrix;

import com.sdsmdg.harjot.vectormaster.DefaultValues;

public class GroupModel extends ParentModel {

    private float rotation;
    private float pivotX, pivotY;
    private float scaleX, scaleY;
    private float translateX, translateY;

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
    protected Matrix createTransformMatrix() {
        Matrix transformMatrix = new Matrix();
        transformMatrix.postScale(scaleX, scaleY, pivotX, pivotY);
        transformMatrix.postRotate(rotation, pivotX, pivotY);
        transformMatrix.postTranslate(translateX, translateY);
        return transformMatrix;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
        updateAndScalePaths();
    }

    public float getPivotX() {
        return pivotX;
    }

    public void setPivotX(float pivotX) {
        this.pivotX = pivotX;
    }

    public float getPivotY() {
        return pivotY;
    }

    public void setPivotY(float pivotY) {
        this.pivotY = pivotY;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
        updateAndScalePaths();
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
        updateAndScalePaths();
    }

    public float getTranslateX() {
        return translateX;
    }

    public void setTranslateX(float translateX) {
        this.translateX = translateX;
        updateAndScalePaths();
    }

    public float getTranslateY() {
        return translateY;
    }

    public void setTranslateY(float translateY) {
        this.translateY = translateY;
        updateAndScalePaths();
    }
}
