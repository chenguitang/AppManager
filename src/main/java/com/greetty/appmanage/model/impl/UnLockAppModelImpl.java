package com.greetty.appmanage.model.impl;

import android.content.Context;

import com.greetty.appmanage.app.AppConfig;
import com.greetty.appmanage.model.LockAppModel;
import com.greetty.appmanage.model.UnLockAppModel;
import com.greetty.appmanage.model.db.dao.AppLockDao;
import com.greetty.appmanage.model.entity.AppInfo;
import com.greetty.appmanage.presenter.listener.OnLockAPPListener;
import com.greetty.appmanage.presenter.listener.OnUnLockAPPListener;
import com.greetty.appmanage.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Greetty on 2017/9/26.
 */
public class UnLockAppModelImpl implements UnLockAppModel {

    @Override
    public void loadLockApp(Context context, OnUnLockAPPListener onUnLockAPPListener) {
        try {
            List<AppInfo> unLockAppInfo = AppUtil.getAppInfos(context);
            onUnLockAPPListener.onSuccess(unLockAppInfo);
        } catch (Exception e) {
            e.printStackTrace();
            onUnLockAPPListener.onError(e);
        }


    }
}
