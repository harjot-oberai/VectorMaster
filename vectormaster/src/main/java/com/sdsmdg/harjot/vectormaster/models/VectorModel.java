package com.sdsmdg.harjot.vectormaster.models;

import android.graphics.Color;

import com.sdsmdg.harjot.vectormaster.enums.TintMode;

import java.util.ArrayList;

public class VectorModel {

    String name;

    int width, height;

    float alpha = 1.0f;

    boolean autoMirrored = false;

    int tint = Color.TRANSPARENT;
    TintMode tintMode = TintMode.SCR_IN;

    float viewportWidth, viewportHeight;

    ArrayList<PathModel> pathModels;

}
