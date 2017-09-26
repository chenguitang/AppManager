package com.greetty.appmanage.ui.view;

import com.greetty.appmanage.model.entity.AppInfo;

import java.util.List;

/**
 * Created by Greetty on 2017/9/26.
 *
 * 为加锁应用视图
 */
public interface UnLockAppView {

    /**
     * 显示加载中
     */
    void showLoading();

    /**
     * 隐藏加载中
     */
    void hideLoading();

    /**
     * 显示加载失败
     */
    void showFailure(Exception e);


    /**
     * 显示加锁应用
     */
    void displayLockApp(List<AppInfo> list);


}
