<div align="center"><img src="/screens/cover_2.gif" width="600"/></div>

# VectorMaster
This library introduces dynamic control over vector drawables. Each and every aspect of a vector drawable can be controlled dynamically(via Java instances), using this library. 

Features :

- <b>Control</b> : Control every attribute related to `path`, `group`, `vector` and `clip-path` like **color**, **alpha**, **strokeWdith**, **translation**, **scale**, **rotation** etc.  
- <b>Clip Paths</b> : The library supports clip paths.
- <b>Trimming</b> : The library allows trimming of path by using `trimEnd`, `trimStart` and `trimOffset` parameters.

# Usage
Just add the following dependency in your app's `build.gradle`
```groovy
dependencies {
      compile 'com.sdsmdg.harjot:vectormaster:1.0.1'
}
```

# Background and Working
`VectorDrawables` are really helpful for removing scaling problem but they lack control. Most of the changes to vector drawables are only possible by creating an `AnimatedVectorDrawable` and also defining animations. All of this is good but lacks control that may be required during runtime. 

For example, if we need to change vector's properties *(Say, color)* based on a user action *(Say, if user is choosing the theme of app)*. We can achieve this using `AnimatedVectorDrawable` but only to an extent, this approach can't be used if user action leads to an infinite number of cases *(Say, if user picks up a random color for theme)* and we need to change property of the vector for each case. Thus we need a mechanism that can be used to change a vector's properties at runtime using basic methods like `setColor`, `setScale`, `setTranslation` etc. This is where this library comes in.

The working of the library is as follows :
- First the `vector.xml`*(the `VectorDrawable` that we wish to control)*, is parsed using `XmlPullParser` and the attributes are stored in Models corresponding to the tag.
- `vector` attributes are stored in `VectorModel`, `group` attributes in `GroupModel`, `path` atrributes in `PathModel` and `clip-path` attributes in `ClipPathModel`. The hierarchy is as follows :<br><br><div align="center"><img src="/screens/Hierarchy.png" width="600"/></div><br>
- The `pathData` in `PathModel` is then parsed using `PathParser.java`; It parses the string data and converts it into a `Path` object.
- All the transformations, scaling etc are done using **Matrices** after the `Path` object is built. All this is done prior to the first draw on canvas.
- At first draw we have the same output as we should have got if we used inbuilt methods to draw the `vector.xml` using `srcCompat`.
- Now, all Models are accessible via `getModelByName(...)` public methods that can be directly called via the instance of `VectorMasterView` that we get using `findViewById(...)`.
- If we wish to change any value, we just need to call `model.setParamter(...)`. `model` is of type `VectorModel`, `GroupModel`, `PathModel` or `ClipPathModel`. `parameter` can be anything like **color**, **scale**, **rotation** etc. depending on the model we are using.
- After setting a paramter the necesarry `paints` and `paths` are rebuilt, scaled, transformed etc.
- A call to `update` method repaints the canvas with the required changes.
