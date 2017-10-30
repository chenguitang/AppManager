package com.greetty.appmanage.model.db.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.greetty.appmanage.app.AppConfig;
import com.greetty.appmanage.model.db.DataBaseSQLHelper;
import com.greetty.appmanage.model.db.contentprovider.AppLockProvider;
import com.greetty.appmanage.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Greetty on 2017/9/26.
 * .
 * 加锁应用数据库工具类
 */
public class AppLockDao {

    private static final String TAG = "AppLockDao";
    private DataBaseSQLHelper appLockHelper;
    private static final String AUTHORITY = "com.greetty.appmanager";
    private Uri mUri;
    private ContentResolver cr;

    public AppLockDao(Context context) {
        appLockHelper = new DataBaseSQLHelper(context, AppConfig.TABLE_LOCK_APP_NAME, null,
                AppUtil.getVersionCode(context));
        mUri = Uri.parse("content://"+AUTHORITY);
        cr=context.getContentResolver();
    }

    /**
     * 插入数据
     *
     * @param packageName 加锁应用包名
     */
    public void insert(String packageName) throws Exception {
        ContentValues values = new ContentValues();
        values.put("packagename", packageName);
        cr.insert(mUri, values);
    }

    /**
     * 删除数据
     *
     * @param packageName 删除加锁应用包名
     */
    public void delete(String packageName) throws Exception {
        cr.delete(mUri,"packagename=?", new String[]{packageName});
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
