package com.greetty.appmanage.model.impl;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import com.greetty.appmanage.model.LockAppModel;
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
public class LockAppModelImpl implements LockAppModel {

    private static final String TAG = "LockAppModelImpl";
    private static final int QUERY_SUCCESS = 101;
    private static final int QUERY_ERROR = 102;

    private List<AppInfo> lockAppInfos;
    private OnLockAPPListener mOnLockAPPListener;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case QUERY_SUCCESS:
                    mOnLockAPPListener.onSuccess(lockAppInfos);
                    Log.e(TAG, "加锁应用大小为: "+lockAppInfos.size());
                    break;
                case QUERY_ERROR:
                    mOnLockAPPListener.onError((Exception) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void loadLockApp(final Context context, final OnLockAPPListener onLockAPPListener) {
        Log.e(TAG, "++++++++++++加载数据:+++++++++++++");

        mOnLockAPPListener=onLockAPPListener;
        new Thread(){
            @Override
            public void run() {
                try {
                    AppLockDao appLockDao = new AppLockDao(context);
                    lockAppInfos = new ArrayList<>();
                    lockAppInfos.clear();
                    List<String> lockAPPs = appLockDao.queryAllLockApp();
                    Log.e(TAG, "lockAPPs size: "+lockAPPs.size());
                    List<AppInfo> allAppInfos = AppUtil.getAppInfos(context);
                    Log.e(TAG, "allAppInfos size: "+allAppInfos.size());
                    for (int i = 0; i < lockAPPs.size(); i++) {
                        for (AppInfo appinfo : allAppInfos) {
                            if (appinfo.getPackname().equals(lockAPPs.get(i)))
                                lockAppInfos.add(appinfo);
                        }
                    }
                    mHandler.sendEmptyMessage(QUERY_SUCCESS);
                } catch (Exception e) {
                    e.printStackTrace();
                    mHandler.obtainMessage(QUERY_ERROR,e).sendToTarget();
                }
            }
        }.start();
    }
}
