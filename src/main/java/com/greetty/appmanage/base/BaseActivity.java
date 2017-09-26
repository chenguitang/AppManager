package com.greetty.appmanage.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.greetty.appmanage.app.AppConfig;
import com.greetty.appmanage.util.UIUtil;

import butterknife.ButterKnife;

/**
* created by Greetty at 2017/9/21 13:09
*
* BaseActivity
*/
public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    private long mPressedOne=0; //第一次按下返回键的系统时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(initContentView());
        ButterKnife.bind(this);
        init(savedInstanceState);
    }

    /**
     * 布局Layout的ID
     *
     * @return id int
     */
    protected abstract int initContentView();

    /**
     * 初始化数据
     */
    protected abstract void init(Bundle savedInstanceState);

    /**
     * 打开一个Activity
     *
     * @param clazz    要启动的Activity
     * @param isFinish 是否finish旧Activity
     */
    public void startActivity(Class clazz, boolean isFinish) {
        startActivity(new Intent(this, clazz));
        if (isFinish)
            finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.d(TAG, "touch home finish BaseActivity");
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0){
            if (System.currentTimeMillis()-mPressedOne> AppConfig.ON_BACK_PRESS_DOUBLE_EXIT){
                UIUtil.Toast(this,"再按一次退出程序");
                mPressedOne= System.currentTimeMillis();
            }else{
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
