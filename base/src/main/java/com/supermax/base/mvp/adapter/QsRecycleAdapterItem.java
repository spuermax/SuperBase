package com.supermax.base.mvp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supermax.base.common.utils.QsHelper;
import com.supermax.base.common.viewbind.ViewBindHelper;

/*
 * @Author yinzh
 * @Date   2018/10/17 10:18
 * @Description
 */
public abstract class QsRecycleAdapterItem <T>{
    private View mItemView;
    private Context mParentContext;

    public QsRecycleAdapterItem(LayoutInflater inflater, ViewGroup parent) {
        mItemView = inflater.inflate(itemViewLayoutId(), parent, false);
        ViewBindHelper bindHelper = new ViewBindHelper(this);
        bindHelper.bindView(mItemView);
        bindHelper.release();
        mParentContext = parent.getContext();
    }

    protected String initTag() {
        return QsHelper.getInstance().getApplication().isLogOpen() ? getClass().getSimpleName() : "QsRecycleAdapterItem";
    }

    protected abstract int itemViewLayoutId();

    protected abstract void onBindItemData(T data, int position, int totalCount);

    public Context getContext() {
        return mParentContext;
    }

    View getItemView() {
        return mItemView;
    }

    public void onViewClick(View view) {
    }
}
