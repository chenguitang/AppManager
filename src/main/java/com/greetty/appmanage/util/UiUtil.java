package com.greetty.appmanage.util;

import android.content.Context;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Greetty on 2017/9/21.
 */

public class UIUtil {

    /**
     * 吐司方法
     *
     * @param context context
     * @param message 内容
     */
    public static void Toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
