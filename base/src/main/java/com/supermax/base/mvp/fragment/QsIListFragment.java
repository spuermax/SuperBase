package com.supermax.base.mvp.fragment;

import android.widget.BaseAdapter;
import android.widget.ListView;

import com.supermax.base.mvp.adapter.QsListAdapterItem;

import java.util.List;

/*
 * @Author yinzh
 * @Date   2018/10/14 14:46
 * @Description
 */
public interface QsIListFragment<D> extends QsIFragment {

    QsListAdapterItem<D> getListAdapterItem(int type);

    int getHeaderLayout();

    int getFooterayout();

    int getTopLayout();

    int getBottomLayout();

    ListView getListView();

    int getViewTypeCount();

    int getItemViewType(int position);

    void setData(List<D> list);

    void setData(List<D> list, boolean showEmptyView);

    void addData(List<D> list);

    void addData(D d);

    void delete(int position);

    void delete(D d);

    void deleteAll();

    List<D> getData();

    D getData(int position);

    void updateAdapter(boolean showEmptyView);

    BaseAdapter onCreateAdapter();

    BaseAdapter getAdapter();

    boolean canListScrollUp();

    boolean canListScrollDown();

    boolean showContentViewWhenDataLoadingComplete();

    void onAdapterGetView(int position, int totalCount);

}
