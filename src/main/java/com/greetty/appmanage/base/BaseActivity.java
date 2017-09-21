package com.greetty.appmanage.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.greetty.appmanage.R;

import butterknife.ButterKnife;

/**
* created by Greetty at 2017/9/21 13:09
*
* BaseActivity
*/
public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(initContentView());
        ButterKnife.bind(this);
        init();
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
    protected abstract void init();

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
}
