package com.greetty.appmanage.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.greetty.appmanage.service.AppManagerService;
import com.greetty.appmanage.service.LockAppListenerService;
import com.greetty.appmanage.service.WakeUpService;

/**
 * Created by Greetty on 2017/9/30.
 */
public class BootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context,LockAppListenerService.class));
        context.startService(new Intent(context,AppManagerService.class));
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            context.startService(new Intent(context,WakeUpService.class));
        }
    }
}
