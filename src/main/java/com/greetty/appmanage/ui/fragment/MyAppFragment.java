package com.greetty.appmanage.ui.fragment;

import android.os.Bundle;

import com.greetty.appmanage.R;
import com.greetty.appmanage.base.BaseFragment;

/**
 * Created by Greetty on 2017/9/23.
 *
 * 我的->本应用设置
 */

public class MyAppFragment extends BaseFragment {

    public static MyAppFragment newInstance() {

        Bundle args = new Bundle();

        MyAppFragment fragment = new MyAppFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initContentView() {
        return R.layout.fragment_my_app_setting;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }
}
