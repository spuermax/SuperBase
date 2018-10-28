package com.supermax.base.common.widget.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.supermax.base.R;
import com.supermax.base.common.utils.QsHelper;
import com.supermax.base.common.viewbind.ViewBindHelper;

/*
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewBindHelper.bindBundle(this, getArguments());
        getDialog().setCanceledOnTouchOutside(true);
        final Window window = getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams params = window.getAttributes();
            setAttribute(params);
            window.setAttributes(params);
        }

        View dialogView = getDialogView(inflater, container);
        ViewBindHelper.bindView(this, dialogView);
        return dialogView;
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

    protected void initData() {

    }

    protected void setAttribute(WindowManager.LayoutParams params) {
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }

    protected abstract int getDialogTheme();

    protected abstract View getDialogView(LayoutInflater inflater, ViewGroup viewGroup);

    public void show() {
        QsHelper.getInstance().commitDialogFragment(this);
    }


}
