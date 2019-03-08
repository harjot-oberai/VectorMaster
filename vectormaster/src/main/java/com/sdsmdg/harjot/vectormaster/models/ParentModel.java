package com.sdsmdg.harjot.vectormaster.models;

import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.ArrayList;

public abstract class ParentModel extends Model {

  private ArrayList<Model> children = new ArrayList<>(0);
  private Matrix scaleMatrix;
  private Matrix transformMatrix;

  public ArrayList<Model> getChildren() {
    return children;
  }

  public void addChild(Model model) {
    getChildren().add(model);
    model.setParent(this);
  }

  public Matrix getTransformMatrix() {
    return transformMatrix;
  }

  protected Matrix createTransformMatrix() {
    return new Matrix();
  }

  @Override
  public void calculateStatic() {
    //create own matrix
    transformMatrix = createTransformMatrix();
    //add the one from the parrent if there is a parent
    if (getParent() != null) {
      transformMatrix.postConcat(getParent().getTransformMatrix());
    }
    //tell all children to do the calculation
    for (Model model : getChildren()) {
      model.calculateStatic();
    }
  }

  @Override
  public void scalePaths(Matrix originalScaleMatrix, Matrix concatTransformMatrix) {
    this.scaleMatrix = originalScaleMatrix;
    Matrix finalTransformMatrix = new Matrix(getTransformMatrix());
    finalTransformMatrix.postConcat(scaleMatrix);
    for (Model child : children) {
      child.scalePaths(originalScaleMatrix, finalTransformMatrix);
    }
  }

  public void scalePaths(Matrix originalScaleMatrix) {
    scalePaths(originalScaleMatrix, null); //because we are a parentModel only the first Matrix matters the second one is not used.
  }

  public void updateAndScalePaths() {
    if (scaleMatrix != null) {
      calculateStatic();
      scalePaths(scaleMatrix);
    }
  }

  @Override
  public void scaleStrokeWidth(float ratio) {
    for (Model model : getChildren()) {
      model.scaleStrokeWidth(ratio);
    }
  }

  @Override
  public void prepare(Canvas canvas, float offsetX, float offsetY, float scaleX, float scaleY) {
    //parents by default do not prepare the canvas recursively
  }

  @Override
  public void draw(Canvas canvas, float offsetX, float offsetY, float scaleX, float scaleY) {
    //parents prepare just their own children before they draw
    for (Model child : getChildren()) {
      child.prepare(canvas, offsetX, offsetY, scaleX, scaleY);
    }
    for (Model child : getChildren()) {
      child.draw(canvas, offsetX, offsetY, scaleX, scaleY);
    }
  }

  public GroupModel getGroupModelByName(String name) {
    return (GroupModel) getModelByTypeAndName(GroupModel.class, name);
  }

  public PathModel getPathModelByName(String name) {
    return (PathModel) getModelByTypeAndName(PathModel.class, name);
  }

  public ClipPathModel getClipPathModelByName(String name) {
    return (ClipPathModel) getModelByTypeAndName(ClipPathModel.class, name);
  }

  public Model getModelByTypeAndName(Class<? extends Model> clazz, String name) {
    if (name == null) {
      return null;
    }
    for (Model child : children) {
      //check if the child is the searched model
      if (name.equals(child.getName()) && clazz.isAssignableFrom(child.getClass())) {
        return child;
      }
      //ask the child to check its children for the searched model
      if (child instanceof ParentModel) {
        Model subchild = ((ParentModel) child).getModelByTypeAndName(clazz, name);
        if (subchild != null) {
          return subchild;
        }
      }
      //its not this child and not one of its children or children-children, so go on to the next child
    }
    return null; //searched element was not found withis this parent
  }

}
