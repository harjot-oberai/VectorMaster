package com.sdsmdg.harjot.vectormaster;

import android.content.res.Resources;
import com.sdsmdg.harjot.vectormaster.models.ClipPathModel;
import com.sdsmdg.harjot.vectormaster.models.GroupModel;
import com.sdsmdg.harjot.vectormaster.models.ParentModel;
import com.sdsmdg.harjot.vectormaster.models.PathModel;
import com.sdsmdg.harjot.vectormaster.models.VectorModel;
import com.sdsmdg.harjot.vectormaster.utilities.Utils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Stack;

public class ModelParser {

  int getAttrPosition(XmlPullParser xpp, String attrName) {
    for (int i = 0; i < xpp.getAttributeCount(); i++) {
      if (xpp.getAttributeName(i).equals(attrName)) {
        return i;
      }
    }
    return -1;
  }

  public VectorModel buildVectorModel(Resources resources, int resID) {

    if (resID == -1) {
      return null;
    }

    XmlPullParser xpp = resources.getXml(resID);

    int tempPosition;
    VectorModel vectorModel = new VectorModel();
    PathModel pathModel = new PathModel();
    GroupModel groupModel = new GroupModel();
    ClipPathModel clipPathModel = new ClipPathModel();
    Stack<ParentModel> parentModelStack = new Stack<>();
    parentModelStack.push(vectorModel);

    try {
      int event = xpp.getEventType();
      while (event != XmlPullParser.END_DOCUMENT) {
        String name = xpp.getName();
        switch (event) {
        case XmlPullParser.START_TAG:
          if (name.equals("vector")) {
            tempPosition = getAttrPosition(xpp, "viewportWidth");
            vectorModel.setViewportWidth((tempPosition != -1) ?
                Float.parseFloat(xpp.getAttributeValue(tempPosition)) :
                DefaultValues.VECTOR_VIEWPORT_WIDTH);

            tempPosition = getAttrPosition(xpp, "viewportHeight");
            vectorModel.setViewportHeight((tempPosition != -1) ?
                Float.parseFloat(xpp.getAttributeValue(tempPosition)) :
                DefaultValues.VECTOR_VIEWPORT_HEIGHT);

            tempPosition = getAttrPosition(xpp, "alpha");
            vectorModel.setAlpha((tempPosition != -1) ?
                Float.parseFloat(xpp.getAttributeValue(tempPosition)) :
                DefaultValues.VECTOR_ALPHA);

            tempPosition = getAttrPosition(xpp, "name");
            vectorModel.setName((tempPosition != -1) ? xpp.getAttributeValue(tempPosition) : null);

            tempPosition = getAttrPosition(xpp, "width");
            vectorModel.setWidth((tempPosition != -1) ?
                Utils.getFloatFromDimensionString(xpp.getAttributeValue(tempPosition)) :
                DefaultValues.VECTOR_WIDTH);

            tempPosition = getAttrPosition(xpp, "height");
            vectorModel.setHeight((tempPosition != -1) ?
                Utils.getFloatFromDimensionString(xpp.getAttributeValue(tempPosition)) :
                DefaultValues.VECTOR_HEIGHT);
          } else if (name.equals("path")) {
            pathModel = new PathModel();

            tempPosition = getAttrPosition(xpp, "name");
            pathModel.setName((tempPosition != -1) ? xpp.getAttributeValue(tempPosition) : null);

            tempPosition = getAttrPosition(xpp, "fillAlpha");
            pathModel.setFillAlpha((tempPosition != -1) ?
                Float.parseFloat(xpp.getAttributeValue(tempPosition)) :
                DefaultValues.PATH_FILL_ALPHA);

            tempPosition = getAttrPosition(xpp, "fillColor");
            pathModel.setFillColor((tempPosition != -1) ?
                Utils.getColorFromString(xpp.getAttributeValue(tempPosition)) :
                DefaultValues.PATH_FILL_COLOR);

            tempPosition = getAttrPosition(xpp, "fillType");
            pathModel.setFillType((tempPosition != -1) ?
                Utils.getFillTypeFromString(xpp.getAttributeValue(tempPosition)) :
                DefaultValues.PATH_FILL_TYPE);

            tempPosition = getAttrPosition(xpp, "pathData");
            pathModel.setPathData((tempPosition != -1) ? xpp.getAttributeValue(tempPosition) : null);

            tempPosition = getAttrPosition(xpp, "strokeAlpha");
            pathModel.setStrokeAlpha((tempPosition != -1) ?
                Float.parseFloat(xpp.getAttributeValue(tempPosition)) :
                DefaultValues.PATH_STROKE_ALPHA);

            tempPosition = getAttrPosition(xpp, "strokeColor");
            pathModel.setStrokeColor((tempPosition != -1) ?
                Utils.getColorFromString(xpp.getAttributeValue(tempPosition)) :
                DefaultValues.PATH_STROKE_COLOR);

            tempPosition = getAttrPosition(xpp, "strokeLineCap");
            pathModel.setStrokeLineCap((tempPosition != -1) ?
                Utils.getLineCapFromString(xpp.getAttributeValue(tempPosition)) :
                DefaultValues.PATH_STROKE_LINE_CAP);

            tempPosition = getAttrPosition(xpp, "strokeLineJoin");
            pathModel.setStrokeLineJoin((tempPosition != -1) ?
                Utils.getLineJoinFromString(xpp.getAttributeValue(tempPosition)) :
                DefaultValues.PATH_STROKE_LINE_JOIN);

            tempPosition = getAttrPosition(xpp, "strokeMiterLimit");
            pathModel.setStrokeMiterLimit((tempPosition != -1) ?
                Float.parseFloat(xpp.getAttributeValue(tempPosition)) :
                DefaultValues.PATH_STROKE_MITER_LIMIT);

            tempPosition = getAttrPosition(xpp, "strokeWidth");
            pathModel.setStrokeWidth((tempPosition != -1) ?
                Float.parseFloat(xpp.getAttributeValue(tempPosition)) :
                DefaultValues.PATH_STROKE_WIDTH);

            tempPosition = getAttrPosition(xpp, "trimPathEnd");
            pathModel.setTrimPathEnd((tempPosition != -1) ?
                Float.parseFloat(xpp.getAttributeValue(tempPosition)) :
                DefaultValues.PATH_TRIM_PATH_END);

            tempPosition = getAttrPosition(xpp, "trimPathOffset");
            pathModel.setTrimPathOffset((tempPosition != -1) ?
                Float.parseFloat(xpp.getAttributeValue(tempPosition)) :
                DefaultValues.PATH_TRIM_PATH_OFFSET);

            tempPosition = getAttrPosition(xpp, "trimPathStart");
            pathModel.setTrimPathStart((tempPosition != -1) ?
                Float.parseFloat(xpp.getAttributeValue(tempPosition)) :
                DefaultValues.PATH_TRIM_PATH_START);

          } else if (name.equals("group")) {
            groupModel = new GroupModel();

            tempPosition = getAttrPosition(xpp, "name");
            groupModel.setName((tempPosition != -1) ? xpp.getAttributeValue(tempPosition) : null);

            tempPosition = getAttrPosition(xpp, "pivotX");
            groupModel.setPivotX((tempPosition != -1) ?
                Float.parseFloat(xpp.getAttributeValue(tempPosition)) :
                DefaultValues.GROUP_PIVOT_X);

            tempPosition = getAttrPosition(xpp, "pivotY");
            groupModel.setPivotY((tempPosition != -1) ?
                Float.parseFloat(xpp.getAttributeValue(tempPosition)) :
                DefaultValues.GROUP_PIVOT_Y);

            tempPosition = getAttrPosition(xpp, "rotation");
            groupModel.setRotation((tempPosition != -1) ?
                Float.parseFloat(xpp.getAttributeValue(tempPosition)) :
                DefaultValues.GROUP_ROTATION);

            tempPosition = getAttrPosition(xpp, "scaleX");
            groupModel.setScaleX((tempPosition != -1) ?
                Float.parseFloat(xpp.getAttributeValue(tempPosition)) :
                DefaultValues.GROUP_SCALE_X);

            tempPosition = getAttrPosition(xpp, "scaleY");
            groupModel.setScaleY((tempPosition != -1) ?
                Float.parseFloat(xpp.getAttributeValue(tempPosition)) :
                DefaultValues.GROUP_SCALE_Y);

            tempPosition = getAttrPosition(xpp, "translateX");
            groupModel.setTranslateX((tempPosition != -1) ?
                Float.parseFloat(xpp.getAttributeValue(tempPosition)) :
                DefaultValues.GROUP_TRANSLATE_X);

            tempPosition = getAttrPosition(xpp, "translateY");
            groupModel.setTranslateY((tempPosition != -1) ?
                Float.parseFloat(xpp.getAttributeValue(tempPosition)) :
                DefaultValues.GROUP_TRANSLATE_Y);

            parentModelStack.push(groupModel);
          } else if (name.equals("clip-path")) {
            clipPathModel = new ClipPathModel();

            tempPosition = getAttrPosition(xpp, "name");
            clipPathModel.setName((tempPosition != -1) ? xpp.getAttributeValue(tempPosition) : null);

            tempPosition = getAttrPosition(xpp, "pathData");
            clipPathModel.setPathData((tempPosition != -1) ? xpp.getAttributeValue(tempPosition) : null);
          }
          break;

        case XmlPullParser.END_TAG:
          if (name.equals("path")) {
            parentModelStack.peek().addChild(pathModel);
          } else if (name.equals("clip-path")) {
            parentModelStack.peek().addChild(clipPathModel);
          } else if (name.equals("group")) {
            ParentModel topGroupModel = parentModelStack.pop();
            parentModelStack.peek().addChild(topGroupModel);
          } else if (name.equals("vector")) {
            //we are done with parsing
          }
          break;
        }
        event = xpp.next();
      }
    } catch (XmlPullParserException | IOException e) {
      e.printStackTrace();
    }

    return vectorModel;

  }

}
