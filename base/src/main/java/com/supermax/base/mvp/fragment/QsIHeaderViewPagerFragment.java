package com.supermax.base.mvp.fragment;

import android.view.ViewGroup;

/**
 * @Author yinzh
 * @Date   2018/10/15 14:12
 * @Description
 */
public interface QsIHeaderViewPagerFragment extends QsIViewPagerFragment{

    int getHeaderLayout();

    ViewGroup createTabView();

}
