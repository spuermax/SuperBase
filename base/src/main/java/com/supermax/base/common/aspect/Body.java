package com.supermax.base.common.aspect;

import android.support.design.widget.Snackbar;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author yinzh
 * @Date   2018/10/19 17:29
 * @Description 请求体注解，POST等请求时将被加入到http请求体中，支持String, byte[], File, Object;
 * 1，mimeType默认是JSON类型，所以当上传的格式非JSON时需要修改成对应的mimeType类型
 * 2，其中Object类型默认解析成Json串并加入请求体中
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Body {
    String mimeType() default "application/json; charset=UTF-8";
}
