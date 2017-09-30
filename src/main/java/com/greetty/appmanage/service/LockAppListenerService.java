package com.greetty.appmanage.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.greetty.appmanage.ProcessConnection;
import com.greetty.appmanage.app.AppConfig;
import com.greetty.appmanage.model.db.dao.AppLockDao;
import com.greetty.appmanage.ui.activity.ComPareActivity;

import java.util.List;

/**
 * created by Greetty at 2017/9/28 16:34
 */
public class LockAppListenerService extends Service {

    private static final String TAG = "LockAppListenerService";
    private ActivityManager mActivityManager;
    private AppLockDao mAppLockDao;
    private List<String> mLockAppS;
    private boolean flag = true;
    //临时停止保护的包名
    private String tempStopProtectPackageName;
    private LockAppBroadcastReceiver mReceiver;

    private class LockAppBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d(TAG, "收到广播，广播action为： " + intent.getAction());
            if (intent.getAction().equals(AppConfig.STOP_PROTECT_BROADCAST_ACATION)) {
                //获取到停止保护的对象
                tempStopProtectPackageName = intent.getStringExtra("packageName");
            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                tempStopProtectPackageName = null;
                // 让狗休息
                flag = false;
            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                //让狗继续干活
                if (!flag) {
                    startLockAppListener();
                }
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        registerMyBroadcastReceiver();
        startLockAppListener();
    }

    /**
     * 注册广播
     */
    private void registerMyBroadcastReceiver() {
        mReceiver = new LockAppBroadcastReceiver();

        IntentFilter filter = new IntentFilter();
        //停止保护
        filter.addAction(AppConfig.STOP_PROTECT_BROADCAST_ACATION);
        //注册一个锁屏的广播
        /**
         * 当屏幕锁住的时候。狗就休息
         * 屏幕解锁的时候。让狗活过来
         */
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(mReceiver, filter);
    }

    /**
     * 监听应用
     */
    private void startLockAppListener() {
        Log.e(TAG, "LockAppListenerService start : ");
        mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        mAppLockDao = new AppLockDao(LockAppListenerService.this);
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    mLockAppS = mAppLockDao.queryAllLockApp();
                    flag = true;
                    while (flag) {
                        List<ActivityManager.RunningTaskInfo> tasks = mActivityManager.getRunningTasks(1);
                        //获取到最上面的进程
                        ActivityManager.RunningTaskInfo taskInfo = tasks.get(0);
                        //获取到最顶端应用程序的包名
                        String packageName = taskInfo.topActivity.getPackageName();
//                        Log.e(TAG, "top app packageName: " + packageName);
                        //线程休息一会
                        SystemClock.sleep(100);
                        //直接从数据库里面查找当前的数据
                        //这个可以优化。改成从内存当中寻找
                        if (!packageName.equals(tempStopProtectPackageName)) {
                            if (mLockAppS.contains(packageName)) {
                                //if(dao.find(packageName)){
                                //System.out.println("在程序锁数据库里面");

                                Intent intent = new Intent(LockAppListenerService.this, ComPareActivity.class);
                                /**
                                 * 需要注意：如果是在服务里面往activity界面跳的话。需要设置flag
                                 */
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                //停止保护的对象
                                intent.putExtra("packageName", packageName);

                                startActivity(intent);
                                Log.e(TAG, packageName + "是保护对象");
                            }
                        } else {
                            if (tempStopProtectPackageName != null && !packageName.equals(
                                    "com.greetty.appmanage") && !packageName.equals(tempStopProtectPackageName)) {
                                Log.e(TAG, "packageName: " + packageName);
                                Log.e(TAG, "设置tempStopProtectPackageName为空: ");
                                tempStopProtectPackageName = null;
                            }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        flag = false;
        unregisterReceiver(mReceiver);
        mReceiver = null;
    }
}
