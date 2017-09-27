package com.greetty.appmanage.model.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by Greetty on 2017/9/26.
 * <p>
 * 安装应用详情
 */
public class AppInfo {

    public AppInfo() {
    }

    public AppInfo(String apkpath, Drawable icon, String name, boolean inRom, long appSize,
                   boolean userApp, String packname, int versionCode,String versionName) {
        this.apkpath = apkpath;
        this.icon = icon;
        this.name = name;
        this.inRom = inRom;
        this.appSize = appSize;
        this.userApp = userApp;
        this.packname = packname;
        this.versionCode = versionCode;
        this.versionName=versionName;
    }

    /**
     * apk路径
     */
    private String apkpath;

    /**
     * 应用程序的图标
     */
    private Drawable icon;

    /**
     * 应用程序名称
     */
    private String name;

    /**
     * 应用程序安装的位置，true手机内存 ，false外部存储卡
     */
    private boolean inRom;

    /**
     * 应用程序的大小
     */
    private long appSize;

    /**
     * 是否是用户程序  true 用户程序 false 系统程序
     */
    private boolean userApp;

    /**
     * 应用程序的包名
     */
    private String packname;

    /**
     * 应用版本
     */
    private int versionCode;

    /**
     * 版本名字
     */
    private String versionName;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getApkpath() {
        return apkpath;
    }

    public void setApkpath(String apkpath) {
        this.apkpath = apkpath;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isInRom() {
        return inRom;
    }

    public void setInRom(boolean inRom) {
        this.inRom = inRom;
    }

    public long getAppSize() {
        return appSize;
    }

    public void setAppSize(long appSize) {
        this.appSize = appSize;
    }

    public boolean isUserApp() {
        return userApp;
    }

    public void setUserApp(boolean userApp) {
        this.userApp = userApp;
    }

    public String getPackname() {
        return packname;
    }

    public void setPackname(String packname) {
        this.packname = packname;
    }
}
