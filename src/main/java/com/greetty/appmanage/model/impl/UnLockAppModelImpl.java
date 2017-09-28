package com.greetty.appmanage.model.impl;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import com.greetty.appmanage.app.AppConfig;
import com.greetty.appmanage.model.LockAppModel;
import com.greetty.appmanage.model.UnLockAppModel;
import com.greetty.appmanage.model.db.dao.AppLockDao;
import com.greetty.appmanage.model.entity.AppInfo;
import com.greetty.appmanage.presenter.listener.OnLockAPPListener;
import com.greetty.appmanage.presenter.listener.OnUnLockAPPListener;
import com.greetty.appmanage.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Greetty on 2017/9/26.
 */
public class UnLockAppModelImpl implements UnLockAppModel {

    private static final int QUERY_SUCCESS = 99;
    private static final int QUERY_ERROR = 100;

    private List<AppInfo> unLockAppInfo;
    private OnUnLockAPPListener mOnUnLockAPPListener;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case QUERY_SUCCESS:
                    mOnUnLockAPPListener.onSuccess(unLockAppInfo);
                    break;
                case QUERY_ERROR:
                    mOnUnLockAPPListener.onError((Exception) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void loadLockApp(final Context context, final OnUnLockAPPListener onUnLockAPPListener) {
        mOnUnLockAPPListener=onUnLockAPPListener;
        new Thread() {
            @Override
            public void run() {
                try {
                    unLockAppInfo = AppUtil.getAppInfos(context);
                    mHandler.sendEmptyMessage(QUERY_SUCCESS);
                } catch (Exception e) {
                    e.printStackTrace();
                    mHandler.obtainMessage(QUERY_ERROR,e).sendToTarget();
                }
            }
        }.start();
    }
}
