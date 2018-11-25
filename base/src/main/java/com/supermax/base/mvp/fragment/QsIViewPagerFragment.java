package com.supermax.base.mvp.fragment;

import android.support.v4.app.Fragment;
import android.view.View;

import com.supermax.base.common.widget.viewpager.PagerSlidingTabStrip;
import com.supermax.base.common.widget.viewpager.QsViewPager;
import com.supermax.base.mvp.adapter.QsViewPagerAdapter;
import com.supermax.base.mvp.model.QsModelPager;

/**
 * @Author yinzh
 * @Date   2018/10/15 14:09
 * @Description
 */
public interface QsIViewPagerFragment extends QsIFragment{
    void onPageScrollStateChanged(int state);

    void onPageSelected(View currentTabItem, View oldTabItem, int position, int oldPosition);

    void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    void initViewPager(QsModelPager[] modelPagers, int offScreenPageLimit);

    QsModelPager[] getModelPagers();

    void replaceViewPageItem(QsModelPager... modelPagers);

    void setIndex(int index, boolean bool);

    PagerSlidingTabStrip getTabs();

    QsViewPager getViewPager();

    QsViewPagerAdapter getViewPagerAdapter();

    int getTabItemLayout();

    void initTab(View tabItem, QsModelPager modelPager);

    Fragment getCurrentFragment();
}
