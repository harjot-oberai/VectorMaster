package com.sdsmdg.harjot.vectormasterdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sdsmdg.harjot.vectormaster.VectorMasterView;
import com.sdsmdg.harjot.vectormaster.models.PathModel;

public class MainActivity extends AppCompatActivity {

    VectorMasterView vectorMasterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vectorMasterView = (VectorMasterView) findViewById(R.id.vector_master);

    }
}
