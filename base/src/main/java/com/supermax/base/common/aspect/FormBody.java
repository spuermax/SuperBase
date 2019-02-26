package com.supermax.base.common.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Author yinzh
 * @Date   2018/10/19 17:38
 * @Description 表单参数注解，支持Map、String（必须是Json格式）和Object类型，Object类型会反射获取字段名和值
 */
@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface FormBody {
}
