package com.greetty.appmanage.ui.activity;

import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.greetty.appmanage.R;
import com.greetty.appmanage.base.BaseActivity;
import com.greetty.appmanage.ui.fragment.HideAppFragment;
import com.greetty.appmanage.ui.fragment.LockedFragment;
import com.greetty.appmanage.ui.fragment.MyAppFragment;
import com.greetty.appmanage.ui.fragment.UnlockedFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import butterknife.BindView;

/**
 * created by Greetty at 2017/9/21 13:09
 * <p>
 * MainActivity 主页面
 */
public class MainActivity extends BaseActivity implements OnMenuTabClickListener {

    private static final String TAG = "MainActivity";

    private HideAppFragment mHideAppFragment;
    private LockedFragment mLockedFragment;
    private UnlockedFragment mUnlockedFragment;
    private MyAppFragment mMyAppFragment;
    private BottomBar mBottomBar;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected int initContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        mBottomBar = BottomBar.attach(this, savedInstanceState);

        // 千万注意：这个方法要用在 setItemsFromMenu 之前，也就是tab还没有设置之前要先调用，不然会报错。
        mBottomBar.noTabletGoodness();
        // 始终显示标题文字，在 setItems() 之前调用
        mBottomBar.useFixedMode();
        //添加初始Fragment
        defaultFragment(null == mUnlockedFragment ? mUnlockedFragment =
                mUnlockedFragment.newInstance() : mUnlockedFragment);
        mBottomBar.setItems(R.menu.bottombar_menu);
        mBottomBar.setOnMenuTabClickListener(this);
    }


    @Override
    public void onMenuTabSelected(@IdRes int menuItemId) {
        //单击事件 menuItemId 是 R.menu.bottombar_menu 中 item 的 id
        switch (menuItemId) {
            case R.id.bb_menu_unlocked:
                if (null == mUnlockedFragment) {
                    mUnlockedFragment = UnlockedFragment.newInstance();
                }
                replaceFragment(mUnlockedFragment);
                break;
            case R.id.bb_menu_locked:
                if (null == mLockedFragment) {
                    mLockedFragment = LockedFragment.newInstance();
                }
                replaceFragment(mLockedFragment);
                break;
            case R.id.bb_menu_hide:
                if (null == mHideAppFragment) {
                    mHideAppFragment = HideAppFragment.newInstance();
                }
                replaceFragment(mHideAppFragment);
                break;
            case R.id.bb_menu_me:
                if (null == mMyAppFragment) {
                    mMyAppFragment = MyAppFragment.newInstance();
                }
                replaceFragment(mMyAppFragment);
                break;
            default:
                break;
        }
        // 当点击不同按钮的时候，设置不同的颜色
        // 可以用以下三种方式来设置颜色.
        mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.colorPrimary));
        mBottomBar.mapColorForTab(1, ContextCompat.getColor(this, R.color.colorAccent));
        mBottomBar.mapColorForTab(2, ContextCompat.getColor(this, R.color.green));
        mBottomBar.mapColorForTab(3, ContextCompat.getColor(this, R.color.orange));
    }

    @Override
    public void onMenuTabReSelected(@IdRes int menuItemId) {
        //重选事件，当前已经选择了这个，又点了这个tab。微博点击首页刷新页面
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //保存BottomBar的状态
        mBottomBar.onSaveInstanceState(outState);
    }


    /**
     * 切换Fragment
     *
     * @param fragment Fragment
     */
    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();
        transaction.replace(R.id.main_fragment_layout, fragment);
//        transaction.hide();
        transaction.commit();

    }


    /**
     * 进入应用，加载默认Fragment->未加锁应用
     *
     * @param fragment Fragment
     */
    private void defaultFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();
        transaction.add(R.id.main_fragment_layout, fragment);
        transaction.commit();
    }


}
