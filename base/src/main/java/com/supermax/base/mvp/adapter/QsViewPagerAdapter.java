package com.supermax.base.mvp.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.supermax.base.common.log.L;
import com.supermax.base.common.utils.QsHelper;
import com.supermax.base.common.widget.viewpager.InnerScrollerContainer;
import com.supermax.base.common.widget.viewpager.OuterPagerAdapter;
import com.supermax.base.common.widget.viewpager.OuterScroller;
import com.supermax.base.common.widget.viewpager.headerpager.QsViewPager;
import com.supermax.base.common.widget.viewpager.ViewPagerHelper;
import com.supermax.base.mvp.fragment.QsIFragment;
import com.supermax.base.mvp.model.QsModelPager;

/*
 * @Author yinzh
 * @Date   2018/10/14 09:51
 * @Description
 */
public class QsViewPagerAdapter extends PagerAdapter implements OuterPagerAdapter {
    private static final String TAG = "QsViewPagerAdapter";
    private int replacePosition = -1;        // 替换标识

    private OuterScroller mOuterScroller;
    private FragmentManager fragmentManager;
    private ViewGroup container;
    private ViewPagerHelper pagerHelper;

    protected String initTag() {
        return QsHelper.getInstance().getApplication().isLogOpen() ? getClass().getSimpleName() : "QsViewPagerAdapter";
    }

    public QsViewPagerAdapter(FragmentManager fragmentManager, ViewPagerHelper pagerHelper) {
        this.fragmentManager = fragmentManager;
        this.pagerHelper = pagerHelper;
    }


    public ViewPagerHelper getPagerHelper() {
        return pagerHelper;
    }

    public QsModelPager[] getViewPagerData() {
        return pagerHelper.getViewPagerData();
    }

    public QsViewPager getViewPager() {
        return pagerHelper.getViewPager();
    }

    /**
     * 设置数据
     */
    public void setModelPager(QsModelPager[] modelPager) {
        pagerHelper.setViewPagerData(modelPager);
    }

    /**
     * 替换
     */
    public void replaceViewPagerDatas(QsModelPager... modelPagers) {
        replacePosition = getViewPager().getCurrentItem();
        for (QsModelPager modelPager : modelPagers) {
            int position = modelPager.position;
            Fragment oldFragment = getViewPagerData()[position].fragment;
            if (oldFragment != null) container.removeView(oldFragment.getView());
            FragmentTransaction transaction = this.fragmentManager.beginTransaction();
            transaction.detach(oldFragment).commitAllowingStateLoss();
            getViewPagerData()[position] = modelPager;
        }
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    public QsModelPager[] getAllData() {
        return getViewPagerData();
    }


    public QsModelPager getData(int position) {
        return getViewPagerData()[position];
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return getViewPagerData()[position].title;
    }

    @Override
    public int getCount() {
        return getViewPagerData().length;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        this.container = container;
        if (position < getViewPagerData().length && getViewPagerData()[position].fragment != null)
            container.removeView(getViewPagerData()[position].fragment.getView());
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    /**
     * 生成
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        this.container = container;
        Fragment fragment = getViewPagerData()[position].fragment;
        if (!fragment.isAdded()) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
            transaction.add(fragment, fragment.getClass().getSimpleName() + position);
            transaction.commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();//如果你想立即执行，可以调用这个方法
            fragment.setHasOptionsMenu(false);//设置actionbar不执行
            if (replacePosition != -1) {
                if (getViewPagerData()[replacePosition].fragment instanceof QsIFragment) {
                    ((QsIFragment) getViewPagerData()[replacePosition].fragment).initDataWhenDelay();
                    ((QsIFragment) getViewPagerData()[replacePosition].fragment).onActionBar();
                }
                getViewPagerData()[replacePosition].fragment.onResume();
                replacePosition = -1;

            }
        }

        if (fragment.getView() == null) throw new NullPointerException("fragment has not view...");
        if (fragment.getView().getParent() == null) container.addView(fragment.getView());

        if (mOuterScroller != null && fragment instanceof InnerScrollerContainer) {
            L.i(TAG, "activity header viewpager...current fragment is :" + fragment.getClass().getSimpleName());
            ((InnerScrollerContainer) fragment).setMyOuterScroller(mOuterScroller, position);
        }
        return fragment.getView();
    }

    @Override
    public void notifyDataSetChanged() {
        pagerHelper.resetPageIndex();
        super.notifyDataSetChanged();
    }

    @Override
    public void setPageOuterScroller(OuterScroller outerScroller) {
        this.mOuterScroller = outerScroller;
    }
}
