package com.sdsmdg.harjot.vectormaster;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.sdsmdg.harjot.vectormaster.models.ClipPathModel;
import com.sdsmdg.harjot.vectormaster.models.GroupModel;
import com.sdsmdg.harjot.vectormaster.models.PathModel;
import com.sdsmdg.harjot.vectormaster.models.VectorModel;

public class VectorMasterView extends View {

    VectorModel vectorModel;
    Context context;

    Resources resources;
    int resID = -1;
    boolean useLegacyParser = true;

    String TAG = "VECTOR_MASTER";

    private Matrix scaleMatrix;

    int width = 0, height = 0;

    private float scaleRatio, strokeRatio;

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

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.VectorMasterView);
        final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.VectorMasterView_vector_src) {
                resID = a.getResourceId(attr, -1);
            } else if (attr == R.styleable.VectorMasterView_use_legacy_parser) {
                useLegacyParser = a.getBoolean(attr, false);
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

        vectorModel = new ModelParser().buildVectorModel(resources, resID);

    }

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != 0 && h != 0) {
            width = w;
            height = h;

            buildScaleMatrix();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width = canvas.getWidth();
        height = canvas.getHeight();

        if (vectorModel == null) {
            return;
        }

        strokeRatio = Math.min(width / vectorModel.getWidth(), height / vectorModel.getHeight());
        Matrix translation = new Matrix(scaleMatrix);

        setAlpha(vectorModel.getAlpha());

        vectorModel.draw(canvas, translation, scaleRatio);

    }

    void buildScaleMatrix() {

        scaleMatrix = new Matrix();

        scaleMatrix.postTranslate(width / 2 - vectorModel.getViewportWidth() / 2, height / 2 - vectorModel.getViewportHeight() / 2);

        float widthRatio = width / vectorModel.getViewportWidth();
        float heightRatio = height / vectorModel.getViewportHeight();
        float ratio = Math.min(widthRatio, heightRatio);

        scaleRatio = ratio;

        scaleMatrix.postScale(ratio, ratio, width / 2, height / 2);
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
        invalidate();
    }

    public float getScaleRatio() {
        return scaleRatio;
    }

    public float getStrokeRatio() {
        return strokeRatio;
    }

    public Matrix getScaleMatrix() {
        return scaleMatrix;
    }
}
