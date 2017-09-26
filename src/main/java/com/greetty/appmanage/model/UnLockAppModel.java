package com.greetty.appmanage.model;

import android.content.Context;

import com.greetty.appmanage.presenter.listener.OnUnLockAPPListener;

/**
 * Created by Greetty on 2017/9/26.
 * <p>
 * 未加锁应用model接口
 */
public interface UnLockAppModel {

    void loadLockApp(Context context, OnUnLockAPPListener onUnLockAPPListener);

}
