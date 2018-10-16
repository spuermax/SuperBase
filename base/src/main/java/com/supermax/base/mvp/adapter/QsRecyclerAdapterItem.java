package com.supermax.base.mvp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supermax.base.common.utils.QsHelper;
import com.supermax.base.common.viewbind.ViewBindHelper;

/*
 * @Author yinzh
 * @Date   2018/10/14 15:00
 * @Description:RecyclerView的Item封装类
 */
public abstract class QsRecyclerAdapterItem<T> {
    private View mItemView;
    private Context mParentContext;

    public QsRecyclerAdapterItem(LayoutInflater inflater, ViewGroup parent) {
        mItemView = inflater.inflate(itemViewLayoutId(), parent, false);
        ViewBindHelper bindHelper = new ViewBindHelper(this);
        bindHelper.bindView(mItemView);
        bindHelper.release();
        mParentContext = parent.getContext();
    }

    public String initTag() {
        return QsHelper.getInstance().getApplication().isLogOpen() ? getClass().getSimpleName() : "QsRecyclerAdapterItem";
    }

    protected abstract void onBindItemData(T data, int position, int totalCount);

    public Context getContext() {
        return mParentContext;
    }

    View getItemView() {
        return mItemView;
    }

    public void onViewClick(View view) {
    }

    protected abstract int itemViewLayoutId();
}
