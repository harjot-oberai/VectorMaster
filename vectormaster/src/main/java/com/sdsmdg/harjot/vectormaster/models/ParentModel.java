package com.sdsmdg.harjot.vectormaster.models;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;

import java.util.ArrayList;

public abstract class ParentModel extends Model {

  private ArrayList<Model> children = new ArrayList<>(0);

  /**
   * If you want to add a model consider using addChild() instead.
   * Gets the list of children from this parent model.
   * Make sure to add/remove/insert models only on the main-thread because the list of children is not thread save.
   * Also make sure to call childModel.setParent(parent) when adding children to this list.
   */
  public ArrayList<Model> getChildren() {
    return children;
  }

  /**
   * Adds a child model to this parent model. Also automatically sets the parent on the child model.
   * Make sure to add new models on the main-thread because the list of children is not thread save.
   * @param model
   */
  public void addChild(Model model) {
    model.setParent(this);
    getChildren().add(model);
  }

  @Override
  public void calculate(Matrix parentTransformation, Boolean transformationChanged, float strokeRatio) {
    for (Model child : getChildren()) {
      child.calculate(parentTransformation, transformationChanged, strokeRatio);
    }
  }

  @Override
  public void prepare(Canvas canvas) {
    //parents by default do not prepare the canvas recursively
  }

  @Override
  public void draw(Canvas canvas) {
    //parents prepare just their own children before they draw
    for (Model child : getChildren()) {
      child.prepare(canvas);
    }
    for (Model child : getChildren()) {
      child.draw(canvas);
    }
  }

  @Override
  public void collectFullPath(Path collectedPath) {
    for (Model child : getChildren()) {
      child.collectFullPath(collectedPath);
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
