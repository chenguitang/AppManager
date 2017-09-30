package com.greetty.appmanage.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.greetty.appmanage.R;
import com.greetty.appmanage.app.AppConfig;
import com.greetty.appmanage.base.BaseActivity;

import butterknife.OnClick;

public class ComPareActivity extends BaseActivity {

    private static final String TAG = "ComPareActivity";
    private String packageName="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_com_pare;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @OnClick(R.id.btn_compare_password)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_compare_password:
                sendBroadcast();
                finish();
                break;
            default:
                break;
        }
    }
    private void sendBroadcast(){
        Intent mIntent = getIntent();
        if (mIntent != null) {
            packageName = mIntent.getStringExtra("packageName");
        }

        Intent intent = new Intent();
        // 发送广播。停止保护
        intent.setAction(AppConfig.STOP_PROTECT_BROADCAST_ACATION);
        //现在停止保护
        intent.putExtra("packageName", packageName);
        sendBroadcast(intent);
    }
}
