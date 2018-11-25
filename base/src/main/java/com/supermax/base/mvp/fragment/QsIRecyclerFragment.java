package com.supermax.base.mvp.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.supermax.base.common.widget.recyclerview.HeaderFooterRecyclerView;
import com.supermax.base.mvp.adapter.QsRecycleAdapterItem;

import java.util.List;

/**
 * @Author yinzh
 * @Date   2018/10/14 14:59
 * @Description
 */
public interface QsIRecyclerFragment<D> extends QsIFragment{

    QsRecycleAdapterItem<D> getRecycleAdapterItem (LayoutInflater inflater, ViewGroup parent, int type);

    int getHeaderLayout();

    int getFooterLayout();

    int getTopLayout();

    int getBottomLayout();

    HeaderFooterRecyclerView getRecyclerView();

    int getItemViewType(int position);

    void setData(List<D> list);

    void setData(List<D> list, boolean showEmptyView);

    void addData(List<D> list);

    void addData(D d);

    void addData(List<D> data, int position);

    void delete(int position);

    void delete(D d);

    void deleteAll();

    List<D> getData();

    D getData(int position);

    void updateAdapter(boolean showEmptyView);

    RecyclerView.Adapter onCreateAdapter();

    RecyclerView.Adapter getAdapter();

    boolean canListScrollUp();

    boolean canListScrollDown();

    boolean showContentViewWhenDataLoadingComplete();

    void onAdapterGetView(int position, int totalCount);

    void onScrollStateChanged(RecyclerView recyclerView, int newState);

    void onScrolled(RecyclerView recyclerView, int dx, int dy);

}
