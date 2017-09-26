package com.greetty.appmanage.model.impl;

import android.content.Context;

import com.greetty.appmanage.model.LockAppModel;
import com.greetty.appmanage.model.db.dao.AppLockDao;
import com.greetty.appmanage.model.entity.AppInfo;
import com.greetty.appmanage.presenter.listener.OnLockAPPListener;
import com.greetty.appmanage.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Greetty on 2017/9/26.
 */
public class LockAppModelImpl implements LockAppModel {

    @Override
    public void loadLockApp(Context context, OnLockAPPListener onLockAPPListener) {
        try {
            AppLockDao appLockDao = new AppLockDao(context);
            List<String> lockAPPs = appLockDao.queryAllLockApp();
            List<AppInfo> lockAppInfos = new ArrayList<>();
            List<AppInfo> allAppInfos = AppUtil.getAppInfos(context);
            for (int i = 0; i < lockAPPs.size(); i++) {
                for (AppInfo appinfo : allAppInfos) {
                    if (appinfo.getPackname().equals(lockAPPs.get(i)))
                        lockAppInfos.add(appinfo);
                }
            }
            onLockAPPListener.onSuccess(lockAppInfos);
        } catch (Exception e) {
            e.printStackTrace();
            onLockAPPListener.onError(e);
        }
    }
}
