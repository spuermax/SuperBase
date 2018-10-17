package com.supermax.base.mvp;

import android.view.KeyEvent;

import com.supermax.base.common.viewbind.OnKeyDownListener;

/*
 * @Author yinzh
 * @Date   2018/10/12 16:51
 * @Description
 */
public interface QsIActivity extends QsIView{
    boolean isTransparentStatusBar();

    boolean isBlackIconStatusBar();

    boolean isTransparentNavigationBar();

    boolean onKeyDown(KeyEvent event, int keyCode);

    void setOnKeyDownListener(OnKeyDownListener listener);


}
