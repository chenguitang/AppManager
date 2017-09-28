package com.greetty.appmanage.ui.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
public class UnLockAppAdapter extends RecyclerView.Adapter<UnLockAppAdapter.MyViewHolder> {

    private static final String TAG = "UnLockAppAdapter";
    private static final int REFRESH_DATA =111;

    private Context mContext;
    private List<AppInfo> listUnlockApp;
    private AppLockDao mAppDao;

    public UnLockAppAdapter(Context context, List<AppInfo> list) {
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
                        mAppDao.insert(listUnlockApp.get(position).getPackname());
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
    public UnLockAppAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_unlock, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final UnLockAppAdapter.MyViewHolder holder, final int position) {
        holder.iv_icon.setImageDrawable(listUnlockApp.get(position).getIcon());
        holder.tv_name.setText(AppUtil.ObjectAppend("应用名字：",
                listUnlockApp.get(position).getName()));
        holder.tv_version.setText(AppUtil.ObjectAppend("版本名称：",
                listUnlockApp.get(position).getVersionName()));
        holder.tv_version.setText(AppUtil.ObjectAppend("版本：",
                listUnlockApp.get(position).getVersionCode()));
        holder.tv_size.setText(AppUtil.ObjectAppend("应用大小",
                listUnlockApp.get(position).getName()));

        holder.iv_unlock.setOnClickListener(new View.OnClickListener() {
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
    public void ClickItemListener(UnLockAppAdapter.MyViewHolder holder,int position){
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 1.0f,
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
        @BindView(R.id.iv_unlock)
        ImageView iv_unlock;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private DataChangeListener mDataChangeListener;
    public void setDataChangeListener(DataChangeListener dataChangeListener){
        this.mDataChangeListener=dataChangeListener;
    }
    public interface DataChangeListener{
        void dataChange(int position);
    }
}
