package com.sdsmdg.harjot.vectormaster;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.sdsmdg.harjot.vectormaster.models.ClipPathModel;
import com.sdsmdg.harjot.vectormaster.models.GroupModel;
import com.sdsmdg.harjot.vectormaster.models.PathModel;
import com.sdsmdg.harjot.vectormaster.models.VectorModel;
import com.sdsmdg.harjot.vectormaster.utilities.Utils;
import org.xmlpull.v1.XmlPullParser;

public class VectorMasterDrawable extends Drawable {

    private VectorModel vectorModel;
    private Context context;

    private Resources resources;
    private int resID = -1;
    private boolean useLegacyParser = true;

    private float offsetX = 0.0f, offsetY = 0.0f;
    private float scaleX = 1.0f, scaleY = 1.0f;

    String TAG = "VECTOR_MASTER";

    private Matrix scaleMatrix = new Matrix();
    private boolean scaleMatrixChanged = true;
    private Matrix newScaleMatrix = new Matrix(); //create a matrix object which is reused on every scaleMatrix creation

    private int width = -1, height = -1;

    private float scaleRatio, strokeRatio;

    private int left = 0, top = 0;
    private int tempSaveCount;

    public VectorMasterDrawable(Context context, int resID) {
        this.context = context;
        this.resID = resID;
        init();
    }

    public VectorMasterDrawable(Context context, int resID, float offsetX, float offsetY) {
        this.context = context;
        this.resID = resID;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        init();
    }

    public VectorMasterDrawable(Context context, int resID, float offsetX, float offsetY, float scaleX, float scaleY) {
        this.context = context;
        this.resID = resID;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        init();
    }

    private void init() {
        resources = context.getResources();
        buildVectorModel();
    }

    private void buildVectorModel() {
        if (resID == -1) {
            vectorModel = null;
            return;
        }
        vectorModel = new ModelParser().buildVectorModel(resources, resID);
    }

    private int getAttrPosition(XmlPullParser xpp, String attrName) {
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
        buildVectorModel();
        scaleMatrix = null;
    }

    public boolean isUseLegacyParser() {
        return useLegacyParser;
    }

    public void setUseLegacyParser(boolean useLegacyParser) {
        this.useLegacyParser = useLegacyParser;
        buildVectorModel();
        scaleMatrix = null;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);

        if (bounds.width() != 0 && bounds.height() != 0) {

            left = bounds.left;
            top = bounds.top;

            width = bounds.width();
            height = bounds.height();

            buildScaleMatrix();
        }
    }

    @Override
    public void draw(Canvas canvas) {

        if (vectorModel == null) {
            return;
        }

        if (scaleMatrix == null) {
            int temp1 = Utils.dpToPx((int) vectorModel.getWidth());
            int temp2 = Utils.dpToPx((int) vectorModel.getHeight());

            setBounds(0, 0, temp1, temp2);
        }

        strokeRatio = Math.min(width / vectorModel.getWidth(), height / vectorModel.getHeight());

        setAlpha(Utils.getAlphaFromFloat(vectorModel.getAlpha()));

        vectorModel.calculate(scaleMatrix, scaleMatrixChanged, strokeRatio);
        scaleMatrixChanged = false;

        if (left != 0 || top != 0) {
            tempSaveCount = canvas.save();
            canvas.translate(left, top);
            vectorModel.draw(canvas);
            canvas.restoreToCount(tempSaveCount);
        } else {
            vectorModel.draw(canvas);
        }

    }

    @Override
    public void setAlpha(int i) {
        vectorModel.setAlpha(Utils.getAlphaFromInt(i));
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicWidth() {
        return Utils.dpToPx((int) vectorModel.getWidth());
    }

    @Override
    public int getIntrinsicHeight() {
        return Utils.dpToPx((int) vectorModel.getHeight());
    }

    private void buildScaleMatrix() {

        newScaleMatrix.reset();
        newScaleMatrix.postTranslate(width / 2 - vectorModel.getViewportWidth() / 2, height / 2 - vectorModel.getViewportHeight() / 2);

        float widthRatio = width / vectorModel.getViewportWidth();
        float heightRatio = height / vectorModel.getViewportHeight();
        float ratio = Math.min(widthRatio, heightRatio);

        scaleRatio = ratio;

        newScaleMatrix.postScale(ratio, ratio, width / 2, height / 2);

        newScaleMatrix.postTranslate(offsetX, offsetY);
        newScaleMatrix.postScale(scaleX, scaleY);

        scaleMatrix.set(newScaleMatrix);
        scaleMatrixChanged = true;
    }

    public Path getFullPath() {
        if (vectorModel != null) {
            return vectorModel.getFullpath();
        }
        return null;
    }

    public GroupModel getGroupModelByName(String name) {
       return vectorModel.getGroupModelByName(name);
    }

    public PathModel getPathModelByName(String name) {
        return vectorModel.getPathModelByName(name);
    }

    public ClipPathModel getClipPathModelByName(String name) {
        return vectorModel.getClipPathModelByName(name);
    }

    public void update() {
        invalidateSelf();
    }

    public float getScaleRatio() {
        return scaleRatio;
    }

    public float getStrokeRatio() {
        return strokeRatio;
    }

    public Matrix getScaleMatrix() {
        return new Matrix(scaleMatrix); //make a copy so that from outside the real scaleMatrix cannot be manipulated because this would not be propagated to the VectorModel correctly because scaleMatrixChanged would not be set.
    }

    public float getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
        buildScaleMatrix();
        invalidateSelf();
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
        buildScaleMatrix();
        invalidateSelf();
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
        buildScaleMatrix();
        invalidateSelf();
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
        buildScaleMatrix();
        invalidateSelf();
    }
}
