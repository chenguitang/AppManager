package com.greetty.appmanage.ui.fragment;

import android.os.Bundle;

import com.greetty.appmanage.R;
import com.greetty.appmanage.base.BaseFragment;

/**
 * Created by Greetty on 2017/9/23.
 *
 * 未加锁应用
 */

public class UnlockedFragment extends BaseFragment {

    public static UnlockedFragment newInstance() {

        Bundle args = new Bundle();

        UnlockedFragment fragment = new UnlockedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initContentView() {
        return R.layout.fragment_unlocked_app;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }
}
