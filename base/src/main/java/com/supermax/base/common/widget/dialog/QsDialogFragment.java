package com.supermax.base.common.widget.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.supermax.base.common.utils.QsHelper;
import com.supermax.base.common.viewbind.ViewBindHelper;

/**
 * @Author yinzh
 * @Date   2018/10/15 14:42
 * @Description
 */
public abstract class QsDialogFragment extends DialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, getDialogTheme());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().getDecorView().setPadding(0, 0, 0, 0);
            setAttribute(getDialog().getWindow().getAttributes());
        }
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewBindHelper.bindBundle(this, getArguments());
        if (getDialog() != null) {
            getDialog().setCanceledOnTouchOutside(true);
            getDialog().setCancelable(true);
        }
        View customView = inflater.inflate(layoutId(), null);
        ViewBindHelper.bindView(this, customView);
        return customView;
    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
    }

    protected String initTag() {
        return QsHelper.getInstance().getApplication().isLogOpen() ? getClass().getSimpleName() : "QsDialogFragment";
    }

    public void onViewClick(View view) {
    }


    protected void setAttribute(WindowManager.LayoutParams params) {
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }

    protected abstract int getDialogTheme();

    protected abstract View getDialogView(LayoutInflater inflater, ViewGroup viewGroup);

    protected abstract int layoutId();

    protected abstract void initData();

    public void show() {
        show(null);
    }

    public void show(Bundle bundle) {
        if (bundle != null) {
            setArguments(bundle);
        }
        QsHelper.getInstance().commitDialogFragment(this);
    }

}
