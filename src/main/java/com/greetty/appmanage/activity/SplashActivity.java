package com.greetty.appmanage.activity;

import android.os.Bundle;
import android.os.Handler;

import com.greetty.appmanage.R;
import com.greetty.appmanage.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";
    private Handler mHander=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        mHander.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(MainActivity.class,true);
            }
        },3000);
    }
}
