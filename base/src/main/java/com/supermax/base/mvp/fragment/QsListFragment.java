package com.supermax.base.mvp.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.supermax.base.common.widget.viewpager.headerpager.InnerScrollerContainer;
import com.supermax.base.mvp.presenter.QsPresenter;

import java.util.ArrayList;
import java.util.List;

/*
 * @Author yinzh
 * @Date   2018/10/15 14:58
 * @Description
 */
public abstract class QsListFragment <P extends QsPresenter, D> extends QsFragment<P> implements QsIListFragment<D>, AdapterView.OnItemLongClickListener,  AbsListView.OnScrollListener, InnerScrollerContainer {



    protected final List<D> mList = new ArrayList<>();

    private ListView mListView;
    private BaseAdapter mListAdapter;
    private View headerView;
    private View        footerView;



}

