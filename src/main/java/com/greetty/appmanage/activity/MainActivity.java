package com.greetty.appmanage.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.greetty.appmanage.R;
import com.greetty.appmanage.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {

    }
}
