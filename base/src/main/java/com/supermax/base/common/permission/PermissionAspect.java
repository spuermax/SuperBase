package com.supermax.base.common.permission;

import android.app.Fragment;
import android.content.Context;

import com.supermax.base.common.permission.activity.PermissionActivity;
import com.supermax.base.common.permission.annotation.Permission;
import com.supermax.base.common.permission.annotation.PermissionCanceled;
import com.supermax.base.common.permission.annotation.PermissionDenied;
import com.supermax.base.common.permission.inter.IPermission;
import com.supermax.base.common.permission.model.CancelModel;
import com.supermax.base.common.permission.model.DenyModel;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/*
 * @Author yinzh
 * @Date   2018/10/18 16:22
 * @Description: 权限切面
 */
@Aspect
public class PermissionAspect {

    private static final String TAG = "PermissionAspect";

    private static final String PERMISSION_REQUEST_POINTCUT =
            "execution(@com.supermax.base.common.permission.annotation.Permission * *(..)) && @annotation(permission)";

    @Pointcut(value = PERMISSION_REQUEST_POINTCUT)
    private void requestPermission(Permission permission){
    }


    @Around("requestPermission(permission)")
    public void aroundJoinPoint(final ProceedingJoinPoint joinPoint, Permission permission){
        Context context = null;
        final Object object = joinPoint.getThis();
        if(object instanceof Context){
            context = (Context) object;
        } else if (object instanceof Fragment){
            context = ((Fragment) object).getActivity();
        } else if (object instanceof android.support.v4.app.Fragment){
            context = ((android.support.v4.app.Fragment) object).getActivity();
        }

        if(null == context && null == permission) return;

        PermissionActivity.PermissionRequest(context, permission.value(),
                permission.requestCode(), new IPermission() {
                    @Override
                    public void PermissionGranted() {

                        try {
                            joinPoint.proceed();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }

                    @Override
                    public void PermissionDenied(int requestCode, List<String> denyList) {
                        Class<?> clazz = object.getClass();
                        Method[] methods = clazz.getDeclaredMethods();
                        for (Method method :methods){
                            boolean isAnnotation = method.isAnnotationPresent(PermissionDenied.class);
                            if(isAnnotation){
                                method.setAccessible(true);
                                Class<?>[] types = method.getParameterTypes();
                                if(types == null && types.length != 1) return;
                                PermissionDenied annotation = method.getAnnotation(PermissionDenied.class);
                                if(annotation == null)return;
                                DenyModel bean = new DenyModel();
                                bean.setRequestCode(requestCode);
                                bean.setDenyList(denyList);
                                try {
                                    method.invoke(object, bean);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }

                            }

                        }

                    }

                    @Override
                    public void PermissionCanceled(int requestCode, List<String> denyList) {
                        Class<?> cls = object.getClass();
                        Method[] methods = cls.getDeclaredMethods();
                        if (methods == null || methods.length == 0) return;
                        for (Method method : methods) {
                            //过滤不含自定义注解PermissionCanceled的方法
                            boolean isHasAnnotation = method.isAnnotationPresent(PermissionCanceled.class);
                            if (isHasAnnotation) {
                                method.setAccessible(true);
                                //获取方法类型
                                Class<?>[] types = method.getParameterTypes();
                                if (types == null || types.length != 1) return;
                                //获取方法上的注解
                                PermissionCanceled aInfo = method.getAnnotation(PermissionCanceled.class);
                                if (aInfo == null) return;
                                //解析注解上对应的信息
                                CancelModel bean = new CancelModel();
                                bean.setRequestCode(requestCode);
                                bean.setDenyList(denyList);
                                try {
                                    method.invoke(object, bean);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });



    }


}
