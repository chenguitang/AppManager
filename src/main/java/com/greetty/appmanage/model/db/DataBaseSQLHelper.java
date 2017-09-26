package com.greetty.appmanage.model.db;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.greetty.appmanage.app.AppConfig;

/**
 * Created by Greetty on 2017/9/26.
 * <p>
 * SQLiteOpenHelper
 */
public class DataBaseSQLHelper extends SQLiteOpenHelper {

    private static final String TAG = "DataBaseSQLHelper";

    public DataBaseSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                             int version) {
        super(context, name, factory, version);
    }

    public DataBaseSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                             int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + AppConfig.TABLE_LOCK_APP_NAME +
                "(_id integer primary key autoincrement," +
                "packagename varchar(40)" +
                ") "
        );
        Log.d(TAG, "加锁应用表已创建 。。。");

        db.execSQL("create table " + AppConfig.TABLE_HIDE_APP_NAME +
                "(_id integer primary key autoincrement," +
                "packagename varchar(40)" +
                ") "
        );
        Log.d(TAG, "隐藏应用表已创建 。。。");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "db onUpgrade");
    }

    /**
     * 关闭数据库
     * @param writeDatabase writeDatabase
     * @param readDatabase readDatabase
     * @param cursor cursor
     */
    public static void closeDatabase(SQLiteDatabase writeDatabase, SQLiteDatabase readDatabase, Cursor cursor) {
        if (cursor != null)
            cursor.close();
        if (writeDatabase != null)
            writeDatabase.close();
        if (readDatabase != null)
            readDatabase.close();
    }
    /**
     * 关闭数据库
     * @param writeOrReadDatabase writeOrReadDatabase
     * @param cursor cursor
     */
    public static void closeDatabase(SQLiteDatabase writeOrReadDatabase, Cursor cursor) {
        if (cursor != null)
            cursor.close();
        if (writeOrReadDatabase != null)
            writeOrReadDatabase.close();
    }
    /**
     * 关闭数据库
     * @param writeOrReadDatabase writeOrReadDatabase
     */
    public static void closeDatabase(SQLiteDatabase writeOrReadDatabase) {
        if (writeOrReadDatabase != null)
            writeOrReadDatabase.close();
    }
}
