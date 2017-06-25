<div align="center"><img src="/screens/search_back.gif" width = 250/></div>

# Search-Back animation

#### search_back_vector.xml
```xml
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="48dp"
    android:height="24dp"
    android:viewportHeight="24"
    android:viewportWidth="48">

    <group
        android:pivotX="24"
        android:pivotY="12">
        <path
            android:name="stem"
            android:pathData="M24.7,12.7 C24.70,12.7 31.8173374,19.9066081 31.8173371,19.9066082 C32.7867437,20.7006357 34.4599991,23 37.5,23 C40.54,23 43,20.54 43,17.5 C43,14.46 40.54,12 37.5,12 C34.46,12 33.2173088,12 31.8173371,12 C31.8173374,12 18.8477173,12 18.8477173,12"
            android:strokeColor="#5D5D5D"
            android:strokeWidth="2"
            android:trimPathEnd="0.185"
            android:trimPathStart="0" />

        <path
            android:name="search_circle"
            android:pathData="M25.39,13.39 A 5.5 5.5 0 1 1 17.61 5.61 A 5.5 5.5 0 1 1 25.39 13.39"
            android:strokeColor="#5D5D5D"
            android:strokeWidth="2"
            android:trimPathEnd="1" />

        <group android:name="arrow_head">
            <path
                android:name="arrow_head_top"
                android:pathData="M16.7017297,12.6957157 L24.7043962,4.69304955"
                android:strokeColor="#5D5D5D"
                android:strokeWidth="2"
                android:trimPathEnd="0" />

            <path
                android:name="arrow_head_bottom"
                android:pathData="M16.7107986,11.2764828 L24.7221527,19.2878361"
                android:strokeColor="#5D5D5D"
                android:strokeWidth="2"
                android:trimPathEnd="0" />
        </group>
    </group>
</vector>
```
<div align="center"><img src="/screens/search_back_img_1.png"/></div>

# Flow
1. Transitions have to take place when the view is clicked.
2. `searchBackState` store the current state. *(0 --> Search visible, 1 --> Back visible)*
3. If searchBackState == 0
	- Animate `search_circle` trimEnd from **1** to **0**
	- Animate `stem` trimStart from **0** to **0.75** and trimEnd from **0.185** to **1**.
	- Animate `arrow_head_top` and `arrow_head_bottom` trimEnd from **0** to **1**.
	- Set searchBackState = 1.
4. If searchBackState == 1
	- Animate `search_circle` trimEnd from **0** to **1**
	- Animate `stem` trimStart from **0.75** to **0** and trimEnd from **1** to **0.185**.
	- Animate `arrow_head_top` and `arrow_head_bottom` trimEnd from **1** to **0**.
	- Set searchBackState = 0.

#### Java
```java
int searchBackState = 0;
PathModel searchCircle, stem, arrowUp, arrowDown;
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	searchBackView = (VectorMasterView) findViewById(R.id.search_back_vector);
	searchCircle = searchBackView.getPathModelByName("search_circle");
	stem = searchBackView.getPathModelByName("stem");
	arrowUp = searchBackView.getPathModelByName("arrow_head_top");
	arrowDown = searchBackView.getPathModelByName("arrow_head_bottom");
	searchBackView.setOnClickListener(new View.OnClickListener() {
	    @Override
	    public void onClick(View view) {
	        if (searchBackState == 0) {
	            animateSearchToBack();
	        } else {
	            animateBackToSearch();
	        }
	    }
	});
}

void animateSearchToBack() {
    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
        @Override
        public void run() {
            circleTrimEnd -= 1.0f / 20;
            stemTrimStart += 0.75f / 20;
            stemTrimEnd += (1 - 0.185f) / 20;
            arrowHeadBottomTrimEnd += 1.0f / 20;
            arrowHeadTopTrimEnd += 1.0f / 20;
            if (circleTrimEnd <= 0) {
                searchBackState = 1;
                cancel();
            }

            searchCircle.setTrimPathEnd(circleTrimEnd);
            stem.setTrimPathEnd(stemTrimEnd);
            stem.setTrimPathStart(stemTrimStart);
            arrowUp.setTrimPathEnd(arrowHeadTopTrimEnd);
            arrowDown.setTrimPathEnd(arrowHeadBottomTrimEnd);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    searchBackView.update();
                }
            });

        }
    }, 0, 1000 / 60);
}

void animateBackToSearch() {
    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
        @Override
        public void run() {
            arrowHeadBottomTrimEnd -= 1.0f / 20;
            arrowHeadTopTrimEnd -= 1.0f / 20;
            stemTrimStart -= 0.75f / 20;
            stemTrimEnd -= (1 - 0.185f) / 20;
            circleTrimEnd += 1.0f / 20;
            if (circleTrimEnd >= 1) {
                searchBackState = 0;
                cancel();
            }

            searchCircle.setTrimPathEnd(circleTrimEnd);
            stem.setTrimPathEnd(stemTrimEnd);
            stem.setTrimPathStart(stemTrimStart);
            arrowUp.setTrimPathEnd(arrowHeadTopTrimEnd);
            arrowDown.setTrimPathEnd(arrowHeadBottomTrimEnd);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    searchBackView.update();
                }
            });
        }
    }, 0, 1000 / 60);
}

```

#### Result
<div align="center"><img src="/screens/search_back.gif" width="250"/></div>