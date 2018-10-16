package com.supermax.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.ViewAnimator;

import com.supermax.base.common.utils.QsHelper;
import com.supermax.base.common.viewbind.OnKeyDownListener;
import com.supermax.base.mvp.presenter.QsPresenter;

/*
 * @Author yinzh
 * @Date   2018/10/12 16:37
 * @Description
 */
public abstract class QsActivity<P extends QsPresenter> extends FragmentActivity implements QsIActivity {
    private P presenter;

    private ViewAnimator mViewAnimator;
    private boolean hasInitData;
    private OnKeyDownListener onKeyDownListener;


    @Override
    public String initTag() {
        return QsHelper.getInstance().getApplication().isLogOpen() ? getClass().getSimpleName() : "QsActivity";
    }

    @Override
    public int layoutId() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QsHelper.getInstance().getScreenHelper().pushActivity(this);
        QsHelper.getInstance().getApplication().onActivityCreate(this);



    }




}
