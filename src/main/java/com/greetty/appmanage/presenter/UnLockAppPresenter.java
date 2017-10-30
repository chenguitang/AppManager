package com.greetty.appmanage.presenter;

import android.content.Context;

/**
 * Created by Greetty on 2017/9/26.
 */
public interface UnLockAppPresenter {

    /**
     * 获取未加锁APP
     */
    /**
     * 获取未加锁APP
     * @param context context
     * @param isShowLoading 是否显示加载进度条
     */
    void getLockApp(Context context, boolean isShowLoading);
}
