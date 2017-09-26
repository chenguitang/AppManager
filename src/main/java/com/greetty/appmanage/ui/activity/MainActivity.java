package com.greetty.appmanage.ui.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.greetty.appmanage.R;
import com.greetty.appmanage.base.BaseActivity;
import com.greetty.appmanage.ui.adapter.ViewPagerAdapter;
import com.greetty.appmanage.ui.fragment.HideAppFragment;
import com.greetty.appmanage.ui.fragment.LockedFragment;
import com.greetty.appmanage.ui.fragment.MyAppFragment;
import com.greetty.appmanage.ui.fragment.UnlockedFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * created by Greetty at 2017/9/21 13:09
 *
 * MainActivity 主页面
 */


public class MainActivity extends BaseActivity implements OnMenuTabClickListener, ViewPager.OnPageChangeListener {

    private static final String TAG = "MainActivity";

    private HideAppFragment mHideAppFragment;
    private LockedFragment mLockedFragment;
    private UnlockedFragment mUnlockedFragment;
    private MyAppFragment mMyAppFragment;
    private BottomBar mBottomBar;
    private List<Fragment> mFragmentList;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

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
        mBottomBar.setItems(R.menu.bottombar_menu);

        mBottomBar.setFadingEdgeLength(10);

        initViewPager();
        initEvent();
    }


    private void initEvent() {
        mBottomBar.setOnMenuTabClickListener(this);
        viewPager.addOnPageChangeListener(this);
    }

    /**
     * 初始化Viewpager
     */
    private void initViewPager() {
        mFragmentList = new ArrayList<>();
        if (mUnlockedFragment == null)
            mUnlockedFragment = UnlockedFragment.newInstance();
        mFragmentList.add(0, mUnlockedFragment);
        if (mLockedFragment == null)
            mLockedFragment = LockedFragment.newInstance();
        mFragmentList.add(1, mLockedFragment);
        if (mHideAppFragment == null)
            mHideAppFragment = HideAppFragment.newInstance();
        mFragmentList.add(2, mHideAppFragment);
        if (mMyAppFragment == null)
            mMyAppFragment = MyAppFragment.newInstance();
        mFragmentList.add(3, mMyAppFragment);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mFragmentList));
    }


    @Override
    public void onMenuTabSelected(@IdRes int menuItemId) {
        //单击事件 menuItemId 是 R.menu.bottombar_menu 中 item 的 id
        switch (menuItemId) {
            case R.id.bb_menu_unlocked:
                viewPager.setCurrentItem(0);
                break;
            case R.id.bb_menu_locked:
                viewPager.setCurrentItem(1);
                break;
            case R.id.bb_menu_hide:
                viewPager.setCurrentItem(2);
                break;
            case R.id.bb_menu_me:
                viewPager.setCurrentItem(3);
                break;
            default:
                break;
        }
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mBottomBar.selectTabAtPosition(position, true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
