package com.supermax.base.common.utils;

import android.support.v4.app.FragmentActivity;

import com.supermax.base.common.log.L;

import java.util.ArrayList;
import java.util.Stack;

/**
 * @Author yinzh
 * @Date 2018/10/13 09:32
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
    public void pushActivity(FragmentActivity activity) {
        if (activity != null) {
            fragmentActivities.add(activity);
            onActivityAdded(activity);
            if (QsHelper.getInstance().getApplication().isLogOpen()) {
                L.i(TAG, "activity入栈:" + activity.getClass().getSimpleName() + "，当前栈大小：" + fragmentActivities.size());
            }
        } else {
            L.e(TAG, "pushActivity 传入的参数为空!");
        }
    }

    /**
     * 将元素置顶
     */
    public void bringActivityToTop(FragmentActivity activity) {
        if (activity == null) return;
        if (currentActivity() == activity) return;
        int index = fragmentActivities.indexOf(activity);
        if (index > 0) {
            FragmentActivity remove = fragmentActivities.remove(index);
            if (remove != null) fragmentActivities.add(remove);
            if (QsHelper.getInstance().getApplication().isLogOpen()) {
                L.i(TAG, "activity(" + activity.getClass().getSimpleName() + ")获取到焦点移到栈顶，当前栈大小：" + fragmentActivities.size());
            }
        }
    }

    public void popActivity(FragmentActivity activity) {
        if (activity != null) {
            activity.finish();
            fragmentActivities.remove(activity);
            onActivityRemoved(activity);
            if (QsHelper.getInstance().getApplication().isLogOpen()) {
                L.i(TAG, "activity出栈:" + activity.getClass().getSimpleName() + "，当前栈大小：" + fragmentActivities.size());
            }
        } else {
            L.e(TAG, "popActivity 传入的参数为空!");
        }

        if (fragmentActivities.size() <= 0) {
            QsHelper.getInstance().getImageHelper().clearMemoryCache();
            QsHelper.getInstance().getThreadHelper().shutdown();
            if (listeners != null) listeners.clear();
            L.i(TAG, "pop all Activity, app shutdown...");
        }
    }

    /**
     * 从栈顶向下 pop activity  直到当前Activity
     * 若如入参不为空而该栈中没有该Activity 则pop时留栈底一个Activity
     */
    public void popAllActivityExceptMain(Class clazz) {
        while (true) {
            if (clazz != null && fragmentActivities.size() <= 1) break;
            FragmentActivity activity = currentActivity();
            if (activity == null) break;
            if (activity.getClass().equals(clazz)) break;
            popActivity(activity);
        }
    }

    public void popAllActivity() {
        while (fragmentActivities.size() > 0) {
            FragmentActivity fragmentActivity = fragmentActivities.get(0);
            popActivity(fragmentActivity);
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


    private void onActivityAdded(FragmentActivity activity) {
        if (listeners != null) {
            for (OnTaskChangeListener listener : listeners) {
                listener.onActivityAdd(activity);
            }
        }
    }

    private void onActivityRemoved(FragmentActivity activity) {
        if (listeners != null) {
            for (OnTaskChangeListener listener : listeners) {
                listener.onActivityRemove(activity);
            }
        }
    }


}
