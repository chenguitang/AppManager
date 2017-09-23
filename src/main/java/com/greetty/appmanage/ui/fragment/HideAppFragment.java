package com.greetty.appmanage.ui.fragment;

import android.os.Bundle;

import com.greetty.appmanage.R;
import com.greetty.appmanage.base.BaseFragment;

/**
 * Created by Greetty on 2017/9/23.
 *
 * 隐藏应用
 */

public class HideAppFragment extends BaseFragment {

    public static HideAppFragment newInstance() {

        Bundle args = new Bundle();

        HideAppFragment fragment = new HideAppFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initContentView() {
        return R.layout.fragment_hide_app;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }
}
