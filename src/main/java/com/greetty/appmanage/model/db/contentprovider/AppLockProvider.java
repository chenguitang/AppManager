package com.greetty.appmanage.model.db.contentprovider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.greetty.appmanage.app.AppConfig;
import com.greetty.appmanage.model.db.DataBaseSQLHelper;
import com.greetty.appmanage.util.AppUtil;

/**
 * Created by Greetty on 2017/10/11.
 * <p>
 * 内容提供者
 */
public class AppLockProvider extends ContentProvider {

    private static final String TAG = "AppLockProvider";
    private DataBaseSQLHelper appLockHelper;
    private SQLiteDatabase db;
    // 若不匹配采用UriMatcher.NO_MATCH(-1)返回
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    // 匹配码
    private static final int CODE_NOPARAM = 100;
    private static final int CODE_PARAM = 101;

    @Override
    public boolean onCreate() {
        appLockHelper = new DataBaseSQLHelper(getContext(), AppConfig.TABLE_LOCK_APP_NAME, null,
                AppUtil.getVersionCode(getContext()));
        db = appLockHelper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor = db.query(AppConfig.TABLE_LOCK_APP_NAME, projection, selection,
                selectionArgs, null, null, sortOrder, null);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long insertNumber = db.insert(AppConfig.TABLE_LOCK_APP_NAME, null, values);
        if (insertNumber > 0) {
            ContentResolver cr = getContext().getContentResolver();
            //发出通知，所有注册在这个uri上的内容观察者都可以收到通知
            cr.notifyChange(uri, null);
        }
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleteNumber = db.delete(AppConfig.TABLE_LOCK_APP_NAME, selection, selectionArgs);
        if (deleteNumber > 0) {
            ContentResolver cr = getContext().getContentResolver();
            cr.notifyChange(uri, null);
        }
        return deleteNumber;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int updateNumber = db.update("book", values, selection, selectionArgs);
        if (updateNumber > 0) {
            ContentResolver cr = getContext().getContentResolver();
            cr.notifyChange(uri, null);
        }
        return updateNumber;
    }
}
