package com.greetty.appmanage.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.greetty.appmanage.R;
import com.greetty.appmanage.app.AppConfig;
import com.greetty.appmanage.app.Constants;
import com.greetty.appmanage.base.BaseActivity;
import com.greetty.appmanage.service.AppManagerService;
import com.greetty.appmanage.service.LockAppListenerService;
import com.greetty.appmanage.service.SplashDownLoadService;
import com.greetty.appmanage.service.WakeUpService;
import com.greetty.appmanage.util.FileUtil;
import com.greetty.appmanage.view.CircleProgressbar;

import java.io.File;

import butterknife.BindView;

/**
 * created by Greetty at 2017/9/21 13:09
 * <p>
 * SplashActivity 闪屏页
 */
public class SplashActivity extends BaseActivity implements
        CircleProgressbar.OnCountdownProgressListener, View.OnClickListener {

    private static final String TAG = "SplashActivity";
    @BindView(R.id.tv_red_skip)
    CircleProgressbar tvRedSkip;
    @BindView(R.id.cl_splash_bg)
    ConstraintLayout clSplashBg;
    @BindView(R.id.iv_splash_bg)
    ImageView ivSplashBg;

    private Handler mHandler = new Handler();
    private boolean isClick = false;  //是否点击跳过按钮
    private String fileName = "Splash.png";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int initContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initSplashImage();
        setTvRedSkip();
        startLockAppListenerService();
        initEvent();
        startImageDownLoad();
    }

    /**
     * 启动监听应用服务
     */
    private void startLockAppListenerService() {
        startService(new Intent(this,LockAppListenerService.class));
        startService(new Intent(this,AppManagerService.class));
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            startService(new Intent(this,WakeUpService.class));
        }
    }

    private void initSplashImage() {
        String SplashFile = FileUtil.getGlobalpath() + fileName;
        File file=new File(SplashFile);
        if (!file.exists()){
            ivSplashBg.setImageDrawable(SplashActivity.this.getResources().
                    getDrawable(R.mipmap.splash_1));
        }else{
            Bitmap bitmap = BitmapFactory.decodeFile(SplashFile);
            ivSplashBg.setImageBitmap(bitmap);
            Log.e(TAG, "加载下载好的闪屏页 ");
        }
    }

    /**
     * 设置倒计时进度条基本信息
     *
     * 启动倒计时
     */
    private void setTvRedSkip() {
        tvRedSkip.setOutLineColor(Color.parseColor("#FFFFFF"));  //外边框颜色
        tvRedSkip.setInCircleColor(Color.parseColor("#e4e3e3")); //内部圆颜色
        tvRedSkip.setProgressColor(Color.parseColor("#1BB079")); //进度条颜色
        tvRedSkip.setProgressLineWidth(5);
        tvRedSkip.setOutLineWidth(5);
        tvRedSkip.setProgressType(CircleProgressbar.ProgressType.COUNT);
        tvRedSkip.setTimeMillis(AppConfig.SPLASH_TIME);
        tvRedSkip.reStart();
    }

    /**
     * 下载最新闪屏页
     */
    private void startImageDownLoad() {
        SplashDownLoadService.startDownLoadSplashImage(this, Constants.DOWNLOAD_SPLASH);
    }

    /**
     * 初始化监听事件
     */
    private void initEvent() {
        tvRedSkip.setCountdownProgressListener(1, this);
        tvRedSkip.setOnClickListener(this);
    }

    /**
     * 进度条
     *
     * @param what     监听码比较
     * @param progress 倒计时进度
     */
    @Override
    public void onProgress(int what, int progress) {
        if (what == 1 && progress == 100 && !isClick) {
           startActivity(MainActivity.class,true);
            Log.d(TAG, "onProgress: ==" + progress);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_red_skip:
                isClick=true;
                tvRedSkip.stop();
                startActivity(MainActivity.class,true);
                Log.d(TAG, "click skip splash page");
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isClick=true;
    }
}
