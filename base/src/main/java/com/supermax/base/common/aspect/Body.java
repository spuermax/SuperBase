package com.supermax.base.common.aspect;

import android.support.design.widget.Snackbar;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * @Author yinzh
 * @Date   2018/10/19 17:29
 * @Description
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Body {
    String mimeType() default "application/json; charset=UTF-8";
}
