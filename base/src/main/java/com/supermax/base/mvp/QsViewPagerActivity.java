package com.supermax.base.mvp;

import android.support.v7.app.AppCompatActivity;

import com.supermax.base.common.widget.viewpager.PagerSlidingTabStrip;
import com.supermax.base.common.widget.viewpager.QsViewPager;
import com.supermax.base.mvp.adapter.QsViewPagerAdapter;
import com.supermax.base.mvp.presenter.QsPresenter;

/*
 * @Author yinzh
 * @Date   2018/10/14 09:44
 * @Description
 */
public abstract class QsViewPagerActivity<P extends QsPresenter> extends AppCompatActivity implements QsIViewPagerActivity {

    private QsViewPagerAdapter adapter;
    private QsViewPager pager;
    private PagerSlidingTabStrip tabs;




}
