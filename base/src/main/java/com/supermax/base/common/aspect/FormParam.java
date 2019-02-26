package com.supermax.base.common.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Author yinzh
 * @Date 2019/2/26 18:02
 * @Description 表单参数注解，http请求时被注解的参数将被放入一级表单中,key为参数名，value为
 */
@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface FormParam {
    String value();
}
