package com.sdsmdg.harjot.vectormasterdemo;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sdsmdg.harjot.vectormaster.VectorMasterView;
import com.sdsmdg.harjot.vectormaster.models.GroupModel;
import com.sdsmdg.harjot.vectormaster.models.PathModel;

public class MainActivity extends AppCompatActivity {

    VectorMasterView vectorMasterView;
    GroupModel groupModel;
    PathModel pathModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vectorMasterView = (VectorMasterView) findViewById(R.id.vector_master);

        groupModel = vectorMasterView.getGroupModelByName("rotationGroup");
        pathModel = vectorMasterView.getPathModelByName("draw");

        vectorMasterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupModel.setRotation(45);
                pathModel.setFillColor(Color.GREEN);
                vectorMasterView.update();
            }
        });

    }
}
