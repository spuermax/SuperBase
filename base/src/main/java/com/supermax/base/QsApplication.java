package com.supermax.base;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.LayoutRes;

import com.supermax.base.common.dialog.QsProgressDialog;
import com.supermax.base.common.log.L;
import com.supermax.base.common.utils.QsHelper;

/*
 * @Author yinzh
 * @Date   2018/10/12 16:32
 * @Description
 */
public abstract class QsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (isLogOpen()) L.init(true);
        QsHelper.getInstance().init(this);
    }

    public abstract boolean isLogOpen();




    public void onActivityCreate(Activity activity) {
    }

    public void onActivityStart(Activity activity) {
    }

    public void onActivityResume(Activity activity) {
    }

    public void onActivityPause(Activity activity) {
    }

    public void onActivityStop(Activity activity) {
    }

    public void onActivityDestroy(Activity activity) {
    }

    /**
     * 公共progressDialog
     */
    public QsProgressDialog getLoadingDialog() {
        return null;
    }

    public @LayoutRes int loadingLayoutId() {
        return 0;
    }

    public @LayoutRes int emptyLayoutId() {
        return 0;
    }

    public @LayoutRes int errorLayoutId() {
        return 0;
    }
}
