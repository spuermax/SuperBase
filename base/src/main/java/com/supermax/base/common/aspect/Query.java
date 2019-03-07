package com.supermax.base.common.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Author yinzh
 * @Date   2018/10/19 17:43
 * @Description 被注解的参数将被当做查询参数添加到url上
 */
@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface Query {
    String value();
}
