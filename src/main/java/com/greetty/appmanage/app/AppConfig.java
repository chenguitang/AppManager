package com.greetty.appmanage.app;

/**
 * Created by Greetty on 2017/9/21.
 * <p>
 * 全局常量，应用设置
 */

public class AppConfig {

    /**
     * 闪屏页面展示时间
     * <p>
     * 单位：毫秒
     */
    public static final int SPLASH_TIME = 1000;

    /**
     * 连接按两下返回键退出应用的点击间隔
     * <p>
     * 单位：毫秒
     */
    public static final int ON_BACK_PRESS_DOUBLE_EXIT = 2000;

    /**
     * 加锁应用表
     */
    public static final String TABLE_LOCK_APP_NAME = "applock";

    /**
     * 隐藏应用表
     */
    public static final String TABLE_HIDE_APP_NAME = "apphide";

    /**
     * 动画执行时间
     *
     * 单位：毫秒
     */
    public static final int ITEM_ANIMATION_TIME = 300;


    /**
     * 暂时停止保护应用广播Action
     */
    public static final String  STOP_PROTECT_BROADCAST_ACATION="com.greetty.stopprotect";


}
