package com.sdsmdg.harjot.vectormaster.models;


import com.sdsmdg.harjot.vectormaster.DefaultValues;

import java.util.ArrayList;

public class GroupModel {
    private String name;

    private float rotation;
    private float pivotX, pivotY;
    private float scaleX, scaleY;
    private float translateX, translateY;

    private ArrayList<PathModel> pathModels;

    public GroupModel() {
        rotation = DefaultValues.GROUP_ROTATION;
        pivotX = DefaultValues.GROUP_PIVOT_X;
        pivotY = DefaultValues.GROUP_PIVOT_Y;
        scaleX = DefaultValues.GROUP_SCALE_X;
        scaleY = DefaultValues.GROUP_SCALE_Y;
        translateX = DefaultValues.GROUP_TRANSLATE_X;
        translateY = DefaultValues.GROUP_TRANSLATE_Y;

        pathModels = new ArrayList<>();

    }

    public void addPathModel(PathModel pathModel) {
        pathModels.add(pathModel);
    }

    public ArrayList<PathModel> getPathModels() {
        return pathModels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
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
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public float getTranslateX() {
        return translateX;
    }

    public void setTranslateX(float translateX) {
        this.translateX = translateX;
    }

    public float getTranslateY() {
        return translateY;
    }

    public void setTranslateY(float translateY) {
        this.translateY = translateY;
    }
}
