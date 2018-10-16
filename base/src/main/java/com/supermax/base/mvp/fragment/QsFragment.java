package com.supermax.base.mvp.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ViewAnimator;

import com.supermax.base.common.dialog.QsProgressDialog;
import com.supermax.base.common.utils.QsHelper;
import com.supermax.base.mvp.presenter.QsPresenter;

/*
 * @Author yinzh
 * @Date   2018/10/15 14:40
 * @Description
 */
public abstract class QsFragment <P extends QsPresenter> extends Fragment implements QsIFragment, View.OnTouchListener {

    private   P                presenter;
    private   boolean          hasInitData;
    protected QsProgressDialog mProgressDialog;
    protected ViewAnimator mViewAnimator;

    @Override public String initTag() {
        return QsHelper.getInstance().getApplication().isLogOpen() ? getClass().getSimpleName() : "QsFragment";
    }

    @Override public void setActivityTitle(Object value) {
        setActivityTitle(value, -1);
    }






}
