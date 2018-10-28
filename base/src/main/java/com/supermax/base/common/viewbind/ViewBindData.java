package com.supermax.base.common.viewbind;

import android.view.View;

import com.supermax.base.common.log.L;
import com.supermax.base.common.utils.QsHelper;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.common.viewbind.annotation.BindBundle;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author yinzh
 * @Date 2018/10/28 11:31
 * @Description: View层控件绑定，控件点击事件，Bundle值寻找
 */
public final class ViewBindData {

    String targetName = "ViewBindData";
    Map<Field, Bind> viewFieldMap;
    Map<Field, BindBundle> bundleFieldMap;
    Method onViewClickMethod;


    ViewBindData(Class<?> target) {
        if (QsHelper.getInstance().getApplication().isLogOpen()) {
            targetName = target.getSimpleName();
        }


        Field[] fields = target.getDeclaredFields();
        if (fields != null && fields.length > 0) {
            out:
            for (Field field : fields) {
                Annotation[] annotations = field.getAnnotations();
                if (annotations == null || annotations.length <= 0) continue;
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Bind) {
                        if (viewFieldMap == null) viewFieldMap = new HashMap<>();
                        field.setAccessible(true);
                        viewFieldMap.put(field, (Bind) annotation);
                        continue out;
                    } else if (annotation instanceof BindBundle) {
                        if (bundleFieldMap == null) bundleFieldMap = new HashMap<>();
                        field.setAccessible(true);
                        bundleFieldMap.put(field, (BindBundle) annotation);
                        continue out;
                    }
                }
            }
        }


        try {
            Method method = target.getDeclaredMethod("onViewClick", View.class);
            if (method != null) {
                method.setAccessible(true);
                this.onViewClickMethod = method;
            }
        } catch (NoSuchMethodException e) {
            L.i(targetName, "never override method:onViewClick(View view), When you need to add click events, you must rewrite it.");
        }
    }


}
