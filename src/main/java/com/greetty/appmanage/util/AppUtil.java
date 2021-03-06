package com.greetty.appmanage.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.greetty.appmanage.model.entity.AppInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * created by Greetty at 2017/9/26 12:00
 * <p>
 * 应用工具类
 */

public class AppUtil {


    /**
     * 得到软件版本号
     *
     * @param context 上下文
     * @return 当前版本Code
     */
    public static int getVersionCode(Context context) {
        int verCode = -1;
        try {
            String packageName = context.getPackageName();
            verCode = context.getPackageManager().getPackageInfo(packageName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verCode;
    }

    /**
     * 获取手机里面的所有的应用程序
     *
     * @param context 上下文
     * @return
     */
    public static List<AppInfo> getAppInfos(Context context) throws Exception {
        //得到一个java保证的 包管理器。
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packInfos = pm.getInstalledPackages(0);
        List<AppInfo> appinfos = new ArrayList<AppInfo>();
        for (PackageInfo packInfo : packInfos) {
            AppInfo appinfo = new AppInfo();
            String packname = packInfo.packageName;
            appinfo.setPackname(packname);
            Drawable icon = packInfo.applicationInfo.loadIcon(pm);
            appinfo.setIcon(icon);
            String appname = packInfo.applicationInfo.loadLabel(pm).toString();
            appinfo.setName(appname);
            //应用版本
            int versionCode = packInfo.versionCode;
            appinfo.setVersionCode(versionCode);
            //应用版本
            String versionName = packInfo.versionName;
            appinfo.setVersionName(versionName);
            //应用程序apk包的路径
            String apkpath = packInfo.applicationInfo.sourceDir;
            appinfo.setApkpath(apkpath);
            File file = new File(apkpath);
            long appSize = file.length();
            appinfo.setAppSize(appSize);
            //应用程序安装的位置。
            int flags = packInfo.applicationInfo.flags; //二进制映射  大bit-map
            if ((ApplicationInfo.FLAG_EXTERNAL_STORAGE & flags) != 0) {
                //外部存储
                appinfo.setInRom(false);
            } else {
                //手机内存
                appinfo.setInRom(true);
            }
            if ((ApplicationInfo.FLAG_SYSTEM & flags) != 0) {
                //系统应用
                appinfo.setUserApp(false);
            } else {
                //用户应用
                appinfo.setUserApp(true);
            }
            appinfos.add(appinfo);
            appinfo = null;
        }
        return appinfos;
    }

    public static String ObjectAppend(Object... objects) {
        StringBuffer sb = new StringBuffer();
        for (Object obj : objects)
            sb.append(obj);
        return sb.toString();
    }
}
