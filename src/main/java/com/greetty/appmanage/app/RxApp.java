package com.greetty.appmanage.app;

import android.app.Application;
import android.content.Context;

import com.greetty.appmanage.util.CrashHandler;

/**
 * Created by Greetty on 2017/9/21.
 */

public class RxApp extends Application {

    private static Context mContext;


    /**
     * 获取上下文对象
     * @return  Context
     */
    public static Context getInstance() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        //异常崩溃信息捕获
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);

    }
}
