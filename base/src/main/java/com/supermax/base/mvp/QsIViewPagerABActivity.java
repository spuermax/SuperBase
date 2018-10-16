package com.supermax.base.mvp;

import android.support.v4.app.Fragment;
import android.view.View;

import com.supermax.base.common.widget.viewpager.QsViewPager;
import com.supermax.base.mvp.model.QsModelPager;

/*
 * @Author yinzh
 * @Date   2018/10/14 09:35
 * @Description
 */
public interface QsIViewPagerABActivity extends QsIABActivity{
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
