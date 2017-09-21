package com.greetty.appmanage.ui.activity;

import android.os.Bundle;

import com.greetty.appmanage.R;
import com.greetty.appmanage.base.BaseActivity;

/**
* created by Greetty at 2017/9/21 13:09
*
* MainActivity 主Activity
*/
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
