package com.supermax.base.mvp.fragment;

import android.view.KeyEvent;

import com.supermax.base.mvp.QsIView;

/**
 * @Author yinzh
 * @Date   2018/10/14 14:23
 * @Description
 */
public interface  QsIFragment extends QsIView{
    void onActionBar();

    void setActivityTitle(Object value);

    void setActivityTitle(Object value, int code);

    boolean shouldInterceptTouchEvent();

    void smoothScrollToTop(boolean autpRefresh);

    boolean onKeyDown(int keyCode, KeyEvent event);

    void onBackPressed();

    void onFragmentSelectedInViewPager(boolean isSelected, int currentPosition, int totalCount);
}
