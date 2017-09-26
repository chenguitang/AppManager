package com.greetty.appmanage.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.greetty.appmanage.R;
import com.greetty.appmanage.app.RxApp;
import com.greetty.appmanage.base.BaseFragment;
import com.greetty.appmanage.model.entity.AppInfo;
import com.greetty.appmanage.presenter.UnLockAppPresenter;
import com.greetty.appmanage.presenter.impl.UnLockAppPresenterImpl;
import com.greetty.appmanage.ui.view.UnLockAppView;
import com.greetty.appmanage.util.LoadingUtil;

import java.util.List;

/**
 * Created by Greetty on 2017/9/23.
 *
 * 未加锁应用
 */

public class UnlockedFragment extends BaseFragment implements UnLockAppView {

    private static final String TAG = "UnlockedFragment";

    public static UnlockedFragment newInstance() {

        Bundle args = new Bundle();

        UnlockedFragment fragment = new UnlockedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private UnLockAppPresenter mUnLockAppPresenter;
    private LoadingUtil mLoadingUtil;

    @Override
    protected int initContentView() {
        return R.layout.fragment_unlocked_app;
    }

    @Override
    protected void init() {
        mUnLockAppPresenter=new UnLockAppPresenterImpl(this);
        mLoadingUtil=new LoadingUtil(getContext());
    }

    @Override
    protected void initData() {
        mUnLockAppPresenter.getLockApp(RxApp.getInstance());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void showLoading() {
//        Toast.makeText(RxApp.getInstance(), "showLoading111 . . .", Toast.LENGTH_SHORT).show();
        mLoadingUtil.showLoading();
    }

    @Override
    public void hideLoading() {
//        Toast.makeText(RxApp.getInstance(), "hideLoading111 . . .", Toast.LENGTH_SHORT).show();
        mLoadingUtil.dismissLoading();
    }

    @Override
    public void showFailure(Exception e) {
        Toast.makeText(RxApp.getInstance(), "出错了："+e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayLockApp(List<AppInfo> list) {
        for (int i=0;i<list.size();i++){
            Log.e(TAG, "packageName: "+list.get(i).getPackname());
        }
        Toast.makeText(RxApp.getInstance(), "显示应用", Toast.LENGTH_SHORT).show();

    }
}
