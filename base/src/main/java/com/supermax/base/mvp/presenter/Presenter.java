package com.supermax.base.mvp.presenter;


import android.icu.text.MessagePattern;
import android.support.design.widget.Snackbar;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.Proxy;

/*
 * @Author yinzh
 * @Date   2018/10/16 14:49
 * @Description
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Presenter {
    Class value();
}
