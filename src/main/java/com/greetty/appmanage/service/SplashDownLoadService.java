package com.greetty.appmanage.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.greetty.appmanage.app.Constants;
import com.greetty.appmanage.http.HttpClient;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Greetty on 2017/9/22.
 *
 * 下载服务
 */

public class SplashDownLoadService extends IntentService {

    private static final String TAG = "SplashDownLoadService";


    public SplashDownLoadService(String name) {
        super(name);
    }

    public static void startDownLoadSplashImage(Context context, String action) {
        Intent intent = new Intent(context, SplashDownLoadService.class);
        intent.putExtra(Constants.EXTRA_DOWNLOAD, action);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e(TAG, "++++++++++++++++++++++++++++++++++++");
        Log.e(TAG, "++++++++++++++++++++++++++++++++++++");
        Log.e(TAG, "++++++++++++++++++++++++++++++++++++");
        Log.e(TAG, "++++++++++++++++++++++++++++++++++++");
        Log.e(TAG, "++++++++++++++++++++++++++++++++++++");

        if (intent!=null){
            String action = intent.getStringExtra(Constants.EXTRA_DOWNLOAD);
            if (action.equals(Constants.DOWNLOAD_SPLASH)) {
                loadSplashNetDate();
            }
        }

    }

    /**
     * 加载闪屏页
     */
    private void loadSplashNetDate() {
        Call<ResponseBody> call = HttpClient.getInstance().downloadSplashImage();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Log.e(TAG, "下载成功: ");
                }else{
                    Log.e(TAG, "error code: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}
