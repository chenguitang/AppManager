package com.greetty.appmanage.util;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.greetty.appmanage.R;


public class LoadingUtil {

    private Context mContext;
    private AlertDialog.Builder mLoadingBuilder;
    private AlertDialog mLoadingAlertDialog;


    public LoadingUtil(Context context) {
        this.mContext = context;
        mLoadingBuilder = new AlertDialog.Builder(mContext);
    }

    public void showLoading() {
        mLoadingAlertDialog = mLoadingBuilder.create();
        View mLoadingView = LayoutInflater.from(mContext).inflate(
                R.layout.dialog_save_loading, null);
        mLoadingAlertDialog.setView(mLoadingView);
        mLoadingAlertDialog.show();
        mLoadingAlertDialog.setCanceledOnTouchOutside(false);
        mLoadingAlertDialog.setCancelable(false);
    }

    public void dismissLoading() {
        if (mLoadingAlertDialog != null)
            mLoadingAlertDialog.dismiss();
        mLoadingAlertDialog = null;
    }

}
