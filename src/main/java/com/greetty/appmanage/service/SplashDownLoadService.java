package com.greetty.appmanage.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.greetty.appmanage.app.Constants;
import com.greetty.appmanage.http.HttpClient;
import com.greetty.appmanage.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Greetty on 2017/9/22.
 * <p>
 * 下载服务
 */

public class SplashDownLoadService extends IntentService {

    private static final String TAG = "SplashDownLoadService";


    public SplashDownLoadService() {
        super("SplashDownLoad");
    }

    public static void startDownLoadSplashImage(Context context, String action) {
        Intent intent = new Intent(context, SplashDownLoadService.class);
        intent.putExtra(Constants.EXTRA_DOWNLOAD, action);
        context.startService(intent);

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
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
                if (response.isSuccessful()) {
                    Log.e(TAG, "下载成功: ");
                    InputStream inputStream = response.body().byteStream();
                    String fileName = "Splash.png";
                    if (FileUtil.hasSdcard()) {
                        String path = FileUtil.getGlobalpath();
                        File dir = new File(path);
                        if (!dir.exists())
                            dir.mkdirs();

                        int len = 0;
                        byte[] buffer = new byte[1024];
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(path + fileName, false);
                            while ((len = inputStream.read(buffer)) > 0) {
                                fos.write(buffer, 0, len);
                            }
                            Log.e(TAG, "图片保存成功: ");
                            
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
//                            try {
//                                if (fos != null)
//                                    fos.flush();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
                            try {
                                if (fos != null)
                                    fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                if (inputStream != null)
                                    inputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                } else {
                    Log.e(TAG, "error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


}
