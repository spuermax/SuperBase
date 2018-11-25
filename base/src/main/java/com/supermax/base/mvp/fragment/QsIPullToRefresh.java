package com.supermax.base.mvp.fragment;

import com.supermax.base.common.widget.listview.LoadingFooter;
import com.supermax.base.common.widget.ptr.PtrFrameLayout;
import com.supermax.base.common.widget.ptr.PtrUIHandler;

/**
 * @Author yinzh
 * @Date   2018/10/15 13:29
 * @Description
 */
public interface QsIPullToRefresh {
    PtrUIHandler getPtrUIHandlerView();

    void onRefresh();

    void onLoad();

    void startRefreshing();

    void stopRefreshing();

    void setLoadingState(LoadingFooter.State state);

    LoadingFooter.State getLoadingState();

    void openPullRefreshing();

    void closePullRefreshing();

    boolean canPullRefreshing();

    void openPullLoading();

    void closePullLoading();

    boolean canPullLoading();

    PtrFrameLayout getPtrFrameLayout();
}
