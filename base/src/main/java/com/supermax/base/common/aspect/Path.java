package com.supermax.base.common.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/*
 * @Author yinzh
 * @Date   2018/10/19 17:40
 * @Description
 */
@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface Path {

}
