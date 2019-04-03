package com.supermax.base.mvp.fragment;

import com.supermax.base.common.widget.listview.LoadingFooter;

import java.util.List;

/**
 * @Author yinzh
 * @Date 2019/4/3 13:58
 * @Description
 */
public interface QsITopBottomLoadListFragment<D> {
    void onTopLoading();

    void onBottomLoading();

    void setTopLoadingState(LoadingFooter.State state);

    void setBottomLoadingState(LoadingFooter.State state);

    LoadingFooter.State getTopLoadingState();

    LoadingFooter.State getBottomLoadingState();

    void openTopLoading();

    void closeTopLoading();

    void openBottomLoading();

    void closeBottomLoading();

    void addTopData(List<D> list);

    void addBottomData(List<D> list);
}
