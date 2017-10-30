package com.greetty.appmanage.ui.fragment;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
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
import com.greetty.appmanage.presenter.UnLockAppPresenter;
import com.greetty.appmanage.presenter.impl.UnLockAppPresenterImpl;
import com.greetty.appmanage.ui.adapter.UnLockAppAdapter;
import com.greetty.appmanage.ui.view.UnLockAppView;
import com.greetty.appmanage.util.LoadingUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Greetty on 2017/9/23.
 * <p>
 * 未加锁应用
 */

public class UnlockedFragment extends BaseFragment implements UnLockAppView, UnLockAppAdapter.DataChangeListener {

    private static final String TAG = "UnlockedFragment";
    private static final String AUTHORITY = "com.greetty.appmanager";

    @BindView(R.id.rv_unlock_list)
    RecyclerView rvUnLockList;

    private UnLockAppPresenter mUnLockAppPresenter;
    private LoadingUtil mLoadingUtil;
    private UnLockAppAdapter mUnLockAppAdapter;
    private List<AppInfo> mList;
    private Uri mUri;

    public static UnlockedFragment newInstance() {

        Bundle args = new Bundle();

        UnlockedFragment fragment = new UnlockedFragment();
        fragment.setArguments(args);
        return fragment;
    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 200:
                    if (!isUIVisible) {
//                        Toast.makeText(mContext, "UnlockedFragment 数据发生变化，请刷新数据 ", Toast.LENGTH_SHORT).show();
                        mUnLockAppPresenter.getLockApp(RxApp.getInstance(), false);
                    }
//                        Toast.makeText(mContext, "UnlockedFragment 我当前不可见，我要刷新数据", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int initContentView() {
        return R.layout.fragment_unlocked_app;
    }

    @Override
    protected void init() {
        mUri = Uri.parse("content://" + AUTHORITY);
        mUnLockAppPresenter = new UnLockAppPresenterImpl(this);
        mLoadingUtil = new LoadingUtil(getContext());
        mList = new ArrayList<>();

        rvUnLockList.setLayoutManager(new LinearLayoutManager(mContext));
        rvUnLockList.setItemAnimator(new DefaultItemAnimator());

        //添加分割线
//        rvUnLockList.addItemDecoration(new DividerItemDecoration(
//                getActivity(), DividerItemDecoration.HORIZONTAL_LIST));
    }

    @Override
    protected void initData() {
        ContentResolver resolver = RxApp.getInstance().getContentResolver();
        resolver.registerContentObserver(mUri, true, new LockAppContentObserver(mHandler));
        mUnLockAppPresenter.getLockApp(RxApp.getInstance(), true);
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
        Toast.makeText(RxApp.getInstance(), "出错了：" + e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayLockApp(List<AppInfo> list) {
        mUnLockAppAdapter = new UnLockAppAdapter(RxApp.getInstance(), list);
        rvUnLockList.setAdapter(mUnLockAppAdapter);
        mUnLockAppAdapter.setDataChangeListener(this);
    }

    @Override
    public void dataChange(int position) {
        Log.d(TAG, "数据已改变，正在刷新数据。。。");
        if (mUnLockAppAdapter != null)
            mUnLockAppAdapter.notifyDataSetChanged();
    }
}
