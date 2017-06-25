package com.sdsmdg.harjot.vectormasterdemo;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sdsmdg.harjot.vectormaster.VectorMasterView;
import com.sdsmdg.harjot.vectormaster.models.ClipPathModel;
import com.sdsmdg.harjot.vectormaster.models.GroupModel;
import com.sdsmdg.harjot.vectormaster.models.PathModel;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Typeface typeface;
    TextView text;

    VectorMasterView lightningView, hourglassView, searchBackView, rainView;

    float trimStart = 0;
    float trimEnd = 0;
    int countLightning = 60;

    float translationY = 0;
    float rotation = 0;
    int state = 0;

    float stemTrimStart = 0;
    float stemTrimEnd = 0.185f;

    float circleTrimEnd = 1;

    float arrowHeadTopTrimEnd = 0;
    float arrowHeadBottomTrimEnd = 0;

    int searchBackState = 0;
    int count = 60;

    float rainTrimStart_C = 0;
    float rainTrimEnd_C = 0;
    int rainCount_C = 60;
    float rainTrimStart_L = 0;
    float rainTrimEnd_L = 0;
    int rainCount_L = 55;
    float rainTrimStart_R = 0;
    float rainTrimEnd_R = 0;
    int rainCount_R = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        typeface = Typeface.createFromAsset(getAssets(), "fonts/adequate.ttf");
        text = (TextView) findViewById(R.id.text);
        text.setTypeface(typeface);

        lightningView = (VectorMasterView) findViewById(R.id.vector_master);
        animateLightning();

        hourglassView = (VectorMasterView) findViewById(R.id.vector_master_1);
        animateHourglass();

        searchBackView = (VectorMasterView) findViewById(R.id.vector_master_2);
        animateSearchBack();

        rainView = (VectorMasterView) findViewById(R.id.vector_master_3);
        animateRain();

    }

    void animateLightning() {
        PathModel cloudModel = lightningView.getPathModelByName("cloud");
        cloudModel.setStrokeColor(Color.parseColor("#5D5D5D"));
        final PathModel lightningModel = lightningView.getPathModelByName("lightning");
        lightningModel.setStrokeColor(Color.parseColor("#FFD700"));
        lightningModel.setTrimPathEnd(0.0f);
        lightningModel.setTrimPathStart(0.0f);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                countLightning++;
                if (countLightning >= 60) {
                    if (trimEnd < 1) {
                        trimEnd += 0.04f;
                    } else if (trimEnd >= 1 && trimStart < 1) {
                        trimStart += 0.04f;
                    } else if (trimEnd >= 1 && trimStart >= 1) {
                        trimEnd = 0;
                        trimStart = 0;
                        countLightning = 0;
                    }
                    lightningModel.setTrimPathEnd(trimEnd);
                    lightningModel.setTrimPathStart(trimStart);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lightningView.update();
                        }
                    });
                }
            }
        }, 500, 1000 / 60);
    }

    void animateHourglass() {
        final GroupModel frame = hourglassView.getGroupModelByName("hourglass_frame");
        final GroupModel fillOutlines = hourglassView.getGroupModelByName("fill_outlines");
        final GroupModel fillOutlinesPivot = hourglassView.getGroupModelByName("fill_outlines_pivot");
        final GroupModel group_fill_path = hourglassView.getGroupModelByName("group_fill_path");

        state = 0;
        translationY = -24;

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (state == 0) {
                    translationY += 0.3f;
                    fillOutlinesPivot.setTranslateY(translationY);
                    group_fill_path.setTranslateY(-1 * translationY);
                    if (translationY >= -12) {
                        state = 1;
                    }
                } else if (state == 1) {
                    rotation += 3f;
                    frame.setRotation(rotation);
                    fillOutlines.setRotation(rotation);
                    if (rotation == 180) {
                        state = 2;
                    }
                } else if (state == 2) {
                    translationY -= 0.3f;
                    fillOutlinesPivot.setTranslateY(translationY);
                    group_fill_path.setTranslateY(-1 * translationY);
                    if (translationY <= -24) {
                        state = 3;
                    }
                } else if (state == 3) {
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
                        hourglassView.update();
                    }
                });
            }
        }, 500, 1000 / 60);

    }

    void animateSearchBack() {
        final PathModel searchCircle = searchBackView.getPathModelByName("search_circle");
        final PathModel stem = searchBackView.getPathModelByName("stem");
        final PathModel arrowUp = searchBackView.getPathModelByName("arrow_head_top");
        final PathModel arrowDown = searchBackView.getPathModelByName("arrow_head_bottom");

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                count++;
                if (count >= 50) {
                    if (searchBackState == 0) {
                        circleTrimEnd -= 1.0f / 20;
                        stemTrimStart += 0.75f / 20;
                        stemTrimEnd += (1 - 0.185f) / 20;
                        arrowHeadBottomTrimEnd += 1.0f / 20;
                        arrowHeadTopTrimEnd += 1.0f / 20;
                        if (circleTrimEnd <= 0) {
                            searchBackState = 1;
                            count = 0;
                        }
                    } else if (searchBackState == 1) {
                        arrowHeadBottomTrimEnd -= 1.0f / 20;
                        arrowHeadTopTrimEnd -= 1.0f / 20;
                        stemTrimStart -= 0.75f / 20;
                        stemTrimEnd -= (1 - 0.185f) / 20;
                        circleTrimEnd += 1.0f / 20;
                        if (circleTrimEnd >= 1) {
                            searchBackState = 0;
                            count = 0;
                        }
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

            }
        }, 1000, 1000 / 60);
    }

    void animateRain() {
        final PathModel rainLeft = rainView.getPathModelByName("rain_left");
        final PathModel rainCenter = rainView.getPathModelByName("rain_center");
        final PathModel rainRight = rainView.getPathModelByName("rain_right");

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                rainCount_C++;
                if (rainCount_C >= 60) {
                    if (rainTrimEnd_C < 1) {
                        rainTrimEnd_C += 0.04f;
                    } else if (rainTrimEnd_C >= 1 && rainTrimStart_C < 1) {
                        rainTrimStart_C += 0.04f;
                    } else if (rainTrimEnd_C >= 1 && rainTrimStart_C >= 1) {
                        rainTrimEnd_C = 0;
                        rainTrimStart_C = 0;
                        rainCount_C = 0;
                    }
                }
                rainCount_L++;
                if (rainCount_L >= 60) {
                    if (rainTrimEnd_L < 1) {
                        rainTrimEnd_L += 0.04f;
                    } else if (rainTrimEnd_L >= 1 && rainTrimStart_L < 1) {
                        rainTrimStart_L += 0.04f;
                    } else if (rainTrimEnd_L >= 1 && rainTrimStart_L >= 1) {
                        rainTrimEnd_L = 0;
                        rainTrimStart_L = 0;
                        rainCount_L = 0;
                    }
                }
                rainCount_R++;
                if (rainCount_R >= 60) {
                    if (rainTrimEnd_R < 1) {
                        rainTrimEnd_R += 0.04f;
                    } else if (rainTrimEnd_R >= 1 && rainTrimStart_R < 1) {
                        rainTrimStart_R += 0.04f;
                    } else if (rainTrimEnd_R >= 1 && rainTrimStart_R >= 1) {
                        rainTrimEnd_R = 0;
                        rainTrimStart_R = 0;
                        rainCount_R = 0;
                    }
                }

                rainCenter.setTrimPathEnd(rainTrimEnd_C);
                rainCenter.setTrimPathStart(rainTrimStart_C);

                rainLeft.setTrimPathEnd(rainTrimEnd_L);
                rainLeft.setTrimPathStart(rainTrimStart_L);

                rainRight.setTrimPathEnd(rainTrimEnd_R);
                rainRight.setTrimPathStart(rainTrimStart_R);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rainView.update();
                    }
                });

            }
        }, 500, 1000 / 60);

    }

}
