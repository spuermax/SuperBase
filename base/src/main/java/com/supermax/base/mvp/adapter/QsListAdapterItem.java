package com.supermax.base.mvp.adapter;

import android.support.annotation.CallSuper;
import android.view.View;

import com.supermax.base.common.utils.QsHelper;
import com.supermax.base.common.viewbind.ViewBindData;
import com.supermax.base.common.viewbind.ViewBindHelper;

/*
 * @Author yinzh
 * @Date   2018/10/14 14:49
 * @Description: 适配器
 */
public abstract class QsListAdapterItem<T> {


    protected String initTag(){
        return QsHelper.getInstance().getApplication().isLogOpen() ? getClass().getSimpleName() : "QsListAdapterItem";
    }

    public abstract int getItemLayout();

    /**
     * 如果你的API允许使用者重写你的方法，但是呢，你又需要你自己的方法(父方法)在重写的时候也被调用，这时候你可以使用@CallSuper标注
     */
    @CallSuper
    public void init(View contentView){
       ViewBindHelper.bindView(this, contentView);
    }
    public abstract void bindData(T t, int position, int count);

    public void onViewClick(View view){}

}
