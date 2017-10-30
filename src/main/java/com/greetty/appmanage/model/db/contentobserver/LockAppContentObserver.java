package com.greetty.appmanage.model.db.contentobserver;

import android.database.ContentObserver;
import android.os.Handler;
import android.os.Message;

/**
 * Created by Greetty on 2017/10/11.
 *
 * 内容监听者
 */
public class LockAppContentObserver extends ContentObserver {

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
        Message msg = new Message();
        msg.what=200;
        mHandler.sendMessage(msg);
    }
}
