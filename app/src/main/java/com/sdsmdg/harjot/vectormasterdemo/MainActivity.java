package com.sdsmdg.harjot.vectormasterdemo;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sdsmdg.harjot.vectormaster.VectorMasterView;
import com.sdsmdg.harjot.vectormaster.models.PathModel;

public class MainActivity extends AppCompatActivity {

    VectorMasterView vectorMasterView;
    PathModel cloudModel;
    PathModel lightningModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vectorMasterView = (VectorMasterView) findViewById(R.id.vector_master);
        cloudModel = vectorMasterView.getPathModelByName("cloud");
        lightningModel = vectorMasterView.getPathModelByName("lightning");

        cloudModel.setStrokeColor(Color.LTGRAY);
//        vectorMasterView.post(new Runnable() {
//            @Override
//            public void run() {
//                animateColor(Color.BLACK, Color.parseColor("#FFD700"));
//            }
//        });

        vectorMasterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lightningModel.setStrokeWidth(lightningModel.getStrokeWidth() - 0.2f);
                vectorMasterView.update();
            }
        });

    }

    void animateColor(int from, int to) {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), from, to);
        colorAnimation.setDuration(3000); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                lightningModel.setStrokeColor((int) animator.getAnimatedValue());
                vectorMasterView.update();
            }
        });
        colorAnimation.start();
    }
}
