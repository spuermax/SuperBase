package com.supermax.base;

import android.app.Activity;
import android.app.Application;

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
}
