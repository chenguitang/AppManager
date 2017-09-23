package com.greetty.appmanage.ui.fragment;

import android.os.Bundle;

import com.greetty.appmanage.R;
import com.greetty.appmanage.base.BaseFragment;

/**
 * Created by Greetty on 2017/9/23.
 *
 * 已加锁应用
 */

public class LockedFragment extends BaseFragment {

    public static LockedFragment newInstance() {

        Bundle args = new Bundle();

        LockedFragment fragment = new LockedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initContentView() {
        return R.layout.fragment_locked_app;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }
}
