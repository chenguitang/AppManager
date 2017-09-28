package com.greetty.appmanage.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.greetty.appmanage.R;
import com.greetty.appmanage.app.RxApp;
import com.greetty.appmanage.base.BaseFragment;
import com.greetty.appmanage.model.entity.AppInfo;
import com.greetty.appmanage.presenter.LockAppPresenter;
import com.greetty.appmanage.presenter.impl.LockAppPresenterImpl;
import com.greetty.appmanage.ui.adapter.LockAppAdapter;
import com.greetty.appmanage.ui.adapter.UnLockAppAdapter;
import com.greetty.appmanage.ui.view.LockAppView;
import com.greetty.appmanage.util.LoadingUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Greetty on 2017/9/23.
 * <p>
 * 已加锁应用
 */

public class LockedFragment extends BaseFragment implements LockAppView, UnLockAppAdapter.DataChangeListener {

    private static final String TAG = "LockedFragment";

    @BindView(R.id.rv_lock_list)
    RecyclerView rvLockList;

    private LockAppPresenter mLockAppPresenter;
    private LoadingUtil mLoadingUtil;
    private LockAppAdapter mLockAppAdapter;


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
        mLockAppPresenter = new LockAppPresenterImpl(this);
        mLoadingUtil = new LoadingUtil(getContext());

        rvLockList.setLayoutManager(new LinearLayoutManager(mContext));
        rvLockList.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initData() {
        mLockAppPresenter.getLockApp(mContext);
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
        Toast.makeText(RxApp.getInstance(), "error：" + e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayLockApp(List<AppInfo> list) {
        mLockAppAdapter = new LockAppAdapter(RxApp.getInstance(), list);
        rvLockList.setAdapter(mLockAppAdapter);
        mLockAppAdapter.setDataChangeListener(this);
    }

    @Override
    public void dataChange(int position) {
        Log.d(TAG, "数据已改变，正在刷新数据。。。");
        if (mLockAppAdapter != null)
            mLockAppAdapter.notifyDataSetChanged();
    }
}
