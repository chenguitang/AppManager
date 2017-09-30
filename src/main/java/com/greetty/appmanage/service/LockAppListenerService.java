package com.greetty.appmanage.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.greetty.appmanage.ProcessConnection;
import com.greetty.appmanage.model.db.dao.AppLockDao;

import java.util.List;

/**
 * created by Greetty at 2017/9/28 16:34
 */
public class LockAppListenerService extends Service {

    private static final String TAG = "LockAppListenerService";
    private ActivityManager mActivityManager;
    private AppLockDao mAppLockDao;
    private List<String> mLockAppS;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "LockAppListenerService start : ");
        mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        mAppLockDao = new AppLockDao(LockAppListenerService.this);
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    mLockAppS = mAppLockDao.queryAllLockApp();

                    while (true) {
                        Thread.sleep(1000);

                        List<ActivityManager.RunningTaskInfo> tasks = mActivityManager.getRunningTasks(1);
                        //获取到最上面的进程
                        ActivityManager.RunningTaskInfo taskInfo = tasks.get(0);
                        //获取到最顶端应用程序的包名
                        String packageName = taskInfo.topActivity.getPackageName();

                        Log.d(TAG, "top app packageName: " + packageName);
                        //线程休息一会
                        SystemClock.sleep(300);
                        //直接从数据库里面查找当前的数据
                        //这个可以优化。改成从内存当中寻找
                        if (mLockAppS.contains(packageName)) {
                            //if(dao.find(packageName)){
                            //System.out.println("在程序锁数据库里面");

//                            Intent intent = new Intent(LockAppListenerService.this, EnterPwdActivity.class);
//                            /**
//                             * 需要注意：如果是在服务里面往activity界面跳的话。需要设置flag
//                             */
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            //停止保护的对象
//                            intent.putExtra("packageName", packageName);
//
//                            startActivity(intent);
                            Log.e(TAG, taskInfo.topActivity.getShortClassName() + "是保护对象");
                            Looper.prepare();
                            Toast.makeText(LockAppListenerService.this, taskInfo.topActivity.
                                    getShortClassName() + "是保护对象", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(1, new Notification());
        bindService(new Intent(this, AppManagerService.class), mServiceConnection, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ProcessConnection.Stub() {

            @Override
            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

            }
        };
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            Toast.makeText(LockAppListenerService.this,"与KeepLive服务建立连接",Toast.LENGTH_SHORT).show();
            Log.d(TAG, "与AppManagerService服务建立连接....");

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            startService(new Intent(LockAppListenerService.this, AppManagerService.class));
            bindService(new Intent(LockAppListenerService.this, AppManagerService.class), mServiceConnection, Context.BIND_IMPORTANT);

        }
    };
}
