package com.greetty.appmanage.presenter.listener;

import com.greetty.appmanage.model.entity.AppInfo;

import java.util.List;

/**
 * Created by Greetty on 2017/9/26.
 */
public interface OnLockAPPListener {

    /**
     * 成功时回调
     *
     * @param
     */
    void onSuccess(List<AppInfo> list);

    /**
     * 失败时回调，简单处理，没做什么
     */
    void onError(Exception e);
}
