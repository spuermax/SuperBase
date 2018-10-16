package com.supermax.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ViewAnimator;

import com.supermax.base.R;
import com.supermax.base.common.dialog.QsProgressDialog;
import com.supermax.base.common.utils.QsHelper;
import com.supermax.base.common.viewbind.OnKeyDownListener;
import com.supermax.base.common.viewbind.ViewBindHelper;
import com.supermax.base.mvp.presenter.QsPresenter;

/*
 * @Author yinzh
 * @Date   2018/10/14 09:38
 * @Description
 */
public abstract class QsABActivity <P extends QsPresenter> extends AppCompatActivity implements QsIABActivity{

    private   P                 presenter;
    protected QsProgressDialog mProgressDialog;
    private ViewAnimator mViewAnimator;
    private   boolean           hasInitData;
    private OnKeyDownListener onKeyDownListener;

    @Override public String initTag() {
        return QsHelper.getInstance().getApplication().isLogOpen() ? getClass().getSimpleName() : "QsABActivity";
    }

    @Override public void setActivityTitle(Object value, int code) {

    }

    @Override public int layoutId() {
        return R.layout.super_framelayout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QsHelper.getInstance().getScreenHelper().pushActivity(this);
        QsHelper.getInstance().getApplication().onActivityCreate(this);
        ViewBindHelper bindHelper = new ViewBindHelper(this);
        bindHelper.bindBundle(getIntent().getExtras());

    }
}
