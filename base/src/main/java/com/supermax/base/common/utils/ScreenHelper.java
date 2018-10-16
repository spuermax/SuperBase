package com.supermax.base.common.utils;

import android.support.v4.app.FragmentActivity;

import com.supermax.base.common.log.L;

import java.util.ArrayList;
import java.util.Stack;

/*
 * @Author yinzh
 * @Date   2018/10/13 09:32
 * @Description 屏幕管理帮助类
 */
public class ScreenHelper {
    private static final String TAG = "ScreenHelper";

    private ScreenHelper() {
    }

    private static final ScreenHelper instance = new ScreenHelper();

    /**
     * FragmentActivity堆栈 单例模式
     */
    private final Stack<FragmentActivity> fragmentActivities = new Stack<>();
    private ArrayList<OnTaskChangeListener> listeners;

    static ScreenHelper getInstance() {
        return instance;
    }

    /**
     * 获取当前活动的activity
     */
    public FragmentActivity currentActivity() {
        if (fragmentActivities.size() == 0) {
            L.i(TAG, "Activity堆栈 size = 0");
            return null;
        }
        return fragmentActivities.peek();
    }

    /**
     * 入栈
     */
    public void pushActivity(FragmentActivity activity){
        if(activity != null){
            fragmentActivities.add(activity);
            onActivityCallback(activity);
            L.i(TAG, "activity入栈:" + activity.getClass().getSimpleName() + "   当前栈大小：" + fragmentActivities.size());
        } else {
            L.e(TAG, "pushActivity 传入的参数为空!");
        }
    }


    public void popActivity(FragmentActivity activity){
        if(activity != null){
            fragmentActivities.remove(activity);
            onActivityRemoveCallback(activity);
            L.i(TAG, "activity出栈:" + activity.getClass().getSimpleName() + "   当前栈大小：" + fragmentActivities.size());
        } else {
            L.e(TAG, "popActivity 传入的参数为空!");
        }

        if(fragmentActivities.size() <= 0){
//            QsHelper.getInstance().getImageHelper().clearMemoryCache();
//            QsHelper.getInstance().getThreadHelper().shutdown();
            if (listeners != null) listeners.clear();
        }
    }

    /**
     * 退出堆栈中所有Activity, 当前的Activity除外
     */
    public void popAllActivityExceptMain(Class clazz) {
        while (true) {
            FragmentActivity activity = currentActivity();
            if (activity == null) break;
            if (activity.getClass().equals(clazz)) break;
            popActivity(activity);
        }
    }

    public Stack<FragmentActivity> getActivityStack() {
        return fragmentActivities;
    }

    public void addOnTaskChangedListener(OnTaskChangeListener listener) {
        if (listener == null) return;
        if (listeners == null) listeners = new ArrayList<>(3);
        if (!listeners.contains(listener)) listeners.add(listener);
    }

    public void removeOnTaskChangedListener(OnTaskChangeListener listener) {
        if (listener == null) return;
        if (listeners != null) {
            listeners.remove(listener);
        }
    }

    public interface OnTaskChangeListener {
        void onActivityAdd(FragmentActivity activity);

        void onActivityRemove(FragmentActivity activity);
    }


    private void onActivityCallback(FragmentActivity activity) {
        if (listeners != null) {
            for (OnTaskChangeListener listener : listeners) {
                listener.onActivityAdd(activity);
            }
        }
    }

    private void onActivityRemoveCallback(FragmentActivity activity) {
        if (listeners != null) {
            for (OnTaskChangeListener listener : listeners) {
                listener.onActivityRemove(activity);
            }
        }
    }





}
