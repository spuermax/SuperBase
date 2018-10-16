package com.supermax.base.common.utils;

import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;

import com.supermax.base.QsApplication;
import com.supermax.base.common.threadpoll.QsThreadPollHelper;

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

    public QsThreadPollHelper getThreadHelper() {
        return QsThreadPollHelper.getInstance();
    }

    public String getString(@StringRes int resId) {
        return getApplication().getString(resId);
    }

    public String getString(@StringRes int resId, Object... formatArgs) {
        return getApplication().getString(resId, formatArgs);
    }

}
