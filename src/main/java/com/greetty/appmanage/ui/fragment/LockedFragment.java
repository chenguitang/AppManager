package com.greetty.appmanage.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.greetty.appmanage.R;
import com.greetty.appmanage.base.BaseFragment;
import com.greetty.appmanage.model.entity.AppInfo;
import com.greetty.appmanage.presenter.LockAppPresenter;
import com.greetty.appmanage.presenter.impl.LockAppPresenterImpl;
import com.greetty.appmanage.ui.view.LockAppView;
import com.greetty.appmanage.util.LoadingUtil;

import java.util.List;

/**
 * Created by Greetty on 2017/9/23.
 *
 * 已加锁应用
 */

public class LockedFragment extends BaseFragment implements LockAppView {

    private static final String TAG = "LockedFragment";

    public static LockedFragment newInstance() {

        Bundle args = new Bundle();

        LockedFragment fragment = new LockedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private LockAppPresenter mLockAppPresenter;
    private LoadingUtil mLoadingUtil;


    @Override
    protected int initContentView() {
        return R.layout.fragment_locked_app;
    }

    @Override
    protected void init() {
        mLockAppPresenter=new LockAppPresenterImpl(this);
        mLoadingUtil=new LoadingUtil(getContext());
    }

    @Override
    protected void initData() {
        mLockAppPresenter.getLockApp(mContext);
    }

    @Override
    public void showLoading() {
//        Log.e(TAG, "showLoading: ");
//        Toast.makeText(mContext, "showLoading . . .", Toast.LENGTH_SHORT).show();
        mLoadingUtil.showLoading();
    }

    @Override
    public void hideLoading() {
//        Log.e(TAG, "hideLoading: ");
//        Toast.makeText(mContext, "hideLoading . . .", Toast.LENGTH_SHORT).show();
        mLoadingUtil.dismissLoading();
    }

    @Override
    public void showFailure(Exception e) {
        Toast.makeText(mContext, "error："+e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayLockApp(List<AppInfo> list) {
        Toast.makeText(mContext, list.size()>0?list.get(0).getName():"空空如也", Toast.LENGTH_SHORT).show();
    }
}
