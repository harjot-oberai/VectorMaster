package com.sdsmdg.harjot.vectormaster.models;

import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.ArrayList;

public abstract class ParentModel extends Model {

  private ArrayList<Model> children = new ArrayList<>(0);

  public ArrayList<Model> getChildren() {
    return children;
  }

  public void addChild(Model model) {
    getChildren().add(model);
    model.setParent(this);
  }

  @Override
  public void init() {
    for (Model child : getChildren()) {
      child.init();
    }
  }

  @Override
  public void prepare(Canvas canvas, Matrix parentTransformation, float strokeRatio) {
    //parents by default do not prepare the canvas recursively
  }

  @Override
  public void draw(Canvas canvas, Matrix parentTransformation, float strokeRatio) {
    //parents prepare just their own children before they draw
    for (Model child : getChildren()) {
      child.prepare(canvas, parentTransformation, strokeRatio);
    }
    for (Model child : getChildren()) {
      child.draw(canvas, parentTransformation, strokeRatio);
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
