package com.supermax.base.common.utils;

import android.content.Context;

/**
 * @Author yinzh
 * @Date 2018/11/19 16:02
 * @Description
 */
public class CommonUtils {

    public static int dip2px(Context context, double dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5);
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
