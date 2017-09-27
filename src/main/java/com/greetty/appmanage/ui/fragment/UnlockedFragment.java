package com.greetty.appmanage.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import butterknife.BindView;

/**
 * Created by Greetty on 2017/9/23.
 *
 * 未加锁应用
 */

public class UnlockedFragment extends BaseFragment implements UnLockAppView {

    private static final String TAG = "UnlockedFragment";

    @BindView(R.id.rv_unlock_list)
    RecyclerView rvUnLockList;

    private UnLockAppPresenter mUnLockAppPresenter;
    private LoadingUtil mLoadingUtil;

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
        mUnLockAppPresenter=new UnLockAppPresenterImpl(this);
        mLoadingUtil=new LoadingUtil(getContext());

        rvUnLockList.setLayoutManager(new LinearLayoutManager(mContext));
        rvUnLockList.setItemAnimator(new DefaultItemAnimator());
        rvUnLockList.setAdapter();
        //添加分割线
//        rvUnLockList.addItemDecoration(new DividerItemDecoration(
//                getActivity(), DividerItemDecoration.HORIZONTAL_LIST));
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
        mLoadingUtil.showLoading();
    }

    @Override
    public void hideLoading() {
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
