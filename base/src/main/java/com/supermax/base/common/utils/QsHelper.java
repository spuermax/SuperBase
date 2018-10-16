package com.supermax.base.common.utils;

import com.supermax.base.QsApplication;

/*
 * @Author yinzh
 * @Date   2018/10/12 16:56
 * @Description
 */
public class QsHelper {
    private static QsHelper helper = new QsHelper();

    private QsApplication mApplication;

    public static QsHelper getInstance() {
        return helper;
    }

    public void init(QsApplication application) {
        mApplication = application;
    }

    public QsApplication getApplication() {
        return mApplication;
    }

    public ScreenHelper getScreenHelper() {
        return ScreenHelper.getInstance();
    }





}
