package com.sdsmdg.harjot.vectormasterdemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sdsmdg.harjot.vectormaster.VectorMasterView;
import com.sdsmdg.harjot.vectormaster.models.GroupModel;
import com.sdsmdg.harjot.vectormaster.models.PathModel;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    VectorMasterView lightningView;
    GroupModel groupModel;
    PathModel cloudModel, lightningModel;

    float trimStart = 0;
    float trimEnd = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lightningView = (VectorMasterView) findViewById(R.id.vector_master_100);
        animateLightning();


    }

    void animateLightning() {
        cloudModel = lightningView.getPathModelByName("cloud");
        cloudModel.setStrokeColor(Color.parseColor("#F1F1F1"));
        lightningModel = lightningView.getPathModelByName("lightning");
        lightningModel.setStrokeColor(Color.parseColor("#FFD700"));
        lightningModel.setTrimPathEnd(0.0f);
        lightningModel.setTrimPathStart(0.0f);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (trimEnd < 1) {
                    trimEnd += 0.04f;
                } else if (trimEnd >= 1 && trimStart < 1) {
                    trimStart += 0.04f;
                } else if (trimEnd >= 1 && trimStart >= 1) {
                    trimEnd = 0;
                    trimStart = 0;
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
        }, 500, 1000 / 60);
    }

}
