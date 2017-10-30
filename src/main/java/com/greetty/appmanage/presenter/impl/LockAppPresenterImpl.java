package com.greetty.appmanage.presenter.impl;

import android.content.Context;
import android.util.Log;

import com.greetty.appmanage.model.LockAppModel;
import com.greetty.appmanage.model.entity.AppInfo;
import com.greetty.appmanage.model.impl.LockAppModelImpl;
import com.greetty.appmanage.presenter.LockAppPresenter;
import com.greetty.appmanage.presenter.listener.OnLockAPPListener;
import com.greetty.appmanage.ui.view.LockAppView;

import java.util.List;

/**
 * Created by Greetty on 2017/9/26.
 */
public class LockAppPresenterImpl implements LockAppPresenter, OnLockAPPListener {

    private static final String TAG = "LockAppPresenterImpl";
    private LockAppView lockAppView;
    private LockAppModel lockAppModel;
    private boolean isShowLoading; //是否显示加载框

    public LockAppPresenterImpl(LockAppView lockAppView) {
        this.lockAppView = lockAppView;
        this.lockAppModel = new LockAppModelImpl();
    }

    @Override
    public void getLockApp(Context context, boolean isShowLoading) {
        Log.d(TAG, "isShowLoading: " + isShowLoading);
        this.isShowLoading = isShowLoading;
        if (isShowLoading)
            lockAppView.showLoading();
        lockAppModel.loadLockApp(context, this);
    }

    @Override
    public void onSuccess(List<AppInfo> list) {
        if (isShowLoading)
            lockAppView.hideLoading();
        lockAppView.displayLockApp(list);
    }

    @Override
    public void onError(Exception e) {
        if (isShowLoading) {
            lockAppView.hideLoading();
            lockAppView.showFailure(e);
        }
    }
}
