package com.supermax.base.common.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author yinzh
 * @Date   2018/10/16 15:05
 * @Description: 线程切点
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface ThreadPoint {
    ThreadType value();

}
