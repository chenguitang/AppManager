package com.greetty.appmanage.presenter.impl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.greetty.appmanage.model.UnLockAppModel;
import com.greetty.appmanage.model.entity.AppInfo;
import com.greetty.appmanage.model.impl.UnLockAppModelImpl;
import com.greetty.appmanage.presenter.UnLockAppPresenter;
import com.greetty.appmanage.presenter.listener.OnUnLockAPPListener;
import com.greetty.appmanage.ui.view.UnLockAppView;

import java.util.List;

/**
 * Created by Greetty on 2017/9/26.
 */
public class UnLockAppPresenterImpl implements UnLockAppPresenter, OnUnLockAPPListener {

    private static final String TAG = "UnLockAppPresenterImpl";
    private UnLockAppView unLockAppView;
    private UnLockAppModel unLockAppModel;

    public UnLockAppPresenterImpl(UnLockAppView unLockAppView) {
        if (unLockAppView == null) {
            Log.e(TAG, "我是空的，你不能初始化我: ");

        }
        this.unLockAppView = unLockAppView;
        this.unLockAppModel = new UnLockAppModelImpl();
    }

    @Override
    public void getLockApp(Context context) {
        unLockAppView.showLoading();
        unLockAppModel.loadLockApp(context, this);
    }

    @Override
    public void onSuccess(List<AppInfo> list) {
        unLockAppView.hideLoading();
        unLockAppView.displayLockApp(list);
    }

    @Override
    public void onError(Exception e) {
        unLockAppView.hideLoading();
        unLockAppView.showFailure(e);
    }
}
