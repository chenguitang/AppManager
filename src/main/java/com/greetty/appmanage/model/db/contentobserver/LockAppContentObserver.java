package com.greetty.appmanage.model.db.contentobserver;

import android.database.ContentObserver;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by Greetty on 2017/10/11.
 *
 * 内容监听者
 */
public class LockAppContentObserver extends ContentObserver {

    private static final String TAG = "LockAppContentObserver";
    private Handler mHandler;

    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public LockAppContentObserver(Handler handler) {
        super(handler);
        this.mHandler=handler;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        //向handler发送消息,更新查询记录
        Log.e(TAG, "++++数据库以改变，通知监听者更新数据++++");

        Message msg = new Message();
        msg.what=200;
        mHandler.sendMessage(msg);
    }
}
