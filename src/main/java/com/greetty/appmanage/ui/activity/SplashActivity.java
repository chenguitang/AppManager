package com.greetty.appmanage.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.greetty.appmanage.R;
import com.greetty.appmanage.app.AppConfig;
import com.greetty.appmanage.base.BaseActivity;
import com.greetty.appmanage.view.CircleProgressbar;

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

    private Handler mHandler = new Handler();
    private boolean isClick = false;  //是否点击跳过按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int initContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        tvRedSkip.setOutLineColor(Color.parseColor("#FFFFFF"));  //外边框颜色
        tvRedSkip.setInCircleColor(Color.parseColor("#e4e3e3")); //内部圆颜色
        tvRedSkip.setProgressColor(Color.parseColor("#1BB079")); //进度条颜色
        tvRedSkip.setProgressLineWidth(5);
        tvRedSkip.setOutLineWidth(5);
        tvRedSkip.setProgressType(CircleProgressbar.ProgressType.COUNT);
        tvRedSkip.setTimeMillis(AppConfig.SPLASH_TIME);
        tvRedSkip.reStart();
        initEvent();
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
            Log.e(TAG, "onProgress: ==" + progress);
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
