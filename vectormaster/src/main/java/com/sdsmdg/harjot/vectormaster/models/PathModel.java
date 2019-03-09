package com.sdsmdg.harjot.vectormaster.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;

import com.sdsmdg.harjot.vectormaster.DefaultValues;
import com.sdsmdg.harjot.vectormaster.utilities.Utils;

public class PathModel extends Model {

  private float fillAlpha;
  private int fillColor;

  private Path.FillType fillType;

  private String pathData;

  private float trimPathStart, trimPathEnd, trimPathOffset;

  private float strokeAlpha;
  private int strokeColor;
  private Paint.Cap strokeLineCap;
  private Paint.Join strokeLineJoin;
  private float strokeMiterLimit;
  private float strokeWidth;

  private float strokeRatio;

  private boolean isFillAndStroke = false;

  // Support for trim-paths is not available

  private Path path;
  private Path trimmedPath;
  private Path transformedPath;
  private Matrix lastTransformation;
  private Paint pathPaint;

  public PathModel() {
    fillAlpha = DefaultValues.PATH_FILL_ALPHA;
    fillColor = DefaultValues.PATH_FILL_COLOR;
    fillType = DefaultValues.PATH_FILL_TYPE;
    trimPathStart = DefaultValues.PATH_TRIM_PATH_START;
    trimPathEnd = DefaultValues.PATH_TRIM_PATH_END;
    trimPathOffset = DefaultValues.PATH_TRIM_PATH_OFFSET;
    strokeAlpha = DefaultValues.PATH_STROKE_ALPHA;
    strokeColor = DefaultValues.PATH_STROKE_COLOR;
    strokeLineCap = DefaultValues.PATH_STROKE_LINE_CAP;
    strokeLineJoin = DefaultValues.PATH_STROKE_LINE_JOIN;
    strokeMiterLimit = DefaultValues.PATH_STROKE_MITER_LIMIT;
    strokeWidth = DefaultValues.PATH_STROKE_WIDTH;
    strokeRatio = DefaultValues.PATH_STROKE_RATIO;

    pathPaint = new Paint();
    pathPaint.setAntiAlias(true);
    updatePaint();
  }

  @Override
  public void prepare(Canvas canvas, Matrix parentTransformation, float strokeRatio) {
    //path do not prepare
  }

  @Override
  public void draw(Canvas canvas, Matrix parentTransformation, float strokeRatio) {
    //try caching as much as possible to do path calculation only if necessary
    if (transformedPath == null || lastTransformation == null || !lastTransformation.equals(parentTransformation)) {
      transformedPath = transform(trimmedPath, parentTransformation);
      lastTransformation = parentTransformation;
    }
    transformedPath.setFillType(fillType);
    Path pathToDraw = transformedPath;
    pathPaint.setStrokeWidth(strokeWidth * this.strokeRatio * strokeRatio);
    if (isFillAndStroke()) {
      makeFillPaint();
      canvas.drawPath(pathToDraw, getPathPaint());
      makeStrokePaint();
      canvas.drawPath(pathToDraw, getPathPaint());
    } else {
      canvas.drawPath(pathToDraw, getPathPaint());
    }
  }

  public void updatePaint() {
    if (fillColor != Color.TRANSPARENT && strokeColor != Color.TRANSPARENT) {
      isFillAndStroke = true;
    } else if (fillColor != Color.TRANSPARENT) {
      pathPaint.setColor(fillColor);
      pathPaint.setAlpha(Utils.getAlphaFromFloat(fillAlpha));
      pathPaint.setStyle(Paint.Style.FILL);
      isFillAndStroke = false;
    } else if (strokeColor != Color.TRANSPARENT) {
      pathPaint.setColor(strokeColor);
      pathPaint.setAlpha(Utils.getAlphaFromFloat(strokeAlpha));
      pathPaint.setStyle(Paint.Style.STROKE);
      isFillAndStroke = false;
    } else {
      pathPaint.setColor(Color.TRANSPARENT);
    }

    pathPaint.setStrokeCap(strokeLineCap);
    pathPaint.setStrokeJoin(strokeLineJoin);
    pathPaint.setStrokeMiter(strokeMiterLimit);
  }

  public void makeStrokePaint() {
    pathPaint.setColor(strokeColor);
    pathPaint.setAlpha(Utils.getAlphaFromFloat(strokeAlpha));
    pathPaint.setStyle(Paint.Style.STROKE);
  }

  public void makeFillPaint() {
    pathPaint.setColor(fillColor);
    pathPaint.setAlpha(Utils.getAlphaFromFloat(fillAlpha));
    pathPaint.setStyle(Paint.Style.FILL);
  }

  public void trimPath() {
      if (path == null) {
        return;
      }

      if (trimPathStart == 0 && trimPathEnd == 1 && trimPathOffset == 0) {
        trimmedPath = new Path(path);
        transformedPath = null;
      } else {
        PathMeasure pathMeasure = new PathMeasure(path, false);
        float length = pathMeasure.getLength();
        Path trimmed = new Path();
        pathMeasure.getSegment(
            (trimPathStart + trimPathOffset) * length,
            (trimPathEnd + trimPathOffset) * length,
            trimmed,
            true);
        trimmedPath = trimmed;
        transformedPath = null;
      }
  }

  public Path getTrimmedPath() {
    return trimmedPath;
  }

  public Path getPath() {
    return path;
  }

  public void setPath(Path path) {
    this.path = path;
    trimPath();
  }

  public Paint getPathPaint() {
    return pathPaint;
  }

  public void setPathPaint(Paint pathPaint) {
    this.pathPaint = pathPaint;
  }

  public float getFillAlpha() {
    return fillAlpha;
  }

  public void setFillAlpha(float fillAlpha) {
    this.fillAlpha = fillAlpha;
    updatePaint();
  }

  public int getFillColor() {
    return fillColor;
  }

  public void setFillColor(int fillColor) {
    this.fillColor = fillColor;
    updatePaint();
  }

  public Path.FillType getFillType() {
    return fillType;
  }

  public void setFillType(Path.FillType fillType) {
    this.fillType = fillType;
  }

  public String getPathData() {
    return pathData;
  }

  public void setPathData(String pathData) {
    this.pathData = pathData;
    this.path = com.sdsmdg.harjot.vectormaster.utilities.legacyparser.PathParser.createPathFromPathData(pathData);
    trimPath();
  }

  public float getTrimPathStart() {
    return trimPathStart;
  }

  public void setTrimPathStart(float trimPathStart) {
    this.trimPathStart = trimPathStart;
    trimPath();
  }

  public float getTrimPathEnd() {
    return trimPathEnd;
  }

  public void setTrimPathEnd(float trimPathEnd) {
    this.trimPathEnd = trimPathEnd;
    trimPath();
  }

  public float getTrimPathOffset() {
    return trimPathOffset;
  }

  public void setTrimPathOffset(float trimPathOffset) {
    this.trimPathOffset = trimPathOffset;
    trimPath();
  }

  public float getStrokeAlpha() {
    return strokeAlpha;
  }

  public void setStrokeAlpha(float strokeAlpha) {
    this.strokeAlpha = strokeAlpha;
    updatePaint();
  }

  public int getStrokeColor() {
    return strokeColor;
  }

  public void setStrokeColor(int strokeColor) {
    this.strokeColor = strokeColor;
    updatePaint();
  }

  public Paint.Cap getStrokeLineCap() {
    return strokeLineCap;
  }

  public void setStrokeLineCap(Paint.Cap strokeLineCap) {
    this.strokeLineCap = strokeLineCap;
    updatePaint();
  }

  public Paint.Join getStrokeLineJoin() {
    return strokeLineJoin;
  }

  public void setStrokeLineJoin(Paint.Join strokeLineJoin) {
    this.strokeLineJoin = strokeLineJoin;
    updatePaint();
  }

  public float getStrokeMiterLimit() {
    return strokeMiterLimit;
  }

  public void setStrokeMiterLimit(float strokeMiterLimit) {
    this.strokeMiterLimit = strokeMiterLimit;
    updatePaint();
  }

  public float getStrokeWidth() {
    return strokeWidth;
  }

  public void setStrokeWidth(float strokeWidth) {
    this.strokeWidth = strokeWidth;
    updatePaint();
  }

  public boolean isFillAndStroke() {
    return isFillAndStroke;
  }

  public float getStrokeRatio() {
    return strokeRatio;
  }

  public void setStrokeRatio(float strokeRatio) {
    this.strokeRatio = strokeRatio;
  }

}
