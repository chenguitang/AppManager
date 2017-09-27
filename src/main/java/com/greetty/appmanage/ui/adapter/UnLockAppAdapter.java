package com.greetty.appmanage.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.greetty.appmanage.R;
import com.greetty.appmanage.model.entity.AppInfo;

import java.util.List;

/**
 * Created by Greetty on 2017/9/27.
 */
public class UnLockAppAdapter extends RecyclerView.Adapter<UnLockAppAdapter.MyViewHolder> {

    private Context mContext;
    private List<AppInfo> listUnlockApp;

    public UnLockAppAdapter(Context context){
        this.mContext=context;
    }

    @Override
    public UnLockAppAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_unlock, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(UnLockAppAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return listUnlockApp.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_icon;
        private TextView tv_name;
        private TextView tv_version;
        private ImageView iv_lock;

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
