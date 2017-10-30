package com.greetty.appmanage.ui.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.greetty.appmanage.R;
import com.greetty.appmanage.app.AppConfig;
import com.greetty.appmanage.model.db.dao.AppLockDao;
import com.greetty.appmanage.model.entity.AppInfo;
import com.greetty.appmanage.util.AppUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Greetty on 2017/9/27.
 */
public class LockAppAdapter extends RecyclerView.Adapter<LockAppAdapter.MyViewHolder> {

    private static final String TAG = "LockAppAdapter";
    private static final int REFRESH_DATA =222;

    private Context mContext;
    private List<AppInfo> listUnlockApp;
    private AppLockDao mAppDao;

    public LockAppAdapter(Context context, List<AppInfo> list) {
        this.mContext = context;
        this.listUnlockApp = list;
    }


    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case REFRESH_DATA:
                    if (mAppDao==null)
                        mAppDao=new AppLockDao(mContext);
                    int position= (int) msg.obj;
                    try {
                        Log.d(TAG, "position: "+position);
                        mAppDao.delete(listUnlockApp.get(position).getPackname());
                        listUnlockApp.remove(position);
                        mDataChangeListener.dataChange(position);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public LockAppAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_lock, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final LockAppAdapter.MyViewHolder holder, final int position) {
        holder.iv_icon.setImageDrawable(listUnlockApp.get(position).getIcon());
        holder.tv_name.setText(AppUtil.ObjectAppend("应用名字：",
                listUnlockApp.get(position).getName()));
        holder.tv_version.setText(AppUtil.ObjectAppend("版本名称：",
                listUnlockApp.get(position).getVersionName()));
        holder.tv_version.setText(AppUtil.ObjectAppend("版本：",
                listUnlockApp.get(position).getVersionCode()));
        holder.tv_size.setText(AppUtil.ObjectAppend("应用大小",
                listUnlockApp.get(position).getName()));


        holder.iv_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickItemListener(holder,position);
            }
        });
    }

    /**
     * 处理item被点击加锁
     * @param holder UnLockAppAdapter.MyViewHolder
     */
    public void ClickItemListener(LockAppAdapter.MyViewHolder holder,int position){
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0);
        // 设置动画时间
        translateAnimation.setDuration(AppConfig.ITEM_ANIMATION_TIME);
        // 开始动画
        holder.itemView.startAnimation(translateAnimation);
        Message message = new Message();
        message.what=REFRESH_DATA;
        message.obj=position;
        mHandler.sendMessageDelayed(message,AppConfig.ITEM_ANIMATION_TIME);
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

    private UnLockAppAdapter.DataChangeListener mDataChangeListener;
    public void setDataChangeListener(UnLockAppAdapter.DataChangeListener dataChangeListener){
        this.mDataChangeListener=dataChangeListener;
    }
    public interface DataChangeListener{
        void dataChange(int position);
    }

    /**
     * 更新未加锁应用数据
     * @param appInfoList 数据
     */
    public void updateUnlockAppData(List<AppInfo> appInfoList){
        listUnlockApp.clear();
        listUnlockApp=appInfoList;
        mDataChangeListener.dataChange(-1);
    }
}
