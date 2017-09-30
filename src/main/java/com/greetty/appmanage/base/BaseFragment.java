package com.greetty.appmanage.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Greetty on 2017/9/23.
 * <p>
 * Fragment基类
 */

public abstract class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";

    //Fragment的View加载完毕的标记
    private boolean isViewCreated;
    //Fragment对用户可见的标记
    private boolean isUIVisible;
    private Unbinder unbinder;
    protected AppCompatActivity mActivity;
    protected Context mContext;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(initContentView(), container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        isViewCreated = true;
        lazyLoad();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();
        mContext = getContext();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }

    /**
     * 判断是否需要加载数据
     */
    private void lazyLoad() {
        //这里进行双重标记判断，是因为setUserVisibleHint会多次回调，而且会在onCreateView执行前回调，
        // 必须确保onCreateView加载完毕且页面可见，才加载数据
        if (isViewCreated && isUIVisible) {
            initData();
            //数据加载完毕，恢复标记，防止重复加载
            isViewCreated = false;
            isUIVisible = false;
        }
    }

    protected abstract int initContentView();

    protected abstract void init();

    protected abstract void initData();

    public static Fragment newInstance() {
        return new Fragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
