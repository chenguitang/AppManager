package com.greetty.appmanage.model;

import android.content.Context;

import com.greetty.appmanage.presenter.listener.OnLockAPPListener;

/**
 * Created by Greetty on 2017/9/26.
 * <p>
 * 已加锁应用model接口
 */
public interface LockAppModel {

    void loadLockApp(Context context, OnLockAPPListener onLockAPPListener);

}
