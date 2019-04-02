package com.supermax.base.common.log;

import android.text.TextUtils;
import android.util.Log;

/**
 * @Author yinzh
 * @Date   2018/10/13 09:34
 * @Description
 */
public class L {
    private static boolean enable = false;

    private L() {
    }

    public static boolean isEnable() {
        return enable;
    }

    /**
     * 初始化操作
     *
     * @param isLogOpen 总开关，是否打开日志
     */
    public static void init(boolean isLogOpen) {
        enable = isLogOpen;
    }

    public static void i(String tag, String message) {
        println(Log.INFO, tag, message);
    }

    public static void v(String tag, String message) {
        println(Log.VERBOSE, tag, message);
    }

    public static void d(String tag, String message) {
        println(Log.DEBUG, tag, message);
    }

    public static void w(String tag, String message) {
        println(Log.WARN, tag, message);
    }

    public static void e(String tag, String message) {
        println(Log.ERROR, tag, message);
    }

    private static void println(int priority, String tag, String message) {
        if (enable && !TextUtils.isEmpty(message)) {
            Log.println(priority, tag, message);
        }
    }
}
