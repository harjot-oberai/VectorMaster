<div align="center"><img src="/screens/hourglass_animation.gif"/></div>

# Hourglass animation (Using clip paths)

#### hourglass_vector.xml
```xml
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportHeight="24"
    android:viewportWidth="24">
    <group
        android:name="hourglass_frame"
        android:scaleX="0.75"
        android:scaleY="0.75"
        android:translateX="12"
        android:translateY="12">
        <group
            android:name="hourglass_frame_pivot"
            android:translateX="-12"
            android:translateY="-12">
            <group
                android:name="group_2_2"
                android:translateX="12"
                android:translateY="6.5">
                <path
                    android:name="path_2_2"
                    android:fillColor="#5D5D5D"
                    android:pathData="M 6.52099609375 -3.89300537109 c 0.0 0.0 -6.52099609375 6.87901306152 -6.52099609375 6.87901306152 c 0 0.0 -6.52099609375 -6.87901306152 -6.52099609375 -6.87901306152 c 0.0 0.0 13.0419921875 0.0 13.0419921875 0.0 Z M 9.99800109863 -6.5 c 0.0 0.0 -19.9960021973 0.0 -19.9960021973 0.0 c -0.890991210938 0.0 -1.33700561523 1.07699584961 -0.707000732422 1.70700073242 c 0.0 0.0 10.7050018311 11.2929992676 10.7050018311 11.2929992676 c 0 0.0 10.7050018311 -11.2929992676 10.7050018311 -11.2929992676 c 0.630004882812 -0.630004882812 0.183990478516 -1.70700073242 -0.707000732422 -1.70700073242 Z" />
            </group>
            <group
                android:name="group_1_2"
                android:translateX="12"
                android:translateY="17.5">
                <path
                    android:name="path_2_1"
                    android:fillColor="#5D5D5D"
                    android:pathData="M 0 -2.98600769043 c 0 0.0 6.52099609375 6.87901306152 6.52099609375 6.87901306152 c 0.0 0.0 -13.0419921875 0.0 -13.0419921875 0.0 c 0.0 0.0 6.52099609375 -6.87901306152 6.52099609375 -6.87901306152 Z M 0 -6.5 c 0 0.0 -10.7050018311 11.2929992676 -10.7050018311 11.2929992676 c -0.630004882812 0.630004882812 -0.184005737305 1.70700073242 0.707000732422 1.70700073242 c 0.0 0.0 19.9960021973 0.0 19.9960021973 0.0 c 0.890991210938 0.0 1.33699035645 -1.07699584961 0.707000732422 -1.70700073242 c 0.0 0.0 -10.7050018311 -11.2929992676 -10.7050018311 -11.2929992676 Z" />
            </group>
        </group>
    </group>
    <group
        android:name="fill_outlines"
        android:scaleX="0.75"
        android:scaleY="0.75"
        android:translateX="12"
        android:translateY="12">
        <group
            android:name="fill_outlines_pivot"
            android:translateX="-12"
            android:translateY="-24">
            <clip-path
                android:name="mask_1"
                android:pathData="M 24 13.3999938965 c 0 0.0 -24 0.0 -24 0.0 c 0 0.0 0 10.6000061035 0 10.6000061035 c 0 0 24 0 24 0 c 0 0 0 -10.6000061035 0 -10.6000061035 Z" />
            <group
                android:name="group_fill_path"
                android:translateX="12"
                android:translateY="24">
                <path
                    android:name="fill_path"
                    android:fillColor="#5D5D5D"
                    android:pathData="M 10.7100067139 10.2900085449 c 0.629989624023 0.629989624023 0.179992675781 1.70999145508 -0.710006713867 1.70999145508 c 0 0 -20 0 -20 0 c -0.889999389648 0 -1.33999633789 -1.08000183105 -0.710006713867 -1.70999145508 c 0.0 0.0 9.76000976562 -10.2900085449 9.76000976563 -10.2900085449 c 0.0 0 -9.76000976562 -10.2899932861 -9.76000976563 -10.2899932861 c -0.629989624023 -0.630004882812 -0.179992675781 -1.71000671387 0.710006713867 -1.71000671387 c 0 0 20 0 20 0 c 0.889999389648 0 1.33999633789 1.08000183105 0.710006713867 1.71000671387 c 0.0 0.0 -9.76000976562 10.2899932861 -9.76000976563 10.2899932861 c 0.0 0 9.76000976562 10.2900085449 9.76000976563 10.2900085449 Z" />
            </group>
        </group>
    </group>
</vector>
```
<div align="center"><img src="/screens/clip_path_img_1.png"/></div>

# Flow
The desired animation is made up of 4 steps : 
1. Slide the clip-path down.
2. Rotate `hourglass_frame` and `fill_outlines` group by 180 degrees.
3. Slide the clip-path up (since the groups are rotated we need to move the clip-path up).
4. Rotate `hourglass_frame` and `fill_outlines` group by 180 degrees. **Repeat**

#### Java

```java
VectorMasterView hourglassView;

float translationY = 0;
float rotation = 0;
int state = 0;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    VectorMasterView hourglassView = (VectorMasterView) findViewById(R.id.hg_vector);
    animateHourglass();
}

void animateHourglass() {
    final GroupModel frame = hourglassView.getGroupModelByName("hourglass_frame");
    final GroupModel fillOutlines = hourglassView.getGroupModelByName("fill_outlines");
    final GroupModel fillOutlinesPivot = hourglassView.getGroupModelByName("fill_outlines_pivot");
    final GroupModel group_fill_path = hourglassView.getGroupModelByName("group_fill_path");

    ClipPathModel mask = hourglassView.getClipPathModelByName("mask_1");

    state = 0;
    translationY = -24;

    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
        @Override
        public void run() {
            if (state == 0) {	// Slide the clip-path down by changing translationY of parent group
                translationY += 0.3f;
                fillOutlinesPivot.setTranslateY(translationY);
                group_fill_path.setTranslateY(-1 * translationY);
                if (translationY >= -12) {
                    state = 1;
                }
            } else if (state == 1) {	// Rotate the groups by 180 degress
                rotation += 3f;
                frame.setRotation(rotation);
                fillOutlines.setRotation(rotation);
                if (rotation == 180) {
                    state = 2;
                }
            } else if (state == 2) {	// Slide the clip-path up by changing translationY of parent group
                translationY -= 0.3f;
                fillOutlinesPivot.setTranslateY(translationY);
                group_fill_path.setTranslateY(-1 * translationY);
                if (translationY <= -24) {
                    state = 3;
                }
            } else if (state == 3) { 	// Rotate the groups by 180 degress
                rotation += 3f;
                frame.setRotation(rotation);
                fillOutlines.setRotation(rotation);
                if (rotation == 360) {
                    rotation = 0;
                    state = 0;
                }
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hourglassView.update();		// Update the view from the UI thread
                }
            });
        }
    }, 500, 1000 / 60);
}
```

#### Result
<div align="center"><img src="/screens/hourglass_animation.gif"/></div>
