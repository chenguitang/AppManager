package com.greetty.appmanage.ui.fragment;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.greetty.appmanage.R;
import com.greetty.appmanage.app.RxApp;
import com.greetty.appmanage.base.BaseFragment;
import com.greetty.appmanage.model.db.contentobserver.LockAppContentObserver;
import com.greetty.appmanage.model.entity.AppInfo;
import com.greetty.appmanage.presenter.LockAppPresenter;
import com.greetty.appmanage.presenter.impl.LockAppPresenterImpl;
import com.greetty.appmanage.ui.adapter.LockAppAdapter;
import com.greetty.appmanage.ui.adapter.UnLockAppAdapter;
import com.greetty.appmanage.ui.view.LockAppView;
import com.greetty.appmanage.util.LoadingUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Greetty on 2017/9/23.
 * <p>
 * 已加锁应用
 */

public class LockedFragment extends BaseFragment implements LockAppView, UnLockAppAdapter.DataChangeListener {

    private static final String TAG = "LockedFragment";
    private static final String AUTHORITY = "com.greetty.appmanager";

    @BindView(R.id.rv_lock_list)
    RecyclerView rvLockList;

    private LockAppPresenter mLockAppPresenter;
    private LoadingUtil mLoadingUtil;
    private LockAppAdapter mLockAppAdapter;
    private Uri mUri;

    public static LockedFragment newInstance() {

        Bundle args = new Bundle();

        LockedFragment fragment = new LockedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 200:
                    if (!isUIVisible) {
//                        Toast.makeText(mContext, "LockedFragment 数据发生变化，请刷新数据", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "我再handle中加载了数据: ++++++++++");

                        mLockAppPresenter.getLockApp(mContext, false);
                    }
//                        Toast.makeText(mContext, "LockedFragment 我当前不可见，我要刷新数据", Toast.LENGTH_SHORT).show();

                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected int initContentView() {
        return R.layout.fragment_locked_app;
    }

    @Override
    protected void init() {
        mUri = Uri.parse("content://" + AUTHORITY);
        mLockAppPresenter = new LockAppPresenterImpl(this);
        mLoadingUtil = new LoadingUtil(getContext());

        rvLockList.setLayoutManager(new LinearLayoutManager(mContext));
        rvLockList.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initData() {
        ContentResolver resolver = RxApp.getInstance().getContentResolver();
        resolver.registerContentObserver(mUri, true, new LockAppContentObserver(mHandler));
        mLockAppPresenter.getLockApp(mContext, true);
        Log.e(TAG, "我再initData中加载了数据: ++++++++++");

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
        Log.e(TAG, "数据已改变，正在刷新数据。。。");
        if (mLockAppAdapter != null)
            mLockAppAdapter.notifyDataSetChanged();
    }

}
