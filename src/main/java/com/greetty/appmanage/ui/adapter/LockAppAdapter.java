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
import com.greetty.appmanage.util.AppUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Greetty on 2017/9/27.
 */
public class LockAppAdapter extends RecyclerView.Adapter<LockAppAdapter.MyViewHolder> {

    private Context mContext;
    private List<AppInfo> listUnlockApp;

    public LockAppAdapter(Context context, List<AppInfo> list) {
        this.mContext = context;
        this.listUnlockApp = list;
    }

    @Override
    public LockAppAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_lock, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(LockAppAdapter.MyViewHolder holder, int position) {
        holder.iv_icon.setImageDrawable(listUnlockApp.get(position).getIcon());
        holder.tv_name.setText(AppUtil.ObjectAppend("应用名字：",
                listUnlockApp.get(position).getName()));
        holder.tv_version.setText(AppUtil.ObjectAppend("版本名称：",
                listUnlockApp.get(position).getVersionName()));
        holder.tv_version.setText(AppUtil.ObjectAppend("版本：",
                listUnlockApp.get(position).getVersionCode()));
        holder.tv_size.setText(AppUtil.ObjectAppend("应用大小",
                listUnlockApp.get(position).getName()));
    }

    @Override
    public int getItemCount() {
        return listUnlockApp.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_icon)
        ImageView iv_icon;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_size)
        TextView tv_size;
        @BindView(R.id.tv_version)
        TextView tv_version;
        @BindView(R.id.iv_lock)
        ImageView iv_lock;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
