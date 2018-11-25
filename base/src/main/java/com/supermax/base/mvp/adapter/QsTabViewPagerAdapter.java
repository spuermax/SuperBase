package com.supermax.base.mvp.adapter;

import android.support.v4.app.FragmentManager;
import android.view.View;

import com.supermax.base.common.utils.QsHelper;
import com.supermax.base.common.widget.viewpager.PagerSlidingTabStrip;
import com.supermax.base.common.widget.viewpager.ViewPagerHelper;

/**
 * @Author yinzh
 * @Date   2018/10/17 10:05
 * @Description
 */
public class QsTabViewPagerAdapter extends QsViewPagerAdapter implements PagerSlidingTabStrip.CustomTabProvider {

    protected String initTag() {
        return QsHelper.getInstance().getApplication().isLogOpen() ? getClass().getSimpleName() : "QsTabViewPagerAdapter";
    }

    public QsTabViewPagerAdapter(FragmentManager fragmentManager, ViewPagerHelper helper) {
        super(fragmentManager, helper);
    }

    @Override public int getCustomTabView() {
        if (getPagerHelper().getViewPagerABActivity() != null) {
            if (getPagerHelper().getViewPagerABActivity().getTabItemLayout() == 0) throw new IllegalArgumentException("QsTabViewPagerAdapter 必须要有自定义布局！getTabItemLayout return>0");
            return getPagerHelper().getViewPagerABActivity().getTabItemLayout();
        }
        if (getPagerHelper().getViewPagerActivity() != null) {
            if (getPagerHelper().getViewPagerActivity().getTabItemLayout() == 0) throw new IllegalArgumentException("QsTabViewPagerAdapter  必须要有自定义布局！getTabItemLayout return>0");
            return getPagerHelper().getViewPagerActivity().getTabItemLayout();
        }
        if (getPagerHelper().getViewPagerFragment() != null) {
            if (getPagerHelper().getViewPagerFragment().getTabItemLayout() == 0) throw new IllegalArgumentException("QsTabViewPagerAdapter  必须要有自定义布局！getTabItemLayout return>0");
            return getPagerHelper().getViewPagerFragment().getTabItemLayout();
        }
        return 0;
    }

    @Override public void initTabsItem(View view, int position) {
        if (getPagerHelper().getViewPagerABActivity() != null) getPagerHelper().getViewPagerABActivity().initTab(view, getPagerHelper().getViewPagerData()[position]);
        if (getPagerHelper().getViewPagerActivity() != null) getPagerHelper().getViewPagerActivity().initTab(view, getPagerHelper().getViewPagerData()[position]);
        if (getPagerHelper().getViewPagerFragment() != null) getPagerHelper().getViewPagerFragment().initTab(view, getPagerHelper().getViewPagerData()[position]);
    }
 }
