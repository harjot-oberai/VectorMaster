package com.sdsmdg.harjot.vectormaster;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.sdsmdg.harjot.vectormaster.models.PathModel;
import com.sdsmdg.harjot.vectormaster.models.VectorModel;
import com.sdsmdg.harjot.vectormaster.utilities.Utils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class VectorMasterView extends View {

    VectorModel vectorModel;

    Context context;

    Resources resources;
    int resID = -1;

    XmlPullParser xpp;

    String TAG = "VECTOR_MASTER";

    Matrix scaleMatrix;

    int width = 0, height = 0;

    public VectorMasterView(Context context) {
        super(context);
        this.context = context;
    }

    public VectorMasterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    public VectorMasterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    void init(AttributeSet attrs) {
        resources = context.getResources();

        vectorModel = new VectorModel();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.VectorMasterView);
        final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.VectorMasterView_vector_src) {
                resID = a.getResourceId(attr, -1);
            }
        }
        a.recycle();

        buildVectorModel();

    }

    void buildVectorModel() {

        if (resID == -1) {
            vectorModel = null;
            return;
        }

        xpp = resources.getXml(resID);

        int tempPosition;
        PathModel pathModel;

        try {
            int event = xpp.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = xpp.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        if (name.equals("path")) {
                            pathModel = new PathModel();

                            Log.d(TAG, "******************************");

                            tempPosition = getAttrPosition(xpp, "name");
                            Log.d(TAG, (tempPosition != -1) ? xpp.getAttributeValue(tempPosition) : "null");
                            pathModel.setName((tempPosition != -1) ? xpp.getAttributeValue(tempPosition) : null);

                            tempPosition = getAttrPosition(xpp, "fillAlpha");
                            Log.d(TAG, (tempPosition != -1) ? xpp.getAttributeValue(tempPosition) : "null");
                            pathModel.setFillAlpha((tempPosition != -1) ? Float.parseFloat(xpp.getAttributeValue(tempPosition)) : DefaultValues.PATH_FILL_ALPHA);

                            tempPosition = getAttrPosition(xpp, "fillColor");
                            Log.d(TAG, (tempPosition != -1) ? xpp.getAttributeValue(tempPosition) : "null");
                            pathModel.setFillColor((tempPosition != -1) ? Utils.getColorFromString(xpp.getAttributeValue(tempPosition)) : DefaultValues.PATH_FILL_COLOR);

                            tempPosition = getAttrPosition(xpp, "fillType");
                            Log.d(TAG, (tempPosition != -1) ? xpp.getAttributeValue(tempPosition) : "null");
                            pathModel.setFillType((tempPosition != -1) ? Utils.getFillTypeFromString(xpp.getAttributeValue(tempPosition)) : DefaultValues.PATH_FILL_TYPE);

                            tempPosition = getAttrPosition(xpp, "pathData");
                            Log.d(TAG, (tempPosition != -1) ? xpp.getAttributeValue(tempPosition) : "null");
                            pathModel.setPathData((tempPosition != -1) ? xpp.getAttributeValue(tempPosition) : null);

                            tempPosition = getAttrPosition(xpp, "strokeAlpha");
                            Log.d(TAG, (tempPosition != -1) ? xpp.getAttributeValue(tempPosition) : "null");
                            pathModel.setStrokeAlpha((tempPosition != -1) ? Float.parseFloat(xpp.getAttributeValue(tempPosition)) : DefaultValues.PATH_STROKE_ALPHA);

                            tempPosition = getAttrPosition(xpp, "strokeColor");
                            Log.d(TAG, (tempPosition != -1) ? xpp.getAttributeValue(tempPosition) : "null");
                            pathModel.setStrokeColor((tempPosition != -1) ? Utils.getColorFromString(xpp.getAttributeValue(tempPosition)) : DefaultValues.PATH_STROKE_COLOR);

                            tempPosition = getAttrPosition(xpp, "strokeLineCap");
                            Log.d(TAG, (tempPosition != -1) ? xpp.getAttributeValue(tempPosition) : "null");
                            pathModel.setStrokeLineCap((tempPosition != -1) ? Utils.getLineCapFromString(xpp.getAttributeValue(tempPosition)) : DefaultValues.PATH_STROKE_LINE_CAP);

                            tempPosition = getAttrPosition(xpp, "strokeLineJoin");
                            Log.d(TAG, (tempPosition != -1) ? xpp.getAttributeValue(tempPosition) : "null");
                            pathModel.setStrokeLineJoin((tempPosition != -1) ? Utils.getLineJoinFromString(xpp.getAttributeValue(tempPosition)) : DefaultValues.PATH_STROKE_LINE_JOIN);

                            tempPosition = getAttrPosition(xpp, "strokeMiterLimit");
                            Log.d(TAG, (tempPosition != -1) ? xpp.getAttributeValue(tempPosition) : "null");
                            pathModel.setStrokeMiterLimit((tempPosition != -1) ? Float.parseFloat(xpp.getAttributeValue(tempPosition)) : DefaultValues.PATH_STROKE_MITER_LIMIT);

                            tempPosition = getAttrPosition(xpp, "strokeWidth");
                            Log.d(TAG, (tempPosition != -1) ? xpp.getAttributeValue(tempPosition) : "null");
                            pathModel.setStrokeWidth((tempPosition != -1) ? Float.parseFloat(xpp.getAttributeValue(tempPosition)) : DefaultValues.PATH_STROKE_WIDTH);

                            Log.d(TAG, "******************************");

                            pathModel.buildPathAndPaint();

                            vectorModel.addPathModel(pathModel);
                            vectorModel.getFullpath().addPath(pathModel.getPath());
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        break;
                }
                event = xpp.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

    }

    int getAttrPosition(XmlPullParser xpp, String attrName) {
        Log.e(TAG, attrName);
        for (int i = 0; i < xpp.getAttributeCount(); i++) {
            if (xpp.getAttributeName(i).equals(attrName)) {
                return i;
            }
        }
        return -1;
    }

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (vectorModel == null) {
            return;
        }

        if (scaleMatrix == null || width != canvas.getWidth() || height != canvas.getHeight()) {
            buildScaleMatrix();
            scaleAllPaths();
        }

        for (PathModel pathModel : vectorModel.getPathModels()) {
            canvas.drawPath(pathModel.getPath(), pathModel.getPathPaint());
        }

//        canvas.drawPath(vectorModel.getPathModels().get(1).getPath(), vectorModel.getPathModels().get(1).getPathPaint());

//        vectorModel.getFullpath().transform(scaleMatrix);
//        canvas.drawPath(vectorModel.getFullpath(), vectorModel.getPathModels().get(0).getPathPaint());

    }

    void buildScaleMatrix() {
        scaleMatrix = getMatrix();

        scaleMatrix.setScale(20, 20);

//        RectF viewRect = new RectF(0, 0, width, height);
//
//        RectF rectF = new RectF();
//        vectorModel.getFullpath().computeBounds(rectF, true);
//
//        scaleMatrix.setRectToRect(rectF, viewRect, Matrix.ScaleToFit.CENTER);
    }

    void scaleAllPaths() {
        for (PathModel pathModel : vectorModel.getPathModels()) {
            pathModel.getPath().transform(scaleMatrix);
        }
    }
}
