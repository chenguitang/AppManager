package com.greetty.appmanage.util;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.greetty.appmanage.R;


public class LoadingUtil {

    private static final String TAG = "LoadingUtil";
    private Context mContext;
    private AlertDialog.Builder mLoadingBuilder;
    private AlertDialog mLoadingAlertDialog;
    private int showSum = 0;
    private int dismissSum = 0;

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
        showSum++;
        Log.e(TAG, "showSum: " + showSum);
        if (mLoadingAlertDialog==null){
            Log.e(TAG, "mLoadingAlertDialog==null");
        }else {
            Log.e(TAG, "mLoadingAlertDialog!=null");
        }


    }

    public void dismissLoading() {
        if (mLoadingAlertDialog != null) {
            mLoadingAlertDialog.dismiss();
            dismissSum++;
            Log.e(TAG, "dismissSum: " + dismissSum);
        }
        Log.e(TAG, "dismissLoading: ");
        
//        mLoadingAlertDialog.dismiss();
//        mLoadingAlertDialog = null;
    }

}
