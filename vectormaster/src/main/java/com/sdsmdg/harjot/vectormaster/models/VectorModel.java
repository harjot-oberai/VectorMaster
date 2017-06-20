package com.sdsmdg.harjot.vectormaster.models;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PointF;

import com.sdsmdg.harjot.vectormaster.DefaultValues;
import com.sdsmdg.harjot.vectormaster.enums.TintMode;

import java.util.ArrayList;

public class VectorModel {

    private String name;

    private float width, height;

    private float alpha = 1.0f;

    private boolean autoMirrored = false;

    private int tint = Color.TRANSPARENT;
    private TintMode tintMode = TintMode.SCR_IN;

    private float viewportWidth, viewportHeight;

    private ArrayList<PathModel> pathModels;

    private Path fullpath;

    public VectorModel() {
        pathModels = new ArrayList<>();
        fullpath = new Path();
    }

    public int getMaxStrokeWidth() {
        int maxStroke = 0;
        for (PathModel pathModel : pathModels) {
            if (pathModel.getStrokeWidth() > maxStroke)
                maxStroke = (int) pathModel.getStrokeWidth();
        }
        return (int) (maxStroke * DefaultValues.STROKE_MULTIPLIER);
    }

    public ArrayList<PathModel> getPathModels() {
        return pathModels;
    }

    public void setPathModels(ArrayList<PathModel> pathModels) {
        this.pathModels = pathModels;
    }

    public void addPathModel(PathModel pathModel) {
        pathModels.add(pathModel);
    }

    public Path getFullpath() {
        return fullpath;
    }

    public void setFullpath(Path fullpath) {
        this.fullpath = fullpath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
