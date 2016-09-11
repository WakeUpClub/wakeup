package com.wakeup.forever.wakeup.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.baidu.mapapi.SDKInitializer;
import com.wakeup.forever.wakeup.R;

public class RunTraceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SDKInitializer.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_trace);
    }
}
