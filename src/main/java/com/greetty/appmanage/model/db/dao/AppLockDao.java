package com.greetty.appmanage.model.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.greetty.appmanage.app.AppConfig;
import com.greetty.appmanage.model.db.DataBaseSQLHelper;
import com.greetty.appmanage.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Greetty on 2017/9/26.
 * .
 * 加锁应用数据库工具类
 */
public class AppLockDao {

    private DataBaseSQLHelper appLockHelper;

    public AppLockDao(Context context) {
        appLockHelper = new DataBaseSQLHelper(context, AppConfig.TABLE_LOCK_APP_NAME, null,
                AppUtil.getVersionCode(context));
    }

    /**
     * 插入数据
     *
     * @param packageName 加锁应用包名
     */
    public void insert(String packageName) throws Exception {
        SQLiteDatabase db = appLockHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("packagename", packageName);
        db.insert(AppConfig.TABLE_LOCK_APP_NAME, null, values);
        db.close();
    }

    /**
     * 删除数据
     *
     * @param packageName 删除加锁应用包名
     */
    public void delete(String packageName) throws Exception {
        SQLiteDatabase db = appLockHelper.getWritableDatabase();
        db.delete(AppConfig.TABLE_LOCK_APP_NAME, "packagename=?", new String[]{packageName});
        db.close();
    }

    /**
     * 查找应用是否已被加锁
     *
     * @param packageName 要查找的应用包名
     */
    public boolean queryAppLock(String packageName) throws Exception {
        SQLiteDatabase db = appLockHelper.getReadableDatabase();
        Cursor cursor = db.query(AppConfig.TABLE_LOCK_APP_NAME, null, "packagename=?",
                new String[]{packageName}, null, null, null);
        if (cursor.moveToNext()) {
            cursor.close();
            db.close();
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }

    /**
     * 查询所有已加锁应用
     */
    public List<String> queryAllLockApp() throws Exception {
        List<String> packageNames = new ArrayList<>();
        SQLiteDatabase db = appLockHelper.getReadableDatabase();
        Cursor cursor = db.query(AppConfig.TABLE_LOCK_APP_NAME, null, null,
                null, null, null, null);
        while (cursor.moveToNext()) {
            packageNames.add(cursor.getString(cursor.getColumnIndex("packagename")));
        }
        cursor.close();
        db.close();
        return packageNames;
    }

}
