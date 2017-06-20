package com.sdsmdg.harjot.vectormasterdemo;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.sdsmdg.harjot.vectormaster.VectorMasterView;

public class MainActivity extends AppCompatActivity {

    VectorMasterView vectorMasterView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vectorMasterView = (VectorMasterView) findViewById(R.id.vector_master);
        imageView = (ImageView) findViewById(R.id.vector_compat);

    }
}
