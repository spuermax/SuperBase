package com.supermax.base.mvp;

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

    void setOnKeyDownListener(OnKeyDownListener listener);


}
