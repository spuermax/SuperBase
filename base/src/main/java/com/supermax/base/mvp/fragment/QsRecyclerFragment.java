package com.supermax.base.mvp.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.supermax.base.common.widget.recyclerview.HeaderFooterRecyclerView;
import com.supermax.base.mvp.presenter.QsPresenter;

import java.util.ArrayList;
import java.util.List;

/*
 * @Author yinzh
 * @Date   2018/10/15 15:09
 * @Description
 */
public abstract class QsRecyclerFragment <P extends QsPresenter, D> extends QsFragment<P> implements QsIRecyclerFragment<D>,  AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {



    public static final byte    TYPE_LIST          = 1 << 2;
    public static final byte    TYPE_GRID          = 2 << 2;
    public static final byte    TYPE_STAGGEREDGRID = 3 << 2;
    private final List<D> mList              = new ArrayList<>();

    private HeaderFooterRecyclerView mRecyclerView;
    private RecyclerView.Adapter     mRecyclerViewAdapter;
    private View headerView;
    private View                     footerView;


}
