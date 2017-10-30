package com.greetty.appmanage.ui.activity;

import android.app.ActivityManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.greetty.appmanage.R;
import com.greetty.appmanage.app.AppConfig;
import com.greetty.appmanage.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ComPareActivity extends AppCompatActivity {

    private static final String TAG = "ComPareActivity";
    private String packageName = "";
    private String PassWord;
    private String savePassWord = "123456";


    @BindView(R.id.et_password)
    EditText etPassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_com_pare);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_ok, R.id.btn_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                PassWord = etPassWord.getText().toString().trim();
                if (TextUtils.isEmpty(PassWord)) {
                    Toast.makeText(this, R.string.password_null, Toast.LENGTH_SHORT).show();
                    return;
                }

                //密码正确
//                if (PassWord.equals(savePassWord)) {
                sendBroadcast();
                finish();
//                } else {
//                    Toast.makeText(this, R.string.password_error, Toast.LENGTH_SHORT).show();
//                    etPassWord.setText("");
//                }
                break;
            case R.id.btn_cancel:
                finish();
                enterLauncher();
                break;
            default:
                break;
        }
    }

    /**
     * 密码正确，发送广播暂时停止监听该应用
     */
    private void sendBroadcast() {
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

    /**
     * 进入系统桌面
     */
    private void enterLauncher() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addCategory("android.intent.category.MONKEY");
        startActivity(intent);
    }


    /**
     * 监听物理按键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // 当用户输入后退健的时候，我们进入到桌面
            enterLauncher();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
